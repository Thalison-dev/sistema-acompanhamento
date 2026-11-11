package br.com.thalison.sistema_acompanhamento;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    private static final String URL = "jdbc:mysql://localhost:3306/sistemaneurodivergente?useTimezone=true&serverTimezone=UTC";
    private static final String USUARIO = "root";
    
    private static final String SENHA = "root";

    public static Connection conectar() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver JDBC do MySQL não encontrado no classpath.", e);
        }

        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }
}
/*
package br.com.thalison.sistema_acompanhamento;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.DriverManager;
public class Conexao {

    private static final String URL =
        "jdbc:mysql://mysql-5a1a421-athlisonaruda-a6d3.h.aivencloud.com:12015/defaultdb?sslMode=REQUIRED";

    private static final String USUARIO = "avnadmin";

    private static final String SENHA = "AVNS_FgkkgpwG0sNAM7QZkPT";
    
    public static Connection conectar() throws SQLException {

        if (SENHA == null || SENHA.isBlank()) {
            throw new SQLException(
                "Senha nao configurada. Configure a variavel MYSQL_PASSWORD."
            );
        }

       return DriverManager.getConnection(URL, USUARIO, SENHA);
    }
}
*/