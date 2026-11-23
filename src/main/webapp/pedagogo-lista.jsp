<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="br.com.thalison.sistema_acompanhamento_Pedagogo.Pedagogo" %>
<%@ page import="br.com.thalison.sistema_acompanhamento_Pedagogo.PedagogoDAO" %>
<%
    Object obj = request.getAttribute("pedagogos");
    List<Pedagogo> pedagogos = new ArrayList<>();

    if (obj instanceof List<?>) {
        for (Object item : (List<?>) obj) {
            if (item instanceof Pedagogo) {
                pedagogos.add((Pedagogo) item);
            }
        }
    } else {
        pedagogos = new PedagogoDAO().listar();
    }

    String msgSucesso = request.getParameter("sucesso");
    String msgErro = request.getParameter("erro");
%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Lista de Pedagogos</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

    <p>
        <a href="${pageContext.request.contextPath}/index.jsp">Início</a> |
        <a href="${pageContext.request.contextPath}/pedagogo">Pedagogos</a> |
        <a href="${pageContext.request.contextPath}/cadastroAluno.jsp">Cadastrar Aluno</a> |
        <a href="${pageContext.request.contextPath}/acompanhamento.jsp">Acompanhamentos</a>
    </p>
    <hr>

    <h2>Pedagogos Cadastrados</h2>

    <% if (msgSucesso != null && !msgSucesso.isEmpty()) { %>
        <p><strong>Sucesso:</strong> <%= msgSucesso %></p>
    <% } %>

    <% if (msgErro != null && !msgErro.isEmpty()) { %>
        <p><strong>Erro:</strong> <%= msgErro %></p>
    <% } %>

    <p>
        <a href="${pageContext.request.contextPath}/pedagogo?acao=novo">+ Novo Pedagogo</a> |
        <a href="${pageContext.request.contextPath}/acompanhamento.jsp">Ver Alunos e Acompanhamentos</a>
    </p>

    <table border="1" cellpadding="6" cellspacing="0">
        <thead>
            <tr>
                <th>ID</th>
                <th>Nome / Pessoa</th>
                <th>Especialidade</th>
                <th>Ações</th>
            </tr>
        </thead>
        <tbody>
            <%
                if (!pedagogos.isEmpty()) {
                    for (Pedagogo p : pedagogos) {
            %>
            <tr>
                <td><%= p.getIdPedagogo() %></td>
                <td><%= p.getPessoa() %></td>
                <td><%= p.getEspecialidade() %></td>
                <td>
                    <a href="${pageContext.request.contextPath}/pedagogo?acao=editar&id=<%= p.getIdPedagogo() %>">Editar</a> |
                    <a href="${pageContext.request.contextPath}/pedagogo?acao=excluir&id=<%= p.getIdPedagogo() %>"
                       onclick="return confirm('Deseja realmente excluir este pedagogo?');">Excluir</a>
                </td>
            </tr>
            <%
                    }
                } else {
            %>
            <tr>
                <td colspan="4">Nenhum pedagogo cadastrado até o momento.</td>
            </tr>
            <%
                }
            %>
        </tbody>
    </table>

</body>
</html>