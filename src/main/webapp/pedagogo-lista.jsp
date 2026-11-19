<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="br.com.thalison.sistema_acompanhamento_Pedagogo.Pedagogo" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Lista de Pedagogos</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 0; background-color: #f4f6f9; color: #333; }
        .navbar { background-color: #2c3e50; padding: 14px 30px; display: flex; gap: 20px; align-items: center; }
        .navbar a { color: #ecf0f1; text-decoration: none; font-weight: bold; font-size: 14px; padding: 6px 10px; border-radius: 4px; }
        .navbar a:hover, .navbar a.active { background-color: #007bff; color: #fff; }
        .container { max-width: 900px; margin: 40px auto; background: #fff; padding: 25px; border-radius: 8px; box-shadow: 0 2px 6px rgba(0,0,0,0.1); }
        .header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px; flex-wrap: wrap; gap: 10px; }
        h2 { margin: 0; color: #2c3e50; }
        .btn { padding: 8px 14px; text-decoration: none; border-radius: 4px; font-size: 14px; font-weight: bold; display: inline-block; }
        .btn-primary { background-color: #007bff; color: #fff; }
        .btn-primary:hover { background-color: #0056b3; }
        .btn-success { background-color: #28a745; color: #fff; }
        .btn-success:hover { background-color: #218838; }
        .btn-edit { background-color: #ffc107; color: #212529; margin-right: 5px; }
        .btn-edit:hover { background-color: #e0a800; }
        .btn-delete { background-color: #dc3545; color: #fff; }
        .btn-delete:hover { background-color: #c82333; }
        table { width: 100%; border-collapse: collapse; margin-top: 10px; }
        th, td { padding: 12px; border-bottom: 1px solid #ddd; text-align: left; }
        th { background-color: #f8f9fa; color: #495057; }
        tr:hover { background-color: #f1f1f1; }
        .empty { text-align: center; padding: 20px; color: #777; }
    </style>
</head>
<body>

<div class="navbar">
    <a href="${pageContext.request.contextPath}/index.jsp">Início</a>
    <a href="${pageContext.request.contextPath}/pedagogo" class="active">Pedagogos</a>
    <a href="${pageContext.request.contextPath}/cadastroAluno.jsp">Alunos</a>
    <a href="${pageContext.request.contextPath}/acompanhamento.jsp">Acompanhamentos</a>
</div>

<div class="container">
    <div class="header">
        <h2>Pedagogos Cadastrados</h2>
        <div>
            <a href="${pageContext.request.contextPath}/acompanhamento.jsp" class="btn btn-success">Acompanhar Alunos</a>
            <a href="${pageContext.request.contextPath}/pedagogo?acao=novo" class="btn btn-primary">+ Novo Pedagogo</a>
        </div>
    </div>

    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Pessoa</th>
                <th>Especialidade</th>
                <th>Ações</th>
            </tr>
        </thead>
        <tbody>
            <%
                Object obj = request.getAttribute("pedagogos");
                List<Pedagogo> pedagogos = new ArrayList<>();

                if (obj instanceof List<?>) {
                    for (Object item : (List<?>) obj) {
                        if (item instanceof Pedagogo) {
                            pedagogos.add((Pedagogo) item);
                        }
                    }
                }

                if (!pedagogos.isEmpty()) {
                    for (Pedagogo p : pedagogos) {
            %>
            <tr>
                <td><%= p.getIdPedagogo() %></td>
                <td><%= p.getPessoa() %></td>
                <td><%= p.getEspecialidade() %></td>
                <td>
                    <a href="${pageContext.request.contextPath}/pedagogo?acao=editar&id=<%= p.getIdPedagogo() %>" class="btn btn-edit">Editar</a>
                    <a href="${pageContext.request.contextPath}/pedagogo?acao=excluir&id=<%= p.getIdPedagogo() %>"
                       class="btn btn-delete"
                       onclick="return confirm('Deseja realmente excluir este pedagogo?');">Excluir</a>
                </td>
            </tr>
            <%
                    }
                } else {
            %>
            <tr>
                <td colspan="4" class="empty">Nenhum pedagogo cadastrado até o momento.</td>
            </tr>
            <%
                }
            %>
        </tbody>
    </table>
</div>
</body>
</html>