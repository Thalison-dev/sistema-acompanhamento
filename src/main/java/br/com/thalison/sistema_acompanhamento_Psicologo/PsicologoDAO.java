
package br.com.thalison.sistema_acompanhamento_Psicologo;

import br.com.thalison.sistema_acompanhamento.*;
import java.sql.*;

public class PsicologoDAO {

    public Psicologo consultarPsicologo(int id) {
        String sql = "SELECT p.id, p.nome, ps.especialidade " +
                     "FROM pessoa p " +
                     "INNER JOIN psicologo ps ON p.id = ps.pessoaId " +
                     "WHERE ps.pessoaId = ?";

        try (Connection c = Conexao.conectar();
             PreparedStatement p = c.prepareStatement(sql)) {

            p.setInt(1, id);

            try (ResultSet r = p.executeQuery()) {
                if (r.next()) {
                    Psicologo psicologo = new Psicologo();
                    psicologo.setId(r.getInt("id"));
                    psicologo.setNome(r.getString("nome"));
                    psicologo.setEspecialidade(r.getString("especialidade"));
                    return psicologo;
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao consultar psicólogo: " + e.getMessage());
        }

        return null;
    }

    public boolean marcarReuniaoComAluno(int psicologoId, int alunoId,
                                         String reuniao) {
        String sql = "UPDATE psicologo SET alunoId = ?, reuniao = ? " +
                     "WHERE pessoaId = ?";

        try (Connection c = Conexao.conectar();
             PreparedStatement p = c.prepareStatement(sql)) {

            p.setInt(1, alunoId);
            p.setString(2, reuniao);
            p.setInt(3, psicologoId);

            return p.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao marcar reunião: " + e.getMessage());
            return false;
        }
    }

    public boolean marcarReuniaoComPedagogo(int psicologoId,
                                            int pedagogoId,
                                            String reuniao) {
        String sql = "INSERT INTO mensagem " +
                     "(remetenteId, destinatarioId, conteudoMensagem, dataEnvioMensagem) " +
                     "VALUES (?, ?, ?, NOW())";

        try (Connection c = Conexao.conectar();
             PreparedStatement p = c.prepareStatement(sql)) {

            p.setInt(1, psicologoId);
            p.setInt(2, pedagogoId);
            p.setString(3, "Solicitação de reunião: " + reuniao);

            return p.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao solicitar reunião: " + e.getMessage());
            return false;
        }
    }

    public boolean atualizarRegistroDoAluno(int alunoId, String registro) {
        String sql = "UPDATE psicologo SET reuniao = ? WHERE alunoId = ?";

        try (Connection c = Conexao.conectar();
             PreparedStatement p = c.prepareStatement(sql)) {

            p.setString(1, registro);
            p.setInt(2, alunoId);

            return p.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar registro: " + e.getMessage());
            return false;
        }
    }

    public void verRegistroDoAluno(int alunoId) {
        String sql = "SELECT alunoId, reuniao FROM psicologo WHERE alunoId = ?";

        try (Connection c = Conexao.conectar();
             PreparedStatement p = c.prepareStatement(sql)) {

            p.setInt(1, alunoId);

            try (ResultSet r = p.executeQuery()) {
                while (r.next()) {
                    System.out.println("Aluno: " + r.getInt("alunoId"));
                    System.out.println("Registro: " + r.getString("reuniao"));
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao consultar registro: " + e.getMessage());
        }
    }
}
