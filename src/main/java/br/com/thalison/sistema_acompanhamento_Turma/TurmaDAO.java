package br.com.thalison.sistema_acompanhamento_Turma;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.thalison.sistema_acompanhamento.Conexao;

public class TurmaDAO {

    public boolean cadastrar(Turma turma) {
        String sql = "INSERT INTO turma (nome, anoLetivo, turno) VALUES (?, ?, ?)";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, turma.getNome());
            stmt.setString(2, turma.getAnoLetivo());
            stmt.setString(3, turma.getTurno());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar turma: " + e.getMessage());
            return false;
        }
    }

    public List<Turma> listar() {
        List<Turma> turmas = new ArrayList<>();
        String sql = "SELECT * FROM turma";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Turma turma = new Turma();
                turma.setId(rs.getInt("id"));
                turma.setNome(rs.getString("nome"));
                turma.setAnoLetivo(rs.getString("anoLetivo"));
                turma.setTurno(rs.getString("turno"));

                turmas.add(turma);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar turmas: " + e.getMessage());
        }

        return turmas;
    }

    public Turma buscarPorId(int id) {
        String sql = "SELECT * FROM turma WHERE id = ?";
        Turma turma = null;

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    turma = new Turma();
                    turma.setId(rs.getInt("id"));
                    turma.setNome(rs.getString("nome"));
                    turma.setAnoLetivo(rs.getString("anoLetivo"));
                    turma.setTurno(rs.getString("turno"));
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar turma: " + e.getMessage());
        }

        return turma;
    }

    public boolean atualizar(Turma turma) {
        String sql = "UPDATE turma SET nome = ?, anoLetivo = ?, turno = ? WHERE id = ?";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setString(1, turma.getNome());
            stmt.setString(2, turma.getAnoLetivo());
            stmt.setString(3, turma.getTurno());
            stmt.setInt(4, turma.getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar turma: " + e.getMessage());
            return false;
        }
    }

    public boolean excluir(int id) {
        String sql = "DELETE FROM turma WHERE id = ?";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao excluir turma: " + e.getMessage());
            return false;
        }
    }
}