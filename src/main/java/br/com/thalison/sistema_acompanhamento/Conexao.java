package br.com.thalison.sistema_acompanhamento;
 
import java.sql.Connection;

import java.sql.DriverManager;

import java.sql.SQLException;
 
public class Conexao {
 
    private static final String URL =

            "jdbc:mysql://localhost:3306/sistema_acompanhamento?useSSL=false&serverTimezone=America/Sao_Paulo&characterEncoding=UTF-8";

    private static final String USER = "root";

    private static final String PASS = "root";
 
    public static Connection conectar() throws SQLException {

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

        } catch (ClassNotFoundException e) {

            throw new SQLException("Driver JDBC do MySQL não encontrado.", e);

        }

        return DriverManager.getConnection(URL, USER, PASS);

    }

}
 