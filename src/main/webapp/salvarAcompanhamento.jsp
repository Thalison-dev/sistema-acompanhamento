
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>

<%@ page import="br.com.thalison.sistema_acompanhamento_Acompanhamento.Acompanhamento" %>
<%@ page import="br.com.thalison.sistema_acompanhamento_Acompanhamento.AcompanhamentoDAO" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="br.com.thalison.sistema_acompanhamento.Conexao" %>

<%
    request.setCharacterEncoding("UTF-8");

    try {

        String alunoIdTexto = request.getParameter("alunoId");
        String dataInicio = request.getParameter("dataInicio");
        String dataFim = request.getParameter("dataFim");
        String objetivo = request.getParameter("objetivo");
        String status = request.getParameter("status");

        if (alunoIdTexto == null || alunoIdTexto.trim().isEmpty()
                || dataInicio == null || dataInicio.trim().isEmpty()
                || objetivo == null || objetivo.trim().isEmpty()
                || status == null || status.trim().isEmpty()) {

            throw new IllegalArgumentException("Preencha todos os campos obrigatórios.");
        }

        int alunoId = Integer.parseInt(alunoIdTexto);

        if (alunoId <= 0) {
            throw new IllegalArgumentException("ID do aluno inválido.");
        }

        if (dataFim != null && !dataFim.isEmpty()
                && dataFim.compareTo(dataInicio) < 0) {
            throw new IllegalArgumentException(
                "A data de fim não pode ser anterior à data de início."
            );
        }

        Acompanhamento acompanhamento = new Acompanhamento();

        acompanhamento.setAlunoId(alunoId);
        acompanhamento.setDataInicio(dataInicio);
        acompanhamento.setDataFim(dataFim);
        acompanhamento.setObjetivo(objetivo);
        acompanhamento.setStatus(status);

        AcompanhamentoDAO dao = new AcompanhamentoDAO();

        if (dao.cadastrar(acompanhamento)) {
%>

            <h2>Acompanhamento cadastrado com sucesso!</h2>
            <p>Aluno ID: <%= alunoId %></p>
            <p>Data de início: <%= dataInicio %></p>
            <p>Data de fim: <%= (dataFim == null || dataFim.isEmpty()) ? "Não informada" : dataFim %></p>
            <p>Status: <%= status %></p>

            <a href="acompanhamento.jsp">Cadastrar outro acompanhamento</a>

<%
        } else {
%>

            <h2>Não foi possível cadastrar o acompanhamento.</h2>
            <p>Verifique se o aluno existe e se a conexão com o banco está funcionando.</p>
            <a href="acompanhamento.jsp">Voltar ao formulário</a>

<%
        }

    } catch (NumberFormatException e) {
%>

        <h2>ID do aluno inválido.</h2>
        <a href="acompanhamento.jsp">Voltar</a>

<%
    } catch (IllegalArgumentException e) {
%>

        <h2>Dados inválidos.</h2>
        <p><%= e.getMessage() %></p>
        <a href="acompanhamento.jsp">Voltar</a>

<%
    } catch (Exception e) {
%>

        <h2>Erro ao processar o cadastro.</h2>
        <p>Entre em contato com o responsável pelo sistema.</p>

<%
        application.log("Erro ao salvar acompanhamento", e);
    }
%>
