package br.com.thalison.sistema_acompanhamento;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Conexao {

    // Pode ser sobrescrito com -Dsistema.db.url=... (útil para banco de testes)
    private static final String URL = System.getProperty("sistema.db.url",
        "jdbc:mysql://localhost:3306/sistemaneurodivergente"
        + "?useSSL=false&serverTimezone=America/Sao_Paulo"
        + "&allowPublicKeyRetrieval=true&useUnicode=true&characterEncoding=UTF-8");

    private static final String USUARIO = System.getProperty("sistema.db.usuario", "root");
    private static final String SENHA = System.getProperty("sistema.db.senha", "root");

    private static volatile boolean tabelasVerificadas = false;

    public static Connection conectar() throws SQLException {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver MySQL não encontrado no projeto.", e);
        }

        Connection conexao = DriverManager.getConnection(URL, USUARIO, SENHA);
        garantirTabelasNovas(conexao);
        return conexao;
    }

    /**
     * Cria (apenas se ainda não existirem) as tabelas adicionadas nesta versão:
     * diretor e reuniao. Não altera nem apaga nada que já esteja no banco.
     */
    private static void garantirTabelasNovas(Connection conexao) {
        if (tabelasVerificadas) {
            return;
        }
        synchronized (Conexao.class) {
            if (tabelasVerificadas) {
                return;
            }
            try (Statement st = conexao.createStatement()) {
                st.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS diretor ("
                    + " diretorId INT NOT NULL,"
                    + " cargo VARCHAR(100),"
                    + " escola VARCHAR(150),"
                    + " formacao VARCHAR(150),"
                    + " PRIMARY KEY (diretorId),"
                    + " CONSTRAINT diretor_ibfk_1 FOREIGN KEY (diretorId) REFERENCES pessoa (id)"
                    + ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci");

                st.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS reuniao ("
                    + " reuniaoId INT NOT NULL AUTO_INCREMENT,"
                    + " solicitanteTipo VARCHAR(50) NOT NULL,"
                    + " solicitanteId INT NULL,"
                    + " publicoAlvo VARCHAR(50) NOT NULL,"
                    + " alunoId INT NULL,"
                    + " acompanhamentoId INT NULL,"
                    + " dataReuniao DATE NOT NULL,"
                    + " motivo TEXT,"
                    + " statusReuniao VARCHAR(30) DEFAULT 'Agendada',"
                    + " dataRegistro DATETIME DEFAULT CURRENT_TIMESTAMP,"
                    + " PRIMARY KEY (reuniaoId),"
                    + " KEY alunoId (alunoId),"
                    + " KEY acompanhamentoId (acompanhamentoId),"
                    + " CONSTRAINT reuniao_ibfk_1 FOREIGN KEY (alunoId) REFERENCES aluno (alunoId) ON DELETE SET NULL,"
                    + " CONSTRAINT reuniao_ibfk_2 FOREIGN KEY (acompanhamentoId) REFERENCES acompanhamento (acompanhamentoId) ON DELETE SET NULL"
                    + ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci");

                tabelasVerificadas = true;
            } catch (SQLException e) {
                System.out.println("Aviso: não foi possível verificar as tabelas diretor/reuniao: " + e.getMessage());
            }
        }
    }
}
