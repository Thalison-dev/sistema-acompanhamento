package br.com.thalison.sistema_acompanhamento_ResponsavelLegal;

import java.sql.*;

import br.com.thalison.sistema_acompanhamento.Conexao;

public class ResponsavelLegalDAO {

    public ResponsavelLegal consultarResponsavelLegal(int id) {
        String sql = "SELECT p.id, p.nome " +
                     "FROM pessoa p " +
                     "INNER JOIN responsavel_legal rl ON p.id = rl.responsavelLegal " +
                     "WHERE rl.responsavelLegal = ?";

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
        String sql = "INSERT INTO mensagem (remetenteId, destinatarioId, conteudoMensagem, dataEnvioMensagem) " +
                     "VALUES (?, ?, ?, NOW())";

        try (Connection c = Conexao.conectar();
             PreparedStatement p = c.prepareStatement(sql)) {

            p.setInt(1, responsavelLegalId);
            p.setInt(2, pedagogoId);
            p.setString(3, "Reunião com Pedagogo | " + reuniao);

            int linhasAfetadas = p.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao marcar reunião com pedagogo: " + e.getMessage());
            return false;
        }
    }
}