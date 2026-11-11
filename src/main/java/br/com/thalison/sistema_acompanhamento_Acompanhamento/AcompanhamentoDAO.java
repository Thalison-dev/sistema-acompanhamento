package br.com.thalison.sistema_acompanhamento_Acompanhamento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.thalison.sistema_acompanhamento.Conexao;

public class AcompanhamentoDAO {

    public boolean cadastrar(Acompanhamento acompanhamento) {
        String sql = "INSERT INTO acompanhamento (dataInicio, dataFim, objetivo, status) VALUES (?, ?, ?, ?)";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setDate(1, acompanhamento.getDataInicio());
            stmt.setDate(2, acompanhamento.getDataFim());
            stmt.setString(3, acompanhamento.getObjetivo());
            stmt.setString(4, acompanhamento.getStatus());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar acompanhamento: " + e.getMessage());
            return false;
        }
    }

    public List<Acompanhamento> listar() {
        List<Acompanhamento> acompanhamentos = new ArrayList<>();
        String sql = "SELECT * FROM acompanhamento";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Acompanhamento acompanhamento = new Acompanhamento();
                acompanhamento.setId(rs.getInt("id"));
                acompanhamento.setDataInicio(rs.getDate("dataInicio"));
                acompanhamento.setDataFim(rs.getDate("dataFim"));
                acompanhamento.setObjetivo(rs.getString("objetivo"));
                acompanhamento.setStatus(rs.getString("status"));

                acompanhamentos.add(acompanhamento);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar acompanhamentos: " + e.getMessage());
        }

        return acompanhamentos;
    }

    public Acompanhamento buscarPorId(int id) {
        String sql = "SELECT * FROM acompanhamento WHERE id = ?";
        Acompanhamento acompanhamento = null;

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    acompanhamento = new Acompanhamento();
                    acompanhamento.setId(rs.getInt("id"));
                    acompanhamento.setDataInicio(rs.getDate("dataInicio"));
                    acompanhamento.setDataFim(rs.getDate("dataFim"));
                    acompanhamento.setObjetivo(rs.getString("objetivo"));
                    acompanhamento.setStatus(rs.getString("status"));
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar acompanhamento: " + e.getMessage());
        }

        return acompanhamento;
    }

    public boolean atualizar(Acompanhamento acompanhamento) {
        String sql = "UPDATE acompanhamento SET dataInicio = ?, dataFim = ?, objetivo = ?, status = ? WHERE id = ?";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setDate(1, acompanhamento.getDataInicio());
            stmt.setDate(2, acompanhamento.getDataFim());
            stmt.setString(3, acompanhamento.getObjetivo());
            stmt.setString(4, acompanhamento.getStatus());
            stmt.setInt(5, acompanhamento.getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar acompanhamento: " + e.getMessage());
            return false;
        }
    }

    public boolean excluir(int id) {
        String sql = "DELETE FROM acompanhamento WHERE id = ?";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao excluir acompanhamento: " + e.getMessage());
            return false;
        }
    }
}