package br.com.thalison.sistema_acompanhamento_Acompanhamento;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.thalison.sistema_acompanhamento_Pessoa.Pessoa;
import br.com.thalison.sistema_acompanhamento_ResponsavelLegal.ResponsavelLegalDAO;
import br.com.thalison.sistema_acompanhamento_aluno.AlunoDAO;

@WebServlet("/acompanhamento")
public class AcompanhamentoServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final AcompanhamentoDAO dao = new AcompanhamentoDAO();
    private final AlunoDAO alunoDAO = new AlunoDAO();
    private final ResponsavelLegalDAO respDAO = new ResponsavelLegalDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Pessoa usuario = (Pessoa) req.getSession().getAttribute("usuarioLogado");
        String acao = req.getParameter("acao");

        if ("excluir".equals(acao) && ("ADMIN".equals(usuario.getPerfil()) || "SECRETARIA".equals(usuario.getPerfil()))) {
            dao.excluir(Integer.parseInt(req.getParameter("id")));
            redirecionar(resp, req.getContextPath(), "sucesso", "Ficha removida.");
            return;
        }

        if ("ficha".equals(acao)) {
            int id = Integer.parseInt(req.getParameter("id"));
            req.setAttribute("fichaSelecionada", dao.buscarPorId(id));
        }

        req.setAttribute("alunos", alunoDAO.listarTodos());
        req.setAttribute("responsaveis", respDAO.listarTodos());
        req.setAttribute("acompanhamentos", dao.listarTodos());
        req.getRequestDispatcher("/acompanhamento.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        Pessoa usuario = (Pessoa) req.getSession().getAttribute("usuarioLogado");
        String perfil = usuario.getPerfil();
        String acao = req.getParameter("acao");

        // 1. SECRETARIA (ou ADMIN) abre a ficha e vincula obrigatoriamente o Responsável Legal do menor
        if ("abrirFichaSecretaria".equals(acao)) {
            if (!"SECRETARIA".equals(perfil) && !"ADMIN".equals(perfil)) {
                redirecionar(resp, req.getContextPath(), "erro", "Somente a Secretaria pode registrar o responsável na ficha.");
                return;
            }
            int alunoId = Integer.parseInt(req.getParameter("alunoId"));
            int responsavelId = Integer.parseInt(req.getParameter("responsavelLegalId"));
            if (alunoId <= 0 || responsavelId <= 0) {
                redirecionar(resp, req.getContextPath(), "erro", "Aluno e Responsável Legal são obrigatórios.");
                return;
            }
            Acompanhamento ac = new Acompanhamento();
            ac.setAlunoId(alunoId);
            ac.setResponsavelLegalId(responsavelId);
            ac.setDataInicio(LocalDate.parse(req.getParameter("dataInicio")));
            String df = req.getParameter("dataFim");
            ac.setDataFim((df != null && !df.isEmpty()) ? LocalDate.parse(df) : null);
            ac.setObjetivo(req.getParameter("objetivo"));
            ac.setStatus("Em Andamento");

            boolean ok = dao.abrirFichaSecretaria(ac);
            redirecionar(resp, req.getContextPath(), ok ? "sucesso" : "erro",
                    ok ? "Ficha aberta e Responsável Legal vinculado com sucesso!" : "Erro ao abrir ficha.");
            return;
        }

        // 2. SECRETARIA (ou ADMIN) altera o Responsável Legal na ficha
        if ("alterarResponsavel".equals(acao)) {
            if (!"SECRETARIA".equals(perfil) && !"ADMIN".equals(perfil)) {
                redirecionar(resp, req.getContextPath(), "erro", "Somente a Secretaria pode alterar o responsável.");
                return;
            }
            int id = Integer.parseInt(req.getParameter("id"));
            int respId = Integer.parseInt(req.getParameter("responsavelLegalId"));
            boolean ok = dao.atualizarResponsavelSecretaria(id, respId);
            redirecionar(resp, req.getContextPath(), ok ? "sucesso" : "erro",
                    ok ? "Responsável Legal atualizado na ficha!" : "Erro ao atualizar responsável.");
            return;
        }

        // 3. PSICÓLOGO (ou ADMIN) registra o parecer psicológico na ficha
        if ("salvarParecerPsicologo".equals(acao)) {
            if (!"PSICOLOGO".equals(perfil) && !"ADMIN".equals(perfil)) {
                redirecionar(resp, req.getContextPath(), "erro", "Apenas o Psicólogo pode registrar o parecer psicológico.");
                return;
            }
            int id = Integer.parseInt(req.getParameter("id"));
            String parecerPsi = req.getParameter("parecerPsicologico");
            boolean ok = dao.registrarParecerPsicologo(id, usuario.getId(), parecerPsi);
            redirecionar(resp, req.getContextPath(), ok ? "sucesso" : "erro",
                    ok ? "Parecer psicológico registrado na ficha!" : "Erro ao registrar parecer psicológico.");
            return;
        }

        // 4. PEDAGOGO (ou ADMIN) registra o parecer pedagógico na ficha
        if ("salvarParecerPedagogo".equals(acao)) {
            if (!"PEDAGOGO".equals(perfil) && !"ADMIN".equals(perfil)) {
                redirecionar(resp, req.getContextPath(), "erro", "Apenas o Pedagogo pode registrar o parecer pedagógico.");
                return;
            }
            int id = Integer.parseInt(req.getParameter("id"));
            String objetivo = req.getParameter("objetivo");
            String parecerPed = req.getParameter("parecerPedagogico");
            String status = req.getParameter("status");
            boolean ok = dao.registrarParecerPedagogo(id, usuario.getId(), objetivo, parecerPed, status);
            redirecionar(resp, req.getContextPath(), ok ? "sucesso" : "erro",
                    ok ? "Parecer pedagógico registrado na ficha!" : "Erro ao registrar parecer pedagógico.");
        }
    }

    private void redirecionar(HttpServletResponse resp, String ctx, String tipo, String msg) throws IOException {
        resp.sendRedirect(ctx + "/acompanhamento?" + tipo + "=" + URLEncoder.encode(msg, StandardCharsets.UTF_8.name()));
    }
}