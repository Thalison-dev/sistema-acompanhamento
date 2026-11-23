package br.com.thalison.sistema_acompanhamento_Pedagogo;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.thalison.sistema_acompanhamento_Acompanhamento.Acompanhamento;
import br.com.thalison.sistema_acompanhamento_Acompanhamento.AcompanhamentoDAO;
import br.com.thalison.sistema_acompanhamento_ResponsavelLegal.ResponsavelLegalDAO;
import br.com.thalison.sistema_acompanhamento_aluno.Aluno;
import br.com.thalison.sistema_acompanhamento_aluno.AlunoDAO;

@WebServlet("/pedagogo")
public class PedagogoServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final PedagogoDAO pedagogoDao = new PedagogoDAO();
    private final AlunoDAO alunoDao = new AlunoDAO();
    private final ResponsavelLegalDAO responsavelDao = new ResponsavelLegalDAO();
    private final AcompanhamentoDAO acompanhamentoDao = new AcompanhamentoDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String acao = req.getParameter("acao");
        if (acao == null) {
            acao = "painel";
        }

        switch (acao) {
            case "reuniao":
                req.setAttribute("alunos", alunoDao.listarTodos());
                req.setAttribute("pedagogos", pedagogoDao.listar());
                req.getRequestDispatcher("/reuniao-pedagogo.jsp").forward(req, resp);
                break;

            case "historicoAluno":
                abrirHistoricoAluno(req, resp);
                break;

            case "perfil":
                req.setAttribute("pedagogos", pedagogoDao.listar());
                req.getRequestDispatcher("/perfil-pedagogo.jsp").forward(req, resp);
                break;

            default:
                abrirPainelPedagogo(req, resp);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        String acao = req.getParameter("acao");

        if ("agendarReuniao".equals(acao)) {
            agendarReuniao(req, resp);
        } else if ("salvarPerfil".equals(acao)) {
            salvarPerfil(req, resp);
        } else {
            redirecionar(resp, req.getContextPath(), "erro", "Ação inválida.");
        }
    }

    private void abrirPainelPedagogo(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("pedagogos", pedagogoDao.listar());
        req.setAttribute("alunos", alunoDao.listarTodos());
        req.setAttribute("responsaveis", responsavelDao.listarTodos());
        req.setAttribute("acompanhamentos", acompanhamentoDao.listar());
        req.setAttribute("reunioes", pedagogoDao.listarReunioes());
        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }

    private void abrirHistoricoAluno(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        int alunoId = parseInt(req.getParameter("alunoId"));
        if (alunoId <= 0) {
            redirecionar(resp, req.getContextPath(), "erro", "Selecione um aluno válido.");
            return;
        }

        Aluno aluno = alunoDao.buscarPorId(alunoId);
        if (aluno == null) {
            redirecionar(resp, req.getContextPath(), "erro", "Aluno não encontrado.");
            return;
        }

        List<Acompanhamento> todos = acompanhamentoDao.listar();
        List<Acompanhamento> doAluno = new ArrayList<>();
        for (Acompanhamento a : todos) {
            if (a.getAlunoId() == alunoId) {
                doAluno.add(a);
            }
        }

        req.setAttribute("aluno", aluno);
        req.setAttribute("acompanhamentosAluno", doAluno);
        req.getRequestDispatcher("/historico-aluno.jsp").forward(req, resp);
    }

    private void agendarReuniao(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        int pedagogoId = parseInt(req.getParameter("pedagogoId"));
        String publicoAlvo = req.getParameter("publicoAlvo");
        int alunoId = parseInt(req.getParameter("alunoId"));
        String dataReuniao = req.getParameter("dataReuniao");
        String motivo = req.getParameter("motivo");

        if (publicoAlvo == null || publicoAlvo.trim().isEmpty()
                || dataReuniao == null || dataReuniao.trim().isEmpty()
                || motivo == null || motivo.trim().isEmpty()) {
            redirecionar(resp, req.getContextPath(), "erro", "Preencha todos os campos da reunião.");
            return;
        }

        if (pedagogoDao.registrarReuniao(pedagogoId, publicoAlvo.trim(), alunoId, dataReuniao.trim(), motivo.trim())) {
            redirecionar(resp, req.getContextPath(), "sucesso", "Reunião com " + publicoAlvo + " agendada com sucesso!");
        } else {
            redirecionar(resp, req.getContextPath(), "erro", "Erro ao agendar reunião no banco.");
        }
    }

    private void salvarPerfil(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        int id = parseInt(req.getParameter("idPedagogo"));
        String pessoa = req.getParameter("pessoa");
        String especialidade = req.getParameter("especialidade");

        if (id <= 0 || pessoa == null || pessoa.trim().isEmpty()
                || especialidade == null || especialidade.trim().isEmpty()) {
            redirecionar(resp, req.getContextPath(), "erro", "Preencha ID, nome e especialidade.");
            return;
        }

        Pedagogo p = new Pedagogo(id, pessoa.trim(), especialidade.trim());
        boolean ok = (pedagogoDao.buscarPorId(id) != null)
                ? pedagogoDao.atualizar(p)
                : pedagogoDao.cadastrar(p);

        if (ok) {
            redirecionar(resp, req.getContextPath(), "sucesso", "Perfil do pedagogo salvo com sucesso!");
        } else {
            redirecionar(resp, req.getContextPath(), "erro", "Não foi possível salvar o perfil.");
        }
    }

    private void redirecionar(HttpServletResponse resp, String contextPath, String tipo, String msg)
            throws IOException {
        String encoded = URLEncoder.encode(msg, StandardCharsets.UTF_8.name());
        resp.sendRedirect(contextPath + "/index.jsp?" + tipo + "=" + encoded);
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
}