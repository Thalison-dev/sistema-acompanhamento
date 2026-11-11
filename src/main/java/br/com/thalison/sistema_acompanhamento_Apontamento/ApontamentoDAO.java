package br.com.thalison.sistema_acompanhamento_Apontamento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.thalison.sistema_acompanhamento.Conexao;

public class ApontamentoDAO {

    public void salvar(Apontamento apontamento) {
        String sql = "INSERT INTO apontamento (data_inicio, nome_professor, motivo, id_pessoa) VALUES (?, ?, ?, ?)";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, apontamento.getDataInicio());
            stmt.setString(2, apontamento.getNomeprofessor());
            stmt.setString(3, apontamento.getMotivo());
            stmt.setInt(4, apontamento.getIdPessoa());
            //havia um set id professor como os acima

            stmt.execute();
            System.out.println("Apontamento registrado com sucesso!");

        } catch (SQLException e) {
            System.err.println("Erro ao salvar apontamento: " + e.getMessage());
        }
    }

    public List<Apontamento> listar() {
        String sql = "SELECT * FROM apontamento";
        List<Apontamento> lista = new ArrayList<>();

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Apontamento apontamento = new Apontamento();
                apontamento.setDataInicio(rs.getString("data_inicio"));
                apontamento.setNomeprofessor(rs.getString("nome_professor"));
                apontamento.setMotivo(rs.getString("motivo"));
                apontamento.setIdAluno(rs.getInt("id_pessoa"));
                //havia um set id professor como os acima

                lista.add(apontamento);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar apontamentos: " + e.getMessage());
        }

        return lista;
    }

    public void atualizar(Apontamento apontamento, int id) {
        String sql = "UPDATE apontamento SET data_inicio = ?, nome_professor = ?, motivo = ?, id_pessoa = ? WHERE id = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, apontamento.getDataInicio());
            stmt.setString(2, apontamento.getNomeprofessor());
            stmt.setString(3, apontamento.getMotivo());
            stmt.setInt(4, apontamento.getIdPessoa());
           //havia um get id professor como os acima
            stmt.setInt(6, id);

            stmt.executeUpdate();
            System.out.println("Apontamento atualizado com sucesso!");

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar apontamento: " + e.getMessage());
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM apontamento WHERE id = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Apontamento excluído com sucesso!");

        } catch (SQLException e) {
            System.err.println("Erro ao deletar apontamento: " + e.getMessage());
        }
    }
}