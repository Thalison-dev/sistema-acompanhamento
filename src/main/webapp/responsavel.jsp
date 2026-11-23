<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Responsáveis Legais</title>
    <link rel="stylesheet" href="${ctx}/css/style.css">
</head>
<body>

<jsp:include page="/WEB-INF/includes/navbar.jsp"><jsp:param name="ativo" value="responsaveis" /></jsp:include>

<div class="container container-wide">
    <jsp:include page="/WEB-INF/includes/mensagens.jsp" />

    <div class="section-title">
        <h2>Responsáveis Legais</h2>
        <a href="${ctx}/responsavel?acao=novo" class="btn btn-success">+ Cadastrar Responsável</a>
    </div>
    <p class="texto-ajuda">Para vincular um responsável a um aluno, edite o cadastro do aluno e escolha o responsável.</p>

    <div class="table-wrap">
    <table class="tabela">
        <thead><tr><th>ID</th><th>Nome</th><th>Parentesco</th><th>E-mail</th><th>Telefone</th><th>Ações</th></tr></thead>
        <tbody>
        <c:forEach var="r" items="${responsaveis}">
            <tr>
                <td>${r.id}</td>
                <td><a href="${ctx}/responsavel?acao=detalhe&id=${r.id}"><c:out value="${r.nome}" /></a></td>
                <td><c:out value="${empty r.parentesco ? '-' : r.parentesco}" /></td>
                <td><c:out value="${r.email}" /></td>
                <td><c:out value="${empty r.telefone ? '-' : r.telefone}" /></td>
                <td class="acoes-linha">
                    <a href="${ctx}/responsavel?acao=detalhe&id=${r.id}">Alunos</a> |
                    <a href="${ctx}/responsavel?acao=editar&id=${r.id}">Editar</a> |
                    <a href="${ctx}/responsavel?acao=excluir&id=${r.id}" class="link-excluir" data-confirmar="Excluir este responsável? Os alunos ficarão sem responsável.">Excluir</a>
                </td>
            </tr>
        </c:forEach>
        <c:if test="${empty responsaveis}">
            <tr><td colspan="6" class="vazio">Nenhum responsável cadastrado.</td></tr>
        </c:if>
        </tbody>
    </table>
    </div>
</div>

</body>
</html>
