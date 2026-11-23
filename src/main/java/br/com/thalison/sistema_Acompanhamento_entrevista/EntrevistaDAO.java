package br.com.thalison.sistema_Acompanhamento_entrevista;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import br.com.thalison.sistema_acompanhamento.Conexao;
import br.com.thalison.sistema_acompanhamento_Psicologo.Psicologo;
import br.com.thalison.sistema_acompanhamento_aluno.Aluno;

public class EntrevistaDAO {

    private static final String SELECT_BASE =
        "SELECT e.*, a.nomeAluno, ps.nome AS nomePsicologo FROM entrevista e "
        + "LEFT JOIN aluno a ON a.alunoId = e.aluno_id "
        + "LEFT JOIN pessoa ps ON ps.id = e.psicologo_id ";

    public boolean cadastrar(Entrevista entrevista) {
        String sql = "INSERT INTO entrevista "
                + "(aluno_id, psicologo_id, motivo, agendada, data, observacoes) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            preencher(stmt, entrevista);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar entrevista: " + e.getMessage());
            return false;
        }
    }

    public List<Entrevista> listar() {
        return listar(SELECT_BASE + "ORDER BY e.data DESC, e.id DESC", 0);
    }

    public List<Entrevista> listarPorAluno(int alunoId) {
        return listar(SELECT_BASE + "WHERE e.aluno_id = ? ORDER BY e.data DESC", alunoId);
    }

    public List<Entrevista> listarPorPsicologo(int psicologoId) {
        return listar(SELECT_BASE + "WHERE e.psicologo_id = ? ORDER BY e.data DESC", psicologoId);
    }

    public Entrevista buscarPorId(int id) {
        List<Entrevista> lista = listar(SELECT_BASE + "WHERE e.id = ?", id);
        return lista.isEmpty() ? null : lista.get(0);
    }

    private List<Entrevista> listar(String sql, int parametro) {
        List<Entrevista> entrevistas = new ArrayList<>();

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            if (parametro > 0) {
                stmt.setInt(1, parametro);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    entrevistas.add(mapear(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar entrevistas: " + e.getMessage());
        }
        return entrevistas;
    }

    public boolean atualizar(Entrevista entrevista) {
        String sql = "UPDATE entrevista SET "
                + "aluno_id = ?, psicologo_id = ?, motivo = ?, agendada = ?, data = ?, observacoes = ? "
                + "WHERE id = ?";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            preencher(stmt, entrevista);
            stmt.setInt(7, entrevista.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar entrevista: " + e.getMessage());
            return false;
        }
    }

    public boolean excluir(int id) {
        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement("DELETE FROM entrevista WHERE id = ?")) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao excluir entrevista: " + e.getMessage());
            return false;
        }
    }

    private void preencher(PreparedStatement stmt, Entrevista entrevista) throws SQLException {
        setChaveEstrangeira(stmt, 1, entrevista.getAlunoId());
        setChaveEstrangeira(stmt, 2, entrevista.getPsicologoId());
        stmt.setString(3, entrevista.getMotivo());
        stmt.setBoolean(4, entrevista.isAgendada());

        if (entrevista.getData() != null) {
            stmt.setDate(5, new Date(entrevista.getData().getTime()));
        } else {
            stmt.setNull(5, Types.DATE);
        }

        stmt.setString(6, entrevista.getObservacoes());
    }

    private Entrevista mapear(ResultSet rs) throws SQLException {
        Entrevista entrevista = new Entrevista();
        entrevista.setId(rs.getInt("id"));

        int alunoId = rs.getInt("aluno_id");
        if (!rs.wasNull() && alunoId > 0) {
            Aluno a = new Aluno();
            a.setId(alunoId);
            a.setNome(rs.getString("nomeAluno"));
            entrevista.setAluno(a);
        }

        int psicologoId = rs.getInt("psicologo_id");
        if (!rs.wasNull() && psicologoId > 0) {
            Psicologo p = new Psicologo();
            p.setId(psicologoId);
            p.setNome(rs.getString("nomePsicologo"));
            entrevista.setPsicologo(p);
        }

        entrevista.setMotivo(rs.getString("motivo"));
        entrevista.setAgendada(rs.getBoolean("agendada"));
        entrevista.setData(rs.getDate("data"));
        entrevista.setObservacoes(rs.getString("observacoes"));
        return entrevista;
    }

    private void setChaveEstrangeira(PreparedStatement stmt, int index, int id) throws SQLException {
        if (id > 0) {
            stmt.setInt(index, id);
        } else {
            stmt.setNull(index, Types.INTEGER);
        }
    }
}
