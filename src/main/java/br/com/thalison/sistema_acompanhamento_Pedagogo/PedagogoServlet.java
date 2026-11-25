package br.com.thalison.sistema_acompanhamento_Pedagogo;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import br.com.thalison.sistema_acompanhamento_Acompanhamento.AcompanhamentoDAO;
import br.com.thalison.sistema_acompanhamento_Pessoa.Pessoa;
import br.com.thalison.sistema_acompanhamento_aluno.AlunoDAO;

@WebServlet("/pedagogo")
public class PedagogoServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final PedagogoDAO pedagogoDAO = new PedagogoDAO();
    private final AlunoDAO alunoDAO = new AlunoDAO();
    private final AcompanhamentoDAO acompanhamentoDAO = new AcompanhamentoDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String acao = req.getParameter("acao");
        if (acao == null) {
            acao = "";
        }

        switch (acao) {
            case "perfil":
                req.setAttribute("pedagogos", pedagogoDAO.listar());
                req.getRequestDispatcher("/perfil-pedagogo.jsp").forward(req, resp);
                break;
            case "lista":
                req.setAttribute("pedagogos", pedagogoDAO.listar());
                req.getRequestDispatcher("/pedagogo-lista.jsp").forward(req, resp);
                break;
            default:
                req.setAttribute("pedagogos", pedagogoDAO.listar());
                req.setAttribute("alunos", alunoDAO.listarTodos());
                req.setAttribute("acompanhamentos", acompanhamentoDAO.listarTodos());
                req.getRequestDispatcher("/pedagogo.jsp").forward(req, resp);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");

        String idStr = req.getParameter("idPedagogo");
        String nome = req.getParameter("pessoa");
        String especialidade = req.getParameter("especialidade");

        int idPedagogo = 4;
        try {
            if (idStr != null && !idStr.trim().isEmpty()) {
                idPedagogo = Integer.parseInt(idStr.trim());
            }
        } catch (NumberFormatException ignored) {
        }

        boolean atualizado = pedagogoDAO.atualizarPerfil(idPedagogo, nome, especialidade);

        if (atualizado) {
            HttpSession session = req.getSession(false);
            if (session != null && session.getAttribute("usuarioLogado") instanceof Pessoa) {
                Pessoa logado = (Pessoa) session.getAttribute("usuarioLogado");
                if (logado.getId() == idPedagogo && nome != null && !nome.trim().isEmpty()) {
                    logado.setNome(nome.trim());
                }
            }
            String msg = URLEncoder.encode("Perfil do pedagogo atualizado com sucesso!", StandardCharsets.UTF_8.name());
            resp.sendRedirect(req.getContextPath() + "/pedagogo?sucesso=" + msg);
        } else {
            String msg = URLEncoder.encode("Não foi possível atualizar o perfil do pedagogo.", StandardCharsets.UTF_8.name());
            resp.sendRedirect(req.getContextPath() + "/pedagogo?erro=" + msg);
        }
    }
}