<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Diretores</title>
    <link rel="stylesheet" href="${ctx}/css/style.css">
</head>
<body>

<jsp:include page="/WEB-INF/includes/navbar.jsp"><jsp:param name="ativo" value="diretor" /></jsp:include>

<div class="container container-wide">
    <jsp:include page="/WEB-INF/includes/mensagens.jsp" />

    <div class="section-title">
        <h2>Diretores Cadastrados</h2>
        <div class="acoes-linha">
            <a href="${ctx}/diretor?acao=novo" class="btn btn-success">+ Cadastrar Diretor</a>
            <a href="${ctx}/diretor" class="btn btn-secondary">Painel do Diretor</a>
        </div>
    </div>

    <div class="table-wrap">
    <table class="tabela">
        <thead><tr><th>ID</th><th>Nome</th><th>Cargo</th><th>Escola</th><th>Formação</th><th>E-mail</th><th>Ações</th></tr></thead>
        <tbody>
        <c:forEach var="d" items="${diretores}">
            <tr>
                <td>${d.id}</td>
                <td><c:out value="${d.nome}" /></td>
                <td><c:out value="${d.cargo}" /></td>
                <td><c:out value="${empty d.escola ? '-' : d.escola}" /></td>
                <td><c:out value="${empty d.formacao ? '-' : d.formacao}" /></td>
                <td><c:out value="${d.email}" /></td>
                <td class="acoes-linha">
                    <a href="${ctx}/diretor?diretorId=${d.id}">Painel</a> |
                    <a href="${ctx}/diretor?acao=editar&id=${d.id}">Editar</a> |
                    <a href="${ctx}/diretor?acao=excluir&id=${d.id}" class="link-excluir" data-confirmar="Tem certeza que deseja excluir este diretor?">Excluir</a>
                </td>
            </tr>
        </c:forEach>
        <c:if test="${empty diretores}">
            <tr><td colspan="7" class="vazio">Nenhum diretor cadastrado. Preencha o cadastro para adicionar o primeiro diretor.</td></tr>
        </c:if>
        </tbody>
    </table>
    </div>
</div>

</body>
</html>
