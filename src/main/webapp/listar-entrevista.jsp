<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Entrevistas</title>
    <link rel="stylesheet" href="${ctx}/css/style.css">
</head>
<body>

<jsp:include page="/WEB-INF/includes/navbar.jsp"><jsp:param name="ativo" value="entrevistas" /></jsp:include>

<div class="container container-wide">
    <jsp:include page="/WEB-INF/includes/mensagens.jsp" />

    <div class="section-title">
        <h2>Entrevistas</h2>
        <a href="${ctx}/nova-entrevista" class="btn btn-success">+ Nova Entrevista</a>
    </div>

    <div class="table-wrap">
        <table class="tabela">
            <thead><tr><th>ID</th><th>Data</th><th>Aluno</th><th>Psicólogo</th><th>Motivo</th><th>Situação</th><th>Ações</th></tr></thead>
            <tbody>
            <c:forEach var="e" items="${entrevistas}">
                <tr>
                    <td>${e.id}</td>
                    <td class="data">${empty e.data ? '-' : e.data}</td>
                    <td>
                        <c:choose>
                            <c:when test="${e.aluno != null}"><a href="${ctx}/aluno?acao=historico&id=${e.alunoId}"><c:out value="${e.aluno.nome}" /></a></c:when>
                            <c:otherwise>-</c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <c:choose>
                            <c:when test="${e.psicologo != null}"><a href="${ctx}/psicologo?acao=detalhe&id=${e.psicologoId}"><c:out value="${e.psicologo.nome}" /></a></c:when>
                            <c:otherwise>-</c:otherwise>
                        </c:choose>
                    </td>
                    <td><c:out value="${e.motivo}" /></td>
                    <td><t:status valor="${e.agendada ? 'Agendada' : 'Realizada'}" /></td>
                    <td class="acoes-linha">
                        <a href="${ctx}/editar-entrevista?id=${e.id}">Editar</a> |
                        <a href="${ctx}/deletar-entrevista?id=${e.id}" class="link-excluir" data-confirmar="Excluir esta entrevista?">Excluir</a>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${empty entrevistas}">
                <tr><td colspan="7" class="vazio">Nenhuma entrevista registrada.</td></tr>
            </c:if>
            </tbody>
        </table>
    </div>
</div>

</body>
</html>
