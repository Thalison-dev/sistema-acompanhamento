package br.com.thalison.sistema_Acompanhamento_entrevista;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.thalison.sistema_acompanhamento.WebUtil;
import br.com.thalison.sistema_acompanhamento_Psicologo.Psicologo;
import br.com.thalison.sistema_acompanhamento_Psicologo.PsicologoDAO;
import br.com.thalison.sistema_acompanhamento_aluno.Aluno;
import br.com.thalison.sistema_acompanhamento_aluno.AlunoDAO;

@WebServlet(urlPatterns = {
    "/entrevista",
    "/nova-entrevista",
    "/inserir-entrevista",
    "/deletar-entrevista",
    "/editar-entrevista",
    "/atualizar-entrevista",
    "/listar-entrevistas"
})
public class EntrevistaServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private EntrevistaDAO dao;

    @Override
    public void init() {
        dao = new EntrevistaDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        switch (request.getServletPath()) {

            case "/nova-entrevista":
                mostrarFormulario(request, response, null);
                break;

            case "/inserir-entrevista":
            case "/atualizar-entrevista":
                salvarEntrevista(request, response);
                break;

            case "/deletar-entrevista":
                if (dao.excluir(WebUtil.parseInt(request.getParameter("id")))) {
                    WebUtil.redirecionar(request, response, "/listar-entrevistas", "sucesso", "Entrevista excluída.");
                } else {
                    WebUtil.redirecionar(request, response, "/listar-entrevistas", "erro", "Não foi possível excluir.");
                }
                break;

            case "/editar-entrevista":
                mostrarFormulario(request, response, dao.buscarPorId(WebUtil.parseInt(request.getParameter("id"))));
                break;

            case "/listar-entrevistas":
            case "/entrevista":
            default:
                request.setAttribute("entrevistas", dao.listar());
                request.getRequestDispatcher("/listar-entrevista.jsp").forward(request, response);
                break;
        }
    }

    private void mostrarFormulario(HttpServletRequest request, HttpServletResponse response, Entrevista entrevista)
            throws ServletException, IOException {

        request.setAttribute("entrevista", entrevista);
        request.setAttribute("alunos", new AlunoDAO().listarTodos());
        request.setAttribute("psicologos", new PsicologoDAO().listar());
        request.setAttribute("alunoSelecionado",
            entrevista != null ? entrevista.getAlunoId() : WebUtil.parseInt(request.getParameter("alunoId")));
        request.setAttribute("psicologoSelecionado",
            entrevista != null ? entrevista.getPsicologoId() : WebUtil.parseInt(request.getParameter("psicologoId")));
        request.getRequestDispatcher("/form-entrevista.jsp").forward(request, response);
    }

    private void salvarEntrevista(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        int id = WebUtil.parseInt(request.getParameter("id"));
        int alunoId = WebUtil.parseInt(request.getParameter("alunoId"));
        int psicologoId = WebUtil.parseInt(request.getParameter("psicologoId"));
        String motivo = WebUtil.texto(request, "motivo");
        String agendadaParam = request.getParameter("agendada");
        boolean agendada = "true".equalsIgnoreCase(agendadaParam) || "on".equalsIgnoreCase(agendadaParam);
        String dataParam = WebUtil.texto(request, "data");
        String observacoes = WebUtil.texto(request, "observacoes");

        if (alunoId <= 0 || motivo == null) {
            WebUtil.redirecionar(request, response,
                id > 0 ? "/editar-entrevista?id=" + id : "/nova-entrevista", "erro",
                "Selecione o aluno e informe o motivo da entrevista.");
            return;
        }

        Date data;
        try {
            data = dataParam != null ? Date.valueOf(dataParam) : null;
        } catch (IllegalArgumentException e) {
            data = null;
        }

        Aluno a = new Aluno();
        a.setId(alunoId);

        Psicologo p = null;
        if (psicologoId > 0) {
            p = new Psicologo();
            p.setId(psicologoId);
        }

        Entrevista entrevista = new Entrevista(a, p, id, motivo, agendada, data, observacoes);
        boolean ok = id > 0 ? dao.atualizar(entrevista) : dao.cadastrar(entrevista);

        if (ok) {
            WebUtil.redirecionar(request, response, "/listar-entrevistas", "sucesso", "Entrevista salva com sucesso!");
        } else {
            WebUtil.redirecionar(request, response, "/listar-entrevistas", "erro", "Não foi possível salvar a entrevista.");
        }
    }
}
