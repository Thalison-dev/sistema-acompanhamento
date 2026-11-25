package br.com.thalison.sistema_acompanhamento_aluno;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/listar-alunos")
public class ListarAlunosServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final AlunoDAO alunoDAO = new AlunoDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("alunos", alunoDAO.listarTodos());
        req.getRequestDispatcher("/alunos.jsp").forward(req, resp);
    }
}