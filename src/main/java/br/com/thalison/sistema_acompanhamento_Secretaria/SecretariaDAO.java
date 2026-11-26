package br.com.thalison.sistema_acompanhamento_Secretaria;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import br.com.thalison.sistema_acompanhamento.Conexao;

public class SecretariaDAO {

    public List<Secretaria> listarTodas() {
        List<Secretaria> lista = new ArrayList<>();
        String sql = "SELECT p.id, p.nome, p.cpf, p.telefone, p.email, COALESCE(s.setor, 'Secretaria Geral') AS setor "
                   + "FROM pessoa p "
                   + "LEFT JOIN secretaria s ON p.id = s.pessoaId "
                   + "WHERE p.perfil = 'SECRETARIA' "
                   + "ORDER BY p.nome";

        try (Connection c = Conexao.conectar();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Secretaria s = new Secretaria();
                s.setId(rs.getInt("id"));
                s.setNome(rs.getString("nome"));
                s.setCpf(rs.getString("cpf"));
                s.setTelefone(rs.getString("telefone"));
                s.setEmail(rs.getString("email"));
                s.setSetor(rs.getString("setor"));
                lista.add(s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}