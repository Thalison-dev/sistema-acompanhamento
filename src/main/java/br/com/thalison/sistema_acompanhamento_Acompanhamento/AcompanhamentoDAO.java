package br.com.thalison.sistema_acompanhamento_Acompanhamento;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import br.com.thalison.sistema_acompanhamento.Conexao;
import br.com.thalison.sistema_acompanhamento_Pedagogo.Pedagogo;
import br.com.thalison.sistema_acompanhamento_Psicologo.Psicologo;
import br.com.thalison.sistema_acompanhamento_ResponsavelLegal.ResponsavelLegal;
import br.com.thalison.sistema_acompanhamento_aluno.Aluno;

/**
 * Camada de persistência que reconstrói e salva o agregado {@link Acompanhamento}
 * respeitando a estrutura de {@code bancodedados.sql}.
 */
public class AcompanhamentoDAO {

    private static final String SQL_SELECT_COMPLETO = """
            SELECT ac.acompanhamentoId,
                   ac.alunoId,
                   ac.dataInicioAcompanhamento,
                   ac.dataFimAcompanhamento,
                   ac.objetivoAcompanhamento,
                   ac.observacaoAcompanhamento,
                   ac.statusAcompanhamento,
                   a.nomeAluno,
                   a.matriculaAluno,
                   a.anoEscolarAluno,
                   a.statusAluno,
                   a.responsavelId,
                   pr.nome AS nomeResponsavel,
                   pr.telefone AS telefoneResponsavel,
                   pr.email AS emailResponsavel,
                   rl.parentescoLegal,
                   COALESCE(GROUP_CONCAT(DISTINCT n.tipoNeurodivergencia SEPARATOR ', '), '-') AS tiposNeurodivergencia
              FROM acompanhamento ac
             INNER JOIN aluno a ON ac.alunoId = a.alunoId
              LEFT JOIN responsavel_legal rl ON a.responsavelId = rl.responsavelLegal
              LEFT JOIN pessoa pr ON rl.responsavelLegal = pr.id
              LEFT JOIN neurodivergencia n ON a.alunoId = n.alunoId
            """;

    private static final String SQL_GROUP_BY = """
             GROUP BY ac.acompanhamentoId,
                      ac.alunoId,
                      ac.dataInicioAcompanhamento,
                      ac.dataFimAcompanhamento,
                      ac.objetivoAcompanhamento,
                      ac.observacaoAcompanhamento,
                      ac.statusAcompanhamento,
                      a.nomeAluno,
                      a.matriculaAluno,
                      a.anoEscolarAluno,
                      a.statusAluno,
                      a.responsavelId,
                      pr.nome,
                      pr.telefone,
                      pr.email,
                      rl.parentescoLegal
            """;

    public Acompanhamento salvar(Acompanhamento acompanhamento) {
        String sqlInsert = """
                INSERT INTO acompanhamento (
                    alunoId,
                    dataInicioAcompanhamento,
                    dataFimAcompanhamento,
                    objetivoAcompanhamento,
                    observacaoAcompanhamento,
                    statusAcompanhamento
                ) VALUES (?, ?, ?, ?, ?, ?)
                """;

        String sqlVincularResponsavel = "UPDATE aluno SET responsavelId = ? WHERE alunoId = ?";

        try (Connection conn = Conexao.conectar()) {
            conn.setAutoCommit(false);
            try {
                Optional<ResponsavelLegal> respOpt = acompanhamento.getResponsavelLegal();
                if (respOpt.isPresent()) {
                    try (PreparedStatement psAluno = conn.prepareStatement(sqlVincularResponsavel)) {
                        psAluno.setInt(1, respOpt.get().getId());
                        psAluno.setInt(2, acompanhamento.getAluno().getId());
                        if (psAluno.executeUpdate() == 0) {
                            throw new SQLException("Aluno não encontrado. ID: " + acompanhamento.getAluno().getId());
                        }
                    }
                }

                try (PreparedStatement ps = conn.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS)) {
                    PeriodoAcompanhamento periodo = acompanhamento.getPeriodo();
                    ps.setInt(1, acompanhamento.getAluno().getId());
                    ps.setDate(2, Date.valueOf(periodo.dataInicio()));
                    setNullableDate(ps, 3, periodo.dataFim());
                    ps.setString(4, acompanhamento.getObjetivo());
                    setNullableString(ps, 5, ObservacaoCodec.serializar(acompanhamento));
                    ps.setString(6, acompanhamento.getStatus().getDescricao());

                    ps.executeUpdate();
                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (!rs.next()) {
                            throw new SQLException("Falha ao recuperar o ID gerado para o acompanhamento.");
                        }
                        acompanhamento.atribuirId(rs.getInt(1));
                    }
                }

                conn.commit();
                return acompanhamento;
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Erro ao persistir acompanhamento.", e);
        }
    }

    public boolean atualizar(Acompanhamento acompanhamento) {
        if (acompanhamento.getId() == null) {
            throw new IllegalArgumentException("Não é possível atualizar um acompanhamento sem ID.");
        }

        String sqlAcompanhamento = """
                UPDATE acompanhamento
                   SET dataInicioAcompanhamento = ?,
                       dataFimAcompanhamento = ?,
                       objetivoAcompanhamento = ?,
                       observacaoAcompanhamento = ?,
                       statusAcompanhamento = ?
                 WHERE acompanhamentoId = ?
                """;

        String sqlResponsavel = "UPDATE aluno SET responsavelId = ? WHERE alunoId = ?";

        try (Connection conn = Conexao.conectar()) {
            conn.setAutoCommit(false);
            try {
                Optional<ResponsavelLegal> respOpt = acompanhamento.getResponsavelLegal();
                if (respOpt.isPresent()) {
                    try (PreparedStatement psResp = conn.prepareStatement(sqlResponsavel)) {
                        psResp.setInt(1, respOpt.get().getId());
                        psResp.setInt(2, acompanhamento.getAluno().getId());
                        psResp.executeUpdate();
                    }
                }

                boolean atualizado;
                try (PreparedStatement ps = conn.prepareStatement(sqlAcompanhamento)) {
                    PeriodoAcompanhamento periodo = acompanhamento.getPeriodo();
                    ps.setDate(1, Date.valueOf(periodo.dataInicio()));
                    setNullableDate(ps, 2, periodo.dataFim());
                    ps.setString(3, acompanhamento.getObjetivo());
                    setNullableString(ps, 4, ObservacaoCodec.serializar(acompanhamento));
                    ps.setString(5, acompanhamento.getStatus().getDescricao());
                    ps.setInt(6, acompanhamento.getId());
                    atualizado = ps.executeUpdate() > 0;
                }

                conn.commit();
                return atualizado;
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Erro ao atualizar acompanhamento ID " + acompanhamento.getId(), e);
        }
    }

    public Optional<Acompanhamento> buscarPorId(int id) {
        String sql = SQL_SELECT_COMPLETO + " WHERE ac.acompanhamentoId = ? " + SQL_GROUP_BY;

        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapear(rs));
                }
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataAccessException("Erro ao buscar acompanhamento ID " + id, e);
        }
    }

    public List<Acompanhamento> listarTodos() {
        String sql = SQL_SELECT_COMPLETO + SQL_GROUP_BY + " ORDER BY ac.acompanhamentoId DESC";
        return executarConsulta(sql, null);
    }

    public List<Acompanhamento> listarPorAluno(int alunoId) {
        String sql = SQL_SELECT_COMPLETO + " WHERE ac.alunoId = ? " + SQL_GROUP_BY + " ORDER BY ac.acompanhamentoId DESC";
        return executarConsulta(sql, ps -> ps.setInt(1, alunoId));
    }

    public boolean excluir(int id) {
        String sql = "DELETE FROM acompanhamento WHERE acompanhamentoId = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataAccessException("Erro ao excluir acompanhamento ID " + id, e);
        }
    }

    private List<Acompanhamento> executarConsulta(String sql, StatementConfigurer configurer) {
        List<Acompanhamento> lista = new ArrayList<>();

        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            if (configurer != null) {
                configurer.configure(ps);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapear(rs));
                }
            }
            return lista;
        } catch (SQLException e) {
            throw new DataAccessException("Erro ao consultar acompanhamentos.", e);
        }
    }

    private Acompanhamento mapear(ResultSet rs) throws SQLException {
        Aluno aluno = new Aluno();
        aluno.setId(rs.getInt("alunoId"));
        aluno.setNome(rs.getString("nomeAluno"));
        aluno.setMatricula(rs.getString("matriculaAluno"));
        aluno.setAnoEscolar(rs.getString("anoEscolarAluno"));
        aluno.setStatus(rs.getString("statusAluno"));
        aluno.setTipoNeurodivergencia(rs.getString("tiposNeurodivergencia"));

        ResponsavelLegal responsavel = null;
        int respId = rs.getInt("responsavelId");
        if (!rs.wasNull() && respId > 0) {
            responsavel = new ResponsavelLegal();
            responsavel.setId(respId);
            responsavel.setNome(rs.getString("nomeResponsavel"));
            responsavel.setTelefone(rs.getString("telefoneResponsavel"));
            responsavel.setEmail(rs.getString("emailResponsavel"));
            responsavel.setParentesco(rs.getString("parentescoLegal"));
        }

        LocalDate inicio = rs.getDate("dataInicioAcompanhamento").toLocalDate();
        Date fimSql = rs.getDate("dataFimAcompanhamento");
        LocalDate fim = (fimSql != null) ? fimSql.toLocalDate() : null;
        PeriodoAcompanhamento periodo = new PeriodoAcompanhamento(inicio, fim);

        Acompanhamento.Builder builder = Acompanhamento.builder(aluno, periodo, rs.getString("objetivoAcompanhamento"))
                .id(rs.getInt("acompanhamentoId"))
                .responsavelLegal(responsavel)
                .status(StatusAcompanhamento.fromValor(rs.getString("statusAcompanhamento")));

        ObservacaoCodec.desserializar(rs.getString("observacaoAcompanhamento"), builder);
        return builder.build();
    }

    private void setNullableDate(PreparedStatement ps, int index, LocalDate data) throws SQLException {
        if (data != null) {
            ps.setDate(index, Date.valueOf(data));
        } else {
            ps.setNull(index, Types.DATE);
        }
    }

    private void setNullableString(PreparedStatement ps, int index, String valor) throws SQLException {
        if (valor != null && !valor.isBlank()) {
            ps.setString(index, valor);
        } else {
            ps.setNull(index, Types.VARCHAR);
        }
    }

    @FunctionalInterface
    private interface StatementConfigurer {
        void configure(PreparedStatement ps) throws SQLException;
    }

    /**
     * Codec interno que preserva os pareceres estruturados e observações gerais dentro de {@code observacaoAcompanhamento}.
     */
    private static final class ObservacaoCodec {

        private static final String TAG_PSICOLOGO = "===PARECER_PSICOLOGO===";
        private static final String TAG_PEDAGOGO = "===PARECER_PEDAGOGO===";
        private static final String TAG_GERAL = "===OBSERVACAO_GERAL===";

        static String serializar(Acompanhamento ac) {
            StringBuilder sb = new StringBuilder();

            ac.getParecerPsicologico().ifPresent(p -> {
                sb.append(TAG_PSICOLOGO).append('\n')
                  .append("id=").append(p.profissional().getId()).append('\n')
                  .append("nome=").append(p.nomeProfissional()).append('\n')
                  .append("data=").append(p.dataRegistro()).append('\n')
                  .append(p.conteudo()).append('\n');
            });

            ac.getParecerPedagogico().ifPresent(p -> {
                sb.append(TAG_PEDAGOGO).append('\n')
                  .append("id=").append(p.profissional().getId()).append('\n')
                  .append("nome=").append(p.nomeProfissional()).append('\n')
                  .append("data=").append(p.dataRegistro()).append('\n')
                  .append(p.conteudo()).append('\n');
            });

            ac.getObservacaoGeral().ifPresent(obs -> {
                sb.append(TAG_GERAL).append('\n').append(obs).append('\n');
            });

            String resultado = sb.toString().trim();
            return resultado.isEmpty() ? null : resultado;
        }

        static void desserializar(String bruto, Acompanhamento.Builder builder) {
            if (bruto == null || bruto.isBlank()) {
                return;
            }
            if (!bruto.contains(TAG_PSICOLOGO) && !bruto.contains(TAG_PEDAGOGO) && !bruto.contains(TAG_GERAL)) {
                builder.observacaoGeral(bruto);
                return;
            }

            String secaoAtual = null;
            StringBuilder buffer = new StringBuilder();

            for (String linha : bruto.split("\\R")) {
                if (TAG_PSICOLOGO.equals(linha) || TAG_PEDAGOGO.equals(linha) || TAG_GERAL.equals(linha)) {
                    aplicarSecao(secaoAtual, buffer.toString().trim(), builder);
                    secaoAtual = linha;
                    buffer.setLength(0);
                } else {
                    buffer.append(linha).append('\n');
                }
            }
            aplicarSecao(secaoAtual, buffer.toString().trim(), builder);
        }

        private static void aplicarSecao(String secao, String conteudo, Acompanhamento.Builder builder) {
            if (secao == null || conteudo.isBlank()) {
                return;
            }
            if (TAG_GERAL.equals(secao)) {
                builder.observacaoGeral(conteudo);
                return;
            }

            String[] linhas = conteudo.split("\\R", 4);
            if (linhas.length < 4) {
                return;
            }

            int profId = parseMetadataInt(linhas[0], "id=");
            String profNome = parseMetadataTexto(linhas[1], "nome=");
            LocalDate data = parseMetadataData(linhas[2], "data=");
            String texto = linhas[3].trim();

            if (texto.isBlank()) {
                return;
            }

            if (TAG_PSICOLOGO.equals(secao)) {
                Psicologo psicologo = new Psicologo();
                psicologo.setId(profId);
                psicologo.setNome(profNome);
                builder.parecerPsicologico(new ParecerTecnico<>(psicologo, texto, data));
            } else if (TAG_PEDAGOGO.equals(secao)) {
                Pedagogo pedagogo = new Pedagogo();
                pedagogo.setId(profId);
                pedagogo.setNome(profNome);
                builder.parecerPedagogico(new ParecerTecnico<>(pedagogo, texto, data));
            }
        }

        private static int parseMetadataInt(String linha, String prefixo) {
            if (linha.startsWith(prefixo)) {
                try {
                    return Integer.parseInt(linha.substring(prefixo.length()).trim());
                } catch (NumberFormatException ignored) {
                }
            }
            return 0;
        }

        private static String parseMetadataTexto(String linha, String prefixo) {
            return linha.startsWith(prefixo) ? linha.substring(prefixo.length()).trim() : "";
        }

        private static LocalDate parseMetadataData(String linha, String prefixo) {
            if (linha.startsWith(prefixo)) {
                try {
                    return LocalDate.parse(linha.substring(prefixo.length()).trim());
                } catch (Exception ignored) {
                }
            }
            return LocalDate.now();
        }
    }
}