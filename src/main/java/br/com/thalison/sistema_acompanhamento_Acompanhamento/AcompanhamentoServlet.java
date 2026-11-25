package br.com.thalison.sistema_acompanhamento_Acompanhamento;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import br.com.thalison.sistema_acompanhamento_Pessoa.Pessoa;
import br.com.thalison.sistema_acompanhamento_ResponsavelLegal.ResponsavelLegalDAO;
import br.com.thalison.sistema_acompanhamento_aluno.AlunoDAO;

@WebServlet("/acompanhamento")
public class AcompanhamentoServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final AcompanhamentoDAO dao = new AcompanhamentoDAO();
    private final AlunoDAO alunoDAO = new AlunoDAO();
    private final ResponsavelLegalDAO responsavelDAO = new ResponsavelLegalDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String acao = req.getParameter("acao");

        if ("excluir".equals(acao) && req.getParameter("id") != null) {
            dao.excluir(Integer.parseInt(req.getParameter("id")));
            resp.sendRedirect(req.getContextPath() + "/acompanhamento?sucesso=Ficha+removida");
            return;
        }

        if ("ficha".equals(acao) && req.getParameter("id") != null) {
            int fichaId = Integer.parseInt(req.getParameter("id"));
            req.setAttribute("fichaSelecionada", dao.buscarPorId(fichaId));
        }

        req.setAttribute("alunos", alunoDAO.listarTodos());
        req.setAttribute("responsaveis", responsavelDAO.listarTodos());
        req.setAttribute("acompanhamentos", dao.listarTodos());
        req.getRequestDispatcher("/acompanhamento.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        String acao = req.getParameter("acao");

        HttpSession session = req.getSession(false);
        Pessoa usuario = (session != null) ? (Pessoa) session.getAttribute("usuarioLogado") : null;
        int usuarioId = (usuario != null) ? usuario.getId() : 0;

        try {
            if ("abrirFichaSecretaria".equals(acao)) {
                Acompanhamento ac = new Acompanhamento();
                ac.setAlunoId(Integer.parseInt(req.getParameter("alunoId")));
                ac.setResponsavelLegalId(Integer.parseInt(req.getParameter("responsavelLegalId")));
                if (usuarioId > 0) ac.setSecretariaId(usuarioId);
                ac.setDataInicio(Date.valueOf(req.getParameter("dataInicio")));
                String fimStr = req.getParameter("dataFim");
                if (fimStr != null && !fimStr.trim().isEmpty()) {
                    ac.setDataFim(Date.valueOf(fimStr.trim()));
                }
                ac.setObjetivo(req.getParameter("objetivo"));
                ac.setStatus("Em Andamento");

                boolean ok = dao.abrirFichaSecretaria(ac);
                String msg = URLEncoder.encode(
                        ok ? "Ficha aberta e responsável legal vinculado!" : "Erro ao abrir ficha (verifique aluno e responsável).",
                        StandardCharsets.UTF_8.name());
                resp.sendRedirect(req.getContextPath() + "/acompanhamento?" + (ok ? "sucesso=" : "erro=") + msg);
                return;
            }

            if ("alterarResponsavel".equals(acao)) {
                int fichaId = Integer.parseInt(req.getParameter("id"));
                int respId = Integer.parseInt(req.getParameter("responsavelLegalId"));
                boolean ok = dao.atualizarResponsavelLegal(fichaId, respId);
                String msg = URLEncoder.encode(ok ? "Responsável legal atualizado!" : "Erro ao atualizar responsável.", StandardCharsets.UTF_8.name());
                resp.sendRedirect(req.getContextPath() + "/acompanhamento?acao=ficha&id=" + fichaId + "&" + (ok ? "sucesso=" : "erro=") + msg);
                return;
            }

            if ("salvarParecerPsicologo".equals(acao)) {
                int fichaId = Integer.parseInt(req.getParameter("id"));
                String parecer = req.getParameter("parecerPsicologico");
                boolean ok = dao.salvarParecerPsicologo(fichaId, usuarioId > 0 ? usuarioId : 3, parecer);
                String msg = URLEncoder.encode(ok ? "Parecer psicológico salvo!" : "Erro ao salvar parecer psicológico.", StandardCharsets.UTF_8.name());
                resp.sendRedirect(req.getContextPath() + "/acompanhamento?acao=ficha&id=" + fichaId + "&" + (ok ? "sucesso=" : "erro=") + msg);
                return;
            }

            if ("salvarParecerPedagogo".equals(acao)) {
                int fichaId = Integer.parseInt(req.getParameter("id"));
                String objetivo = req.getParameter("objetivo");
                String parecer = req.getParameter("parecerPedagogico");
                String status = req.getParameter("status");
                boolean ok = dao.salvarParecerPedagogo(fichaId, usuarioId > 0 ? usuarioId : 4, objetivo, parecer, status);
                String msg = URLEncoder.encode(ok ? "Parecer pedagógico salvo!" : "Erro ao salvar parecer pedagógico.", StandardCharsets.UTF_8.name());
                resp.sendRedirect(req.getContextPath() + "/acompanhamento?acao=ficha&id=" + fichaId + "&" + (ok ? "sucesso=" : "erro=") + msg);
                return;
            }

            resp.sendRedirect(req.getContextPath() + "/acompanhamento");
        } catch (Exception e) {
            String msg = URLEncoder.encode("Erro ao processar ficha: " + e.getMessage(), StandardCharsets.UTF_8.name());
            resp.sendRedirect(req.getContextPath() + "/acompanhamento?erro=" + msg);
        }
    }
}