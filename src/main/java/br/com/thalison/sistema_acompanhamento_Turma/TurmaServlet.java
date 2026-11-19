package br.com.thalison.sistema_acompanhamento_Turma;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/nova-turma", "/inserir-turma", "/deletar-turma", "/editar-turma", "/atualizar-turma", "/listar-turmas"})
public class TurmaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private TurmaDAO dao;

    @Override
    public void init() {
        dao = new TurmaDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();
        try {
            switch (action) {
                case "/nova-turma":
                    mostrarFormularioNovo(request, response);
                    break;
                case "/inserir-turma":
                    inserirTurma(request, response);
                    break;
                case "/deletar-turma":
                    deletarTurma(request, response);
                    break;
                case "/editar-turma":
                    mostrarFormularioEditar(request, response);
                    break;
                case "/atualizar-turma":
                    atualizarTurma(request, response);
                    break;
                default:
                    listarTurmas(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        doGet(request, response);
    }

    private void listarTurmas(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        List<Turma> lista = dao.listar();
        request.setAttribute("turmas", lista);
        RequestDispatcher dispatcher = request.getRequestDispatcher("listar-turma.jsp");
        dispatcher.forward(request, response);
    }

    private void mostrarFormularioNovo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("form-turma.jsp");
        dispatcher.forward(request, response);
    }

    private void mostrarFormularioEditar(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Turma turma = dao.buscarPorId(id);
        request.setAttribute("turma", turma);
        RequestDispatcher dispatcher = request.getRequestDispatcher("form-turma.jsp");
        dispatcher.forward(request, response);
    }

    private void inserirTurma(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String nome = request.getParameter("nome");
        String anoLetivo = request.getParameter("anoLetivo");
        String turno = request.getParameter("turno");

        Turma t = new Turma();
        t.setNome(nome);
        t.setAnoLetivo(anoLetivo);
        t.setTurno(turno);

        dao.cadastrar(t);
        response.sendRedirect("listar-turmas");
    }

    private void atualizarTurma(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String nome = request.getParameter("nome");
        String anoLetivo = request.getParameter("anoLetivo");
        String turno = request.getParameter("turno");

        Turma t = new Turma();
        t.setId(id);
        t.setNome(nome);
        t.setAnoLetivo(anoLetivo);
        t.setTurno(turno);

        dao.atualizar(t);
        response.sendRedirect("listar-turmas");
    }

    private void deletarTurma(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        dao.excluir(id);
        response.sendRedirect("listar-turmas");
    }
}