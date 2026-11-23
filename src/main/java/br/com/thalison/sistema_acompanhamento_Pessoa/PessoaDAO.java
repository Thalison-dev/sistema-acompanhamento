package br.com.thalison.sistema_acompanhamento_Pessoa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Operações comuns na tabela "pessoa", usada como base por
 * Aluno, Pedagogo, Professor, Psicólogo, Responsável Legal e Diretor.
 * Os métodos recebem a conexão para participarem da mesma transação.
 */
public class PessoaDAO {

    private static final String SENHA_PADRAO = "ALTERAR_PARA_HASH_DA_SENHA";

    public int inserir(Connection c, Pessoa p) throws SQLException {
        String sql = "INSERT INTO pessoa (nome, email, senha, telefone, CPF) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, p.getNome());
            ps.setString(2, p.getEmail());
            ps.setString(3, SENHA_PADRAO);
            ps.setString(4, vazioParaNull(p.getTelefone()));
            ps.setString(5, vazioParaNull(p.getCpf()));
            ps.executeUpdate();
            try (ResultSet r = ps.getGeneratedKeys()) {
                if (!r.next()) {
                    throw new SQLException("ID da pessoa não foi gerado.");
                }
                int id = r.getInt(1);
                p.setId(id);
                return id;
            }
        }
    }

    public void atualizar(Connection c, Pessoa p) throws SQLException {
        String sql = "UPDATE pessoa SET nome = ?, email = ?, telefone = ?, CPF = ? WHERE id = ?";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, p.getNome());
            ps.setString(2, p.getEmail());
            ps.setString(3, vazioParaNull(p.getTelefone()));
            ps.setString(4, vazioParaNull(p.getCpf()));
            ps.setInt(5, p.getId());
            ps.executeUpdate();
        }
    }

    public void excluir(Connection c, int id) throws SQLException {
        try (PreparedStatement ps = c.prepareStatement("DELETE FROM pessoa WHERE id = ?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    /** Preenche os campos de Pessoa a partir de um ResultSet com colunas id, nome, CPF, telefone, email. */
    public static void preencher(Pessoa p, ResultSet r) throws SQLException {
        p.setId(r.getInt("id"));
        p.setNome(r.getString("nome"));
        p.setCpf(r.getString("CPF"));
        p.setTelefone(r.getString("telefone"));
        p.setEmail(r.getString("email"));
    }

    public static String vazioParaNull(String valor) {
        return (valor == null || valor.trim().isEmpty()) ? null : valor.trim();
    }
}
