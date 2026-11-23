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
        // no banco, pedagogo.pessoa é o id da pessoa (tabela pessoa); o nome fica em pessoa.nome
        String sql = "INSERT INTO pedagogo (pessoa, especialidade) VALUES (?, ?)";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             PreparedStatement nome = conexao.prepareStatement("UPDATE pessoa SET nome = ? WHERE id = ?")) {

            stmt.setInt(1, pedagogo.getIdPedagogo());
            stmt.setString(2, pedagogo.getEspecialidade());
            boolean ok = stmt.executeUpdate() > 0;

            nome.setString(1, pedagogo.getPessoa());
            nome.setInt(2, pedagogo.getIdPedagogo());
            nome.executeUpdate();

            return ok;

        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar pedagogo: " + e.getMessage());
            return false;
        }
    }

    public List<Pedagogo> listar() {
        List<Pedagogo> pedagogos = new ArrayList<>();
        String sql = "SELECT pe.pessoa, p.nome, pe.especialidade FROM pedagogo pe JOIN pessoa p ON p.id = pe.pessoa";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                pedagogos.add(new Pedagogo(
                    rs.getInt("pessoa"),
                    rs.getString("nome"),
                    rs.getString("especialidade")
                ));
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar pedagogos: " + e.getMessage());
        }

        return pedagogos;
    }

    public Pedagogo buscarPorId(int id) {
        String sql = "SELECT pe.pessoa, p.nome, pe.especialidade FROM pedagogo pe JOIN pessoa p ON p.id = pe.pessoa WHERE pe.pessoa = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Pedagogo(
                        rs.getInt("pessoa"),
                        rs.getString("nome"),
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
        String sql = "UPDATE pedagogo SET especialidade = ? WHERE pessoa = ?";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             PreparedStatement nome = conexao.prepareStatement("UPDATE pessoa SET nome = ? WHERE id = ?")) {

            stmt.setString(1, pedagogo.getEspecialidade());
            stmt.setInt(2, pedagogo.getIdPedagogo());
            boolean ok = stmt.executeUpdate() > 0;

            nome.setString(1, pedagogo.getPessoa());
            nome.setInt(2, pedagogo.getIdPedagogo());
            nome.executeUpdate();

            return ok;

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar pedagogo: " + e.getMessage());
            return false;
        }
    }

    // Registra reuniões com Psicólogo, Responsável Legal, Diretor ou Aluno
    public boolean registrarReuniao(int pedagogoId, String publicoAlvo, int alunoId, String dataReuniao, String motivo) {
        String sql = "INSERT INTO mensagem (remetenteId, destinatarioId, conteudoMensagem, dataEnvioMensagem, lidaMensagem) "
                   + "VALUES (?, ?, ?, NOW(), false)";

        String conteudo = "Reunião com " + publicoAlvo
                        + (alunoId > 0 ? " | Aluno ID: " + alunoId : "")
                        + " | Data: " + dataReuniao
                        + " | Assunto: " + motivo;

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, pedagogoId > 0 ? pedagogoId : 1);
            if (alunoId > 0) stmt.setInt(2, alunoId); else stmt.setNull(2, java.sql.Types.INTEGER);
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
        String sql = "SELECT mensagemId, remetenteId, destinatarioId, conteudoMensagem, dataEnvioMensagem "
                   + "FROM mensagem ORDER BY mensagemId DESC";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                reunioes.add(new String[]{
                    String.valueOf(rs.getInt("mensagemId")),
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