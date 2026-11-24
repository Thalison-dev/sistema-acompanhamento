<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="br.com.thalison.sistema_acompanhamento_Pessoa.Pessoa" %>
<%
    Pessoa usuario = (Pessoa) session.getAttribute("usuarioLogado");
    String perfil = usuario.getPerfil();
%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Painel Principal</title>
</head>
<body>

    <h1>Sistema de Apoio para Alunos Neurodivergentes</h1>
    <p>
        <strong>Usuário:</strong> <%= usuario.getNome() %> |
        <strong>Perfil:</strong> <%= perfil %> |
        <a href="${pageContext.request.contextPath}/logout">Sair</a>
    </p>
    <hr>

    <% if (request.getParameter("erro") != null) { %>
        <p><strong>Aviso:</strong> <%= request.getParameter("erro") %></p>
    <% } %>

    <h2>Módulos Disponíveis para o seu Perfil</h2>
    <ul>
        <% if ("ADMIN".equals(perfil)) { %>
            <li><a href="${pageContext.request.contextPath}/admin">Administração da Escola (Cadastrar Equipe e Usuários)</a></li>
        <% } %>

        <% if ("ADMIN".equals(perfil) || "SECRETARIA".equals(perfil)) { %>
            <li><a href="${pageContext.request.contextPath}/aluno">Secretaria: Cadastrar Alunos Neurodivergentes</a></li>
            <li><a href="${pageContext.request.contextPath}/responsavel">Secretaria: Cadastrar Responsáveis Legais</a></li>
        <% } %>

        <li><a href="${pageContext.request.contextPath}/acompanhamento">Fichas de Acompanhamento dos Alunos</a></li>

        <% if ("ADMIN".equals(perfil) || "PSICOLOGO".equals(perfil)) { %>
            <li><a href="${pageContext.request.contextPath}/entrevistas">Psicologia: Entrevistas e Atendimentos</a></li>
        <% } %>

        <% if ("ADMIN".equals(perfil) || "PROFESSOR".equals(perfil) || "PEDAGOGO".equals(perfil)) { %>
            <li><a href="${pageContext.request.contextPath}/apontamentos">Sala de Aula: Apontamentos do Professor</a></li>
        <% } %>
        <li><a href="${pageContext.request.contextPath}/desempenho">Desempenho Mensal (Media vs. Apontamentos)</a></li>
    </ul>

</body>
</html>