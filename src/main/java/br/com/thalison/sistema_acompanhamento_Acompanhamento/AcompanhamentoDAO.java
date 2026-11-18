
package br.com.thalison.sistema_acompanhamento_Acompanhamento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import br.com.thalison.sistema_acompanhamento.Conexao;

public class AcompanhamentoDAO {

    public boolean cadastrar(Acompanhamento acompanhamento) {

        String sql = "INSERT INTO acompanhamento "
                + "(alunoId, dataInicioAcompanhamento, "
                + "dataFimAcompanhamento, objetivoAcompanhamento, "
                + "statusAcompanhamento) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, acompanhamento.getAlunoId());

            stmt.setDate(2, Date.valueOf(acompanhamento.getDataInicio()));

            if (acompanhamento.getDataFim() == null
                    || acompanhamento.getDataFim().isEmpty()) {
                stmt.setNull(3, java.sql.Types.DATE);
            } else {
                stmt.setDate(3, Date.valueOf(acompanhamento.getDataFim()));
            }

            stmt.setString(4, acompanhamento.getObjetivo());
            stmt.setString(5, acompanhamento.getStatus());

            return stmt.executeUpdate() > 0;

        } catch (SQLException | IllegalArgumentException e) {
            System.out.println("Erro ao cadastrar acompanhamento: "
                    + e.getMessage());
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

                acompanhamento.setAcompanhamentoId(
                        rs.getInt("acompanhamentoId"));

                acompanhamento.setAlunoId(
                        rs.getInt("alunoId"));

                Date inicio = rs.getDate("dataInicioAcompanhamento");
                Date fim = rs.getDate("dataFimAcompanhamento");

                acompanhamento.setDataInicio(
                        inicio != null ? inicio.toString() : null);

                acompanhamento.setDataFim(
                        fim != null ? fim.toString() : null);

                acompanhamento.setObjetivo(
                        rs.getString("objetivoAcompanhamento"));

                acompanhamento.setStatus(
                        rs.getString("statusAcompanhamento"));

                acompanhamentos.add(acompanhamento);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar acompanhamentos: "
                    + e.getMessage());
        }

        return acompanhamentos;
    }

    public boolean atualizar(Acompanhamento acompanhamento) {

        String sql = "UPDATE acompanhamento SET "
                + "alunoId = ?, "
                + "dataInicioAcompanhamento = ?, "
                + "dataFimAcompanhamento = ?, "
                + "objetivoAcompanhamento = ?, "
                + "statusAcompanhamento = ? "
                + "WHERE acompanhamentoId = ?";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, acompanhamento.getAlunoId());

            stmt.setDate(2, Date.valueOf(acompanhamento.getDataInicio()));

            if (acompanhamento.getDataFim() == null
                    || acompanhamento.getDataFim().isEmpty()) {
                stmt.setNull(3, java.sql.Types.DATE);
            } else {
                stmt.setDate(3, Date.valueOf(acompanhamento.getDataFim()));
            }

            stmt.setString(4, acompanhamento.getObjetivo());
            stmt.setString(5, acompanhamento.getStatus());
            stmt.setInt(6, acompanhamento.getAcompanhamentoId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException | IllegalArgumentException e) {
            System.out.println("Erro ao atualizar acompanhamento: "
                    + e.getMessage());
            return false;
        }
    }

    public boolean excluir(int id) {

        String sql = "DELETE FROM acompanhamento "
                + "WHERE acompanhamentoId = ?";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao excluir acompanhamento: "
                    + e.getMessage());
            return false;
        }
    }
}
