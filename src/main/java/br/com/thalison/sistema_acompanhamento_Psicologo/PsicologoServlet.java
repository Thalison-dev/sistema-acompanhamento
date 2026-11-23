package br.com.thalison.sistema_acompanhamento_Psicologo;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.thalison.sistema_acompanhamento.WebUtil;
import br.com.thalison.sistema_Acompanhamento_entrevista.EntrevistaDAO;

@WebServlet("/psicologo")
public class PsicologoServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private PsicologoDAO dao;

    @Override
    public void init() {
        dao = new PsicologoDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String acao = request.getParameter("acao");
        int id = WebUtil.parseInt(request.getParameter("id"));

        if ("novo".equals(acao) || "editar".equals(acao)) {
            request.setAttribute("psicologo", "editar".equals(acao) ? dao.consultarPsicologo(id) : null);
            request.getRequestDispatcher("/cadastro-psicologo.jsp").forward(request, response);

        } else if ("excluir".equals(acao)) {
            boolean ok = dao.excluir(id);
            WebUtil.redirecionar(request, response, "/psicologo", ok ? "sucesso" : "erro",
                ok ? "Psicólogo excluído." : "Não foi possível excluir: o psicólogo possui entrevistas registradas.");

        } else if ("detalhe".equals(acao)) {
            Psicologo psicologo = dao.consultarPsicologo(id);
            if (psicologo == null) {
                WebUtil.redirecionar(request, response, "/psicologo", "erro", "Psicólogo não encontrado.");
                return;
            }
            request.setAttribute("psicologo", psicologo);
            request.setAttribute("entrevistas", new EntrevistaDAO().listarPorPsicologo(id));
            request.getRequestDispatcher("/detalhe-psicologo.jsp").forward(request, response);

        } else {
            request.setAttribute("psicologos", dao.listar());
            request.setAttribute("entrevistas", new EntrevistaDAO().listar());
            request.getRequestDispatcher("/psicologo.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        Psicologo p = new Psicologo();
        p.setId(WebUtil.parseInt(request.getParameter("id")));
        p.setNome(WebUtil.texto(request, "nome"));
        p.setEmail(WebUtil.texto(request, "email"));
        p.setCpf(WebUtil.texto(request, "cpf"));
        p.setTelefone(WebUtil.texto(request, "telefone"));
        p.setEspecialidade(WebUtil.texto(request, "especialidade"));

        if (p.getNome() == null || p.getEmail() == null) {
            WebUtil.redirecionar(request, response, "/psicologo", "erro", "Nome e e-mail são obrigatórios.");
            return;
        }

        boolean ok = p.getId() > 0 ? dao.atualizar(p) : dao.cadastrar(p);
        WebUtil.redirecionar(request, response, "/psicologo", ok ? "sucesso" : "erro",
            ok ? "Psicólogo salvo com sucesso!" : "Não foi possível salvar o psicólogo (o CPF já existe?).");
    }
}
