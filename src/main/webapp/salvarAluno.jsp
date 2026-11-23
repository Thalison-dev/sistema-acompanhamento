<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="br.com.thalison.sistema_acompanhamento_aluno.Aluno" %>
<%@ page import="br.com.thalison.sistema_acompanhamento_aluno.AlunoDAO" %>
<%-- Mantido por compatibilidade: o cadastro principal agora é feito pelo servlet /aluno. --%>
<%!
    private String limpar(String valor) {
        return (valor == null || valor.trim().isEmpty()) ? null : valor.trim();
    }
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
    <title>Cadastro de Aluno</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<jsp:include page="/WEB-INF/includes/navbar.jsp"><jsp:param name="ativo" value="alunos" /></jsp:include>

<div class="container container-narrow">
<%
    request.setCharacterEncoding("UTF-8");

    try {
        Aluno aluno = new Aluno();
        aluno.setNome(limpar(request.getParameter("nome")));
        aluno.setEmail(limpar(request.getParameter("email")));
        aluno.setCpf(limpar(request.getParameter("cpf")));
        aluno.setTelefone(limpar(request.getParameter("telefone")));
        aluno.setDataNascimento(limpar(request.getParameter("dataNascimento")));
        aluno.setMatricula(limpar(request.getParameter("matricula")));
        aluno.setAnoEscolar(limpar(request.getParameter("anoEscolar")));
        String status = limpar(request.getParameter("status"));
        aluno.setStatus(status != null ? status : "Ativo");

        if (aluno.getNome() == null || aluno.getEmail() == null) {
            throw new IllegalArgumentException("Nome e e-mail são obrigatórios.");
        }

        if (new AlunoDAO().cadastrar(aluno)) {
%>
            <div class="alert-success">
                <h2>Aluno cadastrado com sucesso!</h2>
                <p><strong>ID:</strong> <%= aluno.getId() %></p>
                <p><strong>Nome:</strong> <%= esc(aluno.getNome()) %></p>
            </div>
            <div class="actions">
                <a href="${pageContext.request.contextPath}/acompanhamento?alunoId=<%= aluno.getId() %>" class="btn btn-primary">Iniciar Acompanhamento</a>
                <a href="${pageContext.request.contextPath}/aluno" class="btn btn-secondary">Ver Alunos</a>
            </div>
<%
        } else {
%>
            <div class="alert-error">
                <h2>Não foi possível cadastrar o aluno.</h2>
                <p>Verifique se o CPF já está cadastrado e se o banco está acessível.</p>
            </div>
            <div class="actions">
                <a href="${pageContext.request.contextPath}/aluno?acao=novo" class="btn btn-secondary">Tentar Novamente</a>
            </div>
<%
        }
    } catch (Exception e) {
%>
        <div class="alert-error">
            <h2>Dados inválidos.</h2>
            <p><%= esc(e.getMessage()) %></p>
        </div>
        <div class="actions">
            <a href="${pageContext.request.contextPath}/aluno?acao=novo" class="btn btn-secondary">Voltar</a>
        </div>
<%
    }
%>
</div>

</body>
</html>
