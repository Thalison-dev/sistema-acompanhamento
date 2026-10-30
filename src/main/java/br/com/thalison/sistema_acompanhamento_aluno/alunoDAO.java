package br.com.thalison.sistema_acompanhamento_aluno;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.thalison.sistema_acompanhamento.Conexao;

public class alunoDAO {

    public boolean cadastrar(aluno aluno) {

        String sql = "INSERT INTO aluno "
                + "(nome, dataNascimento, matricula, anoEscolar, status) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, aluno.getNome());
            stmt.setString(2, aluno.getDataNascimento());
            stmt.setString(3, aluno.getMatricula());
            stmt.setString(4, aluno.getAnoEscolar());
            stmt.setString(5, aluno.getStatus());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar aluno: " + e.getMessage());
            return false;
        }
    }

    
    public aluno buscarPorId(int id) {

        String sql = "SELECT * FROM aluno WHERE id = ?";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    return mapearAluno(rs);
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao consultar aluno: " + e.getMessage());
        }

        return null;
    }

    
    public List<aluno> listarTodos() {

        List<aluno> alunos = new ArrayList<>();

        String sql = "SELECT * FROM aluno";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                alunos.add(mapearAluno(rs));
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar alunos: " + e.getMessage());
        }

        return alunos;
    }

    
    public boolean atualizar(aluno aluno) {

        String sql = "UPDATE aluno SET nome = ?, dataNascimento = ?, "
                + "matricula = ?, anoEscolar = ?, status = ? WHERE id = ?";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, aluno.getNome());
            stmt.setString(2, aluno.getDataNascimento());
            stmt.setString(3, aluno.getMatricula());
            stmt.setString(4, aluno.getAnoEscolar());
            stmt.setString(5, aluno.getStatus());
            stmt.setInt(6, aluno.getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar aluno: " + e.getMessage());
            return false;
        }
    }

    
    public boolean excluir(int id) {

        String sql = "DELETE FROM aluno WHERE id = ?";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao excluir aluno: " + e.getMessage());
            return false;
        }
    }

    
    private aluno mapearAluno(ResultSet rs) throws SQLException {

        aluno aluno = new aluno();

        aluno.setId(rs.getInt("id"));
        aluno.setNome(rs.getString("nome"));
        aluno.setDataNascimento(rs.getString("dataNascimento"));
        aluno.setMatricula(rs.getString("matricula"));
        aluno.setAnoEscolar(rs.getString("anoEscolar"));
        aluno.setStatus(rs.getString("status"));

        return aluno;
    }
}
