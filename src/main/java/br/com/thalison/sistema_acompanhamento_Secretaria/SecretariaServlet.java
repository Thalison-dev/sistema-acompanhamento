package br.com.thalison.sistema_acompanhamento_Secretaria;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/secretaria")
public class SecretariaServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final SecretariaDAO dao = new SecretariaDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("secretarias", dao.listarTodas());
        req.getRequestDispatcher("/painel.jsp").forward(req, resp);
    }
}