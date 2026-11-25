package br.com.thalison.sistema_acompanhamento_Pedagogo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import br.com.thalison.sistema_acompanhamento.Conexao;

public class PedagogoDAO {

    public boolean atualizarPerfil(int idPedagogo, String nome, String especialidade) {
        String sqlPessoa = "UPDATE pessoa SET nome = ? WHERE id = ?";
        String sqlPedagogo = "UPDATE pedagogo SET especialidade = ? WHERE pessoaId = ?";
        String sqlInsertPedagogo = "INSERT INTO pedagogo (pessoaId, especialidade) VALUES (?, ?)";

        try (Connection c = Conexao.conectar()) {
            c.setAutoCommit(false);
            try {
                if (nome != null && !nome.trim().isEmpty()) {
                    try (PreparedStatement psPessoa = c.prepareStatement(sqlPessoa)) {
                        psPessoa.setString(1, nome.trim());
                        psPessoa.setInt(2, idPedagogo);
                        psPessoa.executeUpdate();
                    }
                }

                int afetadas;
                try (PreparedStatement psPed = c.prepareStatement(sqlPedagogo)) {
                    psPed.setString(1, especialidade != null ? especialidade.trim() : "");
                    psPed.setInt(2, idPedagogo);
                    afetadas = psPed.executeUpdate();
                }

                if (afetadas == 0) {
                    try (PreparedStatement psIns = c.prepareStatement(sqlInsertPedagogo)) {
                        psIns.setInt(1, idPedagogo);
                        psIns.setString(2, especialidade != null ? especialidade.trim() : "");
                        psIns.executeUpdate();
                    }
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
        String sql = "SELECT p.id, p.nome, p.email, COALESCE(pd.especialidade, '') AS especialidade "
                   + "FROM pessoa p "
                   + "LEFT JOIN pedagogo pd ON p.id = pd.pessoaId "
                   + "WHERE p.perfil = 'PEDAGOGO' OR pd.pessoaId IS NOT NULL "
                   + "ORDER BY p.nome";

        try (Connection c = Conexao.conectar();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Pedagogo p = new Pedagogo();
                p.setId(rs.getInt("id"));
                p.setNome(rs.getString("nome"));
                p.setEmail(rs.getString("email"));
                p.setEspecialidade(rs.getString("especialidade"));
                lista.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public Pedagogo buscarPorId(int idPedagogo) {
        String sql = "SELECT p.id, p.nome, p.email, COALESCE(pd.especialidade, '') AS especialidade "
                   + "FROM pessoa p "
                   + "LEFT JOIN pedagogo pd ON p.id = pd.pessoaId "
                   + "WHERE p.id = ?";

        try (Connection c = Conexao.conectar();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, idPedagogo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Pedagogo p = new Pedagogo();
                    p.setId(rs.getInt("id"));
                    p.setNome(rs.getString("nome"));
                    p.setEmail(rs.getString("email"));
                    p.setEspecialidade(rs.getString("especialidade"));
                    return p;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}