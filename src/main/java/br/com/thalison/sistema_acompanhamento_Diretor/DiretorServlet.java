package br.com.thalison.sistema_acompanhamento_Diretor;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.thalison.sistema_acompanhamento.WebUtil;
import br.com.thalison.sistema_acompanhamento_Acompanhamento.AcompanhamentoDAO;
import br.com.thalison.sistema_acompanhamento_aluno.AlunoDAO;

/**
 * Painel do Diretor:
 *   GET  /diretor                          -> painel (indicadores, acompanhamentos, pendências, reuniões)
 *   GET  /diretor?acao=lista               -> diretores cadastrados
 *   GET  /diretor?acao=novo|editar|excluir
 *   GET  /diretor?acao=status&id=X&status=Y -> aprova / suspende / encerra um acompanhamento
 *   POST /diretor (acao=salvar)            -> cadastra/atualiza diretor
 *   POST /diretor (acao=reuniao)           -> agenda reunião
 */
@WebServlet("/diretor")
public class DiretorServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final DiretorDAO diretorDAO = new DiretorDAO();
    private final AcompanhamentoDAO acompanhamentoDAO = new AcompanhamentoDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String acao = req.getParameter("acao");
        if (acao == null) {
            acao = "painel";
        }
        int id = WebUtil.parseInt(req.getParameter("id"));

        switch (acao) {
            case "lista":
                req.setAttribute("diretores", diretorDAO.listar());
                req.getRequestDispatcher("/diretor-lista.jsp").forward(req, resp);
                break;

            case "novo":
                req.getRequestDispatcher("/cadastro-diretor.jsp").forward(req, resp);
                break;

            case "editar":
                req.setAttribute("diretor", diretorDAO.buscarPorId(id));
                req.getRequestDispatcher("/cadastro-diretor.jsp").forward(req, resp);
                break;

            case "excluir": {
                boolean ok = diretorDAO.excluir(id);
                WebUtil.redirecionar(req, resp, "/diretor?acao=lista", ok ? "sucesso" : "erro",
                    ok ? "Diretor excluído." : "Não foi possível excluir o diretor.");
                break;
            }

            case "status": {
                String status = WebUtil.texto(req, "status");
                boolean ok = status != null && acompanhamentoDAO.atualizarStatus(id, status);
                WebUtil.redirecionar(req, resp, "/diretor", ok ? "sucesso" : "erro",
                    ok ? "Acompanhamento #" + id + " agora está: " + status + "."
                       : "Não foi possível alterar o status do acompanhamento.");
                break;
            }

            default:
                abrirPainel(req, resp);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        String acao = req.getParameter("acao");

        if ("reuniao".equals(acao)) {
            String publico = WebUtil.texto(req, "publicoAlvo");
            String data = WebUtil.texto(req, "dataReuniao");
            String motivo = WebUtil.texto(req, "motivo");
            if (publico == null || data == null || motivo == null) {
                WebUtil.redirecionar(req, resp, "/diretor", "erro", "Preencha com quem, a data e o motivo da reunião.");
                return;
            }
            boolean ok = diretorDAO.marcarReuniao(
                WebUtil.parseInt(req.getParameter("diretorId")), publico,
                WebUtil.parseInt(req.getParameter("alunoId")),
                WebUtil.parseInt(req.getParameter("acompanhamentoId")), data, motivo);
            WebUtil.redirecionar(req, resp, "/diretor", ok ? "sucesso" : "erro",
                ok ? "Reunião com " + publico + " agendada!" : "Não foi possível agendar a reunião.");
            return;
        }

        Diretor d = new Diretor();
        d.setId(WebUtil.parseInt(req.getParameter("id")));
        d.setNome(WebUtil.texto(req, "nome"));
        d.setEmail(WebUtil.texto(req, "email"));
        d.setCpf(WebUtil.texto(req, "cpf"));
        d.setTelefone(WebUtil.texto(req, "telefone"));
        d.setCargo(WebUtil.texto(req, "cargo"));
        d.setEscola(WebUtil.texto(req, "escola"));
        d.setFormacao(WebUtil.texto(req, "formacao"));

        if (d.getNome() == null || d.getEmail() == null || d.getCargo() == null) {
            WebUtil.redirecionar(req, resp, "/diretor?acao=lista", "erro", "Preencha nome, e-mail e cargo.");
            return;
        }

        boolean ok = (d.getId() > 0 && diretorDAO.buscarPorId(d.getId()) != null)
                ? diretorDAO.atualizar(d)
                : diretorDAO.cadastrar(d);

        WebUtil.redirecionar(req, resp, "/diretor?acao=lista", ok ? "sucesso" : "erro",
            ok ? "Diretor salvo com sucesso!" : "Não foi possível salvar o diretor (o CPF já existe?).");
    }

    private void abrirPainel(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        List<Diretor> diretores = diretorDAO.listar();
        Diretor ativo = null;
        int diretorId = WebUtil.parseInt(req.getParameter("diretorId"));
        for (Diretor d : diretores) {
            if (d.getId() == diretorId) {
                ativo = d;
            }
        }
        if (ativo == null && !diretores.isEmpty()) {
            ativo = diretores.get(0);
        }

        req.setAttribute("diretores", diretores);
        req.setAttribute("diretorAtivo", ativo);
        req.setAttribute("totais", diretorDAO.contarTotais());
        req.setAttribute("statusTotais", acompanhamentoDAO.contarPorStatus());
        req.setAttribute("acompanhamentos", acompanhamentoDAO.listar());
        req.setAttribute("pendentes", diretorDAO.listarApontamentosSemAcompanhamento());
        req.setAttribute("reunioes", diretorDAO.listarReunioes());
        req.setAttribute("alunos", new AlunoDAO().listarTodos());
        req.getRequestDispatcher("/diretor.jsp").forward(req, resp);
    }
}
