package br.com.thalison.sistema_acompanhamento_Pedagogo;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/pedagogo")
public class PedagogoServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final PedagogoDAO dao = new PedagogoDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession sessao = req.getSession(false);

        if (sessao == null || sessao.getAttribute("usuario") == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        String acao = req.getParameter("acao");

        if ("novo".equals(acao)) {
            req.getRequestDispatcher("/pedagogo.jsp").forward(req, resp);

        } else if ("editar".equals(acao)) {
            try {
                String id = req.getParameter("id");

                if (id == null || id.trim().isEmpty()) {
                    resp.sendError(400, "ID nao informado");
                    return;
                }

                Pedagogo p = dao.buscarPorId(Integer.parseInt(id));

                if (p == null) {
                    resp.sendError(404, "Pedagogo nao encontrado");
                    return;
                }

                req.setAttribute("pedagogo", p);
                req.getRequestDispatcher("/pedagogo.jsp").forward(req, resp);

            } catch (NumberFormatException e) {
                resp.sendError(400, "ID invalido");
            }

        } else {
            req.setAttribute("pedagogos", dao.listar());
            req.getRequestDispatcher("/pedagogo-lista.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession sessao = req.getSession(false);

        if (sessao == null || sessao.getAttribute("usuario") == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        req.setCharacterEncoding("UTF-8");

        Pedagogo p = new Pedagogo();
        p.setPessoa(req.getParameter("pessoa"));
        p.setEspecialidade(req.getParameter("especialidade"));

        String acao = req.getParameter("acao");
        boolean ok;

        try {
            if ("cadastrar".equals(acao)) {
                String id = req.getParameter("idPedagogo");

                if (id == null || id.trim().isEmpty()) {
                    resp.sendError(400, "ID do pedagogo nao informado");
                    return;
                }

                p.setIdPedagogo(Integer.parseInt(id));
                ok = dao.cadastrar(p);

            } else if ("atualizar".equals(acao)) {
                String id = req.getParameter("id");

                if (id == null || id.trim().isEmpty()) {
                    resp.sendError(400, "ID nao informado");
                    return;
                }

                p.setIdPedagogo(Integer.parseInt(id));
                ok = dao.atualizar(p);

            } else {
                resp.sendError(400, "Acao invalida");
                return;
            }

        } catch (NumberFormatException e) {
            resp.sendError(400, "ID invalido");
            return;
        }

        if (ok) {
            resp.sendRedirect(req.getContextPath() + "/pedagogo");
        } else {
            resp.sendError(500, "Nao foi possivel salvar o pedagogo");
        }
    }
}
