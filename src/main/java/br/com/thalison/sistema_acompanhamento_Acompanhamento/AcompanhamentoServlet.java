package br.com.thalison.sistema_acompanhamento_Acompanhamento;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.thalison.sistema_acompanhamento.WebUtil;
import br.com.thalison.sistema_acompanhamento_aluno.AlunoDAO;

/**
 * Centro do sistema: cadastro, edição, listagem e exclusão de acompanhamentos.
 *   GET  /acompanhamento                 -> lista
 *   GET  /acompanhamento?alunoId=X       -> novo acompanhamento já com o aluno selecionado
 *   GET  /acompanhamento?acao=novo|editar|excluir
 *   POST /acompanhamento                 -> salva (insere ou atualiza)
 */
@WebServlet(urlPatterns = {"/acompanhamento", "/novo-acompanhamento", "/listar-acompanhamentos"})
public class AcompanhamentoServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final AcompanhamentoDAO dao = new AcompanhamentoDAO();
    private final AlunoDAO alunoDao = new AlunoDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String acao = request.getParameter("acao");
        if (acao == null) {
            if ("/novo-acompanhamento".equals(request.getServletPath())
                    || request.getParameter("alunoId") != null) {
                acao = "novo";
            } else {
                acao = "lista";
            }
        }

        switch (acao) {
            case "novo":
                mostrarFormulario(request, response, null);
                break;

            case "editar": {
                Acompanhamento a = dao.buscarPorId(WebUtil.parseInt(request.getParameter("id")));
                if (a == null) {
                    WebUtil.redirecionar(request, response, "/acompanhamento", "erro", "Acompanhamento não encontrado.");
                    return;
                }
                mostrarFormulario(request, response, a);
                break;
            }

            case "excluir":
                if (dao.excluir(WebUtil.parseInt(request.getParameter("id")))) {
                    WebUtil.redirecionar(request, response, "/acompanhamento", "sucesso", "Acompanhamento excluído.");
                } else {
                    WebUtil.redirecionar(request, response, "/acompanhamento", "erro",
                        "Não foi possível excluir o acompanhamento.");
                }
                break;

            default:
                request.setAttribute("acompanhamentos", dao.listar());
                request.getRequestDispatcher("/listar-acompanhamento.jsp").forward(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        Acompanhamento a = new Acompanhamento();
        a.setId(WebUtil.parseInt(request.getParameter("id")));
        a.setAlunoId(WebUtil.parseInt(request.getParameter("alunoId")));
        a.setDataInicio(WebUtil.texto(request, "dataInicio"));
        a.setDataFim(WebUtil.texto(request, "dataFim"));
        a.setObjetivo(WebUtil.texto(request, "objetivo"));
        a.setObservacao(WebUtil.texto(request, "observacao"));
        a.setStatus(WebUtil.texto(request, "status"));

        String voltar = a.getId() > 0 ? "/acompanhamento?acao=editar&id=" + a.getId()
                                      : "/acompanhamento?acao=novo&alunoId=" + a.getAlunoId();

        if (a.getAlunoId() <= 0 || a.getDataInicio() == null || a.getObjetivo() == null || a.getStatus() == null) {
            WebUtil.redirecionar(request, response, voltar, "erro",
                "Selecione o aluno e preencha data de início, objetivo e status.");
            return;
        }
        if (a.getDataFim() != null && a.getDataFim().compareTo(a.getDataInicio()) < 0) {
            WebUtil.redirecionar(request, response, voltar, "erro",
                "A data de fim não pode ser anterior à data de início.");
            return;
        }

        boolean ok = a.getId() > 0 ? dao.atualizar(a) : dao.cadastrar(a);

        if (ok) {
            WebUtil.redirecionar(request, response, "/acompanhamento", "sucesso", "Acompanhamento salvo com sucesso!");
        } else {
            WebUtil.redirecionar(request, response, voltar, "erro",
                "Não foi possível salvar. Verifique se o aluno existe e se as datas são válidas.");
        }
    }

    private void mostrarFormulario(HttpServletRequest request, HttpServletResponse response, Acompanhamento a)
            throws ServletException, IOException {
        request.setAttribute("acompanhamento", a);
        request.setAttribute("alunos", alunoDao.listarTodos());
        request.setAttribute("alunoSelecionado",
            a != null ? a.getAlunoId() : WebUtil.parseInt(request.getParameter("alunoId")));
        request.getRequestDispatcher("/acompanhamento.jsp").forward(request, response);
    }
}
