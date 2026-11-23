package br.com.thalison.sistema_acompanhamento_ResponsavelLegal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.thalison.sistema_acompanhamento.Conexao;

public class ResponsavelLegalDAO {

    public boolean cadastrar(ResponsavelLegal rl) {
        String sqlPessoa = "INSERT INTO pessoa (nome, email, senha, telefone, CPF) VALUES (?, ?, ?, ?, ?)";
        String sqlResp = "INSERT INTO ResponsavelLegal (pessoaId, parentesco) VALUES (?, ?)";

        try (Connection c = Conexao.conectar()) {
            c.setAutoCommit(false);
            try {
                int id;
                try (PreparedStatement p = c.prepareStatement(sqlPessoa, Statement.RETURN_GENERATED_KEYS)) {
                    p.setString(1, rl.getNome());
                    p.setString(2, rl.getEmail());
                    p.setString(3, "123456");
                    p.setString(4, rl.getTelefone());
                    p.setString(5, rl.getCpf());
                    p.executeUpdate();

                    try (ResultSet r = p.getGeneratedKeys()) {
                        if (!r.next()) {
                            throw new SQLException("ID não gerado para Responsável Legal.");
                        }
                        id = r.getInt(1);
                    }
                }

                try (PreparedStatement p = c.prepareStatement(sqlResp)) {
                    p.setInt(1, id);
                    p.setString(2, rl.getParentesco());
                    p.executeUpdate();
                }

                c.commit();
                rl.setId(id);
                return true;

            } catch (SQLException e) {
                c.rollback();
                throw e;
            } finally {
                c.setAutoCommit(true);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar responsável legal: " + e.getMessage());
            return false;
        }
    }

    public List<ResponsavelLegal> listarTodos() {
        List<ResponsavelLegal> lista = new ArrayList<>();
        String sql = "SELECT p.id, p.nome, p.CPF, p.telefone, p.email, rl.parentesco "
                   + "FROM pessoa p "
                   + "INNER JOIN ResponsavelLegal rl ON p.id = rl.pessoaId "
                   + "ORDER BY p.nome";

        try (Connection c = Conexao.conectar();
             PreparedStatement p = c.prepareStatement(sql);
             ResultSet r = p.executeQuery()) {

            while (r.next()) {
                ResponsavelLegal rl = new ResponsavelLegal();
                rl.setId(r.getInt("id"));
                rl.setNome(r.getString("nome"));
                rl.setCpf(r.getString("CPF"));
                rl.setTelefone(r.getString("telefone"));
                rl.setEmail(r.getString("email"));
                rl.setParentesco(r.getString("parentesco"));
                lista.add(rl);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar responsáveis legais: " + e.getMessage());
        }
        return lista;
    }

    public ResponsavelLegal consultarResponsavelLegal(int id) {
        String sql = "SELECT p.id, p.nome, p.CPF, p.telefone, p.email, rl.parentesco "
                   + "FROM pessoa p "
                   + "INNER JOIN ResponsavelLegal rl ON p.id = rl.pessoaId "
                   + "WHERE rl.pessoaId = ?";

        try (Connection c = Conexao.conectar();
             PreparedStatement p = c.prepareStatement(sql)) {

            p.setInt(1, id);

            try (ResultSet r = p.executeQuery()) {
                if (r.next()) {
                    ResponsavelLegal responsavelLegal = new ResponsavelLegal();
                    responsavelLegal.setId(r.getInt("id"));
                    responsavelLegal.setNome(r.getString("nome"));
                    responsavelLegal.setCpf(r.getString("CPF"));
                    responsavelLegal.setTelefone(r.getString("telefone"));
                    responsavelLegal.setEmail(r.getString("email"));
                    responsavelLegal.setParentesco(r.getString("parentesco"));
                    return responsavelLegal;
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao consultar Responsavel legal: " + e.getMessage());
        }

        return null;
    }

    public boolean excluir(int id) {
        try (Connection c = Conexao.conectar()) {
            c.setAutoCommit(false);
            try {
                try (PreparedStatement p = c.prepareStatement("DELETE FROM ResponsavelLegal WHERE pessoaId = ?")) {
                    p.setInt(1, id);
                    p.executeUpdate();
                }
                try (PreparedStatement p = c.prepareStatement("DELETE FROM pessoa WHERE id = ?")) {
                    p.setInt(1, id);
                    p.executeUpdate();
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
            System.out.println("Erro ao excluir responsável legal: " + e.getMessage());
            return false;
        }
    }

    public boolean marcarReuniaoComPedagogo(int responsavelLegalId, int pedagogoId, String reuniao) {
        String sql = "UPDATE pedagogo SET ResponsavelLegal = ?, reuniao = ? WHERE idPedagogo = ?";

        try (Connection c = Conexao.conectar();
             PreparedStatement p = c.prepareStatement(sql)) {

            p.setInt(1, responsavelLegalId);
            p.setString(2, reuniao);
            p.setInt(3, pedagogoId);

            return p.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao marcar reunião com pedagogo: " + e.getMessage());
            return false;
        }
    }
}