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
import br.com.thalison.sistema_acompanhamento_aluno.Aluno;

@WebServlet(urlPatterns = {
    "/nova-entrevista", 
    "/inserir-entrevista", 
    "/deletar", 
    "/editar", 
    "/atualizar", 
    "/listar"
})
public class EntrevistaServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private EntrevistaDAO dao;

    @Override
    public void init() {
        dao = new EntrevistaDAO();
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
            case "/nova-entrevista":
                mostrarFormularioNovaEntrevista(request, response);
                break;

            case "/inserir-entrevista":
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

            case "/listar":
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
        List<Entrevista> entrevistas = dao.listar();
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
        int id = parseInteger(request.getParameter("id"));
        
        Entrevista entrevistaExistente = dao.listar().stream()
                .filter(e -> e.getId() == id)
                .findFirst()
                .orElse(null);

        request.setAttribute("entrevista", entrevistaExistente);
        RequestDispatcher dispatcher = request.getRequestDispatcher("form-entrevista.jsp");
        dispatcher.forward(request, response);
    }

    private void inserirEntrevista(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int alunoId = parseInteger(request.getParameter("alunoId"));
        int psicologoId = parseInteger(request.getParameter("psicologoId"));
        String motivo = request.getParameter("motivo");
        
        String agendadaParam = request.getParameter("agendada");
        boolean agendada = "true".equalsIgnoreCase(agendadaParam) || "on".equalsIgnoreCase(agendadaParam);

        String dataParam = request.getParameter("data");
        Date data = (dataParam != null && !dataParam.trim().isEmpty()) ? Date.valueOf(dataParam) : null;
        String observacoes = request.getParameter("observacoes");

        Aluno a = alunoId > 0 ? new Aluno() : null;
        if (a != null) a.setId(alunoId);

        Psicologo p = psicologoId > 0 ? new Psicologo() : null;
        if (p != null) p.setId(psicologoId);

        Entrevista novaEntrevista = new Entrevista(a, p, 0, motivo, agendada, data, observacoes);
        dao.cadastrar(novaEntrevista);

        response.sendRedirect("listar");
    }

    private void atualizarEntrevista(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = parseInteger(request.getParameter("id"));
        int alunoId = parseInteger(request.getParameter("alunoId"));
        int psicologoId = parseInteger(request.getParameter("psicologoId"));
        String motivo = request.getParameter("motivo");
        
        String agendadaParam = request.getParameter("agendada");
        boolean agendada = "true".equalsIgnoreCase(agendadaParam) || "on".equalsIgnoreCase(agendadaParam);

        String dataParam = request.getParameter("data");
        Date data = (dataParam != null && !dataParam.trim().isEmpty()) ? Date.valueOf(dataParam) : null;
        String observacoes = request.getParameter("observacoes");

        Aluno a = alunoId > 0 ? new Aluno() : null;
        if (a != null) a.setId(alunoId);

        Psicologo p = psicologoId > 0 ? new Psicologo() : null;
        if (p != null) p.setId(psicologoId);

        Entrevista entrevistaAtualizada = new Entrevista(a, p, id, motivo, agendada, data, observacoes);
        dao.atualizar(entrevistaAtualizada);

        response.sendRedirect("listar");
    }

    private void deletarEntrevista(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = parseInteger(request.getParameter("id"));
        dao.excluir(id);
        response.sendRedirect("listar");
    }

    private int parseInteger(String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            return 0;
        }
        try {
            return Integer.parseInt(valor.trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}