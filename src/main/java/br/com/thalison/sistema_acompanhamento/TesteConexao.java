package br.com.thalison.sistema_acompanhamento;

import java.sql.Connection;
import java.sql.SQLException;

public class TesteConexao {

    public static void main(String[] args) {

        try (Connection conexao = Conexao.conectar()) {

            if (conexao != null && conexao.isValid(5)) {
                System.out.println("Conexão com MySQL realizada com sucesso!");
                System.out.println("Banco: " +
                    conexao.getCatalog());
            }

        } catch (SQLException e) {
            System.out.println("Erro ao conectar com MySQL:");
            e.printStackTrace();
        }
    }
}