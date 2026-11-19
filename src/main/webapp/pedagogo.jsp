<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="br.com.thalison.sistema_acompanhamento_Pedagogo.Pedagogo" %>
<%
    Object objPedagogo = request.getAttribute("pedagogo");
    Pedagogo pedagogo = (objPedagogo instanceof Pedagogo) ? (Pedagogo) objPedagogo : null;
    boolean isEdicao = (pedagogo != null);
%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title><%= isEdicao ? "Editar Pedagogo" : "Novo Pedagogo" %></title>
    <style>
        body { font-family: Arial, sans-serif; margin: 0; background-color: #f4f6f9; color: #333; }
        .navbar { background-color: #2c3e50; padding: 14px 30px; display: flex; gap: 20px; align-items: center; }
        .navbar a { color: #ecf0f1; text-decoration: none; font-weight: bold; font-size: 14px; padding: 6px 10px; border-radius: 4px; }
        .navbar a:hover, .navbar a.active { background-color: #007bff; color: #fff; }
        .container { max-width: 550px; margin: 40px auto; background: #fff; padding: 25px; border-radius: 8px; box-shadow: 0 2px 6px rgba(0,0,0,0.1); }
        h2 { margin-top: 0; color: #2c3e50; }
        .form-group { margin-bottom: 15px; }
        label { display: block; margin-bottom: 5px; font-weight: bold; }
        input[type="text"], input[type="number"] { width: 100%; padding: 10px; border: 1px solid #ccc; border-radius: 4px; box-sizing: border-box; }
        input[readonly] { background-color: #e9ecef; cursor: not-allowed; }
        .actions { margin-top: 20px; display: flex; gap: 10px; }
        .btn { padding: 10px 16px; border: none; border-radius: 4px; font-size: 14px; font-weight: bold; cursor: pointer; text-decoration: none; }
        .btn-success { background-color: #28a745; color: #fff; }
        .btn-success:hover { background-color: #218838; }
        .btn-secondary { background-color: #6c757d; color: #fff; }
        .btn-secondary:hover { background-color: #5a6268; }
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
    <h2><%= isEdicao ? "Editar Pedagogo" : "Cadastrar Pedagogo" %></h2>

    <form action="${pageContext.request.contextPath}/pedagogo" method="post">
        <input type="hidden" name="acao" value="<%= isEdicao ? "atualizar" : "cadastrar" %>">

        <div class="form-group">
            <label for="idPedagogo">ID do Pedagogo:</label>
            <% if (isEdicao) { %>
                <input type="number" id="idPedagogo" value="<%= pedagogo.getIdPedagogo() %>" readonly>
                <input type="hidden" name="id" value="<%= pedagogo.getIdPedagogo() %>">
            <% } else { %>
                <input type="number" id="idPedagogo" name="idPedagogo" required placeholder="Ex: 1">
            <% } %>
        </div>

        <div class="form-group">
            <label for="pessoa">Nome / Pessoa:</label>
            <input type="text" id="pessoa" name="pessoa" required
                   value="<%= isEdicao && pedagogo.getPessoa() != null ? pedagogo.getPessoa() : "" %>"
                   placeholder="Nome do pedagogo">
        </div>

        <div class="form-group">
            <label for="especialidade">Especialidade:</label>
            <input type="text" id="especialidade" name="especialidade" required
                   value="<%= isEdicao && pedagogo.getEspecialidade() != null ? pedagogo.getEspecialidade() : "" %>"
                   placeholder="Ex: Psicopedagogia, Orientação Educacional">
        </div>

        <div class="actions">
            <button type="submit" class="btn btn-success"><%= isEdicao ? "Atualizar" : "Salvar" %></button>
            <a href="${pageContext.request.contextPath}/pedagogo" class="btn btn-secondary">Cancelar</a>
        </div>
    </form>
</div>
</body>
</html>