package br.com.thalison.sistema_acompanhamento_Professor;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.thalison.sistema_acompanhamento.WebUtil;
import br.com.thalison.sistema_acompanhamento_Avaliacao.avaliacao;
import br.com.thalison.sistema_acompanhamento_Disciplina.DisciplinaDAO;
import br.com.thalison.sistema_acompanhamento_Disciplina.disciplina;
import br.com.thalison.sistema_acompanhamento_adaptacao.adaptacao_metodologica;
import br.com.thalison.sistema_acompanhamento_aluno.AlunoDAO;

/**
 * Professor: cadastro e registros sobre o aluno (ocorrências/observações,
 * avaliações e adaptações), que aparecem no histórico do acompanhamento.
 * (Antes estava mapeado em "/", o que capturava até o CSS do site.)
 */
@WebServlet("/professor")
public class ProfessorServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private ProfessorDAO dao;
    private final DisciplinaDAO disciplinaDao = new DisciplinaDAO();

    @Override
    public void init() {
        dao = new ProfessorDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String acao = req.getParameter("acao");
        if (acao == null) {
            acao = "lista";
        }
        int id = WebUtil.parseInt(req.getParameter("id"));
        int alunoId = WebUtil.parseInt(req.getParameter("alunoId"));
        String voltarHistorico = "/aluno?acao=historico&id=" + alunoId;

        switch (acao) {
            case "novo":
                req.getRequestDispatcher("/cadastro-professor.jsp").forward(req, resp);
                break;

            case "editar":
                req.setAttribute("professor", dao.buscarPorId(id));
                req.getRequestDispatcher("/cadastro-professor.jsp").forward(req, resp);
                break;

            case "excluir":
                resultado(req, resp, "/professor", dao.excluir(id), "Professor excluído.",
                    "Não foi possível excluir: o professor já possui registros vinculados.");
                break;

            case "registrar":
                req.setAttribute("tipoRegistro", req.getParameter("tipo") != null ? req.getParameter("tipo") : "ocorrencia");
                req.setAttribute("alunoSelecionado", alunoId);
                req.setAttribute("professorSelecionado", WebUtil.parseInt(req.getParameter("professorId")));
                req.setAttribute("alunos", new AlunoDAO().listarTodos());
                req.setAttribute("professores", dao.listar());
                req.setAttribute("disciplinas", disciplinaDao.listar());
                req.getRequestDispatcher("/registro-professor.jsp").forward(req, resp);
                break;

            case "excluirOcorrencia":
                resultado(req, resp, voltarHistorico, dao.excluirOcorrencia(id), "Registro excluído.", "Não foi possível excluir.");
                break;

            case "excluirAvaliacao":
                resultado(req, resp, voltarHistorico, dao.excluirAvaliacao(id), "Avaliação excluída.", "Não foi possível excluir.");
                break;

            case "excluirAdaptacao":
                resultado(req, resp, voltarHistorico, dao.excluirAdaptacao(id), "Adaptação excluída.", "Não foi possível excluir.");
                break;

            case "excluirDisciplina":
                resultado(req, resp, "/professor", disciplinaDao.excluir(id), "Disciplina excluída.",
                    "Não foi possível excluir: há avaliações usando essa disciplina.");
                break;

            default:
                req.setAttribute("professores", dao.listar());
                req.setAttribute("disciplinas", disciplinaDao.listar());
                req.setAttribute("ocorrencias", dao.listarOcorrencias());
                req.getRequestDispatcher("/professor.jsp").forward(req, resp);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        String acao = req.getParameter("acao");
        int alunoId = WebUtil.parseInt(req.getParameter("alunoId"));
        int professorId = WebUtil.parseInt(req.getParameter("professorId"));
        String voltarHistorico = "/aluno?acao=historico&id=" + alunoId;

        if ("registrarOcorrencia".equals(acao)) {
            OcorrenciaPedagogica o = new OcorrenciaPedagogica();
            o.setAlunoId(alunoId);
            o.setProfessorId(professorId);
            o.setData(WebUtil.texto(req, "data"));
            o.setTipo(WebUtil.texto(req, "tipo"));
            o.setDescricao(WebUtil.texto(req, "descricao"));
            o.setProvidencia(WebUtil.texto(req, "providencia"));
            o.setStatus(WebUtil.texto(req, "status"));
            if (alunoId <= 0 || o.getTipo() == null || o.getDescricao() == null) {
                WebUtil.redirecionar(req, resp, "/professor?acao=registrar&tipo=ocorrencia", "erro",
                    "Selecione o aluno e informe tipo e descrição.");
                return;
            }
            resultado(req, resp, voltarHistorico, dao.registrarOcorrencia(o),
                o.getTipo() + " registrada no histórico do aluno.", "Não foi possível registrar.");

        } else if ("registrarAvaliacao".equals(acao)) {
            avaliacao a = new avaliacao();
            a.setAlunoId(alunoId);
            a.setDisciplinaId(WebUtil.parseInt(req.getParameter("disciplinaId")));
            a.setNota(WebUtil.parseDouble(req.getParameter("nota")));
            a.setData(WebUtil.texto(req, "data"));
            a.setTipo(WebUtil.texto(req, "tipo"));
            a.setObservacao(WebUtil.texto(req, "observacao"));
            if (alunoId <= 0) {
                WebUtil.redirecionar(req, resp, "/professor?acao=registrar&tipo=avaliacao", "erro", "Selecione o aluno.");
                return;
            }
            resultado(req, resp, voltarHistorico, dao.registrarAvaliacao(a),
                "Avaliação registrada.", "Não foi possível registrar a avaliação.");

        } else if ("registrarAdaptacao".equals(acao)) {
            adaptacao_metodologica a = new adaptacao_metodologica();
            a.setAlunoId(alunoId);
            a.setProfessorId(professorId);
            a.setTipo(WebUtil.texto(req, "tipo"));
            a.setDescricao(WebUtil.texto(req, "descricao"));
            a.setEstrategia(WebUtil.texto(req, "estrategia"));
            a.setDataRegistro(WebUtil.texto(req, "dataRegistro"));
            a.setResultado(WebUtil.texto(req, "resultado"));
            a.setStatus(WebUtil.texto(req, "status"));
            if (alunoId <= 0 || a.getDescricao() == null) {
                WebUtil.redirecionar(req, resp, "/professor?acao=registrar&tipo=adaptacao", "erro",
                    "Selecione o aluno e descreva a adaptação.");
                return;
            }
            resultado(req, resp, voltarHistorico, dao.registrarAdaptacao(a),
                "Adaptação metodológica registrada.", "Não foi possível registrar a adaptação.");

        } else if ("salvarDisciplina".equals(acao)) {
            disciplina d = new disciplina(0, WebUtil.texto(req, "nome"), WebUtil.texto(req, "descricao"));
            if (d.getNome() == null) {
                WebUtil.redirecionar(req, resp, "/professor", "erro", "Informe o nome da disciplina.");
                return;
            }
            resultado(req, resp, "/professor", disciplinaDao.cadastrar(d), "Disciplina cadastrada.",
                "Não foi possível cadastrar a disciplina.");

        } else {
            salvarProfessor(req, resp);
        }
    }

    private void salvarProfessor(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Professor p = new Professor();
        p.setId(WebUtil.parseInt(req.getParameter("id")));
        p.setNome(WebUtil.texto(req, "nome"));
        p.setEmail(WebUtil.texto(req, "email"));
        p.setCpf(WebUtil.texto(req, "cpf"));
        p.setTelefone(WebUtil.texto(req, "telefone"));
        p.setMatricula(WebUtil.texto(req, "matricula"));
        p.setEspecialidade(WebUtil.texto(req, "especialidade"));

        if (p.getNome() == null || p.getEmail() == null) {
            WebUtil.redirecionar(req, resp, "/professor", "erro", "Nome e e-mail são obrigatórios.");
            return;
        }
        boolean ok = p.getId() > 0 ? dao.atualizar(p) : dao.cadastrar(p);
        resultado(req, resp, "/professor", ok, "Professor salvo com sucesso!",
            "Não foi possível salvar o professor (o CPF já existe?).");
    }

    private void resultado(HttpServletRequest req, HttpServletResponse resp, String destino,
                           boolean ok, String msgOk, String msgErro) throws IOException {
        WebUtil.redirecionar(req, resp, destino, ok ? "sucesso" : "erro", ok ? msgOk : msgErro);
    }
}
