<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="br.com.thalison.sistema_acompanhamento_Pessoa.Pessoa" %>
<%
    List<Pessoa> usuarios = (List<Pessoa>) request.getAttribute("usuarios");
%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Administração da Escola</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>

    <p><a href="<%= request.getContextPath() %>/painel.jsp">Voltar ao Painel</a> | <a href="<%= request.getContextPath() %>/logout">Sair</a></p>
    <hr>

    <h1>Administração da Escola - Cadastro Geral de Equipe</h1>

    <% if (request.getParameter("sucesso") != null) { %><p><strong>Sucesso:</strong> <%= request.getParameter("sucesso") %></p><% } %>
    <% if (request.getParameter("erro") != null) { %><p><strong>Erro:</strong> <%= request.getParameter("erro") %></p><% } %>

    <form action="<%= request.getContextPath() %>/admin" method="post">
        <p>
            <label for="perfil">Perfil de Acesso:</label><br>
            <select id="perfil" name="perfil" required>
                <option value="SECRETARIA">Secretaria</option>
                <option value="PSICOLOGO">Psicólogo</option>
                <option value="PEDAGOGO">Pedagogo</option>
                <option value="PROFESSOR">Professor</option>
                <option value="ADMIN">Administrador (Escola)</option>
            </select>
        </p>
        <p>
            <label for="nome">Nome Completo:</label><br>
            <input type="text" id="nome" name="nome" required>
        </p>
        <p>
            <label for="cpf">CPF:</label><br>
            <input type="text" id="cpf" name="cpf" required>
        </p>
        <p>
            <label for="telefone">Telefone:</label><br>
            <input type="text" id="telefone" name="telefone">
        </p>
        <p>
            <label for="email">E-mail de Login:</label><br>
            <input type="email" id="email" name="email" required>
        </p>
        <p>
            <label for="senha">Senha Inicial:</label><br>
            <input type="text" id="senha" name="senha" value="123456" required>
        </p>
        <p>
            <label for="extra1">CRP / Formação / Setor:</label><br>
            <input type="text" id="extra1" name="extra1" placeholder="Ex: CRP 12/0000 ou Setor">
        </p>
        <p>
            <label for="extra2">Especialidade:</label><br>
            <input type="text" id="extra2" name="extra2" placeholder="Ex: Educação Inclusiva">
        </p>
        <p>
            <button type="submit">Cadastrar Usuário</button>
        </p>
    </form>

    <hr>
    <h2>Usuários Cadastrados na Escola</h2>
    <table border="1" cellpadding="6" cellspacing="0">
        <thead>
            <tr>
                <th>ID</th>
                <th>Perfil</th>
                <th>Nome</th>
                <th>E-mail</th>
                <th>Telefone</th>
                <th>Ação</th>
            </tr>
        </thead>
        <tbody>
            <% if (usuarios != null) {
                for (Pessoa u : usuarios) { %>
            <tr>
                <td><%= u.getId() %></td>
                <td><%= u.getPerfil() %></td>
                <td><%= u.getNome() %></td>
                <td><%= u.getEmail() %></td>
                <td><%= u.getTelefone() != null ? u.getTelefone() : "-" %></td>
                <td>
                    <a href="<%= request.getContextPath() %>/admin?acao=excluir&id=<%= u.getId() %>"
                       onclick="return confirm('Excluir este usuário?');">Excluir</a>
                </td>
            </tr>
            <% } } %>
        </tbody>
    </table>

</body>
</html>