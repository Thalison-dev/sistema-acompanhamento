package br.com.thalison.sistema_acompanhamento_aluno;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.thalison.sistema_acompanhamento.WebUtil;
import br.com.thalison.sistema_acompanhamento_Acompanhamento.AcompanhamentoDAO;
import br.com.thalison.sistema_acompanhamento_Apontamento.ApontamentoDAO;
import br.com.thalison.sistema_Acompanhamento_entrevista.EntrevistaDAO;
import br.com.thalison.sistema_acompanhamento_Professor.ProfessorDAO;
import br.com.thalison.sistema_acompanhamento_Reuniao.ReuniaoDAO;
import br.com.thalison.sistema_acompanhamento_ResponsavelLegal.ResponsavelLegalDAO;
import br.com.thalison.sistema_acompanhamento_neurodivergente.NeurodivergenteDAO;

@WebServlet("/aluno")
public class AlunoServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final AlunoDAO dao = new AlunoDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String acao = req.getParameter("acao");
        if (acao == null) {
            acao = "lista";
        }

        switch (acao) {
            case "novo":
                abrirFormulario(req, resp, null);
                break;

            case "editar": {
                Aluno a = dao.buscarPorId(WebUtil.parseInt(req.getParameter("id")));
                if (a == null) {
                    WebUtil.redirecionar(req, resp, "/aluno", "erro", "Aluno não encontrado.");
                    return;
                }
                abrirFormulario(req, resp, a);
                break;
            }

            case "excluir":
                if (dao.excluir(WebUtil.parseInt(req.getParameter("id")))) {
                    WebUtil.redirecionar(req, resp, "/aluno", "sucesso", "Aluno excluído com sucesso.");
                } else {
                    WebUtil.redirecionar(req, resp, "/aluno", "erro",
                        "Não foi possível excluir: o aluno possui acompanhamentos ou outros registros vinculados.");
                }
                break;

            case "historico":
                abrirHistorico(req, resp);
                break;

            default:
                req.setAttribute("alunos", dao.listarTodos());
                req.getRequestDispatcher("/aluno-lista.jsp").forward(req, resp);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        Aluno a = new Aluno();
        a.setId(WebUtil.parseInt(req.getParameter("id")));
        a.setNome(WebUtil.texto(req, "nome"));
        a.setEmail(WebUtil.texto(req, "email"));
        a.setCpf(WebUtil.texto(req, "cpf"));
        a.setTelefone(WebUtil.texto(req, "telefone"));
        a.setDataNascimento(WebUtil.texto(req, "dataNascimento"));
        a.setMatricula(WebUtil.texto(req, "matricula"));
        a.setAnoEscolar(WebUtil.texto(req, "anoEscolar"));
        a.setStatus(WebUtil.texto(req, "status"));
        a.setResponsavelId(WebUtil.parseInt(req.getParameter("responsavelId")));

        if (a.getNome() == null || a.getEmail() == null) {
            WebUtil.redirecionar(req, resp, "/aluno?acao=" + (a.getId() > 0 ? "editar&id=" + a.getId() : "novo"),
                "erro", "Nome e e-mail são obrigatórios.");
            return;
        }

        boolean ok = a.getId() > 0 ? dao.atualizar(a) : dao.cadastrar(a);

        if (ok) {
            WebUtil.redirecionar(req, resp, "/aluno", "sucesso", "Aluno " + a.getNome() + " salvo com sucesso!");
        } else {
            WebUtil.redirecionar(req, resp, "/aluno", "erro",
                "Não foi possível salvar o aluno. Verifique se o CPF já está cadastrado.");
        }
    }

    private void abrirFormulario(HttpServletRequest req, HttpServletResponse resp, Aluno aluno)
            throws ServletException, IOException {
        req.setAttribute("aluno", aluno);
        req.setAttribute("responsaveis", new ResponsavelLegalDAO().listar());
        req.getRequestDispatcher("/cadastroAluno.jsp").forward(req, resp);
    }

    /** Página central do aluno: reúne tudo que está ligado ao acompanhamento dele. */
    private void abrirHistorico(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        int id = WebUtil.parseInt(req.getParameter("id"));
        if (id <= 0) {
            id = WebUtil.parseInt(req.getParameter("alunoId"));
        }
        Aluno aluno = dao.buscarPorId(id);
        if (aluno == null) {
            WebUtil.redirecionar(req, resp, "/aluno", "erro", "Aluno não encontrado.");
            return;
        }

        ProfessorDAO professorDao = new ProfessorDAO();
        req.setAttribute("aluno", aluno);
        req.setAttribute("acompanhamentosAluno", new AcompanhamentoDAO().listarPorAluno(id));
        req.setAttribute("neurodivergencias", new NeurodivergenteDAO().listarPorAluno(id));
        req.setAttribute("apontamentos", new ApontamentoDAO().listarPorAluno(id));
        req.setAttribute("entrevistas", new EntrevistaDAO().listarPorAluno(id));
        req.setAttribute("ocorrencias", professorDao.listarOcorrenciasPorAluno(id));
        req.setAttribute("avaliacoes", professorDao.listarAvaliacoesPorAluno(id));
        req.setAttribute("adaptacoes", professorDao.listarAdaptacoesPorAluno(id));
        req.setAttribute("reunioes", new ReuniaoDAO().listarPorAluno(id));
        req.getRequestDispatcher("/historico-aluno.jsp").forward(req, resp);
    }
}
