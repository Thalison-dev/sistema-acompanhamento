package br.com.thalison.sistema_Acompanhamento_entrevista;

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

import br.com.thalison.sistema_acompanhamento_Psicologo.Psicologo;
import br.com.thalison.sistema_acompanhamento_aluno.aluno;

@WebServlet("/")
public class entrevistaServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private entrevistaDAO dao;

    @Override
    public void init() {
        dao = new entrevistaDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getServletPath();

        try {
            switch (action) {
            case "/novo":
                mostrarFormularioNovaEntrevista(request, response);
                break;

            case "/inserir":
                inserirEntrevista(request, response);
                break;

            case "/deletar":
                deletarEntrevista(request, response);
                break;

            case "/editar":
                mostrarFormularioEditarEntrevista(request, response);
                break;

            case "/atualizar":
                atualizarEntrevista(request, response);
                break;

            default:
                listarEntrevistas(request, response);
                break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void listarEntrevistas(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        List<entrevista> entrevistas = dao.listar();
        request.setAttribute("entrevistas", entrevistas);
        RequestDispatcher dispatcher = request.getRequestDispatcher("listar-entrevista.jsp");
        dispatcher.forward(request, response);
    }

    private void mostrarFormularioNovaEntrevista(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("form-entrevista.jsp");
        dispatcher.forward(request, response);
    }

    private void mostrarFormularioEditarEntrevista(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        
        entrevista entrevistaExistente = dao.listar().stream()
                .filter(e -> e.getId() == id)
                .findFirst()
                .orElse(null);

        request.setAttribute("entrevista", entrevistaExistente);
        RequestDispatcher dispatcher = request.getRequestDispatcher("form-entrevista.jsp");
        dispatcher.forward(request, response);
    }

    private void inserirEntrevista(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int alunoId = Integer.parseInt(request.getParameter("alunoId"));
        int psicologoId = Integer.parseInt(request.getParameter("psicologoId"));
        String motivo = request.getParameter("motivo");
        boolean agendada = Boolean.parseBoolean(request.getParameter("agendada"));
        String dataParam = request.getParameter("data");
        Date data = (dataParam != null && !dataParam.isEmpty()) ? Date.valueOf(dataParam) : null;
        String observacoes = request.getParameter("observacoes");

        aluno a = new aluno();
        a.setId(alunoId);

        Psicologo p = new Psicologo();
        p.setId(psicologoId);

        entrevista novaEntrevista = new entrevista(a, p, 0, motivo, agendada, data, observacoes);
        dao.cadastrar(novaEntrevista);

        response.sendRedirect("listar");
    }

    private void atualizarEntrevista(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        int alunoId = Integer.parseInt(request.getParameter("alunoId"));
        int psicologoId = Integer.parseInt(request.getParameter("psicologoId"));
        String motivo = request.getParameter("motivo");
        boolean agendada = Boolean.parseBoolean(request.getParameter("agendada"));
        String dataParam = request.getParameter("data");
        Date data = (dataParam != null && !dataParam.isEmpty()) ? Date.valueOf(dataParam) : null;
        String observacoes = request.getParameter("observacoes");

        aluno a = new aluno();
        a.setId(alunoId);

        Psicologo p = new Psicologo();
        p.setId(psicologoId);

        entrevista entrevistaAtualizada = new entrevista(a, p, id, motivo, agendada, data, observacoes);
        dao.atualizar(entrevistaAtualizada);

        response.sendRedirect("listar");
    }

    private void deletarEntrevista(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        dao.excluir(id);
        response.sendRedirect("listar");
    }
}
