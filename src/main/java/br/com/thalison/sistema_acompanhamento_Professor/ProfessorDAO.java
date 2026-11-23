package br.com.thalison.sistema_acompanhamento_Professor;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import br.com.thalison.sistema_acompanhamento.Conexao;
import br.com.thalison.sistema_acompanhamento_Avaliacao.avaliacao;
import br.com.thalison.sistema_acompanhamento_Pessoa.PessoaDAO;
import br.com.thalison.sistema_acompanhamento_adaptacao.adaptacao_metodologica;

/**
 * Professor: cadastro e registros pedagógicos sobre os alunos
 * (ocorrências/observações, avaliações e adaptações metodológicas).
 */
public class ProfessorDAO {

    private static final String SELECT_PROFESSOR =
        "SELECT p.id, p.nome, p.CPF, p.telefone, p.email, pr.matriculaProfessor, pr.especialidade "
        + "FROM professor pr JOIN pessoa p ON p.id = pr.professorId ";

    private final PessoaDAO pessoaDao = new PessoaDAO();

    // ------------------------------------------------------------ cadastro

    public boolean cadastrar(Professor professor) {
        try (Connection c = Conexao.conectar()) {
            c.setAutoCommit(false);
            try {
                int id = pessoaDao.inserir(c, professor);
                try (PreparedStatement ps = c.prepareStatement(
                        "INSERT INTO professor (professorId, matriculaProfessor, especialidade) VALUES (?, ?, ?)")) {
                    ps.setInt(1, id);
                    ps.setString(2, professor.getMatricula());
                    ps.setString(3, professor.getEspecialidade());
                    ps.executeUpdate();
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
            System.out.println("Erro ao cadastrar professor: " + e.getMessage());
            return false;
        }
    }

    public List<Professor> listar() {
        List<Professor> lista = new ArrayList<>();
        try (Connection c = Conexao.conectar();
             PreparedStatement ps = c.prepareStatement(SELECT_PROFESSOR + "ORDER BY p.nome");
             ResultSet r = ps.executeQuery()) {
            while (r.next()) {
                lista.add(mapearProfessor(r));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar professores: " + e.getMessage());
        }
        return lista;
    }

    public Professor buscarPorId(int id) {
        try (Connection c = Conexao.conectar();
             PreparedStatement ps = c.prepareStatement(SELECT_PROFESSOR + "WHERE pr.professorId = ?")) {
            ps.setInt(1, id);
            try (ResultSet r = ps.executeQuery()) {
                if (r.next()) {
                    return mapearProfessor(r);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar professor: " + e.getMessage());
        }
        return null;
    }

    public boolean atualizar(Professor professor) {
        try (Connection c = Conexao.conectar()) {
            c.setAutoCommit(false);
            try {
                pessoaDao.atualizar(c, professor);
                try (PreparedStatement ps = c.prepareStatement(
                        "UPDATE professor SET matriculaProfessor = ?, especialidade = ? WHERE professorId = ?")) {
                    ps.setString(1, professor.getMatricula());
                    ps.setString(2, professor.getEspecialidade());
                    ps.setInt(3, professor.getId());
                    ps.executeUpdate();
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
            System.out.println("Erro ao atualizar professor: " + e.getMessage());
            return false;
        }
    }

    /** Falha se o professor já tiver registros (ocorrências, apontamentos, adaptações). */
    public boolean excluir(int id) {
        try (Connection c = Conexao.conectar()) {
            c.setAutoCommit(false);
            try {
                try (PreparedStatement ps = c.prepareStatement("DELETE FROM professor WHERE professorId = ?")) {
                    ps.setInt(1, id);
                    if (ps.executeUpdate() == 0) {
                        throw new SQLException("Professor não encontrado.");
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
            System.out.println("Erro ao excluir professor: " + e.getMessage());
            return false;
        }
    }

    // ------------------------------------------- ocorrências / observações / rendimento

    public boolean registrarOcorrencia(OcorrenciaPedagogica o) {
        String sql = "INSERT INTO ocorrencia_pedagogica (alunoId, professorId, dataOcorrenciaPedagogica, "
                   + "tipoOcorrencia, descricaoOcorrencia, providenciaOcorrencia, statusOcorrencia) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection c = Conexao.conectar();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, o.getAlunoId());
            setInteiroOpcional(ps, 2, o.getProfessorId());
            if (o.getData() == null || o.getData().trim().isEmpty()) {
                ps.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            } else {
                ps.setTimestamp(3, Timestamp.valueOf(o.getData().trim() + " 00:00:00"));
            }
            ps.setString(4, o.getTipo());
            ps.setString(5, o.getDescricao());
            ps.setString(6, o.getProvidencia());
            ps.setString(7, o.getStatus() != null ? o.getStatus() : "Aberta");
            return ps.executeUpdate() > 0;
        } catch (SQLException | IllegalArgumentException e) {
            System.out.println("Erro ao registrar ocorrência: " + e.getMessage());
            return false;
        }
    }

    public List<OcorrenciaPedagogica> listarOcorrencias() {
        return listarOcorrencias("", 0);
    }

    public List<OcorrenciaPedagogica> listarOcorrenciasPorAluno(int alunoId) {
        return listarOcorrencias("WHERE o.alunoId = ? ", alunoId);
    }

    private List<OcorrenciaPedagogica> listarOcorrencias(String filtro, int parametro) {
        String sql = "SELECT o.*, a.nomeAluno, p.nome AS nomeProfessor FROM ocorrencia_pedagogica o "
                   + "LEFT JOIN aluno a ON a.alunoId = o.alunoId "
                   + "LEFT JOIN pessoa p ON p.id = o.professorId "
                   + filtro + "ORDER BY o.dataOcorrenciaPedagogica DESC, o.ocorrenciaId DESC";
        List<OcorrenciaPedagogica> lista = new ArrayList<>();
        try (Connection c = Conexao.conectar();
             PreparedStatement ps = c.prepareStatement(sql)) {
            if (parametro > 0) {
                ps.setInt(1, parametro);
            }
            try (ResultSet r = ps.executeQuery()) {
                while (r.next()) {
                    OcorrenciaPedagogica o = new OcorrenciaPedagogica();
                    o.setId(r.getInt("ocorrenciaId"));
                    o.setAlunoId(r.getInt("alunoId"));
                    o.setNomeAluno(r.getString("nomeAluno"));
                    o.setProfessorId(r.getInt("professorId"));
                    o.setNomeProfessor(r.getString("nomeProfessor"));
                    Timestamp t = r.getTimestamp("dataOcorrenciaPedagogica");
                    o.setData(t != null ? t.toLocalDateTime().toLocalDate().toString() : null);
                    o.setTipo(r.getString("tipoOcorrencia"));
                    o.setDescricao(r.getString("descricaoOcorrencia"));
                    o.setProvidencia(r.getString("providenciaOcorrencia"));
                    o.setStatus(r.getString("statusOcorrencia"));
                    lista.add(o);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar ocorrências: " + e.getMessage());
        }
        return lista;
    }

    public boolean excluirOcorrencia(int id) {
        return excluirRegistro("DELETE FROM ocorrencia_pedagogica WHERE ocorrenciaId = ?", id);
    }

    // ------------------------------------------------------------ avaliações

    public boolean registrarAvaliacao(avaliacao a) {
        String sql = "INSERT INTO avaliacao (alunoId, disciplinaId, notaAvaliacao, dataAvaliacao, "
                   + "tipoAvaliacao, observacaoAvaliacao) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection c = Conexao.conectar();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, a.getAlunoId());
            setInteiroOpcional(ps, 2, a.getDisciplinaId());
            ps.setDouble(3, a.getNota());
            setDataOpcional(ps, 4, a.getData());
            ps.setString(5, a.getTipo());
            ps.setString(6, a.getObservacao());
            return ps.executeUpdate() > 0;
        } catch (SQLException | IllegalArgumentException e) {
            System.out.println("Erro ao registrar avaliação: " + e.getMessage());
            return false;
        }
    }

    public List<avaliacao> listarAvaliacoesPorAluno(int alunoId) {
        String sql = "SELECT av.*, d.nomeDisciplina FROM avaliacao av "
                   + "LEFT JOIN disciplina d ON d.disciplinaId = av.disciplinaId "
                   + "WHERE av.alunoId = ? ORDER BY av.dataAvaliacao DESC";
        List<avaliacao> lista = new ArrayList<>();
        try (Connection c = Conexao.conectar();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, alunoId);
            try (ResultSet r = ps.executeQuery()) {
                while (r.next()) {
                    avaliacao a = new avaliacao();
                    a.setId(r.getInt("avaliacaoId"));
                    a.setAlunoId(r.getInt("alunoId"));
                    a.setDisciplinaId(r.getInt("disciplinaId"));
                    a.setNomeDisciplina(r.getString("nomeDisciplina"));
                    a.setNota(r.getDouble("notaAvaliacao"));
                    a.setData(r.getString("dataAvaliacao"));
                    a.setTipo(r.getString("tipoAvaliacao"));
                    a.setObservacao(r.getString("observacaoAvaliacao"));
                    lista.add(a);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar avaliações: " + e.getMessage());
        }
        return lista;
    }

    public boolean excluirAvaliacao(int id) {
        return excluirRegistro("DELETE FROM avaliacao WHERE avaliacaoId = ?", id);
    }

    // ------------------------------------------------------ adaptações metodológicas

    public boolean registrarAdaptacao(adaptacao_metodologica a) {
        String sql = "INSERT INTO adaptacao_metodologica (alunoId, professorId, adaptacaoTipo, descricaoAdaptacao, "
                   + "estrategiaMetodologia, dataRegistroAdapt, resultado, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection c = Conexao.conectar();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, a.getAlunoId());
            setInteiroOpcional(ps, 2, a.getProfessorId());
            ps.setString(3, a.getTipo());
            ps.setString(4, a.getDescricao());
            ps.setString(5, a.getEstrategia());
            if (a.getDataRegistro() == null || a.getDataRegistro().trim().isEmpty()) {
                ps.setDate(6, new Date(System.currentTimeMillis()));
            } else {
                ps.setDate(6, Date.valueOf(a.getDataRegistro().trim()));
            }
            ps.setString(7, a.getResultado());
            ps.setString(8, a.getStatus() != null ? a.getStatus() : "Em aplicação");
            return ps.executeUpdate() > 0;
        } catch (SQLException | IllegalArgumentException e) {
            System.out.println("Erro ao registrar adaptação: " + e.getMessage());
            return false;
        }
    }

    public List<adaptacao_metodologica> listarAdaptacoesPorAluno(int alunoId) {
        String sql = "SELECT ad.*, p.nome AS nomeProfessor FROM adaptacao_metodologica ad "
                   + "LEFT JOIN pessoa p ON p.id = ad.professorId "
                   + "WHERE ad.alunoId = ? ORDER BY ad.dataRegistroAdapt DESC";
        List<adaptacao_metodologica> lista = new ArrayList<>();
        try (Connection c = Conexao.conectar();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, alunoId);
            try (ResultSet r = ps.executeQuery()) {
                while (r.next()) {
                    adaptacao_metodologica a = new adaptacao_metodologica();
                    a.setId(r.getInt("adaptacaoMetodologiaId"));
                    a.setAlunoId(r.getInt("alunoId"));
                    a.setProfessorId(r.getInt("professorId"));
                    a.setNomeProfessor(r.getString("nomeProfessor"));
                    a.setTipo(r.getString("adaptacaoTipo"));
                    a.setDescricao(r.getString("descricaoAdaptacao"));
                    a.setEstrategia(r.getString("estrategiaMetodologia"));
                    a.setDataRegistro(r.getString("dataRegistroAdapt"));
                    a.setResultado(r.getString("resultado"));
                    a.setStatus(r.getString("status"));
                    lista.add(a);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar adaptações: " + e.getMessage());
        }
        return lista;
    }

    public boolean excluirAdaptacao(int id) {
        return excluirRegistro("DELETE FROM adaptacao_metodologica WHERE adaptacaoMetodologiaId = ?", id);
    }

    // ------------------------------------------------------------ auxiliares

    private boolean excluirRegistro(String sql, int id) {
        try (Connection c = Conexao.conectar();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao excluir registro: " + e.getMessage());
            return false;
        }
    }

    private Professor mapearProfessor(ResultSet r) throws SQLException {
        Professor p = new Professor();
        PessoaDAO.preencher(p, r);
        p.setMatricula(r.getString("matriculaProfessor"));
        p.setEspecialidade(r.getString("especialidade"));
        return p;
    }

    private void setInteiroOpcional(PreparedStatement ps, int indice, int valor) throws SQLException {
        if (valor > 0) {
            ps.setInt(indice, valor);
        } else {
            ps.setNull(indice, Types.INTEGER);
        }
    }

    private void setDataOpcional(PreparedStatement ps, int indice, String data) throws SQLException {
        if (data == null || data.trim().isEmpty()) {
            ps.setDate(indice, new Date(System.currentTimeMillis()));
        } else {
            ps.setDate(indice, Date.valueOf(data.trim()));
        }
    }
}
