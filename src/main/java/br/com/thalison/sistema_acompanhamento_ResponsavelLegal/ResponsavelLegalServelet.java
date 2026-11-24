package br.com.thalison.sistema_acompanhamento_ResponsavelLegal;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/responsavel")
public class ResponsavelLegalServelet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final ResponsavelLegalDAO dao = new ResponsavelLegalDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if ("excluir".equals(req.getParameter("acao"))) {
            dao.excluir(Integer.parseInt(req.getParameter("id")));
            resp.sendRedirect(req.getContextPath() + "/responsavel?sucesso=Responsavel+excluido");
            return;
        }
        req.setAttribute("responsaveis", dao.listarTodos());
        req.getRequestDispatcher("/responsaveis.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        ResponsavelLegal rl = new ResponsavelLegal();
        rl.setNome(req.getParameter("nome"));
        rl.setCpf(req.getParameter("cpf"));
        rl.setTelefone(req.getParameter("telefone"));
        rl.setEmail(req.getParameter("email"));
        rl.setParentesco(req.getParameter("parentesco"));

        boolean ok = dao.cadastrar(rl);
        String msg = URLEncoder.encode(ok ? "Responsável Legal cadastrado!" : "Erro ao cadastrar responsável.", StandardCharsets.UTF_8.name());
        resp.sendRedirect(req.getContextPath() + "/responsavel?" + (ok ? "sucesso=" : "erro=") + msg);
    }
}