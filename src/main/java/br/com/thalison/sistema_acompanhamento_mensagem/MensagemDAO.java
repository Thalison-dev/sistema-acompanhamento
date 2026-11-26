package br.com.thalison.sistema_acompanhamento_mensagem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import br.com.thalison.sistema_acompanhamento.Conexao;

public class MensagemDAO {

    public boolean enviar(Mensagem m) {
        String sql = "INSERT INTO mensagem (remetenteId, destinatarioId, conteudoMensagem, dataEnvioMensagem) VALUES (?, ?, ?, NOW())";
        try (Connection c = Conexao.conectar();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, m.getRemetenteId());
            ps.setInt(2, m.getDestinatarioId());
            ps.setString(3, m.getConteudoMensagem());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Mensagem> listarPorUsuario(int usuarioId) {
        List<Mensagem> lista = new ArrayList<>();
        String sql = "SELECT * FROM mensagem WHERE remetenteId = ? OR destinatarioId = ? ORDER BY dataEnvioMensagem DESC";
        try (Connection c = Conexao.conectar();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, usuarioId);
            ps.setInt(2, usuarioId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Mensagem m = new Mensagem();
                    m.setId(rs.getInt("id"));
                    m.setRemetenteId(rs.getInt("remetenteId"));
                    m.setDestinatarioId(rs.getInt("destinatarioId"));
                    m.setConteudoMensagem(rs.getString("conteudoMensagem"));
                    m.setDataEnvioMensagem(rs.getTimestamp("dataEnvioMensagem"));
                    lista.add(m);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}