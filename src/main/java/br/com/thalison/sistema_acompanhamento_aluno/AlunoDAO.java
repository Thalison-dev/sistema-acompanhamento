package br.com.thalison.sistema_acompanhamento_aluno;

import br.com.thalison.sistema_acompanhamento.Conexao;
import br.com.thalison.sistema_acompanhamento_Pessoa.PessoaDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlunoDAO {

    private static final String SELECT_BASE =
        "SELECT p.id, p.nome, p.CPF, p.telefone, p.email, "
        + "a.dataNascimentoAluno, a.matriculaAluno, a.anoEscolarAluno, a.statusAluno, "
        + "a.responsavelId, pr.nome AS nomeResponsavel "
        + "FROM pessoa p "
        + "JOIN aluno a ON a.alunoId = p.id "
        + "LEFT JOIN pessoa pr ON pr.id = a.responsavelId ";

    private final PessoaDAO pessoaDao = new PessoaDAO();

    public boolean cadastrar(Aluno a) {
        String aluno = "INSERT INTO aluno (alunoId, nomeAluno, dataNascimentoAluno, matriculaAluno, "
                     + "anoEscolarAluno, statusAluno, responsavelId) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection c = Conexao.conectar()) {
            c.setAutoCommit(false);
            try {
                int id = pessoaDao.inserir(c, a);
                try (PreparedStatement p = c.prepareStatement(aluno)) {
                    p.setInt(1, id);
                    preencherAluno(p, a, 2);
                    p.executeUpdate();
                }
                c.commit();
                a.setId(id);
                return true;
            } catch (SQLException e) {
                c.rollback();
                throw e;
            } finally {
                c.setAutoCommit(true);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar aluno: " + e.getMessage());
            return false;
        }
    }

    public Aluno buscarPorId(int id) {
        String sql = SELECT_BASE + "WHERE a.alunoId = ?";
        try (Connection c = Conexao.conectar();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setInt(1, id);
            try (ResultSet r = p.executeQuery()) {
                if (r.next()) return mapearAluno(r);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar aluno: " + e.getMessage());
        }
        return null;
    }

    public List<Aluno> listarTodos() {
        return listar(SELECT_BASE + "ORDER BY p.nome", 0);
    }

    public List<Aluno> listarPorResponsavel(int responsavelId) {
        return listar(SELECT_BASE + "WHERE a.responsavelId = ? ORDER BY p.nome", responsavelId);
    }

    private List<Aluno> listar(String sql, int parametro) {
        List<Aluno> lista = new ArrayList<>();
        try (Connection c = Conexao.conectar();
             PreparedStatement p = c.prepareStatement(sql)) {
            if (parametro > 0) {
                p.setInt(1, parametro);
            }
            try (ResultSet r = p.executeQuery()) {
                while (r.next()) lista.add(mapearAluno(r));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar alunos: " + e.getMessage());
        }
        return lista;
    }

    public boolean atualizar(Aluno a) {
        String aluno = "UPDATE aluno SET nomeAluno = ?, dataNascimentoAluno = ?, matriculaAluno = ?, "
                     + "anoEscolarAluno = ?, statusAluno = ?, responsavelId = ? WHERE alunoId = ?";
        try (Connection c = Conexao.conectar()) {
            c.setAutoCommit(false);
            try {
                pessoaDao.atualizar(c, a);
                try (PreparedStatement p = c.prepareStatement(aluno)) {
                    preencherAluno(p, a, 1);
                    p.setInt(7, a.getId());
                    if (p.executeUpdate() == 0) throw new SQLException("Aluno não encontrado.");
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
            System.out.println("Erro ao atualizar aluno: " + e.getMessage());
            return false;
        }
    }

    /**
     * Exclui o aluno. Falha (retorna false) se ele ainda tiver acompanhamentos,
     * entrevistas ou outros registros vinculados — o banco protege esses vínculos.
     */
    public boolean excluir(int id) {
        try (Connection c = Conexao.conectar()) {
            c.setAutoCommit(false);
            try {
                try (PreparedStatement p = c.prepareStatement("DELETE FROM aluno WHERE alunoId = ?")) {
                    p.setInt(1, id);
                    if (p.executeUpdate() == 0) throw new SQLException("Aluno não encontrado.");
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
            System.out.println("Erro ao excluir aluno: " + e.getMessage());
            return false;
        }
    }

    private void preencherAluno(PreparedStatement p, Aluno a, int inicio) throws SQLException {
        p.setString(inicio, a.getNome());
        if (a.getDataNascimento() == null || a.getDataNascimento().trim().isEmpty()) {
            p.setNull(inicio + 1, Types.DATE);
        } else {
            p.setDate(inicio + 1, Date.valueOf(a.getDataNascimento().trim()));
        }
        p.setString(inicio + 2, PessoaDAO.vazioParaNull(a.getMatricula()));
        p.setString(inicio + 3, PessoaDAO.vazioParaNull(a.getAnoEscolar()));
        p.setString(inicio + 4, a.getStatus() != null ? a.getStatus() : "Ativo");
        if (a.getResponsavelId() > 0) {
            p.setInt(inicio + 5, a.getResponsavelId());
        } else {
            p.setNull(inicio + 5, Types.INTEGER);
        }
    }

    private Aluno mapearAluno(ResultSet r) throws SQLException {
        Aluno a = new Aluno();
        PessoaDAO.preencher(a, r);
        a.setDataNascimento(r.getString("dataNascimentoAluno"));
        a.setMatricula(r.getString("matriculaAluno"));
        a.setAnoEscolar(r.getString("anoEscolarAluno"));
        a.setStatus(r.getString("statusAluno"));
        a.setResponsavelId(r.getInt("responsavelId"));
        a.setNomeResponsavel(r.getString("nomeResponsavel"));
        return a;
    }
}
