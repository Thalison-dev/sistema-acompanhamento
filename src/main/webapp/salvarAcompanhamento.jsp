<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.time.LocalDate" %>
<%@ page import="br.com.thalison.sistema_acompanhamento_Acompanhamento.Acompanhamento" %>
<%@ page import="br.com.thalison.sistema_acompanhamento_Acompanhamento.AcompanhamentoDAO" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Resultado do Acompanhamento</title>
</head>
<body>

    <p>
        <a href="${pageContext.request.contextPath}/painel.jsp">Painel</a> |
        <a href="${pageContext.request.contextPath}/acompanhamento">Fichas de Acompanhamento</a> |
        <a href="${pageContext.request.contextPath}/logout">Sair</a>
    </p>
    <hr>

<%
    request.setCharacterEncoding("UTF-8");

    try {
        String alunoIdTexto = request.getParameter("alunoId");
        String responsavelIdTexto = request.getParameter("responsavelLegalId");
        String dataInicioTexto = request.getParameter("dataInicio");
        String dataFimTexto = request.getParameter("dataFim");
        String objetivo = request.getParameter("objetivo");
        String status = request.getParameter("status");

        if (alunoIdTexto == null || alunoIdTexto.trim().isEmpty()
                || responsavelIdTexto == null || responsavelIdTexto.trim().isEmpty()
                || dataInicioTexto == null || dataInicioTexto.trim().isEmpty()
                || objetivo == null || objetivo.trim().isEmpty()) {
            throw new IllegalArgumentException("Preencha aluno, responsável legal, data de início e objetivo.");
        }

        int alunoId = Integer.parseInt(alunoIdTexto.trim());
        int responsavelId = Integer.parseInt(responsavelIdTexto.trim());
        LocalDate dataInicio = LocalDate.parse(dataInicioTexto.trim());
        LocalDate dataFim = (dataFimTexto != null && !dataFimTexto.trim().isEmpty())
                ? LocalDate.parse(dataFimTexto.trim()) : null;

        if (alunoId <= 0 || responsavelId <= 0) {
            throw new IllegalArgumentException("Aluno e responsável legal devem ser selecionados.");
        }

        if (dataFim != null && dataFim.isBefore(dataInicio)) {
            throw new IllegalArgumentException("A data de fim não pode ser anterior à data de início.");
        }

        Acompanhamento acompanhamento = new Acompanhamento();
        acompanhamento.setAlunoId(alunoId);
        acompanhamento.setResponsavelLegalId(responsavelId);
        acompanhamento.setDataInicio(dataInicio);
        acompanhamento.setDataFim(dataFim);
        acompanhamento.setObjetivo(objetivo.trim());
        acompanhamento.setStatus((status != null && !status.trim().isEmpty()) ? status.trim() : "Em Andamento");

        AcompanhamentoDAO dao = new AcompanhamentoDAO();

        if (dao.abrirFichaSecretaria(acompanhamento)) {
%>
            <h2>Acompanhamento cadastrado com sucesso!</h2>
            <p><strong>Aluno ID:</strong> <%= alunoId %></p>
            <p><strong>Responsável Legal ID:</strong> <%= responsavelId %></p>
            <p><strong>Data de início:</strong> <%= dataInicio %></p>
            <p><strong>Data de fim:</strong> <%= (dataFim == null) ? "Não informada" : dataFim %></p>
            <p><strong>Status:</strong> <%= acompanhamento.getStatus() %></p>
            <p>
                <a href="${pageContext.request.contextPath}/acompanhamento">Ver Fichas de Acompanhamento</a> |
                <a href="${pageContext.request.contextPath}/painel.jsp">Voltar ao Painel</a>
            </p>
<%
        } else {
%>
            <h2>Não foi possível cadastrar o acompanhamento.</h2>
            <p>Verifique se o aluno e o responsável legal existem no banco.</p>
            <p><a href="${pageContext.request.contextPath}/acompanhamento">Voltar</a></p>
<%
        }
    } catch (Exception e) {
%>
        <h2>Erro: <%= e.getMessage() %></h2>
        <p><a href="${pageContext.request.contextPath}/acompanhamento">Voltar</a></p>
<%
    }
%>

</body>
</html>