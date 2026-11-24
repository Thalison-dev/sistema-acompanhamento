package br.com.thalison.sistema_acompanhamento_aluno;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if ("excluir".equals(req.getParameter("acao"))) {
            dao.excluir(Integer.parseInt(req.getParameter("id")));
            resp.sendRedirect(req.getContextPath() + "/aluno?sucesso=Aluno+excluido");
            return;
        }
        req.setAttribute("alunos", dao.listarTodos());
        req.getRequestDispatcher("/alunos.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        Aluno a = new Aluno();
        a.setNome(req.getParameter("nome"));
        a.setCpf(req.getParameter("cpf"));
        a.setTelefone(req.getParameter("telefone"));
        a.setEmail(req.getParameter("email"));
        a.setDataNascimento(req.getParameter("dataNascimento"));
        a.setMatricula(req.getParameter("matricula"));
        a.setAnoEscolar(req.getParameter("anoEscolar"));
        a.setStatus(req.getParameter("status"));
        a.setTipoNeurodivergencia(req.getParameter("tipoNeurodivergencia"));
        a.setDescricaoNeuro(req.getParameter("descricaoNeuro"));

        boolean ok = dao.cadastrar(a);
        String msg = URLEncoder.encode(ok ? "Aluno neurodivergente cadastrado!" : "Erro ao cadastrar aluno.", StandardCharsets.UTF_8.name());
        resp.sendRedirect(req.getContextPath() + "/aluno?" + (ok ? "sucesso=" : "erro=") + msg);
    }
}