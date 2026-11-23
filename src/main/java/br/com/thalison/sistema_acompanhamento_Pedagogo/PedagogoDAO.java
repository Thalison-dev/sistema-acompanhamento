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
        String sql = "INSERT INTO pedagogo (idPedagogo, pessoa, especialidade) VALUES (?, ?, ?)";

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
                pedagogos.add(new Pedagogo(
                    rs.getInt("idPedagogo"),
                    rs.getString("pessoa"),
                    rs.getString("especialidade")
                ));
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar pedagogos: " + e.getMessage());
        }

        return pedagogos;
    }

    public Pedagogo buscarPorId(int id) {
        String sql = "SELECT * FROM pedagogo WHERE idPedagogo = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Pedagogo(
                        rs.getInt("idPedagogo"),
                        rs.getString("pessoa"),
                        rs.getString("especialidade")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar pedagogo: " + e.getMessage());
        }

        return null;
    }

    public boolean atualizar(Pedagogo pedagogo) {
        String sql = "UPDATE pedagogo SET pessoa = ?, especialidade = ? WHERE idPedagogo = ?";

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

    // Registra reuniões com Psicólogo, Responsável Legal, Diretor ou Aluno
    public boolean registrarReuniao(int pedagogoId, String publicoAlvo, int alunoId, String dataReuniao, String motivo) {
        String sql = "INSERT INTO mensagem (remetenteId, destinatarioId, conteudoMensagem, dataEnvioMensagem, lida) "
                   + "VALUES (?, ?, ?, NOW(), false)";

        String conteudo = "Reunião com " + publicoAlvo
                        + (alunoId > 0 ? " | Aluno ID: " + alunoId : "")
                        + " | Data: " + dataReuniao
                        + " | Assunto: " + motivo;

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, pedagogoId > 0 ? pedagogoId : 1);
            stmt.setInt(2, alunoId > 0 ? alunoId : 0);
            stmt.setString(3, conteudo);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao registrar reunião: " + e.getMessage());
            return false;
        }
    }

    // Lista as reuniões agendadas pelo pedagogo
    public List<String[]> listarReunioes() {
        List<String[]> reunioes = new ArrayList<>();
        String sql = "SELECT id, remetenteId, destinatarioId, conteudoMensagem, dataEnvioMensagem "
                   + "FROM mensagem ORDER BY id DESC";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                reunioes.add(new String[]{
                    String.valueOf(rs.getInt("id")),
                    String.valueOf(rs.getInt("remetenteId")),
                    String.valueOf(rs.getInt("destinatarioId")),
                    rs.getString("conteudoMensagem"),
                    rs.getString("dataEnvioMensagem")
                });
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar reuniões: " + e.getMessage());
        }

        return reunioes;
    }
}