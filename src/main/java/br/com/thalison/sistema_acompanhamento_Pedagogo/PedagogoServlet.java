package br.com.thalison.sistema_acompanhamento_Pedagogo;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/pedagogo")
public class PedagogoServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final PedagogoDAO dao = new PedagogoDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        if (!isUsuarioLogado(req, resp)) {
            return;
        }

        String acao = req.getParameter("acao");
        if (acao == null) {
            acao = "listar";
        }

        switch (acao) {
            case "novo":
                req.getRequestDispatcher("/pedagogo.jsp").forward(req, resp);
                break;

            case "editar":
                prepararEdicao(req, resp);
                break;

            case "excluir":
                excluirPedagogo(req, resp);
                break;

            default:
                listarPedagogos(req, resp);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        if (!isUsuarioLogado(req, resp)) {
            return;
        }

        req.setCharacterEncoding("UTF-8");
        String acao = req.getParameter("acao");

        if ("cadastrar".equals(acao)) {
            cadastrarPedagogo(req, resp);
        } else if ("atualizar".equals(acao)) {
            atualizarPedagogo(req, resp);
        } else {
            resp.sendError(400, "Acao invalida");
        }
    }

    private boolean isUsuarioLogado(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        HttpSession sessao = req.getSession(false);
        if (sessao == null || sessao.getAttribute("usuario") == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return false;
        }
        return true;
    }

    private void listarPedagogos(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("pedagogos", dao.listar());
        req.getRequestDispatcher("/pedagogo-lista.jsp").forward(req, resp);
    }

    private void prepararEdicao(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            String idParam = req.getParameter("id");
            if (idParam == null || idParam.trim().isEmpty()) {
                resp.sendError(400, "ID nao informado");
                return;
            }

            int id = Integer.parseInt(idParam);
            Pedagogo pedagogo = dao.buscarPorId(id);

            if (pedagogo == null) {
                resp.sendError(404, "Pedagogo nao encontrado");
                return;
            }

            req.setAttribute("pedagogo", pedagogo);
            req.getRequestDispatcher("/pedagogo.jsp").forward(req, resp);

        } catch (NumberFormatException e) {
            resp.sendError(400, "ID invalido");
        }
    }

    private void excluirPedagogo(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        try {
            String idParam = req.getParameter("id");
            if (idParam == null || idParam.trim().isEmpty()) {
                resp.sendError(400, "ID nao informado");
                return;
            }

            int id = Integer.parseInt(idParam);
            if (dao.excluir(id)) {
                resp.sendRedirect(req.getContextPath() + "/pedagogo");
            } else {
                resp.sendError(500, "Nao foi possivel excluir o pedagogo");
            }

        } catch (NumberFormatException e) {
            resp.sendError(400, "ID invalido");
        }
    }

    private void cadastrarPedagogo(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        try {
            String idParam = req.getParameter("idPedagogo");
            if (idParam == null || idParam.trim().isEmpty()) {
                resp.sendError(400, "ID do pedagogo nao informado");
                return;
            }

            Pedagogo p = montarPedagogo(req, Integer.parseInt(idParam));

            if (dao.cadastrar(p)) {
                resp.sendRedirect(req.getContextPath() + "/pedagogo");
            } else {
                resp.sendError(500, "Nao foi possivel cadastrar o pedagogo");
            }

        } catch (NumberFormatException e) {
            resp.sendError(400, "ID invalido");
        }
    }

    private void atualizarPedagogo(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        try {
            String idParam = req.getParameter("id");
            if (idParam == null || idParam.trim().isEmpty()) {
                resp.sendError(400, "ID nao informado");
                return;
            }

            Pedagogo p = montarPedagogo(req, Integer.parseInt(idParam));

            if (dao.atualizar(p)) {
                resp.sendRedirect(req.getContextPath() + "/pedagogo");
            } else {
                resp.sendError(500, "Nao foi possivel atualizar o pedagogo");
            }

        } catch (NumberFormatException e) {
            resp.sendError(400, "ID invalido");
        }
    }

    private Pedagogo montarPedagogo(HttpServletRequest req, int id) {
        Pedagogo p = new Pedagogo();
        p.setIdPedagogo(id);
        p.setPessoa(req.getParameter("pessoa"));
        p.setEspecialidade(req.getParameter("especialidade"));
        return p;
    }
}