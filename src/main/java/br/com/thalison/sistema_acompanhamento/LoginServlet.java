package br.com.thalison.sistema_acompanhamento;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import br.com.thalison.sistema_acompanhamento_Pessoa.Pessoa;
import br.com.thalison.sistema_acompanhamento_Pessoa.PessoaDAO;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final PessoaDAO pessoaDAO = new PessoaDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        String email = req.getParameter("email");
        String senha = req.getParameter("senha");

        Pessoa usuario = pessoaDAO.autenticar(email, senha);
        if (usuario != null) {
            HttpSession session = req.getSession(true);
            session.setAttribute("usuarioLogado", usuario);
            resp.sendRedirect(req.getContextPath() + "/painel.jsp");
        } else {
            String erro = URLEncoder.encode("E-mail ou senha inválidos.", StandardCharsets.UTF_8.name());
            resp.sendRedirect(req.getContextPath() + "/login.jsp?erro=" + erro);
        }
    }
}