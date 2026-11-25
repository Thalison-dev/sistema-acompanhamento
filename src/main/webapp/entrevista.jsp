<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="br.com.thalison.sistema_acompanhamento_aluno.Aluno" %>
<%@ page import="br.com.thalison.sistema_Acompanhamento_entrevista.Entrevista" %>
<%
    List<Aluno> alunos = (List<Aluno>) request.getAttribute("alunos");
    List<Entrevista> entrevistas = (List<Entrevista>) request.getAttribute("entrevistas");
%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Entrevistas Psicológicas</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>

    <p><a href="<%= request.getContextPath() %>/painel.jsp">Painel</a> | <a href="<%= request.getContextPath() %>/acompanhamento">Fichas de Acompanhamento</a></p>
    <hr>

    <h1>Psicologia - Registro de Entrevistas</h1>

    <form action="<%= request.getContextPath() %>/entrevistas" method="post">
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
            <label>Motivo:</label><br>
            <input type="text" name="motivo" required>
        </p>
        <p>
            <label><input type="checkbox" name="agendada" checked> Entrevista Agendada</label>
        </p>
        <p>
            <label>Observações Clínicas:</label><br>
            <textarea name="observacoes" rows="3" cols="50"></textarea>
        </p>
        <button type="submit">Salvar Entrevista</button>
    </form>

    <hr>
    <h2>Entrevistas Registradas</h2>
    <table border="1" cellpadding="6" cellspacing="0">
        <thead>
            <tr>
                <th>ID</th>
                <th>Aluno</th>
                <th>Psicólogo</th>
                <th>Data</th>
                <th>Motivo</th>
                <th>Observações</th>
                <th>Ação</th>
            </tr>
        </thead>
        <tbody>
            <% if (entrevistas != null) { for (Entrevista e : entrevistas) { %>
            <tr>
                <td><%= e.getId() %></td>
                <td><%= e.getNomeAluno() %></td>
                <td><%= e.getNomePsicologo() %></td>
                <td><%= e.getData() %></td>
                <td><%= e.getMotivo() %></td>
                <td><%= e.getObservacoes() != null ? e.getObservacoes() : "-" %></td>
                <td>
                    <a href="<%= request.getContextPath() %>/entrevistas?acao=excluir&id=<%= e.getId() %>"
                       onclick="return confirm('Excluir entrevista?');">Excluir</a>
                </td>
            </tr>
            <% } } %>
        </tbody>
    </table>

</body>
</html>