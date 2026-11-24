package br.com.thalison.sistema_acompanhamento_desempenho;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import br.com.thalison.sistema_acompanhamento.Conexao;
import br.com.thalison.sistema_acompanhamento_Apontamento.Apontamento;

public class DesempenhoMensalDAO {

    public boolean salvarOuAtualizar(DesempenhoMensal d) {
        String sql = "INSERT INTO desempenho_mensal (alunoId, mesReferencia, anoReferencia, mediaEscola, notaAluno, observacaoGeral) "
                   + "VALUES (?, ?, ?, ?, ?, ?) "
                   + "ON DUPLICATE KEY UPDATE mediaEscola = VALUES(mediaEscola), notaAluno = VALUES(notaAluno), observacaoGeral = VALUES(observacaoGeral)";

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

    public List<DesempenhoMensal> listarComApontamentos() {
        List<DesempenhoMensal> lista = new ArrayList<>();
        String sql = "SELECT dm.*, pa.nome AS nomeAluno, n.tipoNeurodivergencia "
                   + "FROM desempenho_mensal dm "
                   + "INNER JOIN pessoa pa ON pa.id = dm.alunoId "
                   + "LEFT JOIN neurodivergencia n ON n.alunoId = dm.alunoId "
                   + "ORDER BY dm.anoReferencia DESC, dm.mesReferencia DESC, pa.nome ASC";

        try (Connection c = Conexao.conectar();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                DesempenhoMensal d = new DesempenhoMensal();
                d.setId(rs.getInt("id"));
                d.setAlunoId(rs.getInt("alunoId"));
                d.setNomeAluno(rs.getString("nomeAluno"));
                d.setTipoNeurodivergencia(rs.getString("tipoNeurodivergencia"));
                d.setMesReferencia(rs.getInt("mesReferencia"));
                d.setAnoReferencia(rs.getInt("anoReferencia"));
                d.setMediaEscola(rs.getBigDecimal("mediaEscola"));
                d.setNotaAluno(rs.getBigDecimal("notaAluno"));
                d.setObservacaoGeral(rs.getString("observacaoGeral"));
                d.setApontamentosDoMes(buscarApontamentosDoMes(c, d.getAlunoId(), d.getMesReferencia(), d.getAnoReferencia()));
                lista.add(d);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    private List<Apontamento> buscarApontamentosDoMes(Connection c, int alunoId, int mes, int ano) throws SQLException {
        List<Apontamento> apontamentos = new ArrayList<>();
        String sql = "SELECT ap.*, pp.nome AS nomeProfessor "
                   + "FROM apontamento ap "
                   + "INNER JOIN pessoa pp ON pp.id = ap.professorId "
                   + "WHERE ap.alunoId = ? AND MONTH(ap.data) = ? AND YEAR(ap.data) = ? "
                   + "ORDER BY ap.data DESC";

        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, alunoId);
            ps.setInt(2, mes);
            ps.setInt(3, ano);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Apontamento ap = new Apontamento();
                    ap.setId(rs.getInt("id"));
                    ap.setAlunoId(rs.getInt("alunoId"));
                    ap.setNomeProfessor(rs.getString("nomeProfessor"));
                    Date d = rs.getDate("data");
                    ap.setData(d != null ? d.toLocalDate() : null);
                    ap.setMotivo("[" + rs.getString("tipoApontamento") + "] " + rs.getString("motivo"));
                    apontamentos.add(ap);
                }
            }
        }
        return apontamentos;
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