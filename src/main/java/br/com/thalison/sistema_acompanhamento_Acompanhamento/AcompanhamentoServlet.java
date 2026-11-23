package br.com.thalison.sistema_acompanhamento_Acompanhamento;

import java.io.IOException;
<<<<<<< HEAD
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.RequestDispatcher;
=======
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
>>>>>>> aea11f6ce5fbd1f58dabfdfc3ecc8b2629d7611a
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

<<<<<<< HEAD
import br.com.thalison.sistema_acompanhamento_aluno.Aluno;

@WebServlet(urlPatterns = {
    "/novo-acompanhamento",
    "/inserir",
    "/deletar",
    "/editar",
    "/atualizar",
    "/listar"
})
=======
import br.com.thalison.sistema_acompanhamento_ResponsavelLegal.ResponsavelLegalDAO;
import br.com.thalison.sistema_acompanhamento_aluno.AlunoDAO;

@WebServlet(urlPatterns = {"/acompanhamento", "/novo-acompanhamento"})
>>>>>>> aea11f6ce5fbd1f58dabfdfc3ecc8b2629d7611a
public class AcompanhamentoServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private AcompanhamentoDAO dao;
    private AlunoDAO alunoDao;
    private ResponsavelLegalDAO responsavelDao;

    @Override
    public void init() {
        dao = new AcompanhamentoDAO();
        alunoDao = new AlunoDAO();
        responsavelDao = new ResponsavelLegalDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

<<<<<<< HEAD
        request.setCharacterEncoding("UTF-8");
        String action = request.getServletPath();
=======
        String acao = request.getParameter("acao");
        if (acao == null) {
            acao = "listar";
        }
>>>>>>> aea11f6ce5fbd1f58dabfdfc3ecc8b2629d7611a

        switch (acao) {
            case "editar":
                mostrarFormularioEditar(request, response);
                break;

            case "excluir":
                deletarAcompanhamento(request, response);
                break;

            default:
                listarAcompanhamentos(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String acao = request.getParameter("acao");

        if ("atualizar".equals(acao)) {
            atualizarAcompanhamento(request, response);
        } else {
            inserirAcompanhamento(request, response);
        }
    }

    private void listarAcompanhamentos(HttpServletRequest request, HttpServletResponse response)
<<<<<<< HEAD
            throws SQLException, IOException, ServletException {

        List<Acompanhamento> lista = dao.listar();
        request.setAttribute("acompanhamentos", lista);

        RequestDispatcher dispatcher =
                request.getRequestDispatcher("listar-acompanhamento.jsp");
        dispatcher.forward(request, response);
    }

    private void mostrarFormularioNovo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RequestDispatcher dispatcher =
                request.getRequestDispatcher("acompanhamento.jsp");
        dispatcher.forward(request, response);
=======
            throws ServletException, IOException {

        request.setAttribute("alunos", alunoDao.listarTodos());
        request.setAttribute("responsaveis", responsavelDao.listarTodos());
        request.setAttribute("acompanhamentos", dao.listar());
        request.getRequestDispatcher("/acompanhamento.jsp").forward(request, response);
>>>>>>> aea11f6ce5fbd1f58dabfdfc3ecc8b2629d7611a
    }

    private void mostrarFormularioEditar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

<<<<<<< HEAD
        int id = Integer.parseInt(request.getParameter("id"));
        Acompanhamento acompanhamento = dao.buscarPorId(id);

        request.setAttribute("acompanhamento", acompanhamento);

        RequestDispatcher dispatcher =
                request.getRequestDispatcher("form-acompanhamento.jsp");
        dispatcher.forward(request, response);
=======
        int id = parseInt(request.getParameter("id"));
        if (id > 0) {
            Acompanhamento acompanhamento = dao.buscarPorId(id);
            request.setAttribute("acompanhamentoEdit", acompanhamento);
        }
        listarAcompanhamentos(request, response);
>>>>>>> aea11f6ce5fbd1f58dabfdfc3ecc8b2629d7611a
    }

    private void inserirAcompanhamento(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try {
            Acompanhamento a = montarAcompanhamento(request, 0);

<<<<<<< HEAD
        int alunoId = Integer.parseInt(request.getParameter("alunoId"));
        String dataInicioParam = request.getParameter("dataInicio");
        String dataFimParam = request.getParameter("dataFim");

        LocalDate dataInicio = LocalDate.parse(dataInicioParam);
        LocalDate dataFim = (dataFimParam != null && !dataFimParam.trim().isEmpty())
                ? LocalDate.parse(dataFimParam)
                : null;

        String objetivo = request.getParameter("objetivo");
        String status = request.getParameter("status");

        Aluno aluno = new Aluno();
        aluno.setId(alunoId); 

        Acompanhamento a = new Acompanhamento();
        a.setAluno(aluno);
        a.setDataInicio(dataInicio);
        a.setDataFim(dataFim);
        a.setObjetivo(objetivo);
        a.setStatus(status);

        dao.cadastrar(a);

        response.sendRedirect("listar");
=======
            if (dao.cadastrar(a)) {
                redirecionar(response, request.getContextPath(), "sucesso",
                        "Acompanhamento registrado com sucesso!");
            } else {
                redirecionar(response, request.getContextPath(), "erro",
                        "Não foi possível cadastrar o acompanhamento.");
            }
        } catch (IllegalArgumentException e) {
            redirecionar(response, request.getContextPath(), "erro", e.getMessage());
        }
>>>>>>> aea11f6ce5fbd1f58dabfdfc3ecc8b2629d7611a
    }

    private void atualizarAcompanhamento(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try {
            int id = parseInt(request.getParameter("id"));
            if (id <= 0) {
                throw new IllegalArgumentException("ID do acompanhamento inválido.");
            }

<<<<<<< HEAD
        int id = Integer.parseInt(request.getParameter("id"));
        int alunoId = Integer.parseInt(request.getParameter("alunoId"));
        String dataInicioParam = request.getParameter("dataInicio");
        String dataFimParam = request.getParameter("dataFim");

        LocalDate dataInicio = LocalDate.parse(dataInicioParam);
        LocalDate dataFim = (dataFimParam != null && !dataFimParam.trim().isEmpty())
                ? LocalDate.parse(dataFimParam)
                : null;

        String objetivo = request.getParameter("objetivo");
        String status = request.getParameter("status");

        Aluno aluno = new Aluno();
        aluno.setId(alunoId); 

        Acompanhamento a = new Acompanhamento();
        a.setAcompanhamentoId(id);
        a.setAluno(aluno);
        a.setDataInicio(dataInicio);
        a.setDataFim(dataFim);
        a.setObjetivo(objetivo);
        a.setStatus(status);

        dao.atualizar(a);

        response.sendRedirect("listar");
=======
            Acompanhamento a = montarAcompanhamento(request, id);

            if (dao.atualizar(a)) {
                redirecionar(response, request.getContextPath(), "sucesso",
                        "Acompanhamento atualizado com sucesso!");
            } else {
                redirecionar(response, request.getContextPath(), "erro",
                        "Não foi possível atualizar o acompanhamento.");
            }
        } catch (IllegalArgumentException e) {
            redirecionar(response, request.getContextPath(), "erro", e.getMessage());
        }
>>>>>>> aea11f6ce5fbd1f58dabfdfc3ecc8b2629d7611a
    }

    private void deletarAcompanhamento(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

<<<<<<< HEAD
        int id = Integer.parseInt(request.getParameter("id"));
        dao.excluir(id);

        response.sendRedirect("listar");
    }
=======
        int id = parseInt(request.getParameter("id"));
        if (id > 0 && dao.excluir(id)) {
            redirecionar(response, request.getContextPath(), "sucesso",
                    "Acompanhamento excluído com sucesso!");
        } else {
            redirecionar(response, request.getContextPath(), "erro",
                    "Não foi possível excluir o acompanhamento.");
        }
    }

    private Acompanhamento montarAcompanhamento(HttpServletRequest request, int id) {
        int alunoId = parseInt(request.getParameter("alunoId"));
        int responsavelLegalId = parseInt(request.getParameter("responsavelLegalId"));
        String dataInicio = request.getParameter("dataInicio");
        String dataFim = request.getParameter("dataFim");
        String objetivo = request.getParameter("objetivo");
        String status = request.getParameter("status");

        if (alunoId <= 0) {
            throw new IllegalArgumentException("Selecione um aluno válido.");
        }
        if (dataInicio == null || dataInicio.trim().isEmpty()) {
            throw new IllegalArgumentException("A data de início é obrigatória.");
        }
        if (objetivo == null || objetivo.trim().isEmpty()) {
            throw new IllegalArgumentException("O objetivo é obrigatório.");
        }
        if (status == null || status.trim().isEmpty()) {
            throw new IllegalArgumentException("O status é obrigatório.");
        }

        String dataFimLimpa = (dataFim != null && !dataFim.trim().isEmpty()) ? dataFim.trim() : null;
        if (dataFimLimpa != null && dataFimLimpa.compareTo(dataInicio.trim()) < 0) {
            throw new IllegalArgumentException("A data de fim não pode ser anterior à data de início.");
        }

        Acompanhamento a = new Acompanhamento();
        a.setAcompanhamentoId(id);
        a.setAlunoId(alunoId);
        a.setResponsavelLegalId(responsavelLegalId);
        a.setDataInicio(dataInicio.trim());
        a.setDataFim(dataFimLimpa);
        a.setObjetivo(objetivo.trim());
        a.setStatus(status.trim());
        return a;
    }

    private void redirecionar(HttpServletResponse response, String contextPath, String tipo, String msg)
            throws IOException {
        String encoded = URLEncoder.encode(msg, StandardCharsets.UTF_8.name());
        response.sendRedirect(contextPath + "/acompanhamento?" + tipo + "=" + encoded);
    }

    private int parseInt(String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            return 0;
        }
        try {
            return Integer.parseInt(valor.trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }
>>>>>>> aea11f6ce5fbd1f58dabfdfc3ecc8b2629d7611a
}