
package br.com.thalison.sistema_acompanhamento_Diretor;

import br.com.thalison.sistema_acompanhamento.Conexao;
import java.sql.*;
import java.util.*;

public class DiretorDAO {

    public boolean cadastrar(Diretor d) {
        String sql = "INSERT INTO diretor (diretorId, matriculaDiretor) VALUES (?, ?)";

        try (Connection c = Conexao.conectar();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, d.getId());
            ps.setString(2, d.getMatricula().trim());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar diretor: " + e.getMessage());
            return false;
        }
    }

    public List<Diretor> listar() {
        List<Diretor> lista = new ArrayList<>();

        String sql = "SELECT p.id, p.nome, p.email, p.CPF, p.telefone, " +
                     "d.matriculaDiretor " +
                     "FROM diretor d JOIN pessoa p ON p.id = d.diretorId " +
                     "ORDER BY p.nome";

        try (Connection c = Conexao.conectar();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Diretor d = new Diretor();
                d.setId(rs.getInt("id"));
                d.setNome(rs.getString("nome"));
                d.setEmail(rs.getString("email"));
                d.setCpf(rs.getString("CPF"));
                d.setTelefone(rs.getString("telefone"));
                d.setMatricula(rs.getString("matriculaDiretor"));
                lista.add(d);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar diretores: " + e.getMessage());
        }

        return lista;
    }

    public boolean excluir(int id) {
        String sql = "DELETE FROM diretor WHERE diretorId = ?";

        try (Connection c = Conexao.conectar();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao excluir diretor: " + e.getMessage());
            return false;
        }
    }

    public Map<String, Integer> obterIndicadores() {
        Map<String, Integer> dados = new LinkedHashMap<>();

        dados.put("alunos", contar("SELECT COUNT(*) FROM aluno"));
        dados.put("acompanhamentos",
                  contar("SELECT COUNT(*) FROM acompanhamento"));
        dados.put("ocorrencias",
                  contar("SELECT COUNT(*) FROM ocorrencia_pedagogica"));
        dados.put("adaptacoes",
                  contar("SELECT COUNT(*) FROM adaptacao_metodologica"));
        dados.put("turmas", contar("SELECT COUNT(*) FROM turma"));
        dados.put("professores", contar("SELECT COUNT(*) FROM professor"));

        return dados;
    }

    private int contar(String sql) {
        try (Connection c = Conexao.conectar();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao consultar indicador: " + e.getMessage());
        }

        return 0;
    }
}
