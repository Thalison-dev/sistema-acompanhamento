
package br.com.thaliso.sistema_acompanhamento_Neurotipico;

import java.sql.*;
import java.util.*;
import br.com.thalison.sistema_acompanhamento.Conexao;

public class NeurotipicoDAO {

    private Neurotipico mapear(ResultSet r) throws SQLException {
        Neurotipico n = new Neurotipico();
        n.setId(r.getInt("neurotipicoId"));
        n.setAlunoId(r.getInt("alunoId"));
        n.setTipo(r.getString("tipoNeurotipico"));
        n.setDescricao(r.getString("descricaoNeuro"));
        n.setObservacoes(r.getString("observacoesNeuro"));
        n.setDataRegistro(r.getDate("dataRegistroNeuro"));
        return n;
    }

    public boolean cadastrar(Neurotipico n) {
        String sql = "INSERT INTO neurotipico "
                + "(alunoId, tipoNeurotipico, descricaoNeuro, observacoesNeuro, dataRegistroNeuro) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection c = Conexao.conectar();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setInt(1, n.getAlunoId());
            p.setString(2, n.getTipo());
            p.setString(3, n.getDescricao());
            p.setString(4, n.getObservacoes());
            p.setDate(5, n.getDataRegistro());
            return p.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public Neurotipico consultar(int id) {
        String sql = "SELECT * FROM neurotipico WHERE neurotipicoId = ?";

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

    public List<Neurotipico> listarTodos() {
        List<Neurotipico> lista = new ArrayList<>();
        String sql = "SELECT * FROM neurotipico";

        try (Connection c = Conexao.conectar();
             PreparedStatement p = c.prepareStatement(sql);
             ResultSet r = p.executeQuery()) {
            while (r.next()) lista.add(mapear(r));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return lista;
    }

    public boolean atualizar(Neurotipico n) {
        String sql = "UPDATE neurotipico SET alunoId=?, tipoNeurotipico=?, "
                + "descricaoNeuro=?, observacoesNeuro=?, dataRegistroNeuro=? "
                + "WHERE neurotipicoId=?";

        try (Connection c = Conexao.conectar();
             PreparedStatement p = c.prepareStatement(sql)) {
            p.setInt(1, n.getAlunoId());
            p.setString(2, n.getTipo());
            p.setString(3, n.getDescricao());
            p.setString(4, n.getObservacoes());
            p.setDate(5, n.getDataRegistro());
            p.setInt(6, n.getId());
            return p.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean excluir(int id) {
        String sql = "DELETE FROM neurotipico WHERE neurotipicoId = ?";

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
