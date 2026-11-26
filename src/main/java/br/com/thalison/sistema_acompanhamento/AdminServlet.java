package br.com.thalison.sistema_acompanhamento;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import br.com.thalison.sistema_acompanhamento_Pessoa.Pessoa;
import br.com.thalison.sistema_acompanhamento_Pessoa.PessoaDAO;

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final PessoaDAO pessoaDAO = new PessoaDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if ("excluir".equals(req.getParameter("acao"))) {
            String idParam = req.getParameter("id");
            if (idParam != null) {
                pessoaDAO.excluir(Integer.parseInt(idParam));
            }
            resp.sendRedirect(req.getContextPath() + "/admin?sucesso=Usuario+removido");
            return;
        }
        req.setAttribute("usuarios", pessoaDAO.listarTodos());
        req.getRequestDispatcher("/admin.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");

        Pessoa p = new Pessoa();
        p.setNome(req.getParameter("nome"));
        p.setCpf(req.getParameter("cpf"));
        p.setTelefone(req.getParameter("telefone"));
        p.setEmail(req.getParameter("email"));
        p.setSenha(req.getParameter("senha") != null && !req.getParameter("senha").isEmpty()
                ? req.getParameter("senha") : "123456");
        p.setPerfil(req.getParameter("perfil"));

        String extra1 = req.getParameter("extra1");
        String extra2 = req.getParameter("extra2");

        boolean ok = pessoaDAO.cadastrarUsuarioEscola(p, extra1, extra2);
        String msg = URLEncoder.encode(ok ? "Usuário cadastrado com sucesso!" : "Falha ao cadastrar usuário (verifique CPF/e-mail duplicado).", StandardCharsets.UTF_8.name());
        resp.sendRedirect(req.getContextPath() + "/admin?" + (ok ? "sucesso=" : "erro=") + msg);
    }
}