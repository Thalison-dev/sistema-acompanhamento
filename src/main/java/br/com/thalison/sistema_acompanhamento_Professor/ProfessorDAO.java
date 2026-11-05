package br.com.thalison.sistema_acompanhamento_Professor;

import java.sql.*;
import br.com.thalison.sistema_acompanhamento.Conexao;

public class ProfessorDAO {

    public void consultarAluno() throws SQLException {
        String sql = "SELECT * FROM aluno";
        try (Connection c = Conexao.conectar();
             Statement st = c.createStatement();
             ResultSet r = st.executeQuery(sql)) {
            while (r.next())
                System.out.println(r.getString("nome"));
        }
    }

    private void registrar(String tabela, int alunoId, String descricao)
            throws SQLException {
        String sql = "INSERT INTO " + tabela +
                     " (aluno_id, descricao) VALUES (?, ?)";
        try (Connection c = Conexao.conectar();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, alunoId);
            ps.setString(2, descricao);
            ps.executeUpdate();
        }
    }

    public void registrarObservacao(int id, String descricao)
            throws SQLException {
        registrar("observacao", id, descricao);
    }

    public void registrarOcorrencia(int id, String descricao)
            throws SQLException {
        registrar("ocorrencia", id, descricao);
    }

    public void registrarAvaliacao(int id, String descricao)
            throws SQLException {
        registrar("avaliacao", id, descricao);
    }

    public void registrarRendimento(int id, String descricao)
            throws SQLException {
        registrar("rendimento", id, descricao);
    }

    public void registrarAdaptacao(int id, String descricao)
            throws SQLException {
        registrar("adaptacao", id, descricao);
    }

    public void consultarHistorico(int alunoId) throws SQLException {
        String sql = "SELECT * FROM observacao WHERE aluno_id = ?";

        try (Connection c = Conexao.conectar();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, alunoId);
            try (ResultSet r = ps.executeQuery()) {
                while (r.next())
                    System.out.println(r.getString("descricao"));
            }
        }
    }
}
