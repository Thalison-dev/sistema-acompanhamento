package br.com.thalison.sistema_acompanhamento_neurodivergente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.thalison.sistema_acompanhamento.Conexao;
import br.com.thalison.sistema_acompanhamento_aluno.Aluno;

public class NeurodivergenteDAO {

    private Neurodivergente mapear(ResultSet r) throws SQLException {
        Neurodivergente n = new Neurodivergente();
        n.setId(r.getInt("neurodivergenciaId"));

        Aluno aluno = new Aluno();
        aluno.setId(r.getInt("alunoId"));
        n.setAlunoId(aluno);

        n.setTipo(r.getString("tipoNeurodivergencia"));
        n.setDescricao(r.getString("descricaoNeuro"));
        n.setObservacoes(r.getString("observacoesNeuro"));
        return n;
    }

    public boolean cadastrar(Neurodivergente n) {
        String sql = "INSERT INTO neurodivergencia (alunoId, tipoNeurodivergencia, descricaoNeuro, observacoesNeuro) VALUES (?, ?, ?, ?)";
        try (Connection c = Conexao.conectar();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setInt(1, n.getAluno().getId());
            p.setString(2, n.getTipo());
            p.setString(3, n.getDescricao());
            p.setString(4, n.getObservacoes());
            return p.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public Neurodivergente consultar(int id) {
        String sql = "SELECT * FROM neurodivergencia WHERE neurodivergenciaId = ?";
        try (Connection c = Conexao.conectar();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setInt(1, id);
            try (ResultSet r = p.executeQuery()) {
                if (r.next()) return mapear(r);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Neurodivergente> listarTodos() {
        List<Neurodivergente> lista = new ArrayList<>();
        String sql = "SELECT * FROM neurodivergencia";
        try (Connection c = Conexao.conectar();
             PreparedStatement p = c.prepareStatement(sql);
             ResultSet r = p.executeQuery()) {
            while (r.next()) lista.add(mapear(r));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return lista;
    }

    public boolean atualizar(Neurodivergente n) {
        String sql = "UPDATE neurodivergencia SET alunoId=?, tipoNeurodivergencia=?, descricaoNeuro=?, observacoesNeuro=? WHERE neurodivergenciaId=?";
        try (Connection c = Conexao.conectar();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setInt(1, n.getAluno().getId());
            p.setString(2, n.getTipo());
            p.setString(3, n.getDescricao());
            p.setString(4, n.getObservacoes());
            p.setInt(5, n.getId());
            return p.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean excluir(int id) {
        String sql = "DELETE FROM neurodivergencia WHERE neurodivergenciaId = ?";
        try (Connection c = Conexao.conectar();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setInt(1, id);
            return p.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}