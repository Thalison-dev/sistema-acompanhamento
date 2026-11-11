package br.com.thalison.sistema_acompanhamento_ResponsavelLegal;

import java.sql.*;

import br.com.thalison.sistema_acompanhamento.Conexao;

public class ResponsavelLegalDAO {

    public ResponsavelLegal consultarResponsavelLegal(int id) {
        String sql = "SELECT p.id, p.nome " +
                     "FROM pessoa p " +
                     "INNER JOIN ResponsavelLegal rl ON p.id = rl.pessoaId " +
                     "WHERE rl.pessoaId = ?";

        try (Connection c = Conexao.conectar();
             PreparedStatement p = c.prepareStatement(sql)) {

            p.setInt(1, id);

            try (ResultSet r = p.executeQuery()) {
                if (r.next()) {
                    ResponsavelLegal responsavelLegal = new ResponsavelLegal();
                    responsavelLegal.setId(r.getInt("id"));
                    responsavelLegal.setNome(r.getString("nome"));
                    return responsavelLegal;
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao consultar Responsavel legal: " + e.getMessage());
        }

        return null;
    }

    public boolean marcarReuniaoComPedagogo(int responsavelLegalId, int pedagogoId, String reuniao) {
        String sql = "UPDATE Pedagogo SET ResponsavelLegal = ?, reuniao = ? " +
                     "WHERE pessoaId = ?";

        try (Connection c = Conexao.conectar();
             PreparedStatement p = c.prepareStatement(sql)) {

            p.setInt(1, responsavelLegalId);
            p.setString(2, reuniao);
            p.setInt(3, pedagogoId);

            int linhasAfetadas = p.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao marcar reunião com pedagogo: " + e.getMessage());
            return false;
        }
    }
}