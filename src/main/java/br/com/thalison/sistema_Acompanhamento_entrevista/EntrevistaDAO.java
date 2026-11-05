package br.com.thalison.sistema_Acompanhamento_entrevista;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.thalison.sistema_acompanhamento.Conexao;
import br.com.thalison.sistema_acompanhamento_Psicologo.Psicologo;
import br.com.thalison.sistema_acompanhamento_aluno.aluno;

public class EntrevistaDAO {

    public boolean cadastrar(Entrevista entrevista) {

        String sql = "INSERT INTO entrevista "
                + "(aluno_id, psicologo_id, motivo, agendada, data, observacoes) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, entrevista.getAluno() != null ? entrevista.getAluno().getId() : 0);
            stmt.setInt(2, entrevista.getPsicologo() != null ? entrevista.getPsicologo().getId() : 0);
            stmt.setString(3, entrevista.getMotivo());
            stmt.setBoolean(4, entrevista.isAgendada());
            stmt.setDate(5, entrevista.getData() != null ? new Date(entrevista.getData().getTime()) : null);
            stmt.setString(6, entrevista.getObservacoes());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar entrevista: " + e.getMessage());
            return false;
        }
    }

    public List<Entrevista> listar() {

        List<Entrevista> entrevistas = new ArrayList<>();

        String sql = "SELECT * FROM entrevista";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                Entrevista entrevista = new Entrevista();

                entrevista.setId(rs.getInt("id"));

                // Associação das chaves estrangeiras com os objetos do modelo
                aluno a = new aluno();
                a.setId(rs.getInt("aluno_id"));
                entrevista.setAluno(a);

                Psicologo p = new Psicologo();
                p.setId(rs.getInt("psicologo_id"));
                entrevista.setPsicologo(p);

                entrevista.setMotivo(rs.getString("motivo"));
                entrevista.setAgendada(rs.getBoolean("agendada"));
                entrevista.setData(rs.getDate("data"));
                entrevista.setObservacoes(rs.getString("observacoes"));

                entrevistas.add(entrevista);
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

            stmt.setInt(1, entrevista.getAluno() != null ? entrevista.getAluno().getId() : 0);
            stmt.setInt(2, entrevista.getPsicologo() != null ? entrevista.getPsicologo().getId() : 0);
            stmt.setString(3, entrevista.getMotivo());
            stmt.setBoolean(4, entrevista.isAgendada());
            stmt.setDate(5, entrevista.getData() != null ? new Date(entrevista.getData().getTime()) : null);
            stmt.setString(6, entrevista.getObservacoes());
            stmt.setInt(7, entrevista.getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar entrevista: " + e.getMessage());
            return false;
        }
    }

    public boolean excluir(int id) {

        String sql = "DELETE FROM entrevista WHERE id = ?";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao excluir entrevista: " + e.getMessage());
            return false;
        }
    }
}