package br.com.thalison.sistema_acompanhamento_Disciplina;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.thalison.sistema_acompanhamento.Conexao;

public class DisciplinaDAO {

    public boolean cadastrar(disciplina d) {
        String sql = "INSERT INTO disciplina (nomeDisciplina, descricaoDisciplina) VALUES (?, ?)";
        try (Connection c = Conexao.conectar();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, d.getNome());
            ps.setString(2, d.getDescricao());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar disciplina: " + e.getMessage());
            return false;
        }
    }

    public List<disciplina> listar() {
        List<disciplina> lista = new ArrayList<>();
        String sql = "SELECT * FROM disciplina ORDER BY nomeDisciplina";
        try (Connection c = Conexao.conectar();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet r = ps.executeQuery()) {
            while (r.next()) {
                lista.add(new disciplina(r.getInt("disciplinaId"),
                                         r.getString("nomeDisciplina"),
                                         r.getString("descricaoDisciplina")));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar disciplinas: " + e.getMessage());
        }
        return lista;
    }

    public boolean excluir(int id) {
        try (Connection c = Conexao.conectar();
             PreparedStatement ps = c.prepareStatement("DELETE FROM disciplina WHERE disciplinaId = ?")) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao excluir disciplina: " + e.getMessage());
            return false;
        }
    }
}
