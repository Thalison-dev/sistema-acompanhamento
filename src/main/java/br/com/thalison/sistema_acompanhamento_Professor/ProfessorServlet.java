package br.com.thalison.sistema_acompanhamento_Professor;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.thalison.sistema_acompanhamento_Professor.ProfessorDAO;

@WebServlet("/")
public class ProfessorServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private ProfessorDAO dao;

	public void init() {
		dao = new ProfessorDAO();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getServletPath();

		try {
			switch (action) {

			case "/consultarAluno":
				consultarAluno(request, response);
				break;

			case "/registrarObservacao":
				registrarObservacao(request, response);
				break;

			case "/registrarOcorrencia":
				registrarOcorrencia(request, response);
				break;

			case "/registrarAvaliacao":
				registrarAvaliacao(request, response);
				break;

			case "/registrarRendimento":
				registrarRendimento(request, response);
				break;

			case "/registrarAdaptacao":
				registrarAdaptacao(request, response);
				break;

			case "/consultarHistorico":
				consultarHistorico(request, response);
				break;

			default:
				consultarAluno(request, response);
				break;
			}

		} catch (SQLException ex) {
			throw new ServletException(ex);
		}
	}

	private void consultarAluno(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {

		dao.consultarAluno();
		RequestDispatcher dispatcher = request.getRequestDispatcher("listar-alunos.jsp");
		dispatcher.forward(request, response);
	}

	private void registrarObservacao(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException {

		int id = Integer.parseInt(request.getParameter("id"));
		String descricao = request.getParameter("descricao");
		dao.registrarObservacao(id, descricao);
		response.sendRedirect("consultarHistorico?id=" + id);
	}

	private void registrarOcorrencia(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException {

		int id = Integer.parseInt(request.getParameter("id"));
		String descricao = request.getParameter("descricao");
		dao.registrarOcorrencia(id, descricao);
		response.sendRedirect("consultarHistorico?id=" + id);
	}

	private void registrarAvaliacao(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException {

		int id = Integer.parseInt(request.getParameter("id"));
		String descricao = request.getParameter("descricao");
		dao.registrarAvaliacao(id, descricao);
		response.sendRedirect("consultarHistorico?id=" + id);
	}

	private void registrarRendimento(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException {

		int id = Integer.parseInt(request.getParameter("id"));
		String descricao = request.getParameter("descricao");
		dao.registrarRendimento(id, descricao);
		response.sendRedirect("consultarHistorico?id=" + id);
	}

	private void registrarAdaptacao(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException {

		int id = Integer.parseInt(request.getParameter("id"));
		String descricao = request.getParameter("descricao");
		dao.registrarAdaptacao(id, descricao);
		response.sendRedirect("consultarHistorico?id=" + id);
	}

	private void consultarHistorico(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {

		int alunoId = Integer.parseInt(request.getParameter("id"));
		dao.consultarHistorico(alunoId);
		RequestDispatcher dispatcher = request.getRequestDispatcher("historico-aluno.jsp");
		dispatcher.forward(request, response);
	}
}