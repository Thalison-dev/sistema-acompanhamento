package br.com.thalison.sistema_acompanhamento_Psicologo;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {
	    "/consultar", 
	    "/marcarReuniaoAluno", 
	    "/marcarReuniaoPedagogo", 
	    "/atualizarRegistro", 
	    "/verRegistro", 
	})
public class PsicologoServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private PsicologoDAO dao;

	public void init() {
		dao = new PsicologoDAO();
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

			case "/consultar":
				consultarPsicologo(request, response);
				break;

			case "/marcarReuniaoAluno":
				marcarReuniaoComAluno(request, response);
				break;

			case "/marcarReuniaoPedagogo":
				marcarReuniaoComPedagogo(request, response);
				break;

			case "/atualizarRegistro":
				atualizarRegistroDoAluno(request, response);
				break;

			case "/verRegistro":
				verRegistroDoAluno(request, response);
				break;

			default:
				consultarPsicologo(request, response);
				break;
			}

		} catch (SQLException ex) {
			throw new ServletException(ex);
		}
	}

	private void consultarPsicologo(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {

		int id = Integer.parseInt(request.getParameter("id"));
		Psicologo psicologo = dao.consultarPsicologo(id);
		request.setAttribute("psicologo", psicologo);
		RequestDispatcher dispatcher = request.getRequestDispatcher("detalhe-psicologo.jsp");
		dispatcher.forward(request, response);
	}

	private void marcarReuniaoComAluno(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException {

		int psicologoId = Integer.parseInt(request.getParameter("psicologoId"));
		int alunoId = Integer.parseInt(request.getParameter("alunoId"));
		String reuniao = request.getParameter("reuniao");

		dao.marcarReuniaoComAluno(psicologoId, alunoId, reuniao);
		response.sendRedirect("consultar?id=" + psicologoId);
	}

	private void marcarReuniaoComPedagogo(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException {

		int psicologoId = Integer.parseInt(request.getParameter("psicologoId"));
		int pedagogoId = Integer.parseInt(request.getParameter("pedagogoId"));
		String reuniao = request.getParameter("reuniao");

		dao.marcarReuniaoComPedagogo(psicologoId, pedagogoId, reuniao);
		response.sendRedirect("consultar?id=" + psicologoId);
	}

	private void atualizarRegistroDoAluno(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException {

		int alunoId = Integer.parseInt(request.getParameter("alunoId"));
		String registro = request.getParameter("registro");

		dao.atualizarRegistroDoAluno(alunoId, registro);
		response.sendRedirect("verRegistro?alunoId=" + alunoId);
	}

	private void verRegistroDoAluno(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {

		int alunoId = Integer.parseInt(request.getParameter("alunoId"));
		dao.verRegistroDoAluno(alunoId);
		RequestDispatcher dispatcher = request.getRequestDispatcher("registro-aluno.jsp");
		dispatcher.forward(request, response);
	}
}