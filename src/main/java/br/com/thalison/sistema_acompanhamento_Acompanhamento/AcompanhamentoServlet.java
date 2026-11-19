package br.com.thalison.sistema_acompanhamento_Acompanhamento;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/novo-acompanhamento", "/inserir", "/deletar", "/editar", "/atualizar", "/listar"})
public class AcompanhamentoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private AcompanhamentoDAO dao;

    @Override
    public void init() {
        dao = new AcompanhamentoDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();
        try {
            switch (action) {
                case "/novo-acompanhamento":
                    mostrarFormularioNovo(request, response);
                    break;
                case "/inserir":
                    inserirAcompanhamento(request, response);
                    break;
                case "/deletar":
                    deletarAcompanhamento(request, response);
                    break;
                case "/editar":
                    mostrarFormularioEditar(request, response);
                    break;
                case "/atualizar":
                    atualizarAcompanhamento(request, response);
                    break;
                default:
                    listarAcompanhamentos(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    private void listarAcompanhamentos(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        List<Acompanhamento> lista = dao.listar();
        request.setAttribute("acompanhamentos", lista);
        RequestDispatcher dispatcher = request.getRequestDispatcher("listar-acompanhamento.jsp");
        dispatcher.forward(request, response);
    }

    private void mostrarFormularioNovo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("acompanhamento.jsp");
        dispatcher.forward(request, response);
    }

    private void mostrarFormularioEditar(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Acompanhamento acompanhamento = dao.buscarPorId(id);
        request.setAttribute("acompanhamento", acompanhamento);
        RequestDispatcher dispatcher = request.getRequestDispatcher("form-acompanhamento.jsp");
        dispatcher.forward(request, response);
    }

    private void inserirAcompanhamento(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        Date dataInicio = Date.valueOf(request.getParameter("dataInicio"));
        Date dataFim = Date.valueOf(request.getParameter("dataFim"));
        String objetivo = request.getParameter("objetivo");
        String status = request.getParameter("status");

        Acompanhamento a = new Acompanhamento();
        a.setDataInicio(dataInicio);
        a.setDataFim(dataFim);
        a.setObjetivo(objetivo);
        a.setStatus(status);

        dao.cadastrar(a);
        response.sendRedirect("listar");
    }

    private void atualizarAcompanhamento(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Date dataInicio = Date.valueOf(request.getParameter("dataInicio"));
        Date dataFim = Date.valueOf(request.getParameter("dataFim"));
        String objetivo = request.getParameter("objetivo");
        String status = request.getParameter("status");

        Acompanhamento a = new Acompanhamento();
        a.setId(id);
        a.setDataInicio(dataInicio);
        a.setDataFim(dataFim);
        a.setObjetivo(objetivo);
        a.setStatus(status);

        dao.atualizar(a);
        response.sendRedirect("listar");
    }

    private void deletarAcompanhamento(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        dao.excluir(id);
        response.sendRedirect("listar");
    }
}