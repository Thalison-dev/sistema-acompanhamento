
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

        String sql = "INSERT INTO acompanhamento "
                + "(id, dataInicio, dataFim, objetivo, status) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, acompanhamento.getid());
            stmt.setString(2, acompanhamento.dataInicio());
            stmt.setString(3, acompanhamento.getdataFim());
            stmt.setString(4, acompanhamento.getobjetivo());
            stmt.setString(5, acompanhamento.getstatus());

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

                acompanhamento.setid(rs.getString("id"));
                acompanhamento.setdataIncio(rs.getString("dataInicio"));
                acompanhamento.setdataFim(rs.getString("dataFim"));
                acompanhamento.setobjetivo(rs.getString("objetivo"));
                acompanhamento.setstatus(rs.getString("status"));

                acompanhamentos.add(acompanhamento);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar acompanhamentos: " + e.getMessage());
        }

        return acompanhamentos;
    }

    
    public boolean atualizar(Acompanhamento acompanhamento) {

        String sql = "UPDATE acompanhamento SET "
                + "dataInicio = ?, dataFim = ?, objetivo = ?, status = ? "
                + "WHERE id = ?";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, acompanhamento.dataInicio());
            stmt.setString(2, acompanhamento.getdataFim());
            stmt.setString(3, acompanhamento.getobjetivo());
            stmt.setString(4, acompanhamento.getstatus());
            stmt.setString(5, acompanhamento.getid());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar acompanhamento: " + e.getMessage());
            return false;
        }
    }

    
    public boolean excluir(String id) {

        String sql = "DELETE FROM acompanhamento WHERE id = ?";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, id);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao excluir acompanhamento: " + e.getMessage());
            return false;
        }
    }
}
