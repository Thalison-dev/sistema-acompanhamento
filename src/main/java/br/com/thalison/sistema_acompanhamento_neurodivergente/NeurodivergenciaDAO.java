package br.com.thalison.sistema_acompanhamento_neurodivergente;
 
import java.sql.*;

import java.util.ArrayList;

import java.util.List;
 
import br.com.thalison.sistema_acompanhamento.Conexao;

import br.com.thalison.sistema_acompanhamento_aluno.Aluno;
 
public class NeurodivergenciaDAO {
 
    public boolean cadastrar(Neurodivergencia n) {

        String sql = "INSERT INTO neurodivergencia (alunoId, tipoNeurodivergencia, descricaoNeuro, observacoesNeuro, dataRegistroNeuro) "

                   + "VALUES (?, ?, ?, ?, ?)";

        try (Connection c = Conexao.conectar();

             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
 
            ps.setInt(1, n.getAluno().getId());

            ps.setString(2, n.getTipo());

            ps.setString(3, n.getDescricao());

            ps.setString(4, n.getObservacoes());

            if (n.getDataRegistro() != null) {

                ps.setDate(5, Date.valueOf(n.getDataRegistro()));

            } else {

                ps.setNull(5, Types.DATE);

            }
 
            int linhas = ps.executeUpdate();

            if (linhas > 0) {

                try (ResultSet rs = ps.getGeneratedKeys()) {

                    if (rs.next()) {

                        n.setId(rs.getInt(1));

                    }

                }

                return true;

            }

            return false;

        } catch (SQLException e) {

            e.printStackTrace();

            return false;

        }

    }
 
    public Neurodivergencia consultar(int id) {

        String sql = "SELECT * FROM neurodivergencia WHERE neurodivergenciaId = ?";

        try (Connection c = Conexao.conectar();

             PreparedStatement ps = c.prepareStatement(sql)) {
 
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    return mapear(rs);

                }

            }

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return null;

    }
 
    public List<Neurodivergencia> listarTodos() {

        List<Neurodivergencia> lista = new ArrayList<>();

        String sql = "SELECT * FROM neurodivergencia ORDER BY dataRegistroNeuro DESC, neurodivergenciaId DESC";

        try (Connection c = Conexao.conectar();

             PreparedStatement ps = c.prepareStatement(sql);

             ResultSet rs = ps.executeQuery()) {
 
            while (rs.next()) {

                lista.add(mapear(rs));

            }

        } catch (SQLException e) {

            e.printStackTrace();

        }

        return lista;

    }
 
    public boolean atualizar(Neurodivergencia n) {

        String sql = "UPDATE neurodivergencia SET alunoId = ?, tipoNeurodivergencia = ?, descricaoNeuro = ?, observacoesNeuro = ?, dataRegistroNeuro = ? "

                   + "WHERE neurodivergenciaId = ?";

        try (Connection c = Conexao.conectar();

             PreparedStatement ps = c.prepareStatement(sql)) {
 
            ps.setInt(1, n.getAluno().getId());

            ps.setString(2, n.getTipo());

            ps.setString(3, n.getDescricao());

            ps.setString(4, n.getObservacoes());

            if (n.getDataRegistro() != null) {

                ps.setDate(5, Date.valueOf(n.getDataRegistro()));

            } else {

                ps.setNull(5, Types.DATE);

            }

            ps.setInt(6, n.getId());
 
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            e.printStackTrace();

            return false;

        }

    }
 
    public boolean excluir(int id) {

        String sql = "DELETE FROM neurodivergencia WHERE neurodivergenciaId = ?";

        try (Connection c = Conexao.conectar();

             PreparedStatement ps = c.prepareStatement(sql)) {
 
            ps.setInt(1, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            e.printStackTrace();

            return false;

        }

    }
 
    private Neurodivergencia mapear(ResultSet rs) throws SQLException {

        Neurodivergencia n = new Neurodivergencia();

        n.setId(rs.getInt("neurodivergenciaId"));
 
        Aluno aluno = new Aluno();

        aluno.setId(rs.getInt("alunoId"));

        n.setAluno(aluno);
 
        n.setTipo(rs.getString("tipoNeurodivergencia"));

        n.setDescricao(rs.getString("descricaoNeuro"));

        n.setObservacoes(rs.getString("observacoesNeuro"));
 
        Date dataSql = rs.getDate("dataRegistroNeuro");

        if (dataSql != null) {

            n.setDataRegistro(dataSql.toLocalDate());

        }
 
        return n;

    }

}
 