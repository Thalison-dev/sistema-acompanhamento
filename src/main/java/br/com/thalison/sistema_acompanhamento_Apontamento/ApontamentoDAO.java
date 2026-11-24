package br.com.thalison.sistema_acompanhamento_Apontamento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import br.com.thalison.sistema_acompanhamento.Conexao;

public class ApontamentoDAO {

    public boolean cadastrar(Apontamento ap) {
        String sql = "INSERT INTO apontamento (alunoId, professorId, data, motivo) VALUES (?, ?, ?, ?)";
        try (Connection c = Conexao.conectar();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, ap.getAlunoId());
            ps.setInt(2, ap.getProfessorId());
            ps.setDate(3, Date.valueOf(ap.getData()));
            ps.setString(4, ap.getMotivo());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Apontamento> listarTodos() {
        List<Apontamento> lista = new ArrayList<>();
        String sql = "SELECT ap.*, pa.nome AS nomeAluno, pp.nome AS nomeProfessor "
                   + "FROM apontamento ap "
                   + "INNER JOIN pessoa pa ON pa.id = ap.alunoId "
                   + "INNER JOIN pessoa pp ON pp.id = ap.professorId "
                   + "ORDER BY ap.data DESC";
        try (Connection c = Conexao.conectar();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Apontamento ap = new Apontamento();
                ap.setId(rs.getInt("id"));
                ap.setAlunoId(rs.getInt("alunoId"));
                ap.setNomeAluno(rs.getString("nomeAluno"));
                ap.setProfessorId(rs.getInt("professorId"));
                ap.setNomeProfessor(rs.getString("nomeProfessor"));
                Date d = rs.getDate("data");
                ap.setData(d != null ? d.toLocalDate() : null);
                ap.setMotivo(rs.getString("motivo"));
                lista.add(ap);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public boolean excluir(int id) {
        String sql = "DELETE FROM apontamento WHERE id = ?";
        try (Connection c = Conexao.conectar();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}