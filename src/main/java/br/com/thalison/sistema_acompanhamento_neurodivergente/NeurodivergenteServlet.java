package br.com.thalison.sistema_acompanhamento_neurodivergente;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.thalison.sistema_acompanhamento.WebUtil;
import br.com.thalison.sistema_acompanhamento_aluno.AlunoDAO;

@WebServlet("/neurodivergencia")
public class NeurodivergenteServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final NeurodivergenteDAO dao = new NeurodivergenteDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String acao = req.getParameter("acao");

        if ("excluir".equals(acao)) {
            if (dao.excluir(WebUtil.parseInt(req.getParameter("id")))) {
                WebUtil.redirecionar(req, resp, "/neurodivergencia", "sucesso", "Registro excluído.");
            } else {
                WebUtil.redirecionar(req, resp, "/neurodivergencia", "erro", "Não foi possível excluir o registro.");
            }
            return;
        }

        if ("editar".equals(acao)) {
            req.setAttribute("registro", dao.consultar(WebUtil.parseInt(req.getParameter("id"))));
        }

        req.setAttribute("alunoSelecionado", WebUtil.parseInt(req.getParameter("alunoId")));
        req.setAttribute("registros", dao.listarTodos());
        req.setAttribute("alunos", new AlunoDAO().listarTodos());
        req.getRequestDispatcher("/neurodivergencia.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        Neurodivergente n = new Neurodivergente();
        n.setId(WebUtil.parseInt(req.getParameter("id")));
        n.setAlunoId(WebUtil.parseInt(req.getParameter("alunoId")));
        n.setTipo(WebUtil.texto(req, "tipo"));
        n.setDescricao(WebUtil.texto(req, "descricao"));
        n.setObservacoes(WebUtil.texto(req, "observacoes"));
        n.setDataRegistro(WebUtil.texto(req, "dataRegistro"));

        if (n.getAlunoId() <= 0 || n.getTipo() == null) {
            WebUtil.redirecionar(req, resp, "/neurodivergencia", "erro", "Selecione o aluno e o tipo de neurodivergência.");
            return;
        }

        boolean ok = n.getId() > 0 ? dao.atualizar(n) : dao.cadastrar(n);
        if (ok) {
            WebUtil.redirecionar(req, resp, "/neurodivergencia", "sucesso", "Registro salvo com sucesso!");
        } else {
            WebUtil.redirecionar(req, resp, "/neurodivergencia", "erro", "Não foi possível salvar o registro.");
        }
    }
}
