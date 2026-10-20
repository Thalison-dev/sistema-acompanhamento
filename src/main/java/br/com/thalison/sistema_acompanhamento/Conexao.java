
package br.com.thalison.sistema_acompanhamento;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
