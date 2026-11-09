package br.com.thalison.sistema_acompanhamento_Pedagogo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.thalison.sistema_acompanhamento.Conexao;

public class PedagogoDAO {

    public boolean cadastrar(Pedagogo pedagogo) {

        String sql = "INSERT INTO pedagogo "
                + "(idPedagogo, pessoa, especialidade) "
                + "VALUES (?, ?, ?)";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, pedagogo.getIdPedagogo());
            stmt.setString(2, pedagogo.getPessoa());
            stmt.setString(3, pedagogo.getEspecialidade());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar pedagogo: " + e.getMessage());
            return false;
        }
    }

    public List<Pedagogo> listar() {

        List<Pedagogo> pedagogos = new ArrayList<>();

        String sql = "SELECT * FROM pedagogo";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                Pedagogo pedagogo = new Pedagogo(
                    rs.getInt("idPedagogo"),
                    rs.getString("pessoa"),
                    rs.getString("especialidade")
                );

                pedagogos.add(pedagogo);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar pedagogos: " + e.getMessage());
        }

        return pedagogos;
    }

    public boolean atualizar(Pedagogo pedagogo) {

        String sql = "UPDATE pedagogo SET "
                + "pessoa = ?, especialidade = ? "
                + "WHERE idPedagogo = ?";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, pedagogo.getPessoa());
            stmt.setString(2, pedagogo.getEspecialidade());
            stmt.setInt(3, pedagogo.getIdPedagogo());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar pedagogo: " + e.getMessage());
            return false;
        }
    }

    public boolean excluir(int idPedagogo) {

        String sql = "DELETE FROM pedagogo WHERE idPedagogo = ?";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, idPedagogo);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao excluir pedagogo: " + e.getMessage());
            return false;
        }
    }

    public Pedagogo buscarPorId(int id) {
        Pedagogo pedagogo = null;
        String sql = "SELECT * FROM pedagogo WHERE idPedagogo = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    pedagogo = new Pedagogo(
                        rs.getInt("idPedagogo"),
                        rs.getString("pessoa"),
                        rs.getString("especialidade")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar pedagogo por ID: " + e.getMessage());
        }

        return pedagogo;
    }
}