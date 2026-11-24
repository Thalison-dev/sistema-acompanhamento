package br.com.thalison.sistema_acompanhamento_aluno;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import br.com.thalison.sistema_acompanhamento.Conexao;

public class AlunoDAO {

    public boolean cadastrar(Aluno a) {
        String sqlPessoa = "INSERT INTO pessoa (nome, cpf, telefone, email, senha, perfil) VALUES (?, ?, ?, ?, '123456', 'ALUNO')";
        String sqlAluno = "INSERT INTO aluno (alunoId, dataNascimento, matricula, anoEscolar, status) VALUES (?, ?, ?, ?, ?)";
        String sqlNeuro = "INSERT INTO neurodivergencia (alunoId, tipoNeurodivergencia, descricaoNeuro) VALUES (?, ?, ?)";

        try (Connection c = Conexao.conectar()) {
            c.setAutoCommit(false);
            try {
                int id;
                try (PreparedStatement ps = c.prepareStatement(sqlPessoa, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setString(1, a.getNome());
                    ps.setString(2, a.getCpf());
                    ps.setString(3, a.getTelefone());
                    ps.setString(4, a.getEmail());
                    ps.executeUpdate();
                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (!rs.next()) throw new SQLException("ID não gerado.");
                        id = rs.getInt(1);
                    }
                }

                try (PreparedStatement ps = c.prepareStatement(sqlAluno)) {
                    ps.setInt(1, id);
                    ps.setDate(2, Date.valueOf(a.getDataNascimento()));
                    ps.setString(3, a.getMatricula());
                    ps.setString(4, a.getAnoEscolar());
                    ps.setString(5, a.getStatus() != null ? a.getStatus() : "Ativo");
                    ps.executeUpdate();
                }

                if (a.getTipoNeurodivergencia() != null && !a.getTipoNeurodivergencia().trim().isEmpty()) {
                    try (PreparedStatement ps = c.prepareStatement(sqlNeuro)) {
                        ps.setInt(1, id);
                        ps.setString(2, a.getTipoNeurodivergencia().trim());
                        ps.setString(3, a.getDescricaoNeuro());
                        ps.executeUpdate();
                    }
                }

                c.commit();
                a.setId(id);
                return true;
            } catch (SQLException e) {
                c.rollback();
                throw e;
            } finally {
                c.setAutoCommit(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Aluno> listarTodos() {
        List<Aluno> lista = new ArrayList<>();
        String sql = "SELECT p.id, p.nome, p.cpf, p.telefone, p.email, "
                   + "a.dataNascimento, a.matricula, a.anoEscolar, a.status, "
                   + "n.tipoNeurodivergencia, n.descricaoNeuro "
                   + "FROM pessoa p "
                   + "INNER JOIN aluno a ON a.alunoId = p.id "
                   + "LEFT JOIN neurodivergencia n ON n.alunoId = a.alunoId "
                   + "ORDER BY p.nome";

        try (Connection c = Conexao.conectar();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Aluno a = new Aluno();
                a.setId(rs.getInt("id"));
                a.setNome(rs.getString("nome"));
                a.setCpf(rs.getString("cpf"));
                a.setTelefone(rs.getString("telefone"));
                a.setEmail(rs.getString("email"));
                Date dn = rs.getDate("dataNascimento");
                a.setDataNascimento(dn != null ? dn.toString() : "");
                a.setMatricula(rs.getString("matricula"));
                a.setAnoEscolar(rs.getString("anoEscolar"));
                a.setStatus(rs.getString("status"));
                a.setTipoNeurodivergencia(rs.getString("tipoNeurodivergencia"));
                a.setDescricaoNeuro(rs.getString("descricaoNeuro"));
                lista.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public boolean excluir(int id) {
        String sql = "DELETE FROM pessoa WHERE id = ?";
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