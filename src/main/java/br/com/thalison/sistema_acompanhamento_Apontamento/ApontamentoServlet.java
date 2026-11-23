package br.com.thalison.sistema_acompanhamento_Apontamento;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.thalison.sistema_acompanhamento.WebUtil;
import br.com.thalison.sistema_acompanhamento_Professor.Professor;
import br.com.thalison.sistema_acompanhamento_Professor.ProfessorDAO;
import br.com.thalison.sistema_acompanhamento_aluno.AlunoDAO;

/** Apontamentos de alunos feitos por professores (porta de entrada do acompanhamento). */
@WebServlet("/apontamento")
public class ApontamentoServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final ApontamentoDAO dao = new ApontamentoDAO();
    private final ProfessorDAO professorDao = new ProfessorDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String acao = req.getParameter("acao");

        if ("excluir".equals(acao)) {
            if (dao.deletar(WebUtil.parseInt(req.getParameter("id")))) {
                WebUtil.redirecionar(req, resp, "/apontamento", "sucesso", "Apontamento excluído.");
            } else {
                WebUtil.redirecionar(req, resp, "/apontamento", "erro", "Não foi possível excluir o apontamento.");
            }
            return;
        }

        if ("editar".equals(acao)) {
            req.setAttribute("apontamento", dao.buscarPorId(WebUtil.parseInt(req.getParameter("id"))));
        }

        req.setAttribute("alunoSelecionado", WebUtil.parseInt(req.getParameter("alunoId")));
        req.setAttribute("apontamentos", dao.listar());
        req.setAttribute("alunos", new AlunoDAO().listarTodos());
        req.setAttribute("professores", professorDao.listar());
        req.getRequestDispatcher("/apontamento.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        Apontamento a = new Apontamento();
        a.setId(WebUtil.parseInt(req.getParameter("id")));
        a.setAlunoId(WebUtil.parseInt(req.getParameter("alunoId")));
        a.setProfessorId(WebUtil.parseInt(req.getParameter("professorId")));
        a.setDataInicio(WebUtil.texto(req, "dataInicio"));
        a.setNomeProfessor(WebUtil.texto(req, "nomeProfessor"));
        a.setMotivo(WebUtil.texto(req, "motivo"));

        if (a.getProfessorId() > 0 && a.getNomeProfessor() == null) {
            Professor p = professorDao.buscarPorId(a.getProfessorId());
            if (p != null) {
                a.setNomeProfessor(p.getNome());
            }
        }

        if (a.getAlunoId() <= 0 || a.getDataInicio() == null || a.getMotivo() == null) {
            WebUtil.redirecionar(req, resp, "/apontamento", "erro", "Selecione o aluno e informe a data e o motivo.");
            return;
        }

        boolean ok = a.getId() > 0 ? dao.atualizar(a) : dao.salvar(a);
        if (ok) {
            WebUtil.redirecionar(req, resp, "/apontamento", "sucesso", "Apontamento registrado com sucesso!");
        } else {
            WebUtil.redirecionar(req, resp, "/apontamento", "erro", "Não foi possível salvar o apontamento.");
        }
    }
}
