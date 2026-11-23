<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Lista de Acompanhamentos</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<jsp:include page="/WEB-INF/includes/navbar.jsp"><jsp:param name="ativo" value="acompanhamentos" /></jsp:include>

<div class="container container-wide">
    <jsp:include page="/WEB-INF/includes/mensagens.jsp" />

    <div class="section-title">
        <h2>Acompanhamentos Cadastrados</h2>
        <a href="${pageContext.request.contextPath}/novo-acompanhamento" class="btn btn-success">+ Novo Acompanhamento</a>
    </div>

    <div class="table-wrap">
        <table class="tabela">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Aluno</th>
                    <th>Data de Início</th>
                    <th>Data de Fim</th>
                    <th>Objetivo</th>
                    <th>Status</th>
                    <th>Ações</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="a" items="${acompanhamentos}">
                    <tr>
                        <td>${a.id}</td>
                        <td><a href="${pageContext.request.contextPath}/aluno?acao=historico&id=${a.alunoId}"><c:out value="${empty a.nomeAluno ? 'Aluno ' : a.nomeAluno}" /></a></td>
                        <td class="data">${a.dataInicio}</td>
                        <td class="data">${empty a.dataFim ? 'Em aberto' : a.dataFim}</td>
                        <td><c:out value="${a.objetivo}" /></td>
                        <td><t:status valor="${a.status}" /></td>
                        <td class="acoes-linha">
                            <a href="${pageContext.request.contextPath}/acompanhamento?acao=editar&id=${a.id}">Editar</a> |
                            <a href="${pageContext.request.contextPath}/reuniao?acompanhamentoId=${a.id}&alunoId=${a.alunoId}">Reunião</a> |
                            <a href="${pageContext.request.contextPath}/acompanhamento?acao=excluir&id=${a.id}" class="link-excluir"
                               data-confirmar="Tem certeza que deseja excluir?">Excluir</a>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty acompanhamentos}">
                    <tr>
                        <td colspan="7" class="vazio">Nenhum acompanhamento encontrado.</td>
                    </tr>
                </c:if>
            </tbody>
        </table>
    </div>
</div>

</body>
</html>
