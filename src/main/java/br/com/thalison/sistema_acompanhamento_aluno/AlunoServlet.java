
package br.com.thalison.sistema_acompanhamento_aluno;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/aluno")
public class AlunoServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final alunoDAO dao = new alunoDAO();

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
            req.getRequestDispatcher("/aluno-form.jsp")
                    .forward(req, resp);

        } else if ("editar".equals(acao)) {
            try {
                String idTexto = req.getParameter("id");

                if (idTexto == null || idTexto.isBlank()) {
                    resp.sendError(400, "ID nao informado");
                    return;
                }

                aluno a = dao.buscarPorId(Integer.parseInt(idTexto));

                if (a == null) {
                    resp.sendError(404, "Aluno nao encontrado");
                    return;
                }

                req.setAttribute("aluno", a);
                req.getRequestDispatcher("/aluno-form.jsp")
                        .forward(req, resp);

            } catch (NumberFormatException e) {
                resp.sendError(400, "ID invalido");
            }

        } else {
            req.setAttribute("alunos", dao.listarTodos());

            req.getRequestDispatcher("/aluno-lista.jsp")
                    .forward(req, resp);
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

        aluno a = new aluno();	

        a.setNome(req.getParameter("nome"));
        a.setDataNascimento(req.getParameter("dataNascimento"));
        a.setMatricula(req.getParameter("matricula"));
        a.setAnoEscolar(req.getParameter("anoEscolar"));
        a.setStatus(req.getParameter("status"));

        String acao = req.getParameter("acao");
        boolean ok = false;

        try {
            if ("cadastrar".equals(acao)) {
                ok = dao.cadastrar(a);

            } else if ("atualizar".equals(acao)) {
                String idTexto = req.getParameter("id");

                if (idTexto == null || idTexto.isBlank()) {
                    resp.sendError(400, "ID nao informado");
                    return;
                }

                a.setId(Integer.parseInt(idTexto));
                ok = dao.atualizar(a);

            } else {
                resp.sendError(400, "Acao invalida");
                return;
            }

        } catch (NumberFormatException e) {
            resp.sendError(400, "ID invalido");
            return;
        }

        if (ok) {
            resp.sendRedirect(req.getContextPath() + "/aluno");
        } else {
            resp.sendError(500, "Nao foi possivel salvar o aluno");
        }
    }
}
