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
    private ResponsavelLegalDAO dao;

    @Override
    public void init() {
        dao = new ResponsavelLegalDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String acao = req.getParameter("acao");
        if ("excluir".equals(acao)) {
            excluir(req, resp);
            return;
        }

        req.setAttribute("responsaveis", dao.listarTodos());
        req.getRequestDispatcher("/responsavel.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        String nome = req.getParameter("nome");
        String email = req.getParameter("email");
        String cpf = req.getParameter("cpf");
        String telefone = req.getParameter("telefone");
        String parentesco = req.getParameter("parentesco");

        if (nome == null || nome.trim().isEmpty() || email == null || email.trim().isEmpty()) {
            redirecionar(resp, req.getContextPath(), "erro", "Nome e e-mail do responsável são obrigatórios.");
            return;
        }

        ResponsavelLegal rl = new ResponsavelLegal();
        rl.setNome(nome.trim());
        rl.setEmail(email.trim());
        rl.setCpf((cpf != null && !cpf.trim().isEmpty()) ? cpf.trim() : null);
        rl.setTelefone((telefone != null && !telefone.trim().isEmpty()) ? telefone.trim() : null);
        rl.setParentesco((parentesco != null && !parentesco.trim().isEmpty()) ? parentesco.trim() : "Responsável");

        if (dao.cadastrar(rl)) {
            redirecionar(resp, req.getContextPath(), "sucesso", "Responsável legal cadastrado com sucesso!");
        } else {
            redirecionar(resp, req.getContextPath(), "erro", "Não foi possível cadastrar o responsável legal.");
        }
    }

    private void excluir(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            if (dao.excluir(id)) {
                redirecionar(resp, req.getContextPath(), "sucesso", "Responsável legal excluído.");
            } else {
                redirecionar(resp, req.getContextPath(), "erro", "Não foi possível excluir.");
            }
        } catch (NumberFormatException e) {
            redirecionar(resp, req.getContextPath(), "erro", "ID inválido.");
        }
    }

    private void redirecionar(HttpServletResponse resp, String contextPath, String tipo, String msg)
            throws IOException {
        String encoded = URLEncoder.encode(msg, StandardCharsets.UTF_8.name());
        resp.sendRedirect(contextPath + "/responsavel?" + tipo + "=" + encoded);
    }
}