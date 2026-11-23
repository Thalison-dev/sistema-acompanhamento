
package br.com.thalison.sistema_acompanhamento_Apontamento;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/apontamento")

public class ApontamentoServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private ApontamentoDAO dao;

    @Override
    public void init() throws ServletException {
        dao = new ApontamentoDAO();
    }

    // LISTAR APONTAMENTOS
    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("apontamentos", dao.listar());

        request.getRequestDispatcher(
            "/apontamento.jsp"
        ).forward(request, response);
    }

    // RECEBER OPERAÇÕES DO FORMULÁRIO
    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String acao = request.getParameter("acao");

        try {

            if ("salvar".equals(acao)) {

                Apontamento apontamento = new Apontamento();

                apontamento.setDataInicio(
                    request.getParameter("dataInicio")
                );

                apontamento.setNomeProfessor(
                    request.getParameter("nomeProfessor")
                );

                apontamento.setMotivo(
                    request.getParameter("motivo")
                );

                apontamento.setIdPessoa(
                    Integer.parseInt(request.getParameter("idPessoa"))
                );

                dao.salvar(apontamento);

            } else if ("atualizar".equals(acao)) {

                Apontamento apontamento = new Apontamento();

                apontamento.setDataInicio(
                    request.getParameter("dataInicio")
                );

                apontamento.setNomeProfessor(
                    request.getParameter("nomeProfessor")
                );

                apontamento.setMotivo(
                    request.getParameter("motivo")
                );

                apontamento.setIdPessoa(
                    Integer.parseInt(request.getParameter("idPessoa"))
                );

                int id = Integer.parseInt(
                    request.getParameter("id")
                );

                dao.atualizar(apontamento, id);

            } else if ("deletar".equals(acao)) {

                int id = Integer.parseInt(
                    request.getParameter("id")
                );

                dao.deletar(id);

            } else {

                response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Ação inválida."
                );

                return;
            }

            response.sendRedirect(
                request.getContextPath() + "/apontamento"
            );

        } catch (NumberFormatException e) {

            response.sendError(
                HttpServletResponse.SC_BAD_REQUEST,
                "ID inválido ou não informado."
            );
        }
    }
}
