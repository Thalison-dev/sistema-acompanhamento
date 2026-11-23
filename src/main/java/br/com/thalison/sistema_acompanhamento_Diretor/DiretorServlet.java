
package br.com.thalison.sistema_acompanhamento_Diretor;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/diretor")
public class DiretorServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final DiretorDAO dao = new DiretorDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        req.setAttribute("diretores", dao.listar());
        req.setAttribute("indicadores", dao.obterIndicadores());

        req.getRequestDispatcher("/diretor.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        String acao = req.getParameter("acao");

        if ("cadastrar".equals(acao)) {
            cadastrar(req, resp);
        } else if ("excluir".equals(acao)) {
            excluir(req, resp);
        } else {
            redirecionar(req, resp, "erro", "Ação inválida.");
        }
    }

    private void cadastrar(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        int id = inteiro(req.getParameter("id"));
        String matricula = req.getParameter("matricula");

        if (id <= 0 || matricula == null || matricula.trim().isEmpty()) {
            redirecionar(req, resp, "erro",
                    "Informe o ID da pessoa e a matrícula.");
            return;
        }

        Diretor d = new Diretor();
        d.setId(id);
        d.setMatricula(matricula.trim());

        if (dao.cadastrar(d)) {
            redirecionar(req, resp, "sucesso",
                    "Diretor cadastrado com sucesso.");
        } else {
            redirecionar(req, resp, "erro",
                    "Cadastro não realizado. Confira se a pessoa existe e se a matrícula já está em uso.");
        }
    }

    private void excluir(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        int id = inteiro(req.getParameter("id"));

        if (id <= 0) {
            redirecionar(req, resp, "erro", "ID inválido.");
            return;
        }

        if (dao.excluir(id)) {
            redirecionar(req, resp, "sucesso",
                    "Vínculo de diretor excluído com sucesso.");
        } else {
            redirecionar(req, resp, "erro",
                    "Diretor não encontrado ou não foi possível excluir.");
        }
    }

    private int inteiro(String valor) {
        try {
            return Integer.parseInt(valor);
        } catch (Exception e) {
            return 0;
        }
    }

    private void redirecionar(HttpServletRequest req,
                              HttpServletResponse resp,
                              String tipo, String mensagem)
            throws IOException {

        String texto = URLEncoder.encode(
                mensagem, StandardCharsets.UTF_8.name());

        resp.sendRedirect(req.getContextPath()
                + "/diretor?" + tipo + "=" + texto);
    }
}
