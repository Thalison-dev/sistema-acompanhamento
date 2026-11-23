
package br.com.thalison.sistema_acompanhamento_aluno;

import br.com.thalison.sistema_acompanhamento.Conexao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlunoDAO {

    public boolean cadastrar(Aluno a) {
        String pessoa = "INSERT INTO pessoa (nome,email,senha,telefone,CPF) VALUES (?,?,?,?,?)";
        String aluno = "INSERT INTO aluno (alunoId,nomeAluno,dataNascimentoAluno,matriculaAluno,anoEscolarAluno,statusAluno) VALUES (?,?,?,?,?,?)";

        try (Connection c = Conexao.conectar()) {
            c.setAutoCommit(false);
            try {
                int id;
                try (PreparedStatement p = c.prepareStatement(pessoa, Statement.RETURN_GENERATED_KEYS)) {
                    p.setString(1, a.getNome());
                    p.setString(2, a.getEmail());
                    p.setString(3, "ALTERAR_PARA_HASH_DA_SENHA");
                    p.setString(4, a.getTelefone());
                    p.setString(5, a.getCpf());
                    p.executeUpdate();
                    try (ResultSet r = p.getGeneratedKeys()) {
                        if (!r.next()) throw new SQLException("ID não gerado.");
                        id = r.getInt(1);
                    }
                }
                try (PreparedStatement p = c.prepareStatement(aluno)) {
                    p.setInt(1, id);
                    p.setString(2, a.getNome());
                    p.setString(3, a.getDataNascimento());
                    p.setString(4, a.getMatricula());
                    p.setString(5, a.getAnoEscolar());
                    p.setString(6, a.getStatus());
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
            System.out.println("Erro ao cadastrar: " + e.getMessage());
            return false;
        }
    }

    public Aluno buscarPorId(int id) {
        String sql = "SELECT p.id,p.nome,p.CPF,p.telefone,p.email,a.dataNascimentoAluno,a.matriculaAluno,a.anoEscolarAluno,a.statusAluno FROM pessoa p JOIN aluno a ON a.alunoId=p.id WHERE a.alunoId=?";
        try (Connection c = Conexao.conectar();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setInt(1, id);
            try (ResultSet r = p.executeQuery()) {
                if (r.next()) return mapearAluno(r);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar: " + e.getMessage());
        }
        return null;
    }

    public List<Aluno> listarTodos() {
        List<Aluno> lista = new ArrayList<>();
        String sql = "SELECT p.id,p.nome,p.CPF,p.telefone,p.email,a.dataNascimentoAluno,a.matriculaAluno,a.anoEscolarAluno,a.statusAluno FROM pessoa p JOIN aluno a ON a.alunoId=p.id";
        try (Connection c = Conexao.conectar();
             PreparedStatement p = c.prepareStatement(sql);
             ResultSet r = p.executeQuery()) {
            while (r.next()) lista.add(mapearAluno(r));
        } catch (SQLException e) {
            System.out.println("Erro ao listar: " + e.getMessage());
        }
        return lista;
    }

    public boolean atualizar(Aluno a) {
        String pessoa = "UPDATE pessoa SET nome=?,email=?,telefone=?,CPF=? WHERE id=?";
        String aluno = "UPDATE aluno SET nomeAluno=?,dataNascimentoAluno=?,matriculaAluno=?,anoEscolarAluno=?,statusAluno=? WHERE alunoId=?";
        try (Connection c = Conexao.conectar()) {
            c.setAutoCommit(false);
            try {
                try (PreparedStatement p = c.prepareStatement(pessoa)) {
                    p.setString(1, a.getNome());
                    p.setString(2, a.getEmail());
                    p.setString(3, a.getTelefone());
                    p.setString(4, a.getCpf());
                    p.setInt(5, a.getId());
                    p.executeUpdate();
                }
                try (PreparedStatement p = c.prepareStatement(aluno)) {
                    p.setString(1, a.getNome());
                    p.setString(2, a.getDataNascimento());
                    p.setString(3, a.getMatricula());
                    p.setString(4, a.getAnoEscolar());
                    p.setString(5, a.getStatus());
                    p.setInt(6, a.getId());
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
            System.out.println("Erro ao atualizar: " + e.getMessage());
            return false;
        }
    }

    public boolean excluir(int id) {
        try (Connection c = Conexao.conectar()) {
            c.setAutoCommit(false);
            try {
                try (PreparedStatement p = c.prepareStatement("DELETE FROM aluno WHERE alunoId=?")) {
                    p.setInt(1, id);
                    if (p.executeUpdate() == 0) throw new SQLException("Aluno não encontrado.");
                }
                try (PreparedStatement p = c.prepareStatement("DELETE FROM pessoa WHERE id=?")) {
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
            System.out.println("Erro ao excluir: " + e.getMessage());
            return false;
        }
    }

    private Aluno mapearAluno(ResultSet r) throws SQLException {
        Aluno a = new Aluno();
        a.setId(r.getInt("id"));
        a.setNome(r.getString("nome"));
        a.setCpf(r.getString("CPF"));
        a.setTelefone(r.getString("telefone"));
        a.setEmail(r.getString("email"));
        a.setDataNascimento(r.getString("dataNascimentoAluno"));
        a.setMatricula(r.getString("matriculaAluno"));
        a.setAnoEscolar(r.getString("anoEscolarAluno"));
        a.setStatus(r.getString("statusAluno"));
        return a;
    }
}
