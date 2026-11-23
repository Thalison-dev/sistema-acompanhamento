package br.com.thalison.sistema_acompanhamento_ResponsavelLegal;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.thalison.sistema_acompanhamento.WebUtil;
import br.com.thalison.sistema_acompanhamento_aluno.AlunoDAO;

@WebServlet("/responsavel")
public class ResponsavelLegalServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final ResponsavelLegalDAO dao = new ResponsavelLegalDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String acao = req.getParameter("acao");
        int id = WebUtil.parseInt(req.getParameter("id"));

        if ("novo".equals(acao) || "editar".equals(acao)) {
            req.setAttribute("responsavel", "editar".equals(acao) ? dao.consultarResponsavelLegal(id) : null);
            req.getRequestDispatcher("/cadastro-responsavel.jsp").forward(req, resp);

        } else if ("excluir".equals(acao)) {
            boolean ok = dao.excluir(id);
            WebUtil.redirecionar(req, resp, "/responsavel", ok ? "sucesso" : "erro",
                ok ? "Responsável excluído." : "Não foi possível excluir o responsável.");

        } else if ("detalhe".equals(acao)) {
            ResponsavelLegal r = dao.consultarResponsavelLegal(id);
            if (r == null) {
                WebUtil.redirecionar(req, resp, "/responsavel", "erro", "Responsável não encontrado.");
                return;
            }
            req.setAttribute("responsavel", r);
            req.setAttribute("alunos", new AlunoDAO().listarPorResponsavel(id));
            req.getRequestDispatcher("/detalhe-responsavel.jsp").forward(req, resp);

        } else {
            req.setAttribute("responsaveis", dao.listar());
            req.getRequestDispatcher("/responsavel.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        ResponsavelLegal r = new ResponsavelLegal();
        r.setId(WebUtil.parseInt(req.getParameter("id")));
        r.setNome(WebUtil.texto(req, "nome"));
        r.setEmail(WebUtil.texto(req, "email"));
        r.setCpf(WebUtil.texto(req, "cpf"));
        r.setTelefone(WebUtil.texto(req, "telefone"));
        r.setParentesco(WebUtil.texto(req, "parentesco"));

        if (r.getNome() == null || r.getEmail() == null) {
            WebUtil.redirecionar(req, resp, "/responsavel", "erro", "Nome e e-mail são obrigatórios.");
            return;
        }

        boolean ok = r.getId() > 0 ? dao.atualizar(r) : dao.cadastrar(r);
        WebUtil.redirecionar(req, resp, "/responsavel", ok ? "sucesso" : "erro",
            ok ? "Responsável salvo com sucesso!" : "Não foi possível salvar o responsável (o CPF já existe?).");
    }
}
