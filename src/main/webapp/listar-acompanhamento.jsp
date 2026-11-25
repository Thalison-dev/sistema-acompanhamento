<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="br.com.thalison.sistema_acompanhamento_Acompanhamento.Acompanhamento" %>
<%
    List<Acompanhamento> acompanhamentos = (request.getAttribute("acompanhamentos") instanceof List<?>)
            ? (List<Acompanhamento>) request.getAttribute("acompanhamentos")
            : new ArrayList<>();
%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Lista de Acompanhamentos</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>
    <h1>Acompanhamentos Cadastrados</h1>

    <p>
        <a href="<%= request.getContextPath() %>/painel.jsp">Painel</a> |
        <a href="<%= request.getContextPath() %>/acompanhamento">Novo Acompanhamento</a>
    </p>

    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
            <tr>
                <th>ID</th>
                <th>Data de Início</th>
                <th>Data de Fim</th>
                <th>Objetivo</th>
                <th>Status</th>
                <th>Ações</th>
            </tr>
        </thead>
        <tbody>
            <% if (!acompanhamentos.isEmpty()) {
                for (Acompanhamento a : acompanhamentos) { %>
                <tr>
                    <td><%= a.getId() %></td>
                    <td><%= a.getDataInicio() != null ? a.getDataInicio() : "-" %></td>
                    <td><%= a.getDataFim() != null ? a.getDataFim() : "Em aberto" %></td>
                    <td><%= a.getObjetivo() != null ? a.getObjetivo() : "-" %></td>
                    <td><%= a.getStatus() != null ? a.getStatus() : "-" %></td>
                    <td>
                        <a href="<%= request.getContextPath() %>/acompanhamento?acao=ficha&id=<%= a.getId() %>">Abrir Ficha</a> |
                        <a href="<%= request.getContextPath() %>/acompanhamento?acao=excluir&id=<%= a.getId() %>" onclick="return confirm('Tem certeza que deseja excluir?');">Excluir</a>
                    </td>
                </tr>
            <%  }
               } else { %>
                <tr>
                    <td colspan="6" style="text-align: center;">Nenhum acompanhamento encontrado.</td>
                </tr>
            <% } %>
        </tbody>
    </table>
</body>
</html>