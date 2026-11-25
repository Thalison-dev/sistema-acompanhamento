<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="br.com.thalison.sistema_acompanhamento_aluno.Aluno" %>
<%
    List<Aluno> alunos = (List<Aluno>) request.getAttribute("alunos");
%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Cadastro de Alunos Neurodivergentes</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>

    <p>
        <a href="<%= request.getContextPath() %>/painel.jsp">Painel</a> |
        <a href="<%= request.getContextPath() %>/responsavel">Responsáveis Legais</a> |
        <a href="<%= request.getContextPath() %>/acompanhamento">Fichas de Acompanhamento</a>
    </p>
    <hr>

    <h1>Secretaria - Cadastro de Aluno Neurodivergente</h1>

    <% if (request.getParameter("sucesso") != null) { %><p><strong>Sucesso:</strong> <%= request.getParameter("sucesso") %></p><% } %>
    <% if (request.getParameter("erro") != null) { %><p><strong>Erro:</strong> <%= request.getParameter("erro") %></p><% } %>

    <form action="<%= request.getContextPath() %>/aluno" method="post">
        <p>
            <label for="nome">Nome do Aluno:</label><br>
            <input type="text" id="nome" name="nome" required>
        </p>
        <p>
            <label for="dataNascimento">Data de Nascimento (Menor de Idade):</label><br>
            <input type="date" id="dataNascimento" name="dataNascimento" required>
        </p>
        <p>
            <label for="cpf">CPF do Aluno:</label><br>
            <input type="text" id="cpf" name="cpf">
        </p>
        <p>
            <label for="email">E-mail de Contato:</label><br>
            <input type="email" id="email" name="email" required>
        </p>
        <p>
            <label for="telefone">Telefone:</label><br>
            <input type="text" id="telefone" name="telefone">
        </p>
        <p>
            <label for="matricula">Matrícula:</label><br>
            <input type="text" id="matricula" name="matricula" required>
        </p>
        <p>
            <label for="anoEscolar">Ano Escolar:</label><br>
            <input type="text" id="anoEscolar" name="anoEscolar" placeholder="Ex: 5º Ano" required>
        </p>
        <p>
            <label for="tipoNeurodivergencia">Tipo de Neurodivergência:</label><br>
            <input type="text" id="tipoNeurodivergencia" name="tipoNeurodivergencia" placeholder="Ex: TEA, TDAH, Dislexia" required>
        </p>
        <p>
            <label for="descricaoNeuro">Observações Iniciais / Laudo:</label><br>
            <textarea id="descricaoNeuro" name="descricaoNeuro" rows="3" cols="45"></textarea>
        </p>
        <input type="hidden" name="status" value="Ativo">
        <p>
            <button type="submit">Cadastrar Aluno</button>
        </p>
    </form>

    <hr>
    <h2>Alunos Neurodivergentes Cadastrados</h2>
    <table border="1" cellpadding="6" cellspacing="0">
        <thead>
            <tr>
                <th>ID</th>
                <th>Nome</th>
                <th>Matrícula</th>
                <th>Ano Escolar</th>
                <th>Neurodivergência</th>
                <th>Ações</th>
            </tr>
        </thead>
        <tbody>
            <% if (alunos != null) {
                for (Aluno a : alunos) { %>
            <tr>
                <td><%= a.getId() %></td>
                <td><%= a.getNome() %></td>
                <td><%= a.getMatricula() %></td>
                <td><%= a.getAnoEscolar() %></td>
                <td><%= a.getTipoNeurodivergencia() != null ? a.getTipoNeurodivergencia() : "-" %></td>
                <td>
                    <a href="<%= request.getContextPath() %>/acompanhamento">Abrir Ficha</a> |
                    <a href="<%= request.getContextPath() %>/aluno?acao=excluir&id=<%= a.getId() %>"
                       onclick="return confirm('Excluir aluno?');">Excluir</a>
                </td>
            </tr>
            <% } } %>
        </tbody>
    </table>

</body>
</html>