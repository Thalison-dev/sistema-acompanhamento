package br.com.thalison.sistema_acompanhamento_Apontamento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import br.com.thalison.sistema_acompanhamento.Conexao;

public class ApontamentoDAO {

    public boolean inserir(Apontamento ap) {
        String sql = "INSERT INTO apontamento (alunoId, professorId, data, motivo) VALUES (?, ?, ?, ?)";
        try (Connection c = Conexao.conectar();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, ap.getAlunoId());
            ps.setInt(2, ap.getProfessorId());
            ps.setDate(3, ap.getData());
            ps.setString(4, ap.getMotivo());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Apontamento> listarTodos() {
        List<Apontamento> lista = new ArrayList<>();
        String sql = "SELECT ap.*, a.nome AS nomeAluno, p.nome AS nomeProfessor " +
                     "FROM apontamento ap " +
                     "INNER JOIN aluno a ON ap.alunoId = a.id " +
                     "INNER JOIN pessoa p ON ap.professorId = p.id " +
                     "ORDER BY ap.data DESC, ap.id DESC";
        try (Connection c = Conexao.conectar();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Apontamento ap = new Apontamento();
                ap.setId(rs.getInt("id"));
                ap.setAlunoId(rs.getInt("alunoId"));
                ap.setProfessorId(rs.getInt("professorId"));
                ap.setData(rs.getDate("data"));
                ap.setMotivo(rs.getString("motivo"));
                ap.setNomeAluno(rs.getString("nomeAluno"));
                ap.setNomeProfessor(rs.getString("nomeProfessor"));
                lista.add(ap);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public List<Apontamento> listarPorAlunoEMesAno(int alunoId, int mes, int ano) {
        List<Apontamento> lista = new ArrayList<>();
        String sql = "SELECT ap.*, a.nome AS nomeAluno, p.nome AS nomeProfessor " +
                     "FROM apontamento ap " +
                     "INNER JOIN aluno a ON ap.alunoId = a.id " +
                     "INNER JOIN pessoa p ON ap.professorId = p.id " +
                     "WHERE ap.alunoId = ? AND MONTH(ap.data) = ? AND YEAR(ap.data) = ? " +
                     "ORDER BY ap.data ASC, ap.id ASC";
        try (Connection c = Conexao.conectar();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, alunoId);
            ps.setInt(2, mes);
            ps.setInt(3, ano);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Apontamento ap = new Apontamento();
                    ap.setId(rs.getInt("id"));
                    ap.setAlunoId(rs.getInt("alunoId"));
                    ap.setProfessorId(rs.getInt("professorId"));
                    ap.setData(rs.getDate("data"));
                    ap.setMotivo(rs.getString("motivo"));
                    ap.setNomeAluno(rs.getString("nomeAluno"));
                    ap.setNomeProfessor(rs.getString("nomeProfessor"));
                    lista.add(ap);
                }
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