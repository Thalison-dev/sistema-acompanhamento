package br.com.thalison.sistema_acompanhamento_neurodivergente;

import br.com.thalison.sistema_acompanhamento.Conexao;
import java.sql.*;
import java.util.*;

public class NeurodivergenteDAO {

    private static final String SELECT_BASE =
        "SELECT n.*, a.nomeAluno FROM neurodivergencia n "
        + "LEFT JOIN aluno a ON a.alunoId = n.alunoId ";

    private Neurodivergente mapear(ResultSet r) throws SQLException {
        Neurodivergente n = new Neurodivergente();
        n.setId(r.getInt("neurodivergenciaId"));
        n.setAlunoId(r.getInt("alunoId"));
        n.setNomeAluno(r.getString("nomeAluno"));
        n.setTipo(r.getString("tipoNeurodivergencia"));
        n.setDescricao(r.getString("descricaoNeuro"));
        n.setObservacoes(r.getString("observacoesNeuro"));
        n.setDataRegistro(r.getString("dataRegistroNeuro"));
        return n;
    }

    private void preencher(PreparedStatement p, Neurodivergente n) throws SQLException {
        p.setInt(1, n.getAlunoId());
        p.setString(2, n.getTipo());
        p.setString(3, n.getDescricao());
        p.setString(4, n.getObservacoes());
        if (n.getDataRegistro() == null || n.getDataRegistro().trim().isEmpty()) {
            p.setDate(5, new java.sql.Date(System.currentTimeMillis()));
        } else {
            p.setDate(5, java.sql.Date.valueOf(n.getDataRegistro().trim()));
        }
    }

    public boolean cadastrar(Neurodivergente n) {
        String sql = "INSERT INTO neurodivergencia (alunoId, tipoNeurodivergencia, descricaoNeuro, observacoesNeuro, dataRegistroNeuro) VALUES (?, ?, ?, ?, ?)";
        try (Connection c = Conexao.conectar();
             PreparedStatement p = c.prepareStatement(sql)) {
            preencher(p, n);
            return p.executeUpdate() > 0;
        } catch (SQLException | IllegalArgumentException e) {
            System.out.println("Erro ao cadastrar neurodivergência: " + e.getMessage());
            return false;
        }
    }

    public Neurodivergente consultar(int id) {
        try (Connection c = Conexao.conectar();
             PreparedStatement p = c.prepareStatement(SELECT_BASE + "WHERE n.neurodivergenciaId = ?")) {
            p.setInt(1, id);
            try (ResultSet r = p.executeQuery()) {
                if (r.next()) return mapear(r);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao consultar neurodivergência: " + e.getMessage());
        }
        return null;
    }

    public List<Neurodivergente> listarTodos() {
        return listar(SELECT_BASE + "ORDER BY a.nomeAluno", 0);
    }

    public List<Neurodivergente> listarPorAluno(int alunoId) {
        return listar(SELECT_BASE + "WHERE n.alunoId = ? ORDER BY n.dataRegistroNeuro DESC", alunoId);
    }

    private List<Neurodivergente> listar(String sql, int parametro) {
        List<Neurodivergente> lista = new ArrayList<>();
        try (Connection c = Conexao.conectar();
             PreparedStatement p = c.prepareStatement(sql)) {
            if (parametro > 0) {
                p.setInt(1, parametro);
            }
            try (ResultSet r = p.executeQuery()) {
                while (r.next()) lista.add(mapear(r));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar neurodivergências: " + e.getMessage());
        }
        return lista;
    }

    public boolean atualizar(Neurodivergente n) {
        String sql = "UPDATE neurodivergencia SET alunoId=?, tipoNeurodivergencia=?, descricaoNeuro=?, observacoesNeuro=?, dataRegistroNeuro=? WHERE neurodivergenciaId=?";
        try (Connection c = Conexao.conectar();
             PreparedStatement p = c.prepareStatement(sql)) {
            preencher(p, n);
            p.setInt(6, n.getId());
            return p.executeUpdate() > 0;
        } catch (SQLException | IllegalArgumentException e) {
            System.out.println("Erro ao atualizar neurodivergência: " + e.getMessage());
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
            System.out.println("Erro ao excluir neurodivergência: " + e.getMessage());
            return false;
        }
    }
}
