package br.com.thalison.sistema_acompanhamento_Pedagogo;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.thalison.sistema_acompanhamento_Pessoa.Pessoa;

@WebServlet("/pedagogo")
public class PedagogoServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final PedagogoDAO pedagogoDao = new PedagogoDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String acao = req.getParameter("acao");
        req.setAttribute("pedagogos", pedagogoDao.listar());

        if ("perfil".equals(acao)) {
            req.getRequestDispatcher("/perfil-pedagogo.jsp").forward(req, resp);
        } else {
            req.getRequestDispatcher("/pedagogo-lista.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        req.setCharacterEncoding("UTF-8");
        Pessoa usuario = (Pessoa) req.getSession().getAttribute("usuarioLogado");

        int id = parseInt(req.getParameter("idPedagogo"));
        if (id <= 0 && usuario != null) {
            id = usuario.getId();
        }

        String nome = req.getParameter("pessoa");
        String especialidade = req.getParameter("especialidade");

        if (id <= 0 || nome == null || nome.trim().isEmpty()
                || especialidade == null || especialidade.trim().isEmpty()) {
            redirecionar(resp, req.getContextPath(), "erro", "Preencha ID, nome e especialidade.");
            return;
        }

        boolean ok = pedagogoDao.salvarPerfil(id, nome.trim(), especialidade.trim());
        redirecionar(resp, req.getContextPath(), ok ? "sucesso" : "erro",
                ok ? "Perfil do pedagogo atualizado com sucesso!" : "Não foi possível salvar o perfil.");
    }

    private void redirecionar(HttpServletResponse resp, String contextPath, String tipo, String msg)
            throws IOException {
        String encoded = URLEncoder.encode(msg, StandardCharsets.UTF_8.name());
        resp.sendRedirect(contextPath + "/pedagogo?" + tipo + "=" + encoded);
    }

    private int parseInt(String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            return 0;
        }
        try {
            return Integer.parseInt(valor.trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}