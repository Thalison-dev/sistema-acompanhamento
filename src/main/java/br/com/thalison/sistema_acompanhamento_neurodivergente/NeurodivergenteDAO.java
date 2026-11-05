
package br.com.thalison.sistema_acompanhamento_neurodivergente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class NeurodivergenteDAO {

//    private Neurodivergente mapear(ResultSet r) throws SQLException {
//        Neurodivergente n = new Neurodivergente();
//        n.setId(r.getInt("neurodivergenciaId"));
//        n.setAlunoId(r.getInt("alunoId"));
//        n.setTipo(r.getString("tipoNeurodivergencia"));
//        n.setDescricao(r.getString("descricaoNeuro"));
//        n.setObservacoes(r.getString("observacoesNeuro"));
//        n.setDataRegistro(r.getString("dataRegistroNeuro"));
//        return n;
//    }
//
//    public boolean cadastrar(Neurodivergente n) {
//        String sql = "INSERT INTO neurodivergencia (alunoId, tipoNeurodivergencia, descricaoNeuro, observacoesNeuro, dataRegistroNeuro) VALUES (?, ?, ?, ?, ?)";
//        try (Connection c = Conexao.conectar(); PreparedStatement p = c.prepareStatement(sql)) {
//            p.setInt(1, n.getAlunoId());
//            p.setString(2, n.getTipo());
//            p.setString(3, n.getDescricao());
//            p.setString(4, n.getObservacoes());
//            p.setString(5, n.getDataRegistro());
//            return p.executeUpdate() > 0;
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//            return false;
//        }
//    }
//
//    public Neurodivergente consultar(int id) {
//        String sql = "SELECT * FROM neurodivergencia WHERE neurodivergenciaId = ?";
//        try (Connection c = Conexao.conectar(); PreparedStatement p = c.prepareStatement(sql)) {
//            p.setInt(1, id);
//            ResultSet r = p.executeQuery();
//            if (r.next()) return mapear(r);
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//        return null;
//    }
//
//    public List<Neurodivergente> listarTodos() {
//        List<Neurodivergente> lista = new ArrayList<>();
//        String sql = "SELECT * FROM neurodivergencia";
//        try (Connection c = Conexao.conectar(); PreparedStatement p = c.prepareStatement(sql); ResultSet r = p.executeQuery()) {
//            while (r.next()) lista.add(mapear(r));
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        }
//        return lista;
//    }
//
//    public boolean atualizar(Neurodivergente n) {
//        String sql = "UPDATE neurodivergencia SET alunoId=?, tipoNeurodivergencia=?, descricaoNeuro=?, observacoesNeuro=?, dataRegistroNeuro=? WHERE neurodivergenciaId=?";
//        try (Connection c = Conexao.conectar(); PreparedStatement p = c.prepareStatement(sql)) {
//            p.setInt(1, n.getAlunoId());
//            p.setString(2, n.getTipo());
//            p.setString(3, n.getDescricao());
//            p.setString(4, n.getObservacoes());
//            p.setString(5, n.getDataRegistro());
//            p.setInt(6, n.getId());
//            return p.executeUpdate() > 0;
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//            return false;
//        }
//    }
//
//    public boolean excluir(int id) {
//        String sql = "DELETE FROM neurodivergencia WHERE neurodivergenciaId=?";
//        try (Connection c = Conexao.conectar(); PreparedStatement p = c.prepareStatement(sql)) {
//            p.setInt(1, id);
//            return p.executeUpdate() > 0;
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//            return false;
//        }
//    }
}
