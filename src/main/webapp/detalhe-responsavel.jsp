<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Responsável Legal</title>
    <link rel="stylesheet" href="${ctx}/css/style.css">
</head>
<body>

<jsp:include page="/WEB-INF/includes/navbar.jsp"><jsp:param name="ativo" value="responsaveis" /></jsp:include>

<div class="container">
    <jsp:include page="/WEB-INF/includes/mensagens.jsp" />

    <div class="section-title">
        <h2><c:out value="${responsavel.nome}" /></h2>
        <div class="acoes-linha">
            <a href="${ctx}/reuniao?perfil=Responsável Legal&solicitanteId=${responsavel.id}&voltar=/responsavel?acao=detalhe%26id=${responsavel.id}" class="btn btn-primary">Marcar Reunião</a>
            <a href="${ctx}/responsavel?acao=editar&id=${responsavel.id}" class="btn btn-secondary">Editar</a>
        </div>
    </div>

    <div class="ficha">
        <p><strong>Parentesco:</strong> <c:out value="${empty responsavel.parentesco ? '-' : responsavel.parentesco}" /></p>
        <p><strong>E-mail:</strong> <c:out value="${responsavel.email}" /></p>
        <p><strong>Telefone:</strong> <c:out value="${empty responsavel.telefone ? '-' : responsavel.telefone}" /></p>
        <p><strong>CPF:</strong> <c:out value="${empty responsavel.cpf ? '-' : responsavel.cpf}" /></p>
    </div>

    <div class="section">
        <div class="section-title"><h3>Alunos sob responsabilidade</h3></div>
        <div class="table-wrap">
        <table class="tabela">
            <thead><tr><th>ID</th><th>Nome</th><th>Ano Escolar</th><th>Status</th><th>Ações</th></tr></thead>
            <tbody>
            <c:forEach var="a" items="${alunos}">
                <tr>
                    <td>${a.id}</td>
                    <td><c:out value="${a.nome}" /></td>
                    <td><c:out value="${empty a.anoEscolar ? '-' : a.anoEscolar}" /></td>
                    <td><t:status valor="${a.status}" /></td>
                    <td><a href="${ctx}/aluno?acao=historico&id=${a.id}">Ver acompanhamento</a></td>
                </tr>
            </c:forEach>
            <c:if test="${empty alunos}">
                <tr><td colspan="5" class="vazio">Nenhum aluno vinculado. Vincule pelo cadastro do aluno.</td></tr>
            </c:if>
            </tbody>
        </table>
        </div>
    </div>
</div>

</body>
</html>
