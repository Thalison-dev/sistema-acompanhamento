package br.com.thalison.sistema_acompanhamento_Reuniao;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.thalison.sistema_acompanhamento.WebUtil;
import br.com.thalison.sistema_acompanhamento_Acompanhamento.AcompanhamentoDAO;
import br.com.thalison.sistema_acompanhamento_aluno.AlunoDAO;

/** Agenda única de reuniões, usada por todos os perfis. */
@WebServlet("/reuniao")
public class ReuniaoServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final ReuniaoDAO dao = new ReuniaoDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String acao = req.getParameter("acao");
        String voltar = destino(req);

        if ("excluir".equals(acao)) {
            boolean ok = dao.excluir(WebUtil.parseInt(req.getParameter("id")));
            WebUtil.redirecionar(req, resp, voltar, ok ? "sucesso" : "erro",
                ok ? "Reunião excluída." : "Não foi possível excluir a reunião.");
            return;
        }

        if ("status".equals(acao)) {
            String status = WebUtil.texto(req, "status");
            boolean ok = status != null && dao.atualizarStatus(WebUtil.parseInt(req.getParameter("id")), status);
            WebUtil.redirecionar(req, resp, voltar, ok ? "sucesso" : "erro",
                ok ? "Reunião marcada como " + status + "." : "Não foi possível atualizar a reunião.");
            return;
        }

        String perfil = WebUtil.texto(req, "perfil");
        req.setAttribute("perfil", perfil);
        req.setAttribute("solicitanteId", WebUtil.parseInt(req.getParameter("solicitanteId")));
        req.setAttribute("alunoSelecionado", WebUtil.parseInt(req.getParameter("alunoId")));
        req.setAttribute("acompanhamentoSelecionado", WebUtil.parseInt(req.getParameter("acompanhamentoId")));
        req.setAttribute("alunos", new AlunoDAO().listarTodos());
        req.setAttribute("acompanhamentos", new AcompanhamentoDAO().listar());
        req.setAttribute("reunioes", perfil != null ? dao.listarPorPerfil(perfil) : dao.listar());
        req.getRequestDispatcher("/reuniao.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        String voltar = destino(req);

        Reuniao r = new Reuniao();
        r.setSolicitanteTipo(WebUtil.texto(req, "solicitanteTipo"));
        r.setSolicitanteId(WebUtil.parseInt(req.getParameter("solicitanteId")));
        r.setPublicoAlvo(WebUtil.texto(req, "publicoAlvo"));
        r.setAlunoId(WebUtil.parseInt(req.getParameter("alunoId")));
        r.setAcompanhamentoId(WebUtil.parseInt(req.getParameter("acompanhamentoId")));
        r.setDataReuniao(WebUtil.texto(req, "dataReuniao"));
        r.setMotivo(WebUtil.texto(req, "motivo"));

        if (r.getSolicitanteTipo() == null || r.getPublicoAlvo() == null
                || r.getDataReuniao() == null || r.getMotivo() == null) {
            WebUtil.redirecionar(req, resp, voltar, "erro", "Preencha quem solicita, com quem, a data e o motivo.");
            return;
        }

        boolean ok = dao.agendar(r);
        WebUtil.redirecionar(req, resp, voltar, ok ? "sucesso" : "erro",
            ok ? "Reunião com " + r.getPublicoAlvo() + " agendada com sucesso!" : "Não foi possível agendar a reunião.");
    }

    /** Página para onde voltar depois da ação (só caminhos internos do sistema). */
    private String destino(HttpServletRequest req) {
        String voltar = req.getParameter("voltar");
        if (voltar == null || !voltar.startsWith("/") || voltar.startsWith("//")) {
            return "/reuniao";
        }
        return voltar;
    }
}
