package br.com.thalison.sistema_acompanhamento_Apontamento;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import br.com.thalison.sistema_acompanhamento.Conexao;

public class ApontamentoDAO {

    private static final String SELECT_BASE =
        "SELECT ap.*, p.nome AS nomeAluno FROM apontamento ap "
        + "LEFT JOIN pessoa p ON p.id = ap.id_pessoa ";

    public boolean salvar(Apontamento apontamento) {
        String sql = "INSERT INTO apontamento (data_inicio, nome_professor, motivo, id_pessoa, id_professor) "
                   + "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            preencher(stmt, apontamento);
            return stmt.executeUpdate() > 0;

        } catch (SQLException | IllegalArgumentException e) {
            System.err.println("Erro ao salvar apontamento: " + e.getMessage());
            return false;
        }
    }

    public List<Apontamento> listar() {
        return listar(SELECT_BASE + "ORDER BY ap.data_inicio DESC, ap.id DESC", 0);
    }

    public List<Apontamento> listarPorAluno(int alunoId) {
        return listar(SELECT_BASE + "WHERE ap.id_pessoa = ? ORDER BY ap.data_inicio DESC", alunoId);
    }

    private List<Apontamento> listar(String sql, int parametro) {
        List<Apontamento> lista = new ArrayList<>();

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            if (parametro > 0) {
                stmt.setInt(1, parametro);
            }
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapear(rs));
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar apontamentos: " + e.getMessage());
        }

        return lista;
    }

    public Apontamento buscarPorId(int id) {
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BASE + "WHERE ap.id = ?")) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar apontamento: " + e.getMessage());
        }
        return null;
    }

    public boolean atualizar(Apontamento apontamento) {
        String sql = "UPDATE apontamento SET data_inicio = ?, nome_professor = ?, motivo = ?, "
                   + "id_pessoa = ?, id_professor = ? WHERE id = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            preencher(stmt, apontamento);
            stmt.setInt(6, apontamento.getId());
            return stmt.executeUpdate() > 0;

        } catch (SQLException | IllegalArgumentException e) {
            System.err.println("Erro ao atualizar apontamento: " + e.getMessage());
            return false;
        }
    }

    public boolean deletar(int id) {
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM apontamento WHERE id = ?")) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao deletar apontamento: " + e.getMessage());
            return false;
        }
    }

    private void preencher(PreparedStatement stmt, Apontamento a) throws SQLException {
        if (a.getDataInicio() == null || a.getDataInicio().trim().isEmpty()) {
            stmt.setNull(1, Types.DATE);
        } else {
            stmt.setDate(1, Date.valueOf(a.getDataInicio().trim()));
        }
        stmt.setString(2, a.getNomeProfessor());
        stmt.setString(3, a.getMotivo());
        stmt.setInt(4, a.getAlunoId());
        if (a.getProfessorId() > 0) {
            stmt.setInt(5, a.getProfessorId());
        } else {
            stmt.setNull(5, Types.INTEGER);
        }
    }

    private Apontamento mapear(ResultSet rs) throws SQLException {
        Apontamento a = new Apontamento();
        a.setId(rs.getInt("id"));
        a.setDataInicio(rs.getString("data_inicio"));
        a.setNomeProfessor(rs.getString("nome_professor"));
        a.setMotivo(rs.getString("motivo"));
        a.setAlunoId(rs.getInt("id_pessoa"));
        a.setProfessorId(rs.getInt("id_professor"));
        a.setNomeAluno(rs.getString("nomeAluno"));
        return a;
    }
}
