package br.com.thalison.sistema_acompanhamento;

import java.sql.Connection;
import java.sql.SQLException;

public class TesteConexao {

    public static void main(String[] args) {

        try (Connection conexao = Conexao.conectar()) {

            if (conexao != null && conexao.isValid(5)) {
                System.out.println("Conexão com MySQL realizada com sucesso!");
                System.out.println("Servidor: 127.0.0.1:3306");
                System.out.println("Usuário: " + conexao.getMetaData().getUserName());
                System.out.println("Banco (Schema): " + conexao.getCatalog());
            }

        } catch (SQLException e) {
            System.out.println("Erro ao conectar com MySQL: " + e.getMessage());
            e.printStackTrace();
        }
    }
}