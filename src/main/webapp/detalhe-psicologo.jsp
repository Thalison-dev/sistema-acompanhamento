<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Psicólogo</title>
    <link rel="stylesheet" href="${ctx}/css/style.css">
</head>
<body>

<jsp:include page="/WEB-INF/includes/navbar.jsp"><jsp:param name="ativo" value="psicologos" /></jsp:include>

<div class="container container-wide">
    <jsp:include page="/WEB-INF/includes/mensagens.jsp" />

    <div class="section-title">
        <h2><c:out value="${psicologo.nome}" /></h2>
        <div class="acoes-linha">
            <a href="${ctx}/nova-entrevista?psicologoId=${psicologo.id}" class="btn btn-success">+ Nova Entrevista</a>
            <a href="${ctx}/reuniao?perfil=Psicólogo&solicitanteId=${psicologo.id}&voltar=/psicologo?acao=detalhe%26id=${psicologo.id}" class="btn btn-primary">Marcar Reunião</a>
            <a href="${ctx}/psicologo?acao=editar&id=${psicologo.id}" class="btn btn-secondary">Editar</a>
        </div>
    </div>

    <div class="ficha">
        <p><strong>ID:</strong> ${psicologo.id}</p>
        <p><strong>Especialidade:</strong> <c:out value="${empty psicologo.especialidade ? '-' : psicologo.especialidade}" /></p>
        <p><strong>E-mail:</strong> <c:out value="${psicologo.email}" /></p>
        <p><strong>Telefone:</strong> <c:out value="${empty psicologo.telefone ? '-' : psicologo.telefone}" /></p>
    </div>

    <div class="section">
        <div class="section-title"><h3>Entrevistas deste Psicólogo</h3></div>
        <div class="table-wrap">
        <table class="tabela">
            <thead><tr><th>Data</th><th>Aluno</th><th>Motivo</th><th>Situação</th><th>Observações</th><th>Ações</th></tr></thead>
            <tbody>
            <c:forEach var="e" items="${entrevistas}">
                <tr>
                    <td class="data">${empty e.data ? '-' : e.data}</td>
                    <td><a href="${ctx}/aluno?acao=historico&id=${e.alunoId}"><c:out value="${e.aluno.nome}" /></a></td>
                    <td><c:out value="${e.motivo}" /></td>
                    <td><t:status valor="${e.agendada ? 'Agendada' : 'Realizada'}" /></td>
                    <td><c:out value="${e.observacoes}" /></td>
                    <td><a href="${ctx}/editar-entrevista?id=${e.id}">Editar</a></td>
                </tr>
            </c:forEach>
            <c:if test="${empty entrevistas}">
                <tr><td colspan="6" class="vazio">Nenhuma entrevista registrada.</td></tr>
            </c:if>
            </tbody>
        </table>
        </div>
    </div>
</div>

</body>
</html>
