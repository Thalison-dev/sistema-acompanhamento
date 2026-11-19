package br.com.thalison.sistema_acompanhamento;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    private static final String URL =
        "jdbc:mysql://127.0.0.1:3306/sistema_acompanhamento_senac?useTimezone=true&serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true";

    private static final String USUARIO = "root";
    private static final String SENHA = "root";

    public static Connection conectar() throws SQLException {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver MySQL não encontrado no projeto.", e);
        }

        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }
}