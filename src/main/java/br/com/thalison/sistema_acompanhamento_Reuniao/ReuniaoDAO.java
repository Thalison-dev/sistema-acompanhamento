package br.com.thalison.sistema_acompanhamento_Reuniao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import br.com.thalison.sistema_acompanhamento.Conexao;

public class ReuniaoDAO {

    private static final String SELECT_BASE =
        "SELECT r.*, a.nomeAluno, ps.nome AS nomeSolicitante FROM reuniao r "
        + "LEFT JOIN aluno a ON a.alunoId = r.alunoId "
        + "LEFT JOIN pessoa ps ON ps.id = r.solicitanteId ";

    public boolean agendar(Reuniao r) {
        String sql = "INSERT INTO reuniao (solicitanteTipo, solicitanteId, publicoAlvo, alunoId, "
                   + "acompanhamentoId, dataReuniao, motivo, statusReuniao) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection c = Conexao.conectar();
             PreparedStatement ps = c.prepareStatement(sql)) {
            // Reunião ligada a um acompanhamento fica ligada também ao aluno dele
            if (r.getAcompanhamentoId() > 0 && r.getAlunoId() <= 0) {
                try (PreparedStatement busca = c.prepareStatement(
                        "SELECT alunoId FROM acompanhamento WHERE acompanhamentoId = ?")) {
                    busca.setInt(1, r.getAcompanhamentoId());
                    try (ResultSet rs = busca.executeQuery()) {
                        if (rs.next()) {
                            r.setAlunoId(rs.getInt(1));
                        }
                    }
                }
            }
            ps.setString(1, r.getSolicitanteTipo());
            setInteiroOpcional(ps, 2, r.getSolicitanteId());
            ps.setString(3, r.getPublicoAlvo());
            setInteiroOpcional(ps, 4, r.getAlunoId());
            setInteiroOpcional(ps, 5, r.getAcompanhamentoId());
            ps.setDate(6, Date.valueOf(r.getDataReuniao()));
            ps.setString(7, r.getMotivo());
            ps.setString(8, r.getStatus() != null ? r.getStatus() : "Agendada");
            return ps.executeUpdate() > 0;
        } catch (SQLException | IllegalArgumentException e) {
            System.out.println("Erro ao agendar reunião: " + e.getMessage());
            return false;
        }
    }

    public List<Reuniao> listar() {
        return listar(SELECT_BASE + "ORDER BY r.dataReuniao DESC, r.reuniaoId DESC", null);
    }

    public List<Reuniao> listarPorAluno(int alunoId) {
        return listar(SELECT_BASE + "WHERE r.alunoId = ? ORDER BY r.dataReuniao DESC", alunoId);
    }

    /** Reuniões em que o perfil (ex.: "Diretor") é quem pediu ou é o convidado. */
    public List<Reuniao> listarPorPerfil(String perfil) {
        return listar(SELECT_BASE + "WHERE r.solicitanteTipo = ? OR r.publicoAlvo = ? "
                    + "ORDER BY r.dataReuniao DESC", perfil);
    }

    private List<Reuniao> listar(String sql, Object parametro) {
        List<Reuniao> lista = new ArrayList<>();
        try (Connection c = Conexao.conectar();
             PreparedStatement ps = c.prepareStatement(sql)) {
            if (parametro instanceof Integer) {
                ps.setInt(1, (Integer) parametro);
            } else if (parametro instanceof String) {
                ps.setString(1, (String) parametro);
                ps.setString(2, (String) parametro);
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapear(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar reuniões: " + e.getMessage());
        }
        return lista;
    }

    public boolean atualizarStatus(int id, String status) {
        try (Connection c = Conexao.conectar();
             PreparedStatement ps = c.prepareStatement("UPDATE reuniao SET statusReuniao = ? WHERE reuniaoId = ?")) {
            ps.setString(1, status);
            ps.setInt(2, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar reunião: " + e.getMessage());
            return false;
        }
    }

    public boolean excluir(int id) {
        try (Connection c = Conexao.conectar();
             PreparedStatement ps = c.prepareStatement("DELETE FROM reuniao WHERE reuniaoId = ?")) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao excluir reunião: " + e.getMessage());
            return false;
        }
    }

    private Reuniao mapear(ResultSet rs) throws SQLException {
        Reuniao r = new Reuniao();
        r.setId(rs.getInt("reuniaoId"));
        r.setSolicitanteTipo(rs.getString("solicitanteTipo"));
        r.setSolicitanteId(rs.getInt("solicitanteId"));
        r.setNomeSolicitante(rs.getString("nomeSolicitante"));
        r.setPublicoAlvo(rs.getString("publicoAlvo"));
        r.setAlunoId(rs.getInt("alunoId"));
        r.setNomeAluno(rs.getString("nomeAluno"));
        r.setAcompanhamentoId(rs.getInt("acompanhamentoId"));
        r.setDataReuniao(rs.getString("dataReuniao"));
        r.setMotivo(rs.getString("motivo"));
        r.setStatus(rs.getString("statusReuniao"));
        r.setDataRegistro(rs.getString("dataRegistro"));
        return r;
    }

    private void setInteiroOpcional(PreparedStatement ps, int indice, int valor) throws SQLException {
        if (valor > 0) {
            ps.setInt(indice, valor);
        } else {
            ps.setNull(indice, Types.INTEGER);
        }
    }
}
