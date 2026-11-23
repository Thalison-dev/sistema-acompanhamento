package br.com.thalison.sistema_acompanhamento_Diretor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import br.com.thalison.sistema_acompanhamento.Conexao;
import br.com.thalison.sistema_acompanhamento_Apontamento.Apontamento;
import br.com.thalison.sistema_acompanhamento_Pessoa.PessoaDAO;
import br.com.thalison.sistema_acompanhamento_Reuniao.Reuniao;
import br.com.thalison.sistema_acompanhamento_Reuniao.ReuniaoDAO;

public class DiretorDAO {

    private static final String SELECT_BASE =
        "SELECT p.id, p.nome, p.CPF, p.telefone, p.email, d.cargo, d.escola, d.formacao "
        + "FROM diretor d JOIN pessoa p ON p.id = d.diretorId ";

    private final PessoaDAO pessoaDao = new PessoaDAO();

    // ------------------------------------------------------------ cadastro

    public boolean cadastrar(Diretor diretor) {
        try (Connection con = Conexao.conectar()) {
            con.setAutoCommit(false);
            try {
                int id = pessoaDao.inserir(con, diretor);
                try (PreparedStatement ps = con.prepareStatement(
                        "INSERT INTO diretor (diretorId, cargo, escola, formacao) VALUES (?, ?, ?, ?)")) {
                    ps.setInt(1, id);
                    ps.setString(2, diretor.getCargo());
                    ps.setString(3, diretor.getEscola());
                    ps.setString(4, diretor.getFormacao());
                    ps.executeUpdate();
                }
                con.commit();
                return true;
            } catch (SQLException e) {
                con.rollback();
                throw e;
            } finally {
                con.setAutoCommit(true);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar diretor: " + e.getMessage());
            return false;
        }
    }

    public List<Diretor> listar() {
        List<Diretor> lista = new ArrayList<>();
        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(SELECT_BASE + "ORDER BY p.nome");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar diretores: " + e.getMessage());
        }
        return lista;
    }

    public Diretor buscarPorId(int id) {
        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(SELECT_BASE + "WHERE d.diretorId = ?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar diretor: " + e.getMessage());
        }
        return null;
    }

    public boolean atualizar(Diretor diretor) {
        try (Connection con = Conexao.conectar()) {
            con.setAutoCommit(false);
            try {
                pessoaDao.atualizar(con, diretor);
                try (PreparedStatement ps = con.prepareStatement(
                        "UPDATE diretor SET cargo = ?, escola = ?, formacao = ? WHERE diretorId = ?")) {
                    ps.setString(1, diretor.getCargo());
                    ps.setString(2, diretor.getEscola());
                    ps.setString(3, diretor.getFormacao());
                    ps.setInt(4, diretor.getId());
                    ps.executeUpdate();
                }
                con.commit();
                return true;
            } catch (SQLException e) {
                con.rollback();
                throw e;
            } finally {
                con.setAutoCommit(true);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar diretor: " + e.getMessage());
            return false;
        }
    }

    public boolean excluir(int id) {
        try (Connection con = Conexao.conectar()) {
            con.setAutoCommit(false);
            try {
                try (PreparedStatement ps = con.prepareStatement("DELETE FROM diretor WHERE diretorId = ?")) {
                    ps.setInt(1, id);
                    if (ps.executeUpdate() == 0) {
                        throw new SQLException("Diretor não encontrado.");
                    }
                }
                try {
                    pessoaDao.excluir(con, id);
                } catch (SQLException vinculada) {
                    // pessoa ainda usada em outro registro: mantém só a pessoa
                }
                con.commit();
                return true;
            } catch (SQLException e) {
                con.rollback();
                throw e;
            } finally {
                con.setAutoCommit(true);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao excluir diretor: " + e.getMessage());
            return false;
        }
    }

    // ------------------------------------------------------- visão da direção

    /** Totais gerais da escola para o painel do Diretor. */
    public Map<String, Integer> contarTotais() {
        Map<String, Integer> totais = new LinkedHashMap<>();
        String[][] consultas = {
            {"Alunos", "SELECT COUNT(*) FROM aluno"},
            {"Acompanhamentos", "SELECT COUNT(*) FROM acompanhamento"},
            {"Apontamentos", "SELECT COUNT(*) FROM apontamento"},
            {"Entrevistas", "SELECT COUNT(*) FROM entrevista"},
            {"Reuniões agendadas", "SELECT COUNT(*) FROM reuniao WHERE statusReuniao = 'Agendada'"},
            {"Professores", "SELECT COUNT(*) FROM professor"},
            {"Pedagogos", "SELECT COUNT(*) FROM pedagogo"},
            {"Psicólogos", "SELECT COUNT(*) FROM psicologo"}
        };
        try (Connection con = Conexao.conectar()) {
            for (String[] consulta : consultas) {
                try (PreparedStatement ps = con.prepareStatement(consulta[1]);
                     ResultSet rs = ps.executeQuery()) {
                    totais.put(consulta[0], rs.next() ? rs.getInt(1) : 0);
                } catch (SQLException e) {
                    totais.put(consulta[0], 0);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao contar totais: " + e.getMessage());
        }
        return totais;
    }

    /** Alunos apontados por professores que ainda não têm nenhum acompanhamento aberto. */
    public List<Apontamento> listarApontamentosSemAcompanhamento() {
        String sql = "SELECT ap.*, p.nome AS nomeAluno FROM apontamento ap "
                   + "JOIN pessoa p ON p.id = ap.id_pessoa "
                   + "WHERE NOT EXISTS (SELECT 1 FROM acompanhamento ac WHERE ac.alunoId = ap.id_pessoa "
                   + "AND COALESCE(ac.statusAcompanhamento, '') NOT IN ('Concluído', 'Cancelado')) "
                   + "ORDER BY ap.data_inicio";
        List<Apontamento> lista = new ArrayList<>();
        try (Connection con = Conexao.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Apontamento a = new Apontamento();
                a.setId(rs.getInt("id"));
                a.setAlunoId(rs.getInt("id_pessoa"));
                a.setNomeAluno(rs.getString("nomeAluno"));
                a.setDataInicio(rs.getString("data_inicio"));
                a.setNomeProfessor(rs.getString("nome_professor"));
                a.setMotivo(rs.getString("motivo"));
                lista.add(a);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar pendências: " + e.getMessage());
        }
        return lista;
    }

    /** Agenda uma reunião em nome do diretor. */
    public boolean marcarReuniao(int diretorId, String publicoAlvo, int alunoId, int acompanhamentoId,
                                 String dataReuniao, String motivo) {
        Reuniao r = new Reuniao();
        r.setSolicitanteTipo("Diretor");
        r.setSolicitanteId(diretorId);
        r.setPublicoAlvo(publicoAlvo);
        r.setAlunoId(alunoId);
        r.setAcompanhamentoId(acompanhamentoId);
        r.setDataReuniao(dataReuniao);
        r.setMotivo(motivo);
        return new ReuniaoDAO().agendar(r);
    }

    public List<Reuniao> listarReunioes() {
        return new ReuniaoDAO().listarPorPerfil("Diretor");
    }

    private Diretor mapear(ResultSet rs) throws SQLException {
        Diretor d = new Diretor();
        PessoaDAO.preencher(d, rs);
        d.setCargo(rs.getString("cargo"));
        d.setEscola(rs.getString("escola"));
        d.setFormacao(rs.getString("formacao"));
        return d;
    }
}
