package br.com.thalison.sistema_acompanhamento_Psicologo;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.thalison.sistema_acompanhamento_aluno.Aluno;
import br.com.thalison.sistema_acompanhamento_aluno.AlunoDAO;
import br.com.thalison.sistema_acompanhamento_Pedagogo.Pedagogo;
import br.com.thalison.sistema_acompanhamento_Pedagogo.PedagogoDAO;

@WebServlet(urlPatterns = {
        "/consultar",
        "/agendarEntrevista",
        "/enviarMensagemPedagogo"
})
public class PsicologoServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private PsicologoDAO dao;
    private AlunoDAO alunoDAO;
    private PedagogoDAO pedagogoDAO;

    public void init() {
        dao = new PsicologoDAO();
        alunoDAO = new AlunoDAO();
        pedagogoDAO = new PedagogoDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getServletPath();

        switch (action) {

        case "/consultar":
            consultarPsicologo(request, response);
            break;

        case "/agendarEntrevista":
            agendarEntrevista(request, response);
            break;

        case "/enviarMensagemPedagogo":
            enviarMensagemPedagogo(request, response);
            break;

        default:
            consultarPsicologo(request, response);
            break;
        }
    }

    private void consultarPsicologo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));
        Psicologo psicologo = dao.buscarPorId(id);
        request.setAttribute("psicologo", psicologo);
        RequestDispatcher dispatcher = request.getRequestDispatcher("detalhe-psicologo.jsp");
        dispatcher.forward(request, response);
    }

    private void agendarEntrevista(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        int psicologoId = Integer.parseInt(request.getParameter("psicologoId"));
        int alunoId = Integer.parseInt(request.getParameter("alunoId"));
        String motivo = request.getParameter("motivo");
        String data = request.getParameter("data");

        Psicologo psicologo = dao.buscarPorId(psicologoId);
        Aluno aluno = alunoDAO.buscarPorId(alunoId);

        dao.agendarEntrevista(psicologo, aluno, motivo, data);
        response.sendRedirect("consultar?id=" + psicologoId);
    }

    private void enviarMensagemPedagogo(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        int psicologoId = Integer.parseInt(request.getParameter("psicologoId"));
        int pedagogoId = Integer.parseInt(request.getParameter("pedagogoId"));
        String conteudo = request.getParameter("conteudo");

        Psicologo psicologo = dao.buscarPorId(psicologoId);
        Pedagogo pedagogo = pedagogoDAO.buscarPorId(pedagogoId);

        dao.enviarMensagemParaPedagogo(psicologo, pedagogo, conteudo);
        response.sendRedirect("consultar?id=" + psicologoId);
    }
}