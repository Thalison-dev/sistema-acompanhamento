package br.com.thalison.sistema_acompanhamento_aluno;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
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
        if ("excluir".equals(req.getParameter("acao")) && req.getParameter("id") != null) {
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

        Aluno aluno = new Aluno();
        aluno.setNome(req.getParameter("nome"));
        aluno.setEmail(req.getParameter("email"));
        aluno.setCpf(req.getParameter("cpf"));
        aluno.setTelefone(req.getParameter("telefone"));

        String nasc = req.getParameter("dataNascimento");
        if (nasc != null && !nasc.trim().isEmpty()) {
            aluno.setDataNascimento(Date.valueOf(nasc.trim()));
        }

        aluno.setMatricula(req.getParameter("matricula"));
        aluno.setAnoEscolar(req.getParameter("anoEscolar"));
        aluno.setTipoNeurodivergencia(req.getParameter("tipoNeurodivergencia"));
        aluno.setDescricaoNeuro(req.getParameter("descricaoNeuro"));
        aluno.setStatus(req.getParameter("status") != null ? req.getParameter("status") : "Ativo");

        boolean ok = dao.cadastrar(aluno);
        String msg = URLEncoder.encode(
                ok ? "Aluno neurodivergente cadastrado com sucesso!" : "Erro ao cadastrar aluno (verifique se a matrícula já existe).",
                StandardCharsets.UTF_8.name());
        resp.sendRedirect(req.getContextPath() + "/aluno?" + (ok ? "sucesso=" : "erro=") + msg);
    }
}