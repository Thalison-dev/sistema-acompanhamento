
package br.com.thalison.sistema_acompanhamento_aluno;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/aluno")
public class AlunoServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final AlunoDAO dao = new AlunoDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String acao = req.getParameter("acao");

        if ("novo".equals(acao)) {

            req.getRequestDispatcher("/aluno-form.jsp")
                    .forward(req, resp);

        } else if ("editar".equals(acao)) {

            try {
                String id = req.getParameter("id");

                if (id == null || id.trim().isEmpty()) {
                    resp.sendError(400, "ID nao informado");
                    return;
                }

                Aluno aluno = dao.buscarPorId(Integer.parseInt(id));

                if (aluno == null) {
                    resp.sendError(404, "Aluno nao encontrado");
                    return;
                }

                req.setAttribute("aluno", aluno);

                req.getRequestDispatcher("/aluno-form.jsp")
                        .forward(req, resp);

            } catch (NumberFormatException e) {
                resp.sendError(400, "ID invalido");
            }

        } else {

            // Abre o formulario diretamente para testar o modulo Aluno
            req.getRequestDispatcher("/aluno-form.jsp")
                    .forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        Aluno aluno = new Aluno();

        aluno.setNome(req.getParameter("nome"));
        aluno.setEmail(req.getParameter("email"));
        aluno.setCpf(req.getParameter("cpf"));
        aluno.setTelefone(req.getParameter("telefone"));
        aluno.setDataNascimento(req.getParameter("dataNascimento"));
        aluno.setMatricula(req.getParameter("matricula"));
        aluno.setAnoEscolar(req.getParameter("anoEscolar"));
        aluno.setStatus(req.getParameter("status"));

        String acao = req.getParameter("acao");
        boolean ok = false;

        try {

            if ("cadastrar".equals(acao)) {

                ok = dao.cadastrar(aluno);

            } else if ("atualizar".equals(acao)) {

                String id = req.getParameter("id");

                if (id == null || id.trim().isEmpty()) {
                    resp.sendError(400, "ID nao informado");
                    return;
                }

                aluno.setId(Integer.parseInt(id));
                ok = dao.atualizar(aluno);

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
