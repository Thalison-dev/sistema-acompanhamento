package br.com.thalison.sistema_acompanhamento_aluno;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import br.com.thalison.sistema_acompanhamento_Acompanhamento.AcompanhamentoDAO;

@WebServlet("/historico-aluno")
public class HistoricoAlunoServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final AlunoDAO alunoDAO = new AlunoDAO();
    private final AcompanhamentoDAO acompanhamentoDAO = new AcompanhamentoDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idParam = req.getParameter("id");
        if (idParam == null || idParam.trim().isEmpty()) {
            idParam = req.getParameter("alunoId");
        }

        if (idParam != null && !idParam.trim().isEmpty()) {
            try {
                int alunoId = Integer.parseInt(idParam.trim());
                req.setAttribute("aluno", alunoDAO.buscarPorId(alunoId));
                req.setAttribute("acompanhamentosAluno", acompanhamentoDAO.listarPorAluno(alunoId));
            } catch (NumberFormatException e) {
                req.setAttribute("aluno", null);
            }
        }

        req.getRequestDispatcher("/historico-aluno.jsp").forward(req, resp);
    }
}