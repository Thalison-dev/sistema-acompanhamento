package br.com.thalison.sistema_Acompanhamento_entrevista;

import java.io.IOException;
import java.time.LocalDate;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.thalison.sistema_acompanhamento_Pessoa.Pessoa;
import br.com.thalison.sistema_acompanhamento_aluno.AlunoDAO;

@WebServlet("/entrevistas")
public class EntrevistaServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final EntrevistaDAO dao = new EntrevistaDAO();
    private final AlunoDAO alunoDAO = new AlunoDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if ("excluir".equals(req.getParameter("acao"))) {
            dao.excluir(Integer.parseInt(req.getParameter("id")));
            resp.sendRedirect(req.getContextPath() + "/entrevistas?sucesso=Entrevista+removida");
            return;
        }
        req.setAttribute("alunos", alunoDAO.listarTodos());
        req.setAttribute("entrevistas", dao.listarTodas());
        req.getRequestDispatcher("/entrevistas.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        Pessoa usuario = (Pessoa) req.getSession().getAttribute("usuarioLogado");

        Entrevista e = new Entrevista();
        e.setAlunoId(Integer.parseInt(req.getParameter("alunoId")));
        e.setPsicologoId(usuario.getId());
        e.setMotivo(req.getParameter("motivo"));
        e.setAgendada(req.getParameter("agendada") != null);
        e.setData(LocalDate.parse(req.getParameter("data")));
        e.setObservacoes(req.getParameter("observacoes"));

        boolean ok = dao.cadastrar(e);
        resp.sendRedirect(req.getContextPath() + "/entrevistas?" + (ok ? "sucesso=Entrevista+registrada" : "erro=Erro+ao+registrar"));
    }
}