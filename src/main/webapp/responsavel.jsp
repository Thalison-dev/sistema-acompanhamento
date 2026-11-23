<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="br.com.thalison.sistema_acompanhamento_ResponsavelLegal.ResponsavelLegal" %>
<%@ page import="br.com.thalison.sistema_acompanhamento_ResponsavelLegal.ResponsavelLegalDAO" %>
<%
    List<ResponsavelLegal> responsaveis = (request.getAttribute("responsaveis") instanceof List<?>)
            ? (List<ResponsavelLegal>) request.getAttribute("responsaveis")
            : new ResponsavelLegalDAO().listarTodos();

    String msgSucesso = request.getParameter("sucesso");
    String msgErro = request.getParameter("erro");
%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Responsáveis Legais</title>
</head>
<body>

    <p>
        <a href="${pageContext.request.contextPath}/index.jsp">Início</a> |
        <a href="${pageContext.request.contextPath}/cadastroAluno.jsp">Cadastrar Aluno</a> |
        <a href="${pageContext.request.contextPath}/responsavel">Responsáveis Legais</a> |
        <a href="${pageContext.request.contextPath}/acompanhamento">Acompanhamentos</a>
    </p>
    <hr>

    <h2>Cadastrar Responsável Legal</h2>

    <% if (msgSucesso != null && !msgSucesso.isEmpty()) { %>
        <p><strong>Sucesso:</strong> <%= msgSucesso %></p>
    <% } %>
    <% if (msgErro != null && !msgErro.isEmpty()) { %>
        <p><strong>Erro:</strong> <%= msgErro %></p>
    <% } %>

    <form action="${pageContext.request.contextPath}/responsavel" method="post">
        <p>
            <label for="nome">Nome do Responsável:</label><br>
            <input type="text" id="nome" name="nome" required>
        </p>
        <p>
            <label for="parentesco">Parentesco:</label><br>
            <select id="parentesco" name="parentesco" required>
                <option value="Mãe">Mãe</option>
                <option value="Pai">Pai</option>
                <option value="Avô/Avó">Avô/Avó</option>
                <option value="Tutor Legal">Tutor Legal</option>
                <option value="Outro">Outro</option>
            </select>
        </p>
        <p>
            <label for="email">E-mail:</label><br>
            <input type="email" id="email" name="email" required>
        </p>
        <p>
            <label for="telefone">Telefone:</label><br>
            <input type="text" id="telefone" name="telefone" placeholder="(00) 00000-0000">
        </p>
        <p>
            <label for="cpf">CPF:</label><br>
            <input type="text" id="cpf" name="cpf" placeholder="000.000.000-00">
        </p>
        <p>
            <button type="submit">Cadastrar Responsável</button>
            <a href="${pageContext.request.contextPath}/index.jsp">Voltar ao Início</a>
        </p>
    </form>

    <hr>

    <h3>Responsáveis Legais Cadastrados</h3>
    <table border="1" cellpadding="6" cellspacing="0">
        <thead>
            <tr>
                <th>ID</th>
                <th>Nome</th>
                <th>Parentesco</th>
                <th>Telefone</th>
                <th>E-mail</th>
                <th>Ações</th>
            </tr>
        </thead>
        <tbody>
            <% if (!responsaveis.isEmpty()) {
                for (ResponsavelLegal rl : responsaveis) { %>
            <tr>
                <td><%= rl.getId() %></td>
                <td><%= rl.getNome() %></td>
                <td><%= rl.getParentesco() != null ? rl.getParentesco() : "-" %></td>
                <td><%= rl.getTelefone() != null ? rl.getTelefone() : "-" %></td>
                <td><%= rl.getEmail() %></td>
                <td>
                    <a href="${pageContext.request.contextPath}/acompanhamento?responsavelLegalId=<%= rl.getId() %>">Vincular a Acompanhamento</a> |
                    <a href="${pageContext.request.contextPath}/responsavel?acao=excluir&id=<%= rl.getId() %>"
                       onclick="return confirm('Deseja excluir este responsável?');">Excluir</a>
                </td>
            </tr>
            <%  }
               } else { %>
            <tr>
                <td colspan="6">Nenhum responsável legal cadastrado.</td>
            </tr>
            <% } %>
        </tbody>
    </table>

</body>
</html>