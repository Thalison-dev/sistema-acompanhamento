package br.com.thalison.sistema_acompanhamento_Professor;

import br.com.thalison.sistema_acompanhamento.Conexao;
import br.com.thalison.sistema_acompanhamento_aluno.Aluno;
import br.com.thalison.sistema_acompanhamento_Apontamento.Apontamento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProfessorDAO {

    public List<Professor> listarTodos() {
        List<Professor> lista = new ArrayList<>();
        String sql = "SELECT p.id, p.nome, p.email, p.cpf, p.telefone, p.perfil, " +
                     "pr.formacao, pr.especialidade " +
                     "FROM pessoa p " +
                     "INNER JOIN professor pr ON p.id = pr.pessoaId " +
                     "ORDER BY p.nome";

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

    public Professor buscarPorId(int pessoaId) {
        String sql = "SELECT p.id, p.nome, p.email, p.cpf, p.telefone, p.perfil, " +
                     "pr.formacao, pr.especialidade " +
                     "FROM pessoa p " +
                     "INNER JOIN professor pr ON p.id = pr.pessoaId " +
                     "WHERE p.id = ?";

        try (Connection c = Conexao.conectar();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, pessoaId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Aluno> listarAlunos() {
        List<Aluno> lista = new ArrayList<>();
        String sql = "SELECT * FROM aluno WHERE status = 'Ativo' ORDER BY nome";

        try (Connection c = Conexao.conectar();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Aluno a = new Aluno();
                a.setId(rs.getInt("id"));
                a.setNome(rs.getString("nome"));
                a.setMatricula(rs.getString("matricula"));
                a.setAnoEscolar(rs.getString("anoEscolar"));
                lista.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // Registra apontamento — recebe objetos Professor e Aluno (não ints soltos)
    public boolean registrarApontamento(Professor professor, Aluno aluno,
                                         String descricao, String disciplina) {
        String sql = "INSERT INTO apontamento " +
                     "(aluno_id, professor_id, data, disciplina, descricao) " +
                     "VALUES (?, ?, CURDATE(), ?, ?)";

        try (Connection c = Conexao.conectar();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, aluno.getId());
            ps.setInt(2, professor.getId());
            ps.setString(3, disciplina);
            ps.setString(4, descricao);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Consulta histórico de apontamentos de um Aluno (recebe objeto Aluno)
    public List<Apontamento> consultarApontamentos(Aluno aluno) {
        List<Apontamento> lista = new ArrayList<>();
        String sql = "SELECT ap.id, ap.aluno_id, ap.professor_id, ap.data, " +
                     "ap.disciplina, ap.descricao, " +
                     "al.nome AS nomeAluno, p.nome AS nomeProfessor " +
                     "FROM apontamento ap " +
                     "INNER JOIN aluno al ON ap.aluno_id = al.id " +
                     "LEFT JOIN pessoa p ON ap.professor_id = p.id " +
                     "WHERE ap.aluno_id = ? " +
                     "ORDER BY ap.data DESC";

        try (Connection c = Conexao.conectar();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, aluno.getId());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Apontamento ap = new Apontamento();
                    ap.setId(rs.getInt("id"));
                    ap.setAlunoId(rs.getInt("aluno_id"));
                    ap.setProfessorId(rs.getInt("professor_id"));
                    ap.setData(rs.getDate("data"));
                    ap.setMotivo(rs.getString("descricao"));
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

    private Professor mapear(ResultSet rs) throws SQLException {
        Professor p = new Professor();
        p.setId(rs.getInt("id"));
        p.setNome(rs.getString("nome"));
        p.setEmail(rs.getString("email"));
        p.setCpf(rs.getString("cpf"));
        p.setTelefone(rs.getString("telefone"));
        p.setPerfil(rs.getString("perfil"));
        p.setFormacao(rs.getString("formacao"));
        p.setEspecialidade(rs.getString("especialidade"));
        return p;
    }
}