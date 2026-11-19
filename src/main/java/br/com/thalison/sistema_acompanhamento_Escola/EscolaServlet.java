package br.com.thalison.sistema_acompanhamento_Escola;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/nova-escola", "/inserir-escola", "/deletar-escola", "/editar-escola", "/atualizar-escola", "/listar-escolas"})
public class EscolaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private EscolaDAO dao;

    @Override
    public void init() {
        dao = new EscolaDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();
        try {
            switch (action) {
                case "/nova-escola":
                    mostrarFormularioNovo(request, response);
                    break;
                case "/inserir-escola":
                    inserirEscola(request, response);
                    break;
                case "/deletar-escola":
                    deletarEscola(request, response);
                    break;
                case "/editar-escola":
                    mostrarFormularioEditar(request, response);
                    break;
                case "/atualizar-escola":
                    atualizarEscola(request, response);
                    break;
                default:
                    listarEscolas(request, response);
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

    private void listarEscolas(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        List<Escola> lista = dao.listar();
        request.setAttribute("escolas", lista);
        RequestDispatcher dispatcher = request.getRequestDispatcher("listar-escola.jsp");
        dispatcher.forward(request, response);
    }

    private void mostrarFormularioNovo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("form-escola.jsp");
        dispatcher.forward(request, response);
    }

    private void mostrarFormularioEditar(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Escola escola = dao.buscarPorId(id);
        request.setAttribute("escola", escola);
        RequestDispatcher dispatcher = request.getRequestDispatcher("form-escola.jsp");
        dispatcher.forward(request, response);
    }

    private void inserirEscola(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        String nome = request.getParameter("nome");
        String endereco = request.getParameter("endereco");
        String telefone = request.getParameter("telefone");
        String email = request.getParameter("email");

        Escola e = new Escola();
        e.setNome(nome);
        e.setEndereco(endereco);
        e.setTelefone(telefone);
        e.setEmail(email);

        dao.cadastrar(e);
        response.sendRedirect("listar-escolas");
    }

    private void atualizarEscola(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String nome = request.getParameter("nome");
        String endereco = request.getParameter("endereco");
        String telefone = request.getParameter("telefone");
        String email = request.getParameter("email");

        Escola e = new Escola();
        e.setId(id);
        e.setNome(nome);
        e.setEndereco(endereco);
        e.setTelefone(telefone);
        e.setEmail(email);

        dao.atualizar(e);
        response.sendRedirect("listar-escolas");
    }

    private void deletarEscola(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        dao.excluir(id);
        response.sendRedirect("listar-escolas");
    }
}