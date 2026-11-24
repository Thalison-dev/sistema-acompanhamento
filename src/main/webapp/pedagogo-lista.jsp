<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="br.com.thalison.sistema_acompanhamento_Pedagogo.Pedagogo" %>
<%@ page import="br.com.thalison.sistema_acompanhamento_Pedagogo.PedagogoDAO" %>
<%
    List<Pedagogo> pedagogos = (request.getAttribute("pedagogos") instanceof List<?>)
            ? (List<Pedagogo>) request.getAttribute("pedagogos")
            : new PedagogoDAO().listar();
%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Lista de Pedagogos</title>
</head>
<body>

    <p>
        <a href="${pageContext.request.contextPath}/painel.jsp">Painel</a> |
        <a href="${pageContext.request.contextPath}/pedagogo?acao=perfil">Meu Perfil</a> |
        <a href="${pageContext.request.contextPath}/acompanhamento">Fichas de Acompanhamento</a> |
        <a href="${pageContext.request.contextPath}/logout">Sair</a>
    </p>
    <hr>

    <h2>Pedagogos Cadastrados</h2>

    <% if (request.getParameter("sucesso") != null) { %>
        <p><strong>Sucesso:</strong> <%= request.getParameter("sucesso") %></p>
    <% } %>
    <% if (request.getParameter("erro") != null) { %>
        <p><strong>Erro:</strong> <%= request.getParameter("erro") %></p>
    <% } %>

    <table border="1" cellpadding="6" cellspacing="0">
        <thead>
            <tr>
                <th>ID</th>
                <th>Nome</th>
                <th>E-mail</th>
                <th>Especialidade</th>
            </tr>
        </thead>
        <tbody>
            <% if (!pedagogos.isEmpty()) {
                for (Pedagogo p : pedagogos) { %>
            <tr>
                <td><%= p.getId() %></td>
                <td><%= p.getNome() %></td>
                <td><%= p.getEmail() != null ? p.getEmail() : "-" %></td>
                <td><%= p.getEspecialidade() != null ? p.getEspecialidade() : "-" %></td>
            </tr>
            <%  }
               } else { %>
            <tr>
                <td colspan="4">Nenhum pedagogo cadastrado até o momento.</td>
            </tr>
            <% } %>
        </tbody>
    </table>

</body>
</html>