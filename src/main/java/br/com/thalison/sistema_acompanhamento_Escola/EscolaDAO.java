package br.com.thalison.sistema_acompanhamento_Escola;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.thalison.sistema_acompanhamento.Conexao;

public class EscolaDAO {

    public boolean cadastrar(Escola escola) {
        String sql = "INSERT INTO escola (nome, endereco, telefone, email) VALUES (?, ?, ?, ?)";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, escola.getNome());
            stmt.setString(2, escola.getEndereco());
            stmt.setString(3, escola.getTelefone());
            stmt.setString(4, escola.getEmail());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar escola: " + e.getMessage());
            return false;
        }
    }

    public List<Escola> listar() {
        List<Escola> escolas = new ArrayList<>();
        String sql = "SELECT * FROM escola";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Escola escola = new Escola();
                escola.setId(rs.getInt("id"));
                escola.setNome(rs.getString("nome"));
                escola.setEndereco(rs.getString("endereco"));
                escola.setTelefone(rs.getString("telefone"));
                escola.setEmail(rs.getString("email"));

                escolas.add(escola);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar escolas: " + e.getMessage());
        }

        return escolas;
    }

    public Escola buscarPorId(int id) {
        String sql = "SELECT * FROM escola WHERE id = ?";
        Escola escola = null;

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    escola = new Escola();
                    escola.setId(rs.getInt("id"));
                    escola.setNome(rs.getString("nome"));
                    escola.setEndereco(rs.getString("endereco"));
                    escola.setTelefone(rs.getString("telefone"));
                    escola.setEmail(rs.getString("email"));
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar escola: " + e.getMessage());
        }

        return escola;
    }

    public boolean atualizar(Escola escola) {
        String sql = "UPDATE escola SET nome = ?, endereco = ?, telefone = ?, email = ? WHERE id = ?";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, escola.getNome());
            stmt.setString(2, escola.getEndereco());
            stmt.setString(3, escola.getTelefone());
            stmt.setString(4, escola.getEmail());
            stmt.setInt(5, escola.getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar escola: " + e.getMessage());
            return false;
        }
    }

    public boolean excluir(int id) {
        String sql = "DELETE FROM escola WHERE id = ?";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao excluir escola: " + e.getMessage());
            return false;
        }
    }
}