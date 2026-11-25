<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="br.com.thalison.sistema_acompanhamento_aluno.Aluno" %>
<%@ page import="br.com.thalison.sistema_acompanhamento_Apontamento.Apontamento" %>
<%
    List<Aluno> alunos = (List<Aluno>) request.getAttribute("alunos");
    List<Apontamento> apontamentos = (List<Apontamento>) request.getAttribute("apontamentos");
%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Apontamentos de Sala de Aula</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>

    <p><a href="<%= request.getContextPath() %>/painel.jsp">Painel</a> | <a href="<%= request.getContextPath() %>/acompanhamento">Fichas de Acompanhamento</a></p>
    <hr>

    <h1>Apontamentos Pedagógicos em Sala de Aula</h1>

    <form action="<%= request.getContextPath() %>/apontamentos" method="post">
        <p>
            <label>Aluno:</label><br>
            <select name="alunoId" required>
                <option value="">-- Selecione --</option>
                <% if (alunos != null) { for (Aluno a : alunos) { %>
                    <option value="<%= a.getId() %>"><%= a.getNome() %></option>
                <% } } %>
            </select>
        </p>
        <p>
            <label>Data:</label><br>
            <input type="date" name="data" required>
        </p>
        <p>
            <label>Relato / Observação de Aula:</label><br>
            <textarea name="motivo" rows="3" cols="50" required></textarea>
        </p>
        <button type="submit">Registrar Apontamento</button>
    </form>

    <hr>
    <h2>Histórico de Apontamentos</h2>
    <table border="1" cellpadding="6" cellspacing="0">
        <thead>
            <tr>
                <th>ID</th>
                <th>Aluno</th>
                <th>Professor</th>
                <th>Data</th>
                <th>Relato</th>
                <th>Ação</th>
            </tr>
        </thead>
        <tbody>
            <% if (apontamentos != null) { for (Apontamento ap : apontamentos) { %>
            <tr>
                <td><%= ap.getId() %></td>
                <td><%= ap.getNomeAluno() %></td>
                <td><%= ap.getNomeProfessor() %></td>
                <td><%= ap.getData() %></td>
                <td><%= ap.getMotivo() %></td>
                <td><a href="<%= request.getContextPath() %>/apontamentos?acao=excluir&id=<%= ap.getId() %>" onclick="return confirm('Excluir apontamento?');">Excluir</a></td>
            </tr>
            <% } } %>
        </tbody>
    </table>

</body>
</html>