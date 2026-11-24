<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="br.com.thalison.sistema_acompanhamento_ResponsavelLegal.ResponsavelLegal" %>
<%
    List<ResponsavelLegal> responsaveis = (List<ResponsavelLegal>) request.getAttribute("responsaveis");
%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Responsáveis Legais</title>
</head>
<body>

    <p>
        <a href="${pageContext.request.contextPath}/painel.jsp">Painel</a> |
        <a href="${pageContext.request.contextPath}/aluno">Alunos</a> |
        <a href="${pageContext.request.contextPath}/acompanhamento">Fichas de Acompanhamento</a>
    </p>
    <hr>

    <h1>Secretaria - Cadastro de Responsável Legal</h1>

    <% if (request.getParameter("sucesso") != null) { %><p><strong>Sucesso:</strong> <%= request.getParameter("sucesso") %></p><% } %>
    <% if (request.getParameter("erro") != null) { %><p><strong>Erro:</strong> <%= request.getParameter("erro") %></p><% } %>

    <form action="${pageContext.request.contextPath}/responsavel" method="post">
        <p>
            <label for="nome">Nome Completo do Responsável:</label><br>
            <input type="text" id="nome" name="nome" required>
        </p>
        <p>
            <label for="parentesco">Parentesco:</label><br>
            <select id="parentesco" name="parentesco" required>
                <option value="Mãe">Mãe</option>
                <option value="Pai">Pai</option>
                <option value="Avô/Avó">Avô/Avó</option>
                <option value="Tutor Legal">Tutor Legal</option>
            </select>
        </p>
        <p>
            <label for="cpf">CPF:</label><br>
            <input type="text" id="cpf" name="cpf" required>
        </p>
        <p>
            <label for="telefone">Telefone:</label><br>
            <input type="text" id="telefone" name="telefone" required>
        </p>
        <p>
            <label for="email">E-mail:</label><br>
            <input type="email" id="email" name="email" required>
        </p>
        <p>
            <button type="submit">Cadastrar Responsável Legal</button>
        </p>
    </form>

    <hr>
    <h2>Responsáveis Legais Cadastrados</h2>
    <table border="1" cellpadding="6" cellspacing="0">
        <thead>
            <tr>
                <th>ID</th>
                <th>Nome</th>
                <th>Parentesco</th>
                <th>CPF</th>
                <th>Telefone</th>
                <th>E-mail</th>
                <th>Ação</th>
            </tr>
        </thead>
        <tbody>
            <% if (responsaveis != null) {
                for (ResponsavelLegal rl : responsaveis) { %>
            <tr>
                <td><%= rl.getId() %></td>
                <td><%= rl.getNome() %></td>
                <td><%= rl.getParentesco() %></td>
                <td><%= rl.getCpf() %></td>
                <td><%= rl.getTelefone() %></td>
                <td><%= rl.getEmail() %></td>
                <td>
                    <a href="${pageContext.request.contextPath}/responsavel?acao=excluir&id=<%= rl.getId() %>"
                       onclick="return confirm('Excluir responsável?');">Excluir</a>
                </td>
            </tr>
            <% } } %>
        </tbody>
    </table>

</body>
</html>