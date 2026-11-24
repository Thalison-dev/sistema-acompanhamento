package br.com.thalison.sistema_acompanhamento_ResponsavelLegal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import br.com.thalison.sistema_acompanhamento.Conexao;

public class ResponsavelLegalDAO {

    public boolean cadastrar(ResponsavelLegal rl) {
        String sqlPessoa = "INSERT INTO pessoa (nome, cpf, telefone, email, senha, perfil) VALUES (?, ?, ?, ?, '123456', 'RESPONSAVEL')";
        String sqlResp = "INSERT INTO responsavel_legal (pessoaId, parentesco) VALUES (?, ?)";

        try (Connection c = Conexao.conectar()) {
            c.setAutoCommit(false);
            try {
                int id;
                try (PreparedStatement ps = c.prepareStatement(sqlPessoa, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setString(1, rl.getNome());
                    ps.setString(2, rl.getCpf());
                    ps.setString(3, rl.getTelefone());
                    ps.setString(4, rl.getEmail());
                    ps.executeUpdate();
                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (!rs.next()) throw new SQLException("ID não gerado.");
                        id = rs.getInt(1);
                    }
                }

                try (PreparedStatement ps = c.prepareStatement(sqlResp)) {
                    ps.setInt(1, id);
                    ps.setString(2, rl.getParentesco());
                    ps.executeUpdate();
                }

                c.commit();
                rl.setId(id);
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

    public List<ResponsavelLegal> listarTodos() {
        List<ResponsavelLegal> lista = new ArrayList<>();
        String sql = "SELECT p.id, p.nome, p.cpf, p.telefone, p.email, rl.parentesco "
                   + "FROM pessoa p "
                   + "INNER JOIN responsavel_legal rl ON p.id = rl.pessoaId "
                   + "ORDER BY p.nome";

        try (Connection c = Conexao.conectar();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ResponsavelLegal rl = new ResponsavelLegal();
                rl.setId(rs.getInt("id"));
                rl.setNome(rs.getString("nome"));
                rl.setCpf(rs.getString("cpf"));
                rl.setTelefone(rs.getString("telefone"));
                rl.setEmail(rs.getString("email"));
                rl.setParentesco(rs.getString("parentesco"));
                lista.add(rl);
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