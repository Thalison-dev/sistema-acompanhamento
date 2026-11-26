package br.com.thalison.sistema_acompanhamento_desempenho;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import br.com.thalison.sistema_acompanhamento.Conexao;
import br.com.thalison.sistema_acompanhamento_Apontamento.ApontamentoDAO;

public class DesempenhoMensalDAO {

    private final ApontamentoDAO apontamentoDAO = new ApontamentoDAO();

    public boolean salvarOuAtualizar(DesempenhoMensal d) {
        String sql = "INSERT INTO desempenho_mensal "
                   + "(alunoId, mesReferencia, anoReferencia, mediaEscola, notaAluno, observacaoGeral) "
                   + "VALUES (?, ?, ?, ?, ?, ?) "
                   + "ON DUPLICATE KEY UPDATE "
                   + "mediaEscola = VALUES(mediaEscola), "
                   + "notaAluno = VALUES(notaAluno), "
                   + "observacaoGeral = VALUES(observacaoGeral)";

        try (Connection c = Conexao.conectar();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, d.getAlunoId());
            ps.setInt(2, d.getMesReferencia());
            ps.setInt(3, d.getAnoReferencia());
            ps.setBigDecimal(4, d.getMediaEscola());
            ps.setBigDecimal(5, d.getNotaAluno());
            ps.setString(6, d.getObservacaoGeral());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<DesempenhoMensal> listarTodosComApontamentos() {
        List<DesempenhoMensal> lista = new ArrayList<>();
        String sql = "SELECT dm.*, a.nome AS nomeAluno "
                   + "FROM desempenho_mensal dm "
                   + "INNER JOIN aluno a ON dm.alunoId = a.id "
                   + "ORDER BY dm.anoReferencia DESC, dm.mesReferencia DESC, a.nome ASC";

        try (Connection c = Conexao.conectar();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                DesempenhoMensal d = new DesempenhoMensal();
                d.setId(rs.getInt("id"));
                d.setAlunoId(rs.getInt("alunoId"));
                d.setNomeAluno(rs.getString("nomeAluno"));
                d.setMesReferencia(rs.getInt("mesReferencia"));
                d.setAnoReferencia(rs.getInt("anoReferencia"));
                d.setMediaEscola(rs.getBigDecimal("mediaEscola"));
                d.setNotaAluno(rs.getBigDecimal("notaAluno"));
                d.setObservacaoGeral(rs.getString("observacaoGeral"));
                d.setApontamentosDoMes(apontamentoDAO.listarPorAlunoEMesAno(
                        d.getAlunoId(), d.getMesReferencia(), d.getAnoReferencia()));
                lista.add(d);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public boolean excluir(int id) {
        String sql = "DELETE FROM desempenho_mensal WHERE id = ?";
        try (Connection c = Conexao.conectar();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}