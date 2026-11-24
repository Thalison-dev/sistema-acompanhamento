package br.com.thalison.sistema_acompanhamento_desempenho;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.thalison.sistema_acompanhamento_aluno.AlunoDAO;

@WebServlet("/desempenho")
public class DesempenhoServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final DesempenhoMensalDAO dao = new DesempenhoMensalDAO();
    private final AlunoDAO alunoDAO = new AlunoDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if ("excluir".equals(req.getParameter("acao"))) {
            dao.excluir(Integer.parseInt(req.getParameter("id")));
            redirecionar(resp, req.getContextPath(), "sucesso", "Registro de desempenho removido.");
            return;
        }

        req.setAttribute("alunos", alunoDAO.listarTodos());
        req.setAttribute("desempenhos", dao.listarComApontamentos());
        req.getRequestDispatcher("/desempenho.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");

        try {
            DesempenhoMensal d = new DesempenhoMensal();
            d.setAlunoId(Integer.parseInt(req.getParameter("alunoId")));
            d.setMesReferencia(Integer.parseInt(req.getParameter("mesReferencia")));
            d.setAnoReferencia(Integer.parseInt(req.getParameter("anoReferencia")));
            d.setMediaEscola(new BigDecimal(req.getParameter("mediaEscola").replace(",", ".")));
            d.setNotaAluno(new BigDecimal(req.getParameter("notaAluno").replace(",", ".")));
            d.setObservacaoGeral(req.getParameter("observacaoGeral"));

            boolean ok = dao.salvarOuAtualizar(d);
            redirecionar(resp, req.getContextPath(), ok ? "sucesso" : "erro",
                    ok ? "Desempenho mensal salvo e cruzado com os apontamentos do mes!" : "Erro ao salvar desempenho mensal.");
        } catch (Exception e) {
            redirecionar(resp, req.getContextPath(), "erro", "Dados invalidos. Verifique media, nota e mes.");
        }
    }

    private void redirecionar(HttpServletResponse resp, String ctx, String tipo, String msg) throws IOException {
        resp.sendRedirect(ctx + "/desempenho?" + tipo + "=" + URLEncoder.encode(msg, StandardCharsets.UTF_8.name()));
    }
}