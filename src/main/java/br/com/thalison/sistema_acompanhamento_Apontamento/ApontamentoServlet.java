package br.com.thalison.sistema_acompanhamento_Apontamento;

import java.io.IOException;
import java.time.LocalDate;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.thalison.sistema_acompanhamento_Pessoa.Pessoa;
import br.com.thalison.sistema_acompanhamento_aluno.AlunoDAO;

@WebServlet("/apontamentos")
public class ApontamentoServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final ApontamentoDAO dao = new ApontamentoDAO();
    private final AlunoDAO alunoDAO = new AlunoDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if ("excluir".equals(req.getParameter("acao"))) {
            dao.excluir(Integer.parseInt(req.getParameter("id")));
            resp.sendRedirect(req.getContextPath() + "/apontamentos?sucesso=Apontamento+removido");
            return;
        }
        req.setAttribute("alunos", alunoDAO.listarTodos());
        req.setAttribute("apontamentos", dao.listarTodos());
        req.getRequestDispatcher("/apontamentos.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        Pessoa usuario = (Pessoa) req.getSession().getAttribute("usuarioLogado");

        Apontamento ap = new Apontamento();
        ap.setAlunoId(Integer.parseInt(req.getParameter("alunoId")));
        ap.setProfessorId(usuario.getId());
        ap.setData(LocalDate.parse(req.getParameter("data")));
        ap.setMotivo(req.getParameter("motivo"));

        boolean ok = dao.cadastrar(ap);
        resp.sendRedirect(req.getContextPath() + "/apontamentos?" + (ok ? "sucesso=Apontamento+registrado" : "erro=Erro+ao+registrar"));
    }
}