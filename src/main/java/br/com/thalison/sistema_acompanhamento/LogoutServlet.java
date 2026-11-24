package br.com.thalison.sistema_acompanhamento;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession sessao = req.getSession(false);
        if (sessao != null) {
            sessao.invalidate();
        }
        resp.sendRedirect(req.getContextPath() + "/login.jsp");
    }
}