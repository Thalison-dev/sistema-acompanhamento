package br.com.thalison.sistema_acompanhamento_Acompanhamento;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import br.com.thalison.sistema_acompanhamento.Conexao;

public class AcompanhamentoDAO {

    private static final String SELECT_BASE =
        "SELECT ac.*, a.nomeAluno FROM acompanhamento ac "
        + "LEFT JOIN aluno a ON a.alunoId = ac.alunoId ";

    public boolean cadastrar(Acompanhamento acompanhamento) {

        String sql = "INSERT INTO acompanhamento "
                + "(alunoId, dataInicioAcompanhamento, dataFimAcompanhamento, "
                + "objetivoAcompanhamento, observacaoAcompanhamento, statusAcompanhamento) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            preencher(stmt, acompanhamento);
            return stmt.executeUpdate() > 0;

        } catch (SQLException | IllegalArgumentException e) {
            System.out.println("Erro ao cadastrar acompanhamento: " + e.getMessage());
            return false;
        }
    }

    public List<Acompanhamento> listar() {
        return listar(SELECT_BASE + "ORDER BY ac.dataInicioAcompanhamento DESC, ac.acompanhamentoId DESC", 0);
    }

    public List<Acompanhamento> listarPorAluno(int alunoId) {
        return listar(SELECT_BASE + "WHERE ac.alunoId = ? ORDER BY ac.dataInicioAcompanhamento DESC", alunoId);
    }

    private List<Acompanhamento> listar(String sql, int parametro) {

        List<Acompanhamento> acompanhamentos = new ArrayList<>();

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            if (parametro > 0) {
                stmt.setInt(1, parametro);
            }
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    acompanhamentos.add(mapear(rs));
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar acompanhamentos: " + e.getMessage());
        }

        return acompanhamentos;
    }

    public Acompanhamento buscarPorId(int id) {

        String sql = SELECT_BASE + "WHERE ac.acompanhamentoId = ?";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar acompanhamento: " + e.getMessage());
        }

        return null;
    }

    public boolean atualizar(Acompanhamento acompanhamento) {

        String sql = "UPDATE acompanhamento SET "
                + "alunoId = ?, "
                + "dataInicioAcompanhamento = ?, "
                + "dataFimAcompanhamento = ?, "
                + "objetivoAcompanhamento = ?, "
                + "observacaoAcompanhamento = ?, "
                + "statusAcompanhamento = ? "
                + "WHERE acompanhamentoId = ?";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            preencher(stmt, acompanhamento);
            stmt.setInt(7, acompanhamento.getAcompanhamentoId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException | IllegalArgumentException e) {
            System.out.println("Erro ao atualizar acompanhamento: " + e.getMessage());
            return false;
        }
    }

    /** Usado pelo Diretor para aprovar, suspender ou encerrar um acompanhamento. */
    public boolean atualizarStatus(int id, String status) {
        String sql = "UPDATE acompanhamento SET statusAcompanhamento = ?, "
                   + "dataFimAcompanhamento = CASE WHEN ? IN ('Concluído','Cancelado') "
                   + "AND dataFimAcompanhamento IS NULL THEN CURDATE() ELSE dataFimAcompanhamento END "
                   + "WHERE acompanhamentoId = ?";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setString(2, status);
            stmt.setInt(3, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar status do acompanhamento: " + e.getMessage());
            return false;
        }
    }

    public boolean excluir(int id) {

        try (Connection conexao = Conexao.conectar()) {
            conexao.setAutoCommit(false);
            try {
                // Remove itens que dependem do acompanhamento antes de apagá-lo
                for (String tabela : new String[] {"procedimento", "intervencao"}) {
                    try (PreparedStatement st = conexao.prepareStatement(
                            "DELETE FROM " + tabela + " WHERE acompanhamentoId = ?")) {
                        st.setInt(1, id);
                        st.executeUpdate();
                    }
                }
                int linhas;
                try (PreparedStatement stmt = conexao.prepareStatement(
                        "DELETE FROM acompanhamento WHERE acompanhamentoId = ?")) {
                    stmt.setInt(1, id);
                    linhas = stmt.executeUpdate();
                }
                conexao.commit();
                return linhas > 0;
            } catch (SQLException e) {
                conexao.rollback();
                throw e;
            } finally {
                conexao.setAutoCommit(true);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao excluir acompanhamento: " + e.getMessage());
            return false;
        }
    }

    /** Quantidade de acompanhamentos por status (painel do Diretor). */
    public Map<String, Integer> contarPorStatus() {
        Map<String, Integer> totais = new LinkedHashMap<>();
        totais.put("Pendente", 0);
        totais.put("Em Andamento", 0);
        totais.put("Concluído", 0);
        totais.put("Cancelado", 0);

        String sql = "SELECT COALESCE(statusAcompanhamento, 'Pendente') AS status, COUNT(*) AS total "
                   + "FROM acompanhamento GROUP BY COALESCE(statusAcompanhamento, 'Pendente')";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                totais.put(rs.getString("status"), rs.getInt("total"));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao contar acompanhamentos: " + e.getMessage());
        }
        return totais;
    }

    private void preencher(PreparedStatement stmt, Acompanhamento acompanhamento) throws SQLException {
        stmt.setInt(1, acompanhamento.getAlunoId());
        stmt.setDate(2, Date.valueOf(acompanhamento.getDataInicio()));

        if (acompanhamento.getDataFim() == null
                || acompanhamento.getDataFim().trim().isEmpty()) {
            stmt.setNull(3, Types.DATE);
        } else {
            stmt.setDate(3, Date.valueOf(acompanhamento.getDataFim()));
        }

        stmt.setString(4, acompanhamento.getObjetivo());
        stmt.setString(5, acompanhamento.getObservacao());
        stmt.setString(6, acompanhamento.getStatus());
    }

    private Acompanhamento mapear(ResultSet rs) throws SQLException {
        Acompanhamento acompanhamento = new Acompanhamento();

        acompanhamento.setAcompanhamentoId(rs.getInt("acompanhamentoId"));
        acompanhamento.setAlunoId(rs.getInt("alunoId"));
        acompanhamento.setNomeAluno(rs.getString("nomeAluno"));

        Date inicio = rs.getDate("dataInicioAcompanhamento");
        Date fim = rs.getDate("dataFimAcompanhamento");

        acompanhamento.setDataInicio(inicio != null ? inicio.toString() : null);
        acompanhamento.setDataFim(fim != null ? fim.toString() : null);

        acompanhamento.setObjetivo(rs.getString("objetivoAcompanhamento"));
        acompanhamento.setObservacao(rs.getString("observacaoAcompanhamento"));
        acompanhamento.setStatus(rs.getString("statusAcompanhamento"));

        return acompanhamento;
    }
}
