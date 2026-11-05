package br.com.thalison.sistema_acompanhamento_Acompanhamento; 

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/novo-acompanhamento"})
public class AcompanhamentoServelet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private AcompanhamentoDAO dao;

    public void init() {
     //   dao = new AcompanhamentoDAOImpl();
    }

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

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    private void listarAcompanhamentos(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
    //    List<Acompanhamento> lista = dao.recuperarTodos();
    //    request.setAttribute("acompanhamentos", lista);
        RequestDispatcher dispatcher = request.getRequestDispatcher("listar-acompanhamento.jsp");
        dispatcher.forward(request, response);
    }

    private void mostrarFormularioNovo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("form-acompanhamento.jsp");
        dispatcher.forward(request, response);
    }

    private void mostrarFormularioEditar(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        //long id = Long.parseLong(request.getParameter("id"));
       // Acompanhamento acompanhamento = dao.recuperarPorId(id);
       // request.setAttribute("acompanhamento", acompanhamento);
        RequestDispatcher dispatcher = request.getRequestDispatcher("form-acompanhamento.jsp");
        dispatcher.forward(request, response);
    }

    private void inserirAcompanhamento(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String dataInicio = request.getParameter("dataInicio");
        String dataFim = request.getParameter("dataFim");
        String objetivo = request.getParameter("objetivo");
        String status = request.getParameter("status");

        Acompanhamento a = new Acompanhamento();
        a.setdataIncio(dataInicio);
        a.setdataFim(dataFim);
        a.setobjetivo(objetivo);
        a.setstatus(status);

    //    dao.inserir(a);
        response.sendRedirect("./");
    }

    private void atualizarAcompanhamento(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String id = request.getParameter("id");
        String dataInicio = request.getParameter("dataInicio");
        String dataFim = request.getParameter("dataFim");
        String objetivo = request.getParameter("objetivo");
        String status = request.getParameter("status");

        Acompanhamento a = new Acompanhamento();
        a.setid(id);
        a.setdataIncio(dataInicio);
        a.setdataFim(dataFim);
        a.setobjetivo(objetivo);
        a.setstatus(status);

        dao.atualizar(a);
        response.sendRedirect("./");
    }

    private void deletarAcompanhamento(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        long id = Long.parseLong(request.getParameter("id"));
     //   Acompanhamento a = dao.recuperarPorId(id);
      //  if (a != null) {
        //    dao.deletar(a);
//        }
        response.sendRedirect("./");
    }
}