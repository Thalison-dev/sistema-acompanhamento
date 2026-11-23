package br.com.thalison.sistema_acompanhamento_Acompanhamento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;


import br.com.thalison.sistema_acompanhamento.Conexao;
import br.com.thalison.sistema_acompanhamento_aluno.Aluno;

public class AcompanhamentoDAO {

    public boolean cadastrar(Acompanhamento acompanhamento) {

        String sql = "INSERT INTO acompanhamento "
                + "(alunoId, responsavelLegalId, dataInicioAcompanhamento, "
                + "dataFimAcompanhamento, objetivoAcompanhamento, "
                + "statusAcompanhamento) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

<<<<<<< HEAD
        	stmt.setInt(1, acompanhamento.getAluno().getId());
        	stmt.setDate(2, java.sql.Date.valueOf(acompanhamento.getDataInicio()));
        	if (acompanhamento.getDataFim() == null) {
        	    stmt.setNull(3, java.sql.Types.DATE);
        	} else {
        	    stmt.setDate(3, java.sql.Date.valueOf(acompanhamento.getDataFim()));
        	}
=======
            stmt.setInt(1, acompanhamento.getAlunoId());

            if (acompanhamento.getResponsavelLegalId() > 0) {
                stmt.setInt(2, acompanhamento.getResponsavelLegalId());
            } else {
                stmt.setNull(2, Types.INTEGER);
            }

            stmt.setDate(3, Date.valueOf(acompanhamento.getDataInicio()));

            if (acompanhamento.getDataFim() == null
                    || acompanhamento.getDataFim().trim().isEmpty()) {
                stmt.setNull(4, Types.DATE);
            } else {
                stmt.setDate(4, Date.valueOf(acompanhamento.getDataFim()));
            }
>>>>>>> aea11f6ce5fbd1f58dabfdfc3ecc8b2629d7611a

            stmt.setString(5, acompanhamento.getObjetivo());
            stmt.setString(6, acompanhamento.getStatus());

            return stmt.executeUpdate() > 0;

        } catch (SQLException | IllegalArgumentException e) {
            System.out.println("Erro ao cadastrar acompanhamento: " + e.getMessage());
            return false;
        }
    }

    public List<Acompanhamento> listar() {

        List<Acompanhamento> acompanhamentos = new ArrayList<>();
        String sql = "SELECT * FROM acompanhamento ORDER BY acompanhamentoId DESC";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                acompanhamentos.add(mapear(rs));
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar acompanhamentos: " + e.getMessage());
        }

        return acompanhamentos;
    }

    public Acompanhamento buscarPorId(int id) {

        String sql = "SELECT * FROM acompanhamento WHERE acompanhamentoId = ?";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar acompanhamento: " + e.getMessage());
        }

        return null;
    }

    public boolean atualizar(Acompanhamento acompanhamento) {

        String sql = "UPDATE acompanhamento SET "
                + "alunoId = ?, "
                + "responsavelLegalId = ?, "
                + "dataInicioAcompanhamento = ?, "
                + "dataFimAcompanhamento = ?, "
                + "objetivoAcompanhamento = ?, "
                + "statusAcompanhamento = ? "
                + "WHERE acompanhamentoId = ?";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

<<<<<<< HEAD
        	stmt.setInt(1, acompanhamento.getAluno().getId());
        	stmt.setDate(2, java.sql.Date.valueOf(acompanhamento.getDataInicio()));

        	if (acompanhamento.getDataFim() == null) {
        	    stmt.setNull(3, java.sql.Types.DATE);
        	} else {
        	    stmt.setDate(3, java.sql.Date.valueOf(acompanhamento.getDataFim()));
        	}
=======
            stmt.setInt(1, acompanhamento.getAlunoId());

            if (acompanhamento.getResponsavelLegalId() > 0) {
                stmt.setInt(2, acompanhamento.getResponsavelLegalId());
            } else {
                stmt.setNull(2, Types.INTEGER);
            }

            stmt.setDate(3, Date.valueOf(acompanhamento.getDataInicio()));

            if (acompanhamento.getDataFim() == null
                    || acompanhamento.getDataFim().trim().isEmpty()) {
                stmt.setNull(4, Types.DATE);
            } else {
                stmt.setDate(4, Date.valueOf(acompanhamento.getDataFim()));
            }
>>>>>>> aea11f6ce5fbd1f58dabfdfc3ecc8b2629d7611a

            stmt.setString(5, acompanhamento.getObjetivo());
            stmt.setString(6, acompanhamento.getStatus());
            stmt.setInt(7, acompanhamento.getAcompanhamentoId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException | IllegalArgumentException e) {
            System.out.println("Erro ao atualizar acompanhamento: " + e.getMessage());
            return false;
        }
    }

    public boolean excluir(int id) {

        String sql = "DELETE FROM acompanhamento WHERE acompanhamentoId = ?";

        try (Connection conexao = Conexao.conectar();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao excluir acompanhamento: " + e.getMessage());
            return false;
        }
    }

    private Acompanhamento mapear(ResultSet rs) throws SQLException {
        Acompanhamento acompanhamento = new Acompanhamento();

        acompanhamento.setAcompanhamentoId(rs.getInt("acompanhamentoId"));
<<<<<<< HEAD

        Aluno aluno = new Aluno();
        aluno.setId(rs.getInt("alunoId"));
        acompanhamento.setAluno(aluno);
=======
        acompanhamento.setAlunoId(rs.getInt("alunoId"));
        acompanhamento.setResponsavelLegalId(rs.getInt("responsavelLegalId"));
>>>>>>> aea11f6ce5fbd1f58dabfdfc3ecc8b2629d7611a

        Date inicio = rs.getDate("dataInicioAcompanhamento");
        Date fim = rs.getDate("dataFimAcompanhamento");

        
        acompanhamento.setDataInicio(inicio != null ? inicio.toLocalDate() : null);
        acompanhamento.setDataFim(fim != null ? fim.toLocalDate() : null);

        acompanhamento.setObjetivo(rs.getString("objetivoAcompanhamento"));
        acompanhamento.setStatus(rs.getString("statusAcompanhamento"));

        return acompanhamento;
    }
    }