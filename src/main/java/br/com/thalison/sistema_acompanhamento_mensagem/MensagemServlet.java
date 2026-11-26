package br.com.thalison.sistema_acompanhamento_mensagem;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import br.com.thalison.sistema_acompanhamento_Pessoa.Pessoa;

@WebServlet("/mensagens")
public class MensagemServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final MensagemDAO dao = new MensagemDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        Pessoa usuario = (session != null) ? (Pessoa) session.getAttribute("usuarioLogado") : null;
        int uid = (usuario != null) ? usuario.getId() : 1;

        req.setAttribute("mensagens", dao.listarPorUsuario(uid));
        req.getRequestDispatcher("/painel.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession(false);
        Pessoa usuario = (session != null) ? (Pessoa) session.getAttribute("usuarioLogado") : null;

        Mensagem m = new Mensagem();
        m.setRemetenteId(usuario != null ? usuario.getId() : 1);
        m.setDestinatarioId(Integer.parseInt(req.getParameter("destinatarioId")));
        m.setConteudoMensagem(req.getParameter("conteudoMensagem"));

        dao.enviar(m);
        resp.sendRedirect(req.getContextPath() + "/mensagens");
    }
}