package br.com.thalison.sistema_acompanhamento_Psicologo;

import br.com.thalison.sistema_acompanhamento.Conexao;
import br.com.thalison.sistema_acompanhamento_Pessoa.PessoaDAO;
import br.com.thalison.sistema_acompanhamento_Reuniao.Reuniao;
import br.com.thalison.sistema_acompanhamento_Reuniao.ReuniaoDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PsicologoDAO {

    private static final String SELECT_BASE =
        "SELECT p.id, p.nome, p.CPF, p.telefone, p.email, ps.especialidade "
        + "FROM psicologo ps INNER JOIN pessoa p ON p.id = ps.pessoaId ";

    private final PessoaDAO pessoaDao = new PessoaDAO();

    public boolean cadastrar(Psicologo psicologo) {
        try (Connection c = Conexao.conectar()) {
            c.setAutoCommit(false);
            try {
                int id = pessoaDao.inserir(c, psicologo);
                try (PreparedStatement p = c.prepareStatement(
                        "INSERT INTO psicologo (pessoaId, especialidade) VALUES (?, ?)")) {
                    p.setInt(1, id);
                    p.setString(2, psicologo.getEspecialidade());
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
            System.out.println("Erro ao cadastrar psicólogo: " + e.getMessage());
            return false;
        }
    }

    public List<Psicologo> listar() {
        List<Psicologo> lista = new ArrayList<>();
        try (Connection c = Conexao.conectar();
             PreparedStatement p = c.prepareStatement(SELECT_BASE + "ORDER BY p.nome");
             ResultSet r = p.executeQuery()) {
            while (r.next()) {
                lista.add(mapear(r));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar psicólogos: " + e.getMessage());
        }
        return lista;
    }

    public Psicologo consultarPsicologo(int id) {
        try (Connection c = Conexao.conectar();
             PreparedStatement p = c.prepareStatement(SELECT_BASE + "WHERE ps.pessoaId = ?")) {

            p.setInt(1, id);

            try (ResultSet r = p.executeQuery()) {
                if (r.next()) {
                    return mapear(r);
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao consultar psicólogo: " + e.getMessage());
        }

        return null;
    }

    public boolean atualizar(Psicologo psicologo) {
        try (Connection c = Conexao.conectar()) {
            c.setAutoCommit(false);
            try {
                pessoaDao.atualizar(c, psicologo);
                try (PreparedStatement p = c.prepareStatement(
                        "UPDATE psicologo SET especialidade = ? WHERE pessoaId = ?")) {
                    p.setString(1, psicologo.getEspecialidade());
                    p.setInt(2, psicologo.getId());
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
            System.out.println("Erro ao atualizar psicólogo: " + e.getMessage());
            return false;
        }
    }

    /** Falha se o psicólogo já tiver entrevistas registradas. */
    public boolean excluir(int id) {
        try (Connection c = Conexao.conectar()) {
            c.setAutoCommit(false);
            try {
                try (PreparedStatement p = c.prepareStatement("DELETE FROM psicologo WHERE pessoaId = ?")) {
                    p.setInt(1, id);
                    if (p.executeUpdate() == 0) {
                        throw new SQLException("Psicólogo não encontrado.");
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
            System.out.println("Erro ao excluir psicólogo: " + e.getMessage());
            return false;
        }
    }

    public boolean marcarReuniaoComAluno(int psicologoId, int alunoId, String dataReuniao, String motivo) {
        return agendar(psicologoId, "Aluno", alunoId, dataReuniao, motivo);
    }

    public boolean marcarReuniaoComPedagogo(int psicologoId, int alunoId, String dataReuniao, String motivo) {
        return agendar(psicologoId, "Pedagogo", alunoId, dataReuniao, motivo);
    }

    private boolean agendar(int psicologoId, String publico, int alunoId, String data, String motivo) {
        Reuniao r = new Reuniao();
        r.setSolicitanteTipo("Psicólogo");
        r.setSolicitanteId(psicologoId);
        r.setPublicoAlvo(publico);
        r.setAlunoId(alunoId);
        r.setDataReuniao(data);
        r.setMotivo(motivo);
        return new ReuniaoDAO().agendar(r);
    }

    private Psicologo mapear(ResultSet r) throws SQLException {
        Psicologo psicologo = new Psicologo();
        PessoaDAO.preencher(psicologo, r);
        psicologo.setEspecialidade(r.getString("especialidade"));
        return psicologo;
    }
}
