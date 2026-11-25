package br.com.thalison.sistema_acompanhamento_Psicologo;

import br.com.thalison.sistema_acompanhamento.Conexao;
import br.com.thalison.sistema_acompanhamento_aluno.Aluno;
import br.com.thalison.sistema_acompanhamento_Pedagogo.Pedagogo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PsicologoDAO {

	// Busca psicólogo por ID — retorna objeto Psicologo completo
	public Psicologo buscarPorId(int pessoaId) {
		String sql = "SELECT p.id, p.nome, p.email, p.cpf, p.telefone, p.perfil, " + "ps.crp, ps.especialidade "
				+ "FROM pessoa p " + "INNER JOIN psicologo ps ON p.id = ps.pessoaId " + "WHERE p.id = ?";

		try (Connection c = Conexao.conectar(); PreparedStatement ps = c.prepareStatement(sql)) {

			ps.setInt(1, pessoaId);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return mapear(rs);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	// Lista todos os psicólogos cadastrados
	public List<Psicologo> listarTodos() {
		List<Psicologo> lista = new ArrayList<>();
		String sql = "SELECT p.id, p.nome, p.email, p.cpf, p.telefone, p.perfil, " + "ps.crp, ps.especialidade "
				+ "FROM pessoa p " + "INNER JOIN psicologo ps ON p.id = ps.pessoaId " + "ORDER BY p.nome";

		try (Connection c = Conexao.conectar();
				PreparedStatement ps = c.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				lista.add(mapear(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lista;
	}

	// Agenda entrevista — recebe objetos Psicologo e Aluno (não ints soltos)
	public boolean agendarEntrevista(Psicologo psicologo, Aluno aluno, String motivo, String data) {
		String sql = "INSERT INTO entrevista "
				+ "(aluno_id, psicologo_id, nome_aluno, nome_psicologo, data, motivo, agendada) "
				+ "VALUES (?, ?, ?, ?, ?, ?, TRUE)";

		try (Connection c = Conexao.conectar(); PreparedStatement ps = c.prepareStatement(sql)) {

			ps.setInt(1, aluno.getId());
			ps.setInt(2, psicologo.getId());
			ps.setString(3, aluno.getNome());
			ps.setString(4, psicologo.getNome());
			ps.setString(5, data);
			ps.setString(6, motivo);

			return ps.executeUpdate() > 0;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	// Envia mensagem para Pedagogo — recebe objetos (não ints soltos)
	public boolean enviarMensagemParaPedagogo(Psicologo psicologo, Pedagogo pedagogo, String conteudo) {
		String sql = "INSERT INTO mensagem " + "(remetenteId, destinatarioId, conteudoMensagem, dataEnvioMensagem) "
				+ "VALUES (?, ?, ?, NOW())";

		try (Connection c = Conexao.conectar(); PreparedStatement ps = c.prepareStatement(sql)) {

			ps.setInt(1, psicologo.getId());
			ps.setInt(2, pedagogo.getId());
			ps.setString(3, conteudo);

			return ps.executeUpdate() > 0;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	private Psicologo mapear(ResultSet rs) throws SQLException {
		Psicologo p = new Psicologo();
		p.setId(rs.getInt("id"));
		p.setNome(rs.getString("nome"));
		p.setEmail(rs.getString("email"));
		p.setCpf(rs.getString("cpf"));
		p.setTelefone(rs.getString("telefone"));
		p.setPerfil(rs.getString("perfil"));
		p.setCrp(rs.getString("crp"));
		p.setEspecialidade(rs.getString("especialidade"));
		return p;
	}
}