
package br.com.thalison.sistema_acompanhamento_aluno;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import br.com.thalison.sistema_acompanhamento_Pessoa.Pessoa;

@WebServlet("/aluno")
public class AlunoServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final AlunoDAO dao = new AlunoDAO();

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
            req.getRequestDispatcher("/aluno-form.jsp").forward(req, resp);

        } else if ("editar".equals(acao)) {
            try {
                String id = req.getParameter("id");

                if (id == null || id.trim().isEmpty()) {
                    resp.sendError(400, "ID nao informado");
                    return;
                }

                Aluno a = dao.buscarPorId(Integer.parseInt(id));

                if (a == null) {
                    resp.sendError(404, "Aluno nao encontrado");
                    return;
                }

                req.setAttribute("aluno", a);
                req.getRequestDispatcher("/aluno-form.jsp").forward(req, resp);

            } catch (NumberFormatException e) {
                resp.sendError(400, "ID invalido");
            }

        } else {
            req.setAttribute("alunos", dao.listarTodos());
            req.getRequestDispatcher("/aluno-lista.jsp").forward(req, resp);
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

        Aluno a = new Aluno();
        a.setNome(req.getParameter("nome"));
        a.setEmail(req.getParameter("email"));
        a.setCpf(req.getParameter("cpf"));
        a.setTelefone(req.getParameter("telefone"));
        a.setDataNascimento(req.getParameter("dataNascimento"));
        a.setMatricula(req.getParameter("matricula"));
        a.setAnoEscolar(req.getParameter("anoEscolar"));
        a.setStatus(req.getParameter("status"));

        String acao = req.getParameter("acao");
        boolean ok;

        try {
            if ("cadastrar".equals(acao)) {
                ok = dao.cadastrar(a);

            } else if ("atualizar".equals(acao)) {
                String id = req.getParameter("id");

                if (id == null || id.trim().isEmpty()) {
                    resp.sendError(400, "ID nao informado");
                    return;
                }

                a.setId(Integer.parseInt(id));
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
