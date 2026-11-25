package br.com.thalison.sistema_acompanhamento_Professor;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.thalison.sistema_acompanhamento_aluno.Aluno;
import br.com.thalison.sistema_acompanhamento_aluno.AlunoDAO;
import br.com.thalison.sistema_acompanhamento_Apontamento.Apontamento;

import java.util.List;

@WebServlet(urlPatterns = {
        "/listarAlunosProfessor",
        "/registrarApontamento",
        "/consultarApontamentos"
})
public class ProfessorServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private ProfessorDAO dao;
    private AlunoDAO alunoDAO;

    public void init() {
        dao = new ProfessorDAO();
        alunoDAO = new AlunoDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getServletPath();

        switch (action) {

        case "/listarAlunosProfessor":
            listarAlunos(request, response);
            break;

        case "/registrarApontamento":
            registrarApontamento(request, response);
            break;

        case "/consultarApontamentos":
            consultarApontamentos(request, response);
            break;

        default:
            listarAlunos(request, response);
            break;
        }
    }

    private void listarAlunos(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Aluno> alunos = dao.listarAlunos();
        request.setAttribute("alunos", alunos);
        RequestDispatcher dispatcher = request.getRequestDispatcher("listar-alunos.jsp");
        dispatcher.forward(request, response);
    }

    private void registrarApontamento(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        int professorId = Integer.parseInt(request.getParameter("professorId"));
        int alunoId = Integer.parseInt(request.getParameter("alunoId"));
        String descricao = request.getParameter("descricao");
        String disciplina = request.getParameter("disciplina");

        // Busca os objetos completos — nunca passa int solto pro DAO
        Professor professor = dao.buscarPorId(professorId);
        Aluno aluno = alunoDAO.buscarPorId(alunoId);

        dao.registrarApontamento(professor, aluno, descricao, disciplina);
        response.sendRedirect("consultarApontamentos?alunoId=" + alunoId);
    }

    private void consultarApontamentos(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int alunoId = Integer.parseInt(request.getParameter("alunoId"));
        Aluno aluno = alunoDAO.buscarPorId(alunoId);

        List<Apontamento> apontamentos = dao.consultarApontamentos(aluno);

        request.setAttribute("aluno", aluno);
        request.setAttribute("apontamentos", apontamentos);
        RequestDispatcher dispatcher = request.getRequestDispatcher("historico-aluno.jsp");
        dispatcher.forward(request, response);
    }
}