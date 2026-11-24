package br.com.thalison.sistema_acompanhamento_Acompanhamento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import br.com.thalison.sistema_acompanhamento.Conexao;

public class AcompanhamentoDAO {

    // Abertura de ficha e vínculo do Responsável Legal feita pela Secretaria (ou Admin)
    public boolean abrirFichaSecretaria(Acompanhamento a) {
        String sql = "INSERT INTO acompanhamento (alunoId, responsavelLegalId, dataInicio, dataFim, objetivo, status) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection c = Conexao.conectar();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, a.getAlunoId());
            ps.setInt(2, a.getResponsavelLegalId());
            ps.setDate(3, Date.valueOf(a.getDataInicio()));
            if (a.getDataFim() != null) {
                ps.setDate(4, Date.valueOf(a.getDataFim()));
            } else {
                ps.setNull(4, Types.DATE);
            }
            ps.setString(5, a.getObjetivo());
            ps.setString(6, a.getStatus() != null ? a.getStatus() : "Em Andamento");
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Secretaria altera o Responsável Legal vinculado na ficha do menor
    public boolean atualizarResponsavelSecretaria(int acompanhamentoId, int responsavelLegalId) {
        String sql = "UPDATE acompanhamento SET responsavelLegalId = ? WHERE acompanhamentoId = ?";
        try (Connection c = Conexao.conectar();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, responsavelLegalId);
            ps.setInt(2, acompanhamentoId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Psicólogo registra seu parecer clínico na ficha
    public boolean registrarParecerPsicologo(int acompanhamentoId, int psicologoId, String parecerPsicologico) {
        String sql = "UPDATE acompanhamento SET psicologoId = ?, parecerPsicologico = ? WHERE acompanhamentoId = ?";
        try (Connection c = Conexao.conectar();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, psicologoId);
            ps.setString(2, parecerPsicologico);
            ps.setInt(3, acompanhamentoId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Pedagogo registra seu parecer pedagógico, objetivo e status na ficha
    public boolean registrarParecerPedagogo(int acompanhamentoId, int pedagogoId, String objetivo, String parecerPedagogico, String status) {
        String sql = "UPDATE acompanhamento SET pedagogoId = ?, objetivo = ?, parecerPedagogico = ?, status = ? WHERE acompanhamentoId = ?";
        try (Connection c = Conexao.conectar();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, pedagogoId);
            ps.setString(2, objetivo);
            ps.setString(3, parecerPedagogico);
            ps.setString(4, status);
            ps.setInt(5, acompanhamentoId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Acompanhamento> listarTodos() {
        List<Acompanhamento> lista = new ArrayList<>();
        String sql = "SELECT ac.*, "
                   + "pa.nome AS nomeAluno, n.tipoNeurodivergencia, "
                   + "pr.nome AS nomeResponsavel, rl.parentesco, "
                   + "ppsi.nome AS nomePsicologo, pped.nome AS nomePedagogo "
                   + "FROM acompanhamento ac "
                   + "INNER JOIN pessoa pa ON pa.id = ac.alunoId "
                   + "LEFT JOIN neurodivergencia n ON n.alunoId = ac.alunoId "
                   + "INNER JOIN pessoa pr ON pr.id = ac.responsavelLegalId "
                   + "INNER JOIN responsavel_legal rl ON rl.pessoaId = ac.responsavelLegalId "
                   + "LEFT JOIN pessoa ppsi ON ppsi.id = ac.psicologoId "
                   + "LEFT JOIN pessoa pped ON pped.id = ac.pedagogoId "
                   + "ORDER BY ac.acompanhamentoId DESC";

        try (Connection c = Conexao.conectar();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public Acompanhamento buscarPorId(int id) {
        String sql = "SELECT ac.*, "
                   + "pa.nome AS nomeAluno, n.tipoNeurodivergencia, "
                   + "pr.nome AS nomeResponsavel, rl.parentesco, "
                   + "ppsi.nome AS nomePsicologo, pped.nome AS nomePedagogo "
                   + "FROM acompanhamento ac "
                   + "INNER JOIN pessoa pa ON pa.id = ac.alunoId "
                   + "LEFT JOIN neurodivergencia n ON n.alunoId = ac.alunoId "
                   + "INNER JOIN pessoa pr ON pr.id = ac.responsavelLegalId "
                   + "INNER JOIN responsavel_legal rl ON rl.pessoaId = ac.responsavelLegalId "
                   + "LEFT JOIN pessoa ppsi ON ppsi.id = ac.psicologoId "
                   + "LEFT JOIN pessoa pped ON pped.id = ac.pedagogoId "
                   + "WHERE ac.acompanhamentoId = ?";

        try (Connection c = Conexao.conectar();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean excluir(int id) {
        String sql = "DELETE FROM acompanhamento WHERE acompanhamentoId = ?";
        try (Connection c = Conexao.conectar();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Acompanhamento mapear(ResultSet rs) throws SQLException {
        Acompanhamento ac = new Acompanhamento();
        ac.setId(rs.getInt("acompanhamentoId"));
        ac.setAlunoId(rs.getInt("alunoId"));
        ac.setNomeAluno(rs.getString("nomeAluno"));
        ac.setTipoNeurodivergencia(rs.getString("tipoNeurodivergencia"));
        ac.setResponsavelLegalId(rs.getInt("responsavelLegalId"));
        ac.setNomeResponsavel(rs.getString("nomeResponsavel"));
        ac.setParentescoResponsavel(rs.getString("parentesco"));
        ac.setPsicologoId(rs.getInt("psicologoId"));
        ac.setNomePsicologo(rs.getString("nomePsicologo"));
        ac.setPedagogoId(rs.getInt("pedagogoId"));
        ac.setNomePedagogo(rs.getString("nomePedagogo"));

        Date di = rs.getDate("dataInicio");
        Date df = rs.getDate("dataFim");
        ac.setDataInicio(di != null ? di.toLocalDate() : null);
        ac.setDataFim(df != null ? df.toLocalDate() : null);

        ac.setObjetivo(rs.getString("objetivo"));
        ac.setParecerPsicologico(rs.getString("parecerPsicologico"));
        ac.setParecerPedagogico(rs.getString("parecerPedagogico"));
        ac.setStatus(rs.getString("status"));
        return ac;
    }
}