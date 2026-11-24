package br.com.thalison.sistema_acompanhamento_Pedagogo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.thalison.sistema_acompanhamento.Conexao;

public class PedagogoDAO {

    public boolean salvarPerfil(int pessoaId, String nome, String especialidade) {
        String sqlPessoa = "UPDATE pessoa SET nome = ? WHERE id = ?";
        String sqlPedagogo = "INSERT INTO pedagogo (pessoaId, especialidade) VALUES (?, ?) "
                           + "ON DUPLICATE KEY UPDATE especialidade = VALUES(especialidade)";

        try (Connection c = Conexao.conectar()) {
            c.setAutoCommit(false);
            try {
                try (PreparedStatement psPessoa = c.prepareStatement(sqlPessoa)) {
                    psPessoa.setString(1, nome);
                    psPessoa.setInt(2, pessoaId);
                    psPessoa.executeUpdate();
                }

                try (PreparedStatement psPed = c.prepareStatement(sqlPedagogo)) {
                    psPed.setInt(1, pessoaId);
                    psPed.setString(2, especialidade);
                    psPed.executeUpdate();
                }

                c.commit();
                return true;
            } catch (SQLException e) {
                c.rollback();
                throw e;
            } finally {
                c.setAutoCommit(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Pedagogo> listar() {
        List<Pedagogo> lista = new ArrayList<>();
        String sql = "SELECT p.id, p.nome, p.cpf, p.telefone, p.email, p.perfil, ped.especialidade "
                   + "FROM pessoa p "
                   + "INNER JOIN pedagogo ped ON ped.pessoaId = p.id "
                   + "ORDER BY p.nome";

        try (Connection c = Conexao.conectar();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Pedagogo ped = new Pedagogo();
                ped.setId(rs.getInt("id"));
                ped.setNome(rs.getString("nome"));
                ped.setCpf(rs.getString("cpf"));
                ped.setTelefone(rs.getString("telefone"));
                ped.setEmail(rs.getString("email"));
                ped.setPerfil(rs.getString("perfil"));
                ped.setEspecialidade(rs.getString("especialidade"));
                lista.add(ped);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public Pedagogo buscarPorId(int id) {
        String sql = "SELECT p.id, p.nome, p.cpf, p.telefone, p.email, p.perfil, ped.especialidade "
                   + "FROM pessoa p "
                   + "LEFT JOIN pedagogo ped ON ped.pessoaId = p.id "
                   + "WHERE p.id = ?";

        try (Connection c = Conexao.conectar();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Pedagogo ped = new Pedagogo();
                    ped.setId(rs.getInt("id"));
                    ped.setNome(rs.getString("nome"));
                    ped.setCpf(rs.getString("cpf"));
                    ped.setTelefone(rs.getString("telefone"));
                    ped.setEmail(rs.getString("email"));
                    ped.setPerfil(rs.getString("perfil"));
                    ped.setEspecialidade(rs.getString("especialidade"));
                    return ped;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}