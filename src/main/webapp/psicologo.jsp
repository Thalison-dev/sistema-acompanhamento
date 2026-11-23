<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Psicólogos</title>
    <link rel="stylesheet" href="${ctx}/css/style.css">
</head>
<body>

<jsp:include page="/WEB-INF/includes/navbar.jsp"><jsp:param name="ativo" value="psicologos" /></jsp:include>

<div class="container container-wide">
    <jsp:include page="/WEB-INF/includes/mensagens.jsp" />

    <div class="section-title">
        <h2>Psicólogos</h2>
        <div class="acoes-linha">
            <a href="${ctx}/psicologo?acao=novo" class="btn btn-success">+ Cadastrar Psicólogo</a>
            <a href="${ctx}/nova-entrevista" class="btn btn-primary">+ Nova Entrevista</a>
            <a href="${ctx}/reuniao?perfil=Psicólogo&voltar=/psicologo" class="btn btn-secondary">Reuniões</a>
        </div>
    </div>

    <div class="table-wrap">
    <table class="tabela">
        <thead><tr><th>ID</th><th>Nome</th><th>Especialidade</th><th>E-mail</th><th>Telefone</th><th>Ações</th></tr></thead>
        <tbody>
        <c:forEach var="p" items="${psicologos}">
            <tr>
                <td>${p.id}</td>
                <td><a href="${ctx}/psicologo?acao=detalhe&id=${p.id}"><c:out value="${p.nome}" /></a></td>
                <td><c:out value="${empty p.especialidade ? '-' : p.especialidade}" /></td>
                <td><c:out value="${p.email}" /></td>
                <td><c:out value="${empty p.telefone ? '-' : p.telefone}" /></td>
                <td class="acoes-linha">
                    <a href="${ctx}/nova-entrevista?psicologoId=${p.id}">Entrevista</a> |
                    <a href="${ctx}/psicologo?acao=editar&id=${p.id}">Editar</a> |
                    <a href="${ctx}/psicologo?acao=excluir&id=${p.id}" class="link-excluir" data-confirmar="Excluir este psicólogo?">Excluir</a>
                </td>
            </tr>
        </c:forEach>
        <c:if test="${empty psicologos}">
            <tr><td colspan="6" class="vazio">Nenhum psicólogo cadastrado.</td></tr>
        </c:if>
        </tbody>
    </table>
    </div>

    <div class="section">
        <div class="section-title"><h3>Entrevistas Recentes</h3><a href="${ctx}/listar-entrevistas">Ver todas</a></div>
        <div class="table-wrap">
        <table class="tabela">
            <thead><tr><th>Data</th><th>Aluno</th><th>Psicólogo</th><th>Motivo</th><th>Situação</th></tr></thead>
            <tbody>
            <c:forEach var="e" items="${entrevistas}" end="9">
                <tr>
                    <td class="data">${empty e.data ? '-' : e.data}</td>
                    <td><c:out value="${e.aluno != null ? e.aluno.nome : '-'}" /></td>
                    <td><c:out value="${e.psicologo != null ? e.psicologo.nome : '-'}" /></td>
                    <td><c:out value="${e.motivo}" /></td>
                    <td><t:status valor="${e.agendada ? 'Agendada' : 'Realizada'}" /></td>
                </tr>
            </c:forEach>
            <c:if test="${empty entrevistas}">
                <tr><td colspan="5" class="vazio">Nenhuma entrevista registrada.</td></tr>
            </c:if>
            </tbody>
        </table>
        </div>
    </div>
</div>

</body>
</html>
