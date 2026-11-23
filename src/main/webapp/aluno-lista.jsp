<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Alunos</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<jsp:include page="/WEB-INF/includes/navbar.jsp"><jsp:param name="ativo" value="alunos" /></jsp:include>

<div class="container container-wide">
    <jsp:include page="/WEB-INF/includes/mensagens.jsp" />

    <div class="section-title">
        <h2>Alunos Cadastrados</h2>
        <a href="${pageContext.request.contextPath}/aluno?acao=novo" class="btn btn-success">+ Cadastrar Aluno</a>
    </div>

    <div class="table-wrap">
        <table class="tabela">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Nome</th>
                    <th>Matrícula</th>
                    <th>Ano Escolar</th>
                    <th>Responsável</th>
                    <th>Status</th>
                    <th>Ações</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="a" items="${alunos}">
                    <tr>
                        <td>${a.id}</td>
                        <td><a href="${pageContext.request.contextPath}/aluno?acao=historico&id=${a.id}"><c:out value="${a.nome}" /></a></td>
                        <td><c:out value="${empty a.matricula ? '-' : a.matricula}" /></td>
                        <td><c:out value="${empty a.anoEscolar ? '-' : a.anoEscolar}" /></td>
                        <td><c:out value="${empty a.nomeResponsavel ? '-' : a.nomeResponsavel}" /></td>
                        <td><t:status valor="${a.status}" /></td>
                        <td class="acoes-linha">
                            <a href="${pageContext.request.contextPath}/aluno?acao=historico&id=${a.id}">Histórico</a> |
                            <a href="${pageContext.request.contextPath}/acompanhamento?alunoId=${a.id}">Acompanhar</a> |
                            <a href="${pageContext.request.contextPath}/aluno?acao=editar&id=${a.id}">Editar</a> |
                            <a href="${pageContext.request.contextPath}/aluno?acao=excluir&id=${a.id}" class="link-excluir"
                               data-confirmar="Excluir este aluno? Só é possível se ele não tiver registros vinculados.">Excluir</a>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty alunos}">
                    <tr><td colspan="7" class="vazio">Nenhum aluno cadastrado.</td></tr>
                </c:if>
            </tbody>
        </table>
    </div>
</div>

</body>
</html>
