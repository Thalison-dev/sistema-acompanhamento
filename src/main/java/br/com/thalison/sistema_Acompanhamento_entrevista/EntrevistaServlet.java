package br.com.thalison.sistema_Acompanhamento_entrevista;

import java.io.IOException;
import java.sql.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import br.com.thalison.sistema_acompanhamento_Pessoa.Pessoa;
import br.com.thalison.sistema_acompanhamento_aluno.AlunoDAO;

@WebServlet("/entrevistas")
public class EntrevistaServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final EntrevistaDAO dao = new EntrevistaDAO();
    private final AlunoDAO alunoDAO = new AlunoDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if ("excluir".equals(req.getParameter("acao")) && req.getParameter("id") != null) {
            dao.excluir(Integer.parseInt(req.getParameter("id")));
            resp.sendRedirect(req.getContextPath() + "/entrevistas");
            return;
        }
        req.setAttribute("alunos", alunoDAO.listarTodos());
        req.setAttribute("entrevistas", dao.listarTodos());
        req.getRequestDispatcher("/entrevista.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession(false);
        Pessoa usuario = (session != null) ? (Pessoa) session.getAttribute("usuarioLogado") : null;

        Entrevista e = new Entrevista();
        e.setAlunoId(Integer.parseInt(req.getParameter("alunoId")));
        e.setPsicologoId(usuario != null ? usuario.getId() : 3);
        e.setData(Date.valueOf(req.getParameter("data")));
        e.setMotivo(req.getParameter("motivo"));
        e.setAgendada("on".equals(req.getParameter("agendada")) || "true".equals(req.getParameter("agendada")));
        e.setObservacoes(req.getParameter("observacoes"));

        dao.inserir(e);
        resp.sendRedirect(req.getContextPath() + "/entrevistas");
    }
}