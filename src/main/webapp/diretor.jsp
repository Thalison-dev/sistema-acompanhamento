<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List, java.util.Map" %>
<%@ page import="br.com.thalison.sistema_acompanhamento_Diretor.Diretor" %>
<%@ page import="br.com.thalison.sistema_acompanhamento_Pessoa.Pessoa" %>
<%
    Pessoa usuario = (session != null) ? (Pessoa) session.getAttribute("usuarioLogado") : null;
    if (usuario == null) {
        response.sendRedirect(request.getContextPath() + "/login.jsp?erro=Faca+login+para+continuar");
        return;
    }

    List<Diretor> diretores = (List<Diretor>) request.getAttribute("diretores");
    Map<String, Integer> indicadores = (Map<String, Integer>) request.getAttribute("indicadores");
%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
<meta charset="UTF-8">
<title>Painel do Diretor</title>
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>

    <h1>Painel do Diretor</h1>
    <p>
        <strong>Usuário:</strong> <%= usuario.getNome() %> |
        <a href="<%= request.getContextPath() %>/painel.jsp">Voltar ao Painel</a> |
        <a href="<%= request.getContextPath() %>/logout">Sair</a>
    </p>
    <hr>

    <% if (request.getParameter("sucesso") != null) { %>
        <p style="color: green;"><strong>Sucesso:</strong> <%= request.getParameter("sucesso") %></p>
    <% } %>
    <% if (request.getParameter("erro") != null) { %>
        <p style="color: red;"><strong>Erro:</strong> <%= request.getParameter("erro") %></p>
    <% } %>

    <h2>Indicadores Gerais da Instituição</h2>
    <table border="1" cellpadding="8" cellspacing="0">
        <tr>
            <th>Alunos</th>
            <th>Acompanhamentos</th>
            <th>Entrevistas</th>
            <th>Apontamentos</th>
            <th>Professores</th>
            <th>Psicólogos</th>
            <th>Pedagogos</th>
        </tr>
        <tr>
            <td><%= indicadores != null ? indicadores.get("alunos") : 0 %></td>
            <td><%= indicadores != null ? indicadores.get("acompanhamentos") : 0 %></td>
            <td><%= indicadores != null ? indicadores.get("entrevistas") : 0 %></td>
            <td><%= indicadores != null ? indicadores.get("apontamentos") : 0 %></td>
            <td><%= indicadores != null ? indicadores.get("professores") : 0 %></td>
            <td><%= indicadores != null ? indicadores.get("psicologos") : 0 %></td>
            <td><%= indicadores != null ? indicadores.get("pedagogos") : 0 %></td>
        </tr>
    </table>

    <h2>Cadastrar Novo Diretor</h2>
    <p><em>Informe o ID de uma Pessoa já cadastrada (com perfil DIRETOR) e a matrícula.</em></p>
    <form method="post" action="<%= request.getContextPath() %>/diretor">
        <input type="hidden" name="acao" value="cadastrar">
        <label>ID da Pessoa:</label>
        <input type="number" name="id" required>

        <label>Matrícula:</label>
        <input type="text" name="matricula" required>

        <button type="submit">Cadastrar Diretor</button>
    </form>

    <h2>Diretores Cadastrados</h2>
    <table border="1" cellpadding="8" cellspacing="0">
        <tr>
            <th>ID</th>
            <th>Nome</th>
            <th>Email</th>
            <th>CPF</th>
            <th>Telefone</th>
            <th>Matrícula</th>
            <th>Ação</th>
        </tr>
        <% if (diretores != null && !diretores.isEmpty()) {
            for (Diretor d : diretores) { %>
        <tr>
            <td><%= d.getId() %></td>
            <td><%= d.getNome() %></td>
            <td><%= d.getEmail() %></td>
            <td><%= d.getCpf() %></td>
            <td><%= d.getTelefone() %></td>
            <td><%= d.getMatricula() %></td>
            <td>
                <form method="post" action="<%= request.getContextPath() %>/diretor" style="display:inline;">
                    <input type="hidden" name="acao" value="excluir">
                    <input type="hidden" name="id" value="<%= d.getId() %>">
                    <button type="submit" onclick="return confirm('Confirma a exclusão?');">Excluir</button>
                </form>
            </td>
        </tr>
        <% }
        } else { %>
        <tr>
            <td colspan="7">Nenhum diretor cadastrado ainda.</td>
        </tr>
        <% } %>
    </table>

</body>
</html>