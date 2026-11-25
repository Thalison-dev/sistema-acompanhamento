package br.com.thalison.sistema_acompanhamento_aluno;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import br.com.thalison.sistema_acompanhamento.Conexao;

public class AlunoDAO {

    public boolean cadastrar(Aluno aluno) {
        String sql = "INSERT INTO aluno (nome, email, cpf, telefone, dataNascimento, matricula, anoEscolar, tipoNeurodivergencia, descricaoNeuro, status) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection c = Conexao.conectar();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, aluno.getNome());
            ps.setString(2, aluno.getEmail());
            ps.setString(3, aluno.getCpf());
            ps.setString(4, aluno.getTelefone());
            if (aluno.getDataNascimento() != null) {
                ps.setDate(5, aluno.getDataNascimento());
            } else {
                ps.setNull(5, Types.DATE);
            }
            ps.setString(6, aluno.getMatricula());
            ps.setString(7, aluno.getAnoEscolar());
            ps.setString(8, aluno.getTipoNeurodivergencia());
            ps.setString(9, aluno.getDescricaoNeuro());
            ps.setString(10, aluno.getStatus() != null ? aluno.getStatus() : "Ativo");

            int linhas = ps.executeUpdate();
            if (linhas > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        aluno.setId(rs.getInt(1));
                    }
                }
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Aluno> listarTodos() {
        List<Aluno> lista = new ArrayList<>();
        String sql = "SELECT * FROM aluno ORDER BY nome ASC";

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

    public Aluno buscarPorId(int id) {
        String sql = "SELECT * FROM aluno WHERE id = ?";
        try (Connection c = Conexao.conectar();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean excluir(int id) {
        String sql = "DELETE FROM aluno WHERE id = ?";
        try (Connection c = Conexao.conectar();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Aluno mapear(ResultSet rs) throws SQLException {
        Aluno a = new Aluno();
        a.setId(rs.getInt("id"));
        a.setPessoaId(rs.getInt("pessoaId"));
        a.setNome(rs.getString("nome"));
        a.setEmail(rs.getString("email"));
        a.setCpf(rs.getString("cpf"));
        a.setTelefone(rs.getString("telefone"));
        a.setDataNascimento(rs.getDate("dataNascimento"));
        a.setMatricula(rs.getString("matricula"));
        a.setAnoEscolar(rs.getString("anoEscolar"));
        a.setTipoNeurodivergencia(rs.getString("tipoNeurodivergencia"));
        a.setDescricaoNeuro(rs.getString("descricaoNeuro"));
        a.setStatus(rs.getString("status"));
        return a;
    }
}