package br.com.thalison.sistema_acompanhamento_Pessoa;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import br.com.thalison.sistema_acompanhamento.Conexao;

public class PessoaDAO {

    public Pessoa autenticar(String email, String senha) {
        String sql = "SELECT * FROM pessoa WHERE email = ? AND senha = ?";
        try (Connection c = Conexao.conectar();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, senha);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean cadastrarUsuarioEscola(Pessoa p, String infoExtra1, String infoExtra2) {
        String sqlPessoa = "INSERT INTO pessoa (nome, cpf, telefone, email, senha, perfil) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection c = Conexao.conectar()) {
            c.setAutoCommit(false);
            try {
                int id;
                try (PreparedStatement ps = c.prepareStatement(sqlPessoa, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setString(1, p.getNome());
                    ps.setString(2, p.getCpf());
                    ps.setString(3, p.getTelefone());
                    ps.setString(4, p.getEmail());
                    ps.setString(5, p.getSenha());
                    ps.setString(6, p.getPerfil());
                    ps.executeUpdate();
                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (!rs.next()) throw new SQLException("Falha ao gerar ID.");
                        id = rs.getInt(1);
                    }
                }

                switch (p.getPerfil()) {
                    case "SECRETARIA":
                        try (PreparedStatement ps = c.prepareStatement("INSERT INTO secretaria (pessoaId, setor) VALUES (?, ?)")) {
                            ps.setInt(1, id);
                            ps.setString(2, infoExtra1 != null && !infoExtra1.isEmpty() ? infoExtra1 : "Secretaria Geral");
                            ps.executeUpdate();
                        }
                        break;
                    case "PSICOLOGO":
                        try (PreparedStatement ps = c.prepareStatement("INSERT INTO psicologo (pessoaId, crp, especialidade) VALUES (?, ?, ?)")) {
                            ps.setInt(1, id);
                            ps.setString(2, infoExtra1);
                            ps.setString(3, infoExtra2);
                            ps.executeUpdate();
                        }
                        break;
                    case "PEDAGOGO":
                        try (PreparedStatement ps = c.prepareStatement("INSERT INTO pedagogo (pessoaId, especialidade) VALUES (?, ?)")) {
                            ps.setInt(1, id);
                            ps.setString(2, infoExtra1);
                            ps.executeUpdate();
                        }
                        break;
                    case "PROFESSOR":
                        try (PreparedStatement ps = c.prepareStatement("INSERT INTO professor (pessoaId, formacao, especialidade) VALUES (?, ?, ?)")) {
                            ps.setInt(1, id);
                            ps.setString(2, infoExtra1);
                            ps.setString(3, infoExtra2);
                            ps.executeUpdate();
                        }
                        break;
                    default:
                        break;
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

    public List<Pessoa> listarTodos() {
        List<Pessoa> lista = new ArrayList<>();
        String sql = "SELECT * FROM pessoa ORDER BY perfil, nome";
        try (Connection c = Conexao.conectar();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public boolean excluir(int id) {
        String sql = "DELETE FROM pessoa WHERE id = ?";
        try (Connection c = Conexao.conectar();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Pessoa mapear(ResultSet rs) throws SQLException {
        Pessoa p = new Pessoa();
        p.setId(rs.getInt("id"));
        p.setNome(rs.getString("nome"));
        p.setCpf(rs.getString("cpf"));
        p.setTelefone(rs.getString("telefone"));
        p.setEmail(rs.getString("email"));
        p.setSenha(rs.getString("senha"));
        p.setPerfil(rs.getString("perfil"));
        return p;
    }
}