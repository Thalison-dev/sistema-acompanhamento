package br.com.thalison.sistema_Acompanhamento_entrevista;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import br.com.thalison.sistema_acompanhamento.Conexao;

public class EntrevistaDAO {

    public boolean cadastrar(Entrevista e) {
        String sql = "INSERT INTO entrevista (alunoId, psicologoId, motivo, agendada, data, observacoes) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection c = Conexao.conectar();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, e.getAlunoId());
            ps.setInt(2, e.getPsicologoId());
            ps.setString(3, e.getMotivo());
            ps.setBoolean(4, e.isAgendada());
            ps.setDate(5, Date.valueOf(e.getData()));
            ps.setString(6, e.getObservacoes());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public List<Entrevista> listarTodas() {
        List<Entrevista> lista = new ArrayList<>();
        String sql = "SELECT e.*, pa.nome AS nomeAluno, pp.nome AS nomePsicologo "
                   + "FROM entrevista e "
                   + "INNER JOIN pessoa pa ON pa.id = e.alunoId "
                   + "INNER JOIN pessoa pp ON pp.id = e.psicologoId "
                   + "ORDER BY e.data DESC";
        try (Connection c = Conexao.conectar();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Entrevista e = new Entrevista();
                e.setId(rs.getInt("id"));
                e.setAlunoId(rs.getInt("alunoId"));
                e.setNomeAluno(rs.getString("nomeAluno"));
                e.setPsicologoId(rs.getInt("psicologoId"));
                e.setNomePsicologo(rs.getString("nomePsicologo"));
                e.setMotivo(rs.getString("motivo"));
                e.setAgendada(rs.getBoolean("agendada"));
                Date d = rs.getDate("data");
                e.setData(d != null ? d.toLocalDate() : null);
                e.setObservacoes(rs.getString("observacoes"));
                lista.add(e);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return lista;
    }

    public boolean excluir(int id) {
        String sql = "DELETE FROM entrevista WHERE id = ?";
        try (Connection c = Conexao.conectar();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}