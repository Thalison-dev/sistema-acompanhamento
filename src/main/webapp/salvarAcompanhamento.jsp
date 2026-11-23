<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="br.com.thalison.sistema_acompanhamento_Acompanhamento.Acompanhamento" %>
<%@ page import="br.com.thalison.sistema_acompanhamento_Acompanhamento.AcompanhamentoDAO" %>
<%@ page import="br.com.thalison.sistema_acompanhamento_aluno.Aluno" %>
<%@ page import="br.com.thalison.sistema_acompanhamento_aluno.AlunoDAO" %>
<%-- Mantido por compatibilidade: o cadastro principal agora é feito pelo servlet /acompanhamento. --%>
<%!
    private String esc(Object valor) {
        if (valor == null) return "";
        return String.valueOf(valor).replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;");
    }
%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Resultado do Acompanhamento</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<jsp:include page="/WEB-INF/includes/navbar.jsp"><jsp:param name="ativo" value="acompanhamentos" /></jsp:include>

<div class="container container-narrow">
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

        int alunoId = Integer.parseInt(alunoIdTexto.trim());

        if (alunoId <= 0) {
            throw new IllegalArgumentException("ID do aluno inválido.");
        }

        if (dataFim != null && !dataFim.isEmpty() && dataFim.compareTo(dataInicio) < 0) {
            throw new IllegalArgumentException("A data de fim não pode ser anterior à data de início.");
        }

        Acompanhamento acompanhamento = new Acompanhamento();
        acompanhamento.setAlunoId(alunoId);
        acompanhamento.setDataInicio(dataInicio);
        acompanhamento.setDataFim(dataFim);
        acompanhamento.setObjetivo(objetivo);
        acompanhamento.setObservacao(request.getParameter("observacao"));
        acompanhamento.setStatus(status);

        if (new AcompanhamentoDAO().cadastrar(acompanhamento)) {
            Aluno aluno = new AlunoDAO().buscarPorId(alunoId);
            String nomeAluno = aluno != null ? " - " + aluno.getNome() : "";
%>
            <div class="alert-success">
                <h2>Acompanhamento cadastrado com sucesso!</h2>
                <p><strong>Aluno:</strong> ID <%= alunoId %><%= esc(nomeAluno) %></p>
                <p><strong>Data de início:</strong> <%= esc(dataInicio) %></p>
                <p><strong>Data de fim:</strong> <%= (dataFim == null || dataFim.isEmpty()) ? "Não informada" : esc(dataFim) %></p>
                <p><strong>Status:</strong> <%= esc(status) %></p>
            </div>
            <div class="actions">
                <a href="${pageContext.request.contextPath}/acompanhamento" class="btn btn-primary">Voltar para Acompanhamentos</a>
                <a href="${pageContext.request.contextPath}/aluno?acao=historico&id=<%= alunoId %>" class="btn btn-secondary">Histórico do Aluno</a>
            </div>
<%
        } else {
%>
            <div class="alert-error">
                <h2>Não foi possível cadastrar o acompanhamento.</h2>
                <p>Verifique se o aluno existe e se a conexão com o banco está funcionando.</p>
            </div>
            <div class="actions">
                <a href="${pageContext.request.contextPath}/acompanhamento?acao=novo" class="btn btn-secondary">Voltar ao Formulário</a>
            </div>
<%
        }
    } catch (NumberFormatException e) {
%>
        <div class="alert-error">
            <h2>ID do aluno inválido.</h2>
        </div>
        <div class="actions">
            <a href="${pageContext.request.contextPath}/acompanhamento?acao=novo" class="btn btn-secondary">Voltar</a>
        </div>
<%
    } catch (IllegalArgumentException e) {
%>
        <div class="alert-error">
            <h2>Dados inválidos.</h2>
            <p><%= esc(e.getMessage()) %></p>
        </div>
        <div class="actions">
            <a href="${pageContext.request.contextPath}/acompanhamento?acao=novo" class="btn btn-secondary">Voltar</a>
        </div>
<%
    } catch (Exception e) {
        application.log("Erro ao salvar acompanhamento", e);
%>
        <div class="alert-error">
            <h2>Erro ao processar o cadastro.</h2>
            <p>Entre em contato com o responsável pelo sistema.</p>
        </div>
        <div class="actions">
            <a href="${pageContext.request.contextPath}/acompanhamento" class="btn btn-secondary">Voltar</a>
        </div>
<%
    }
%>
</div>

</body>
</html>
