package br.com.thalison.sistema_acompanhamento_ResponsavelLegal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import br.com.thalison.sistema_acompanhamento.Conexao;
import br.com.thalison.sistema_acompanhamento_Pessoa.PessoaDAO;
import br.com.thalison.sistema_acompanhamento_Reuniao.Reuniao;
import br.com.thalison.sistema_acompanhamento_Reuniao.ReuniaoDAO;

public class ResponsavelLegalDAO {

    private static final String SELECT_BASE =
        "SELECT p.id, p.nome, p.CPF, p.telefone, p.email, rl.parentescoLegal "
        + "FROM responsavel_legal rl INNER JOIN pessoa p ON p.id = rl.responsavelLegal ";

    private final PessoaDAO pessoaDao = new PessoaDAO();

    public boolean cadastrar(ResponsavelLegal r) {
        try (Connection c = Conexao.conectar()) {
            c.setAutoCommit(false);
            try {
                int id = pessoaDao.inserir(c, r);
                try (PreparedStatement p = c.prepareStatement(
                        "INSERT INTO responsavel_legal (responsavelLegal, parentescoLegal) VALUES (?, ?)")) {
                    p.setInt(1, id);
                    p.setString(2, r.getParentesco());
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
            System.out.println("Erro ao cadastrar responsável legal: " + e.getMessage());
            return false;
        }
    }

    public List<ResponsavelLegal> listar() {
        List<ResponsavelLegal> lista = new ArrayList<>();
        try (Connection c = Conexao.conectar();
             PreparedStatement p = c.prepareStatement(SELECT_BASE + "ORDER BY p.nome");
             ResultSet r = p.executeQuery()) {
            while (r.next()) {
                lista.add(mapear(r));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar responsáveis: " + e.getMessage());
        }
        return lista;
    }

    public ResponsavelLegal consultarResponsavelLegal(int id) {
        try (Connection c = Conexao.conectar();
             PreparedStatement p = c.prepareStatement(SELECT_BASE + "WHERE rl.responsavelLegal = ?")) {

            p.setInt(1, id);

            try (ResultSet r = p.executeQuery()) {
                if (r.next()) {
                    return mapear(r);
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao consultar Responsavel legal: " + e.getMessage());
        }

        return null;
    }

    public boolean atualizar(ResponsavelLegal r) {
        try (Connection c = Conexao.conectar()) {
            c.setAutoCommit(false);
            try {
                pessoaDao.atualizar(c, r);
                try (PreparedStatement p = c.prepareStatement(
                        "UPDATE responsavel_legal SET parentescoLegal = ? WHERE responsavelLegal = ?")) {
                    p.setString(1, r.getParentesco());
                    p.setInt(2, r.getId());
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
            System.out.println("Erro ao atualizar responsável: " + e.getMessage());
            return false;
        }
    }

    /** Desvincula os alunos do responsável e o exclui. */
    public boolean excluir(int id) {
        try (Connection c = Conexao.conectar()) {
            c.setAutoCommit(false);
            try {
                try (PreparedStatement p = c.prepareStatement(
                        "UPDATE aluno SET responsavelId = NULL WHERE responsavelId = ?")) {
                    p.setInt(1, id);
                    p.executeUpdate();
                }
                try (PreparedStatement p = c.prepareStatement(
                        "DELETE FROM responsavel_legal WHERE responsavelLegal = ?")) {
                    p.setInt(1, id);
                    if (p.executeUpdate() == 0) {
                        throw new SQLException("Responsável não encontrado.");
                    }
                }
                pessoaDao.excluir(c, id);
                c.commit();
                return true;
            } catch (SQLException e) {
                c.rollback();
                throw e;
            } finally {
                c.setAutoCommit(true);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao excluir responsável: " + e.getMessage());
            return false;
        }
    }

    public boolean marcarReuniaoComPedagogo(int responsavelLegalId, int alunoId, String dataReuniao, String motivo) {
        Reuniao r = new Reuniao();
        r.setSolicitanteTipo("Responsável Legal");
        r.setSolicitanteId(responsavelLegalId);
        r.setPublicoAlvo("Pedagogo");
        r.setAlunoId(alunoId);
        r.setDataReuniao(dataReuniao);
        r.setMotivo(motivo);
        return new ReuniaoDAO().agendar(r);
    }

    private ResponsavelLegal mapear(ResultSet r) throws SQLException {
        ResponsavelLegal responsavelLegal = new ResponsavelLegal();
        PessoaDAO.preencher(responsavelLegal, r);
        responsavelLegal.setParentesco(r.getString("parentescoLegal"));
        return responsavelLegal;
    }
}
