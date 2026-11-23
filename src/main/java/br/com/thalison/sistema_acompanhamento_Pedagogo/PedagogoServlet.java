package br.com.thalison.sistema_acompanhamento_Pedagogo;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.thalison.sistema_acompanhamento.WebUtil;
import br.com.thalison.sistema_acompanhamento_Acompanhamento.AcompanhamentoDAO;
import br.com.thalison.sistema_acompanhamento_Apontamento.ApontamentoDAO;
import br.com.thalison.sistema_acompanhamento_aluno.AlunoDAO;

@WebServlet("/pedagogo")
public class PedagogoServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final PedagogoDAO pedagogoDao = new PedagogoDAO();
    private final AlunoDAO alunoDao = new AlunoDAO();
    private final AcompanhamentoDAO acompanhamentoDao = new AcompanhamentoDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String acao = req.getParameter("acao");
        if (acao == null) {
            acao = "painel";
        }

        switch (acao) {
            case "reuniao":
                req.setAttribute("alunos", alunoDao.listarTodos());
                req.setAttribute("pedagogos", pedagogoDao.listar());
                req.getRequestDispatcher("/reuniao-pedagogo.jsp").forward(req, resp);
                break;

            case "historicoAluno":
                resp.sendRedirect(req.getContextPath() + "/aluno?acao=historico&id="
                    + WebUtil.parseInt(req.getParameter("alunoId")));
                break;

            case "perfil":
                req.setAttribute("pedagogos", pedagogoDao.listar());
                req.getRequestDispatcher("/perfil-pedagogo.jsp").forward(req, resp);
                break;

            case "lista":
                req.setAttribute("pedagogos", pedagogoDao.listar());
                req.getRequestDispatcher("/pedagogo-lista.jsp").forward(req, resp);
                break;

            case "novo":
                req.getRequestDispatcher("/RegistroPedagogo.jsp").forward(req, resp);
                break;

            case "editar": {
                Pedagogo p = pedagogoDao.buscarPorId(WebUtil.parseInt(req.getParameter("id")));
                if (p == null) {
                    WebUtil.redirecionar(req, resp, "/pedagogo?acao=lista", "erro", "Pedagogo não encontrado.");
                    return;
                }
                req.setAttribute("pedagogo", p);
                req.getRequestDispatcher("/RegistroPedagogo.jsp").forward(req, resp);
                break;
            }

            case "excluir":
                if (pedagogoDao.excluir(WebUtil.parseInt(req.getParameter("id")))) {
                    WebUtil.redirecionar(req, resp, "/pedagogo?acao=lista", "sucesso", "Pedagogo excluído.");
                } else {
                    WebUtil.redirecionar(req, resp, "/pedagogo?acao=lista", "erro", "Não foi possível excluir o pedagogo.");
                }
                break;

            default:
                abrirPainelPedagogo(req, resp);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        String acao = req.getParameter("acao");

        if ("agendarReuniao".equals(acao)) {
            agendarReuniao(req, resp);
        } else if ("salvarPerfil".equals(acao) || "salvar".equals(acao)) {
            salvarPerfil(req, resp);
        } else {
            WebUtil.redirecionar(req, resp, "/pedagogo", "erro", "Ação inválida.");
        }
    }

    private void abrirPainelPedagogo(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Pedagogo> pedagogos = pedagogoDao.listar();
        req.setAttribute("pedagogos", pedagogos);
        req.setAttribute("pedagogoAtivo", pedagogos.isEmpty() ? null : pedagogos.get(0));
        req.setAttribute("alunos", alunoDao.listarTodos());
        req.setAttribute("acompanhamentos", acompanhamentoDao.listar());
        req.setAttribute("apontamentos", new ApontamentoDAO().listar());
        req.setAttribute("reunioes", pedagogoDao.listarReunioes());
        req.getRequestDispatcher("/pedagogo.jsp").forward(req, resp);
    }

    private void agendarReuniao(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        int pedagogoId = WebUtil.parseInt(req.getParameter("pedagogoId"));
        String publicoAlvo = WebUtil.texto(req, "publicoAlvo");
        int alunoId = WebUtil.parseInt(req.getParameter("alunoId"));
        String dataReuniao = WebUtil.texto(req, "dataReuniao");
        String motivo = WebUtil.texto(req, "motivo");

        if (publicoAlvo == null || dataReuniao == null || motivo == null) {
            WebUtil.redirecionar(req, resp, "/pedagogo?acao=reuniao", "erro", "Preencha todos os campos da reunião.");
            return;
        }

        if (pedagogoDao.registrarReuniao(pedagogoId, publicoAlvo, alunoId, dataReuniao, motivo)) {
            WebUtil.redirecionar(req, resp, "/pedagogo", "sucesso", "Reunião com " + publicoAlvo + " agendada com sucesso!");
        } else {
            WebUtil.redirecionar(req, resp, "/pedagogo", "erro", "Erro ao agendar reunião no banco.");
        }
    }

    private void salvarPerfil(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        int id = WebUtil.parseInt(req.getParameter("idPedagogo"));
        String voltar = "salvar".equals(req.getParameter("acao")) ? "/pedagogo?acao=lista" : "/pedagogo";

        Pedagogo p = new Pedagogo(id, WebUtil.texto(req, "pessoa"), WebUtil.texto(req, "especialidade"));
        p.setEmail(WebUtil.texto(req, "email"));
        p.setTelefone(WebUtil.texto(req, "telefone"));
        p.setCpf(WebUtil.texto(req, "cpf"));

        if (p.getPessoa() == null || p.getEspecialidade() == null || p.getEmail() == null) {
            WebUtil.redirecionar(req, resp, voltar, "erro", "Preencha nome, e-mail e especialidade.");
            return;
        }

        boolean ok = (id > 0 && pedagogoDao.buscarPorId(id) != null)
                ? pedagogoDao.atualizar(p)
                : pedagogoDao.cadastrar(p);

        if (ok) {
            WebUtil.redirecionar(req, resp, voltar, "sucesso", "Pedagogo salvo com sucesso!");
        } else {
            WebUtil.redirecionar(req, resp, voltar, "erro", "Não foi possível salvar o pedagogo (o CPF já existe?).");
        }
    }
}
