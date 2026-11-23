package br.com.thalison.sistema_acompanhamento_Pedagogo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.thalison.sistema_acompanhamento.Conexao;
import br.com.thalison.sistema_acompanhamento_Pessoa.Pessoa;
import br.com.thalison.sistema_acompanhamento_Pessoa.PessoaDAO;
import br.com.thalison.sistema_acompanhamento_Reuniao.Reuniao;
import br.com.thalison.sistema_acompanhamento_Reuniao.ReuniaoDAO;

public class PedagogoDAO {

    private static final String SELECT_BASE =
        "SELECT p.id, p.nome, p.email, p.telefone, p.CPF, pe.especialidade "
        + "FROM pedagogo pe JOIN pessoa p ON p.id = pe.pessoa ";

    private final PessoaDAO pessoaDao = new PessoaDAO();

    public boolean cadastrar(Pedagogo pedagogo) {
        try (Connection c = Conexao.conectar()) {
            c.setAutoCommit(false);
            try {
                int id = pessoaDao.inserir(c, paraPessoa(pedagogo));
                try (PreparedStatement ps = c.prepareStatement(
                        "INSERT INTO pedagogo (pessoa, especialidade) VALUES (?, ?)")) {
                    ps.setInt(1, id);
                    ps.setString(2, pedagogo.getEspecialidade());
                    ps.executeUpdate();
                }
                c.commit();
                pedagogo.setIdPedagogo(id);
                return true;
            } catch (SQLException e) {
                c.rollback();
                throw e;
            } finally {
                c.setAutoCommit(true);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar pedagogo: " + e.getMessage());
            return false;
        }
    }

    public List<Pedagogo> listar() {
        List<Pedagogo> pedagogos = new ArrayList<>();

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(SELECT_BASE + "ORDER BY p.nome");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                pedagogos.add(mapear(rs));
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar pedagogos: " + e.getMessage());
        }

        return pedagogos;
    }

    public Pedagogo buscarPorId(int id) {
        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(SELECT_BASE + "WHERE pe.pessoa = ?")) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar pedagogo: " + e.getMessage());
        }

        return null;
    }

    public boolean atualizar(Pedagogo pedagogo) {
        try (Connection c = Conexao.conectar()) {
            c.setAutoCommit(false);
            try {
                pessoaDao.atualizar(c, paraPessoa(pedagogo));
                try (PreparedStatement ps = c.prepareStatement(
                        "UPDATE pedagogo SET especialidade = ? WHERE pessoa = ?")) {
                    ps.setString(1, pedagogo.getEspecialidade());
                    ps.setInt(2, pedagogo.getIdPedagogo());
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
            System.out.println("Erro ao atualizar pedagogo: " + e.getMessage());
            return false;
        }
    }

    public boolean excluir(int id) {
        try (Connection c = Conexao.conectar()) {
            c.setAutoCommit(false);
            try {
                try (PreparedStatement ps = c.prepareStatement("DELETE FROM pedagogo WHERE pessoa = ?")) {
                    ps.setInt(1, id);
                    if (ps.executeUpdate() == 0) {
                        throw new SQLException("Pedagogo não encontrado.");
                    }
                }
                try {
                    pessoaDao.excluir(c, id);
                } catch (SQLException vinculada) {
                    // a pessoa ainda é referenciada em outro lugar: mantém só o cadastro de pessoa
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
            System.out.println("Erro ao excluir pedagogo: " + e.getMessage());
            return false;
        }
    }

    // Registra reuniões com Psicólogo, Responsável Legal, Diretor ou Aluno na agenda de reuniões
    public boolean registrarReuniao(int pedagogoId, String publicoAlvo, int alunoId, String dataReuniao, String motivo) {
        Reuniao r = new Reuniao();
        r.setSolicitanteTipo("Pedagogo");
        r.setSolicitanteId(pedagogoId);
        r.setPublicoAlvo(publicoAlvo);
        r.setAlunoId(alunoId);
        r.setDataReuniao(dataReuniao);
        r.setMotivo(motivo);
        return new ReuniaoDAO().agendar(r);
    }

    // Lista as reuniões em que o pedagogo participa
    public List<Reuniao> listarReunioes() {
        return new ReuniaoDAO().listarPorPerfil("Pedagogo");
    }

    private Pessoa paraPessoa(Pedagogo p) {
        return new Pessoa(p.getIdPedagogo(), p.getPessoa(), p.getCpf(), p.getTelefone(), p.getEmail());
    }

    private Pedagogo mapear(ResultSet rs) throws SQLException {
        Pedagogo p = new Pedagogo(rs.getInt("id"), rs.getString("nome"), rs.getString("especialidade"));
        p.setEmail(rs.getString("email"));
        p.setTelefone(rs.getString("telefone"));
        p.setCpf(rs.getString("CPF"));
        return p;
    }
}
