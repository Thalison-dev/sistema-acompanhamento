
package br.com.thalison.sistema_acompanhamento;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    private static final String URL =
        "jdbc:mysql://localhost:3306/sistema_acompanhamento?serverTimezone=UTC";

    private static final String USER = "root";
    private static final String PASS = "root";


public static Connection conectar() throws SQLException {
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        System.out.println("Driver MySQL carregado!");

    } catch (ClassNotFoundException e) {
        throw new SQLException("O Tomcat não encontrou o driver MySQL.", e);
    }

    Connection conexao = DriverManager.getConnection(URL, USER, PASS);

    System.out.println("Conexão com o banco realizada!");

    return conexao;
}
}