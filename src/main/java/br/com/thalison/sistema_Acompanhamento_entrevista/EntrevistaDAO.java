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

    public boolean cadastrar(Entrevista entrevista) throws SQLException {
        String sql = "INSERT INTO entrevista "
                + "(aluno_id, psicologo_id, motivo, agendada, data, observacoes) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            setChaveEstrangeira(stmt, 1, entrevista.getAluno() != null ? entrevista.getAluno().getIdPessoa() : 0);
            setChaveEstrangeira(stmt, 2, entrevista.getPsicologo() != null ? entrevista.getPsicologo().getId() : 0);
            stmt.setString(3, entrevista.getMotivo());
            stmt.setBoolean(4, entrevista.isAgendada());
            
            if (entrevista.getData() != null) {
                stmt.setDate(5, new Date(entrevista.getData().getTime()));
            } else {
                stmt.setNull(5, Types.DATE);
            }
            
            stmt.setString(6, entrevista.getObservacoes());

            return stmt.executeUpdate() > 0;
        }
    }

    public List<Entrevista> listar() throws SQLException {
        List<Entrevista> entrevistas = new ArrayList<>();
        String sql = "SELECT * FROM entrevista";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Entrevista entrevista = new Entrevista();
                entrevista.setId(rs.getInt("id"));

                int alunoId = rs.getInt("aluno_id");
                if (!rs.wasNull() && alunoId > 0) {
                    Aluno a = new Aluno();
                    a.setIdAluno(alunoId);
                    entrevista.setAluno(a);
                }

                int psicologoId = rs.getInt("psicologo_id");
                if (!rs.wasNull() && psicologoId > 0) {
                    Psicologo p = new Psicologo();
                    p.setId(psicologoId);
                    entrevista.setPsicologo(p);
                }

                entrevista.setMotivo(rs.getString("motivo"));
                entrevista.setAgendada(rs.getBoolean("agendada"));
                entrevista.setData(rs.getDate("data"));
                entrevista.setObservacoes(rs.getString("observacoes"));

                entrevistas.add(entrevista);
            }
        }
        return entrevistas;
    }

    public boolean atualizar(Entrevista entrevista) throws SQLException {
        String sql = "UPDATE entrevista SET "
                + "aluno_id = ?, psicologo_id = ?, motivo = ?, agendada = ?, data = ?, observacoes = ? "
                + "WHERE id = ?";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            setChaveEstrangeira(stmt, 1, entrevista.getAluno() != null ? entrevista.getAluno().getIdPessoa() : 0);
            setChaveEstrangeira(stmt, 2, entrevista.getPsicologo() != null ? entrevista.getPsicologo().getId() : 0);
            stmt.setString(3, entrevista.getMotivo());
            stmt.setBoolean(4, entrevista.isAgendada());

            if (entrevista.getData() != null) {
                stmt.setDate(5, new Date(entrevista.getData().getTime()));
            } else {
                stmt.setNull(5, Types.DATE);
            }

            stmt.setString(6, entrevista.getObservacoes());
            stmt.setInt(7, entrevista.getId());

            return stmt.executeUpdate() > 0;
        }
    }

    public boolean excluir(int id) throws SQLException {
        String sql = "DELETE FROM entrevista WHERE id = ?";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    private void setChaveEstrangeira(PreparedStatement stmt, int index, int id) throws SQLException {
        if (id > 0) {
            stmt.setInt(index, id);
        } else {
            stmt.setNull(index, Types.INTEGER);
        }
    }
}