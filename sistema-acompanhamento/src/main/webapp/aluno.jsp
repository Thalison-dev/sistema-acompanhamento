
<%@ page import="java.util.List" %>
<%@ page import="br.com.thalison.sistema_acompanhamento_aluno.aluno" %>

<%
    List<aluno> alunos = (List<aluno>) request.getAttribute("alunos");
    aluno editando = (aluno) request.getAttribute("alunoEdicao");
%>

<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <title>Alunos</title>
</head>
<body>

    <h1>Cadastro de Alunos</h1>

    <form action="AlunoServlet" method="post">

        <input type="hidden" name="acao"
               value="<%= editando != null ? "atualizar" : "cadastrar" %>">

        <% if (editando != null) { %>
            <input type="hidden" name="id" value="<%= editando.getId() %>">
        <% } %>

        <label>Nome:</label>
        <input type="text" name="nome"
               value="<%= editando != null ? editando.getNome() : "" %>" required>
        <br><br>

        <label>Data de nascimento:</label>
        <input type="date" name="dataNascimento"
               value="<%= editando != null ? editando.getDataNascimento() : "" %>" required>
        <br><br>

        <label>Matrícula:</label>
        <input type="text" name="matricula"
               value="<%= editando != null ? editando.getMatricula() : "" %>" required>
        <br><br>

        <label>Ano escolar:</label>
        <input type="text" name="anoEscolar"
               value="<%= editando != null ? editando.getAnoEscolar() : "" %>" required>
        <br><br>

        <label>Status:</label>
        <input type="text" name="status"
               value="<%= editando != null ? editando.getStatus() : "" %>" required>
        <br><br>

        <button type="submit">
            <%= editando != null ? "Atualizar" : "Cadastrar" %>
        </button>

    </form>

    <hr>

    <h2>Lista de Alunos</h2>

    <table border="1">

        <tr>
            <th>ID</th>
            <th>Nome</th>
            <th>Data de nascimento</th>
            <th>Matrícula</th>
            <th>Ano escolar</th>
            <th>Status</th>
            <th>Ações</th>
        </tr>

        <% if (alunos != null) {
            for (aluno a : alunos) { %>

            <tr>
                <td><%= a.getId() %></td>
                <td><%= a.getNome() %></td>
                <td><%= a.getDataNascimento() %></td>
                <td><%= a.getMatricula() %></td>
                <td><%= a.getAnoEscolar() %></td>
                <td><%= a.getStatus() %></td>
                <td>
                    <a href="AlunoServlet?acao=buscar&id=<%= a.getId() %>">Editar</a>

                    <a href="AlunoServlet?acao=excluir&id=<%= a.getId() %>"
                       onclick="return confirm('Deseja excluir?')">Excluir</a>
                </td>
            </tr>

        <% } } %>

    </table>

</body>
</html>
