package br.com.thalison.sistema_acompanhamento_Acompanhamento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import br.com.thalison.sistema_acompanhamento.Conexao;

public class AcompanhamentoDAO {

    private static final String SQL_BASE =
            "SELECT ac.*, " +
            "       a.nome AS nomeAluno, a.tipoNeurodivergencia, " +
            "       pr.nome AS nomeResponsavel, rl.parentesco AS parentescoResponsavel, " +
            "       pps.nome AS nomePsicologo, " +
            "       ppd.nome AS nomePedagogo " +
            "FROM acompanhamento ac " +
            "INNER JOIN aluno a ON ac.alunoId = a.id " +
            "INNER JOIN responsavel_legal rl ON ac.responsavelLegalId = rl.id " +
            "INNER JOIN pessoa pr ON rl.pessoaId = pr.id " +
            "LEFT JOIN pessoa pps ON ac.psicologoId = pps.id " +
            "LEFT JOIN pessoa ppd ON ac.pedagogoId = ppd.id ";

    public boolean abrirFichaSecretaria(Acompanhamento ac) {
        String sql = "INSERT INTO acompanhamento " +
                     "(alunoId, responsavelLegalId, secretariaId, dataInicio, dataFim, objetivo, status) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection c = Conexao.conectar();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, ac.getAlunoId());
            ps.setInt(2, ac.getResponsavelLegalId());
            if (ac.getSecretariaId() != null) ps.setInt(3, ac.getSecretariaId()); else ps.setNull(3, Types.INTEGER);
            ps.setDate(4, ac.getDataInicio());
            if (ac.getDataFim() != null) ps.setDate(5, ac.getDataFim()); else ps.setNull(5, Types.DATE);
            ps.setString(6, ac.getObjetivo());
            ps.setString(7, ac.getStatus() != null ? ac.getStatus() : "Em Andamento");
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) ac.setId(rs.getInt(1));
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean atualizarResponsavelLegal(int fichaId, int responsavelLegalId) {
        String sql = "UPDATE acompanhamento SET responsavelLegalId = ? WHERE id = ?";
        try (Connection c = Conexao.conectar();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, responsavelLegalId);
            ps.setInt(2, fichaId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean salvarParecerPsicologo(int fichaId, int psicologoId, String parecerPsicologico) {
        String sql = "UPDATE acompanhamento SET psicologoId = ?, parecerPsicologico = ? WHERE id = ?";
        try (Connection c = Conexao.conectar();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, psicologoId);
            ps.setString(2, parecerPsicologico);
            ps.setInt(3, fichaId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean salvarParecerPedagogo(int fichaId, int pedagogoId, String objetivo, String parecerPedagogico, String status) {
        String sql = "UPDATE acompanhamento SET pedagogoId = ?, objetivo = ?, parecerPedagogico = ?, status = ? WHERE id = ?";
        try (Connection c = Conexao.conectar();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, pedagogoId);
            ps.setString(2, objetivo);
            ps.setString(3, parecerPedagogico);
            ps.setString(4, status);
            ps.setInt(5, fichaId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Acompanhamento buscarPorId(int id) {
        String sql = SQL_BASE + "WHERE ac.id = ?";
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

    public List<Acompanhamento> listarTodos() {
        List<Acompanhamento> lista = new ArrayList<>();
        String sql = SQL_BASE + "ORDER BY ac.id DESC";
        try (Connection c = Conexao.conectar();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public List<Acompanhamento> listarPorAluno(int alunoId) {
        List<Acompanhamento> lista = new ArrayList<>();
        String sql = SQL_BASE + "WHERE ac.alunoId = ? ORDER BY ac.id DESC";
        try (Connection c = Conexao.conectar();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, alunoId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public boolean excluir(int id) {
        String sql = "DELETE FROM acompanhamento WHERE id = ?";
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
        ac.setId(rs.getInt("id"));
        ac.setAlunoId(rs.getInt("alunoId"));
        ac.setResponsavelLegalId(rs.getInt("responsavelLegalId"));
        int sec = rs.getInt("secretariaId"); if (!rs.wasNull()) ac.setSecretariaId(sec);
        int psi = rs.getInt("psicologoId"); if (!rs.wasNull()) ac.setPsicologoId(psi);
        int ped = rs.getInt("pedagogoId"); if (!rs.wasNull()) ac.setPedagogoId(ped);
        ac.setDataInicio(rs.getDate("dataInicio"));
        ac.setDataFim(rs.getDate("dataFim"));
        ac.setObjetivo(rs.getString("objetivo"));
        ac.setParecerPsicologico(rs.getString("parecerPsicologico"));
        ac.setParecerPedagogico(rs.getString("parecerPedagogico"));
        ac.setStatus(rs.getString("status"));
        ac.setNomeAluno(rs.getString("nomeAluno"));
        ac.setTipoNeurodivergencia(rs.getString("tipoNeurodivergencia"));
        ac.setNomeResponsavel(rs.getString("nomeResponsavel"));
        ac.setParentescoResponsavel(rs.getString("parentescoResponsavel"));
        ac.setNomePsicologo(rs.getString("nomePsicologo"));
        ac.setNomePedagogo(rs.getString("nomePedagogo"));
        return ac;
    }
}