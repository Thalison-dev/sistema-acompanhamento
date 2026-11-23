<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Lista de Pedagogos</title>
    <link rel="stylesheet" href="${ctx}/css/style.css">
</head>
<body>

<jsp:include page="/WEB-INF/includes/navbar.jsp"><jsp:param name="ativo" value="pedagogos" /></jsp:include>

<div class="container">
    <jsp:include page="/WEB-INF/includes/mensagens.jsp" />

    <div class="section-title">
        <h2>Pedagogos Cadastrados</h2>
        <div class="acoes-linha">
            <a href="${ctx}/pedagogo?acao=novo" class="btn btn-success">+ Novo Pedagogo</a>
            <a href="${ctx}/pedagogo" class="btn btn-secondary">Painel do Pedagogo</a>
        </div>
    </div>

    <div class="table-wrap">
    <table class="tabela">
        <thead>
            <tr><th>ID</th><th>Nome</th><th>E-mail</th><th>Especialidade</th><th>Ações</th></tr>
        </thead>
        <tbody>
            <c:forEach var="p" items="${pedagogos}">
            <tr>
                <td>${p.idPedagogo}</td>
                <td><c:out value="${p.pessoa}" /></td>
                <td><c:out value="${p.email}" /></td>
                <td><c:out value="${p.especialidade}" /></td>
                <td class="acoes-linha">
                    <a href="${ctx}/pedagogo?acao=editar&id=${p.idPedagogo}">Editar</a> |
                    <a href="${ctx}/pedagogo?acao=excluir&id=${p.idPedagogo}" class="link-excluir"
                       data-confirmar="Deseja realmente excluir este pedagogo?">Excluir</a>
                </td>
            </tr>
            </c:forEach>
            <c:if test="${empty pedagogos}">
            <tr><td colspan="5" class="vazio">Nenhum pedagogo cadastrado até o momento.</td></tr>
            </c:if>
        </tbody>
    </table>
    </div>
</div>

</body>
</html>
