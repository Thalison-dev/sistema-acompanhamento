
package br.com.thalison.sistema_acompanhamento_ResponsavelLegal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.thalison.sistema_acompanhamento.Conexao;

public class ResponsavelLegalDAO {

    // Cadastrar responsável legal
    public boolean cadastrar(ResponsavelLegal rl) {

        String sql = "INSERT INTO responsavel_legal "
                   + "(nome, parentesco, cpf, telefone, email) "
                   + "VALUES (?, ?, ?, ?, ?)";

        try (Connection c = Conexao.conectar();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, rl.getNome());
            ps.setString(2, rl.getParentesco());
            ps.setString(3, rl.getCpf());
            ps.setString(4, rl.getTelefone());
            ps.setString(5, rl.getEmail());

            int linhas = ps.executeUpdate();

            return linhas > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar responsável legal:");
            e.printStackTrace();
            return false;
        }
    }

    // Listar todos os responsáveis legais
    public List<ResponsavelLegal> listarTodos() {

        List<ResponsavelLegal> lista = new ArrayList<>();

        String sql = "SELECT id, nome, parentesco, cpf, telefone, email "
                   + "FROM responsavel_legal ORDER BY nome";

        try (Connection c = Conexao.conectar();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                ResponsavelLegal rl = new ResponsavelLegal();

                rl.setId(rs.getInt("id"));
                rl.setNome(rs.getString("nome"));
                rl.setParentesco(rs.getString("parentesco"));
                rl.setCpf(rs.getString("cpf"));
                rl.setTelefone(rs.getString("telefone"));
                rl.setEmail(rs.getString("email"));

                lista.add(rl);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar responsáveis legais:");
            e.printStackTrace();
        }

        return lista;
    }

    // Excluir responsável legal
    public boolean excluir(int id) {

        String sql = "DELETE FROM responsavel_legal WHERE id = ?";

        try (Connection c = Conexao.conectar();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao excluir responsável legal:");
            e.printStackTrace();
            return false;
        }
    }
}
