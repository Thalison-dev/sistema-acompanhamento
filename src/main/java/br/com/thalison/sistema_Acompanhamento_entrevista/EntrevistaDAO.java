package br.com.thalison.sistema_Acompanhamento_entrevista;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import br.com.thalison.sistema_acompanhamento.Conexao;
import br.com.thalison.sistema_acompanhamento_Psicologo.Psicologo;
import br.com.thalison.sistema_acompanhamento_aluno.Aluno;

public class EntrevistaDAO {

    public boolean inserir(Entrevista e) {
        String sql = "INSERT INTO entrevista (alunoId, psicologoId, data, motivo, agendada, observacoes) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection c = Conexao.conectar();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, e.getAluno().getId());
            ps.setInt(2, e.getPsicologo().getId());
            ps.setDate(3, e.getData() != null ? Date.valueOf(e.getData()) : null);
            ps.setString(4, e.getMotivo());
            ps.setBoolean(5, e.isAgendada());
            ps.setString(6, e.getObservacoes());

            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public List<Entrevista> listarTodos() {
        List<Entrevista> lista = new ArrayList<>();
        String sql = "SELECT e.*, a.nome AS nomeAluno, p.nome AS nomePsicologo " +
                     "FROM entrevista e " +
                     "INNER JOIN aluno a ON e.alunoId = a.id " +
                     "INNER JOIN pessoa p ON e.psicologoId = p.id " +
                     "ORDER BY e.data DESC, e.id DESC";
        try (Connection c = Conexao.conectar();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapear(rs));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return lista;
    }

    public boolean excluir(int id) {
        String sql = "DELETE FROM entrevista WHERE id = ?";
        try (Connection c = Conexao.conectar();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    private Entrevista mapear(ResultSet rs) throws SQLException {
        Entrevista e = new Entrevista();
        e.setId(rs.getInt("id"));

        Aluno aluno = new Aluno();
        aluno.setId(rs.getInt("alunoId"));
        aluno.setNome(rs.getString("nomeAluno"));
        e.setAluno(aluno);

        Psicologo psicologo = new Psicologo();
        psicologo.setId(rs.getInt("psicologoId"));
        psicologo.setNome(rs.getString("nomePsicologo"));
        e.setPsicologo(psicologo);

        Date dataSql = rs.getDate("data");
        if (dataSql != null) {
            e.setData(dataSql.toLocalDate());
        }

        e.setMotivo(rs.getString("motivo"));
        e.setAgendada(rs.getBoolean("agendada"));
        e.setObservacoes(rs.getString("observacoes"));
        e.setNomeAluno(rs.getString("nomeAluno"));
        e.setNomePsicologo(rs.getString("nomePsicologo"));
        return e;
    }
}