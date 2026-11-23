<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Área do Professor</title>
    <link rel="stylesheet" href="${ctx}/css/style.css">
</head>
<body>

<jsp:include page="/WEB-INF/includes/navbar.jsp"><jsp:param name="ativo" value="professores" /></jsp:include>

<div class="container container-wide">
    <jsp:include page="/WEB-INF/includes/mensagens.jsp" />

    <h2>Área do Professor</h2>
    <p class="texto-ajuda">Os registros feitos aqui entram direto no histórico de acompanhamento do aluno.</p>

    <div class="actions">
        <a href="${ctx}/apontamento" class="btn btn-warning">Apontar Aluno para Acompanhamento</a>
        <a href="${ctx}/professor?acao=registrar&tipo=ocorrencia" class="btn btn-primary">+ Observação / Ocorrência</a>
        <a href="${ctx}/professor?acao=registrar&tipo=avaliacao" class="btn btn-primary">+ Avaliação</a>
        <a href="${ctx}/professor?acao=registrar&tipo=adaptacao" class="btn btn-primary">+ Adaptação Metodológica</a>
    </div>

    <div class="section">
        <div class="section-title">
            <h3>Professores</h3>
            <a href="${ctx}/professor?acao=novo" class="btn btn-success btn-sm">+ Cadastrar Professor</a>
        </div>
        <div class="table-wrap">
        <table class="tabela">
            <thead><tr><th>ID</th><th>Nome</th><th>Matrícula</th><th>Especialidade</th><th>E-mail</th><th>Ações</th></tr></thead>
            <tbody>
            <c:forEach var="p" items="${professores}">
                <tr>
                    <td>${p.id}</td>
                    <td><c:out value="${p.nome}" /></td>
                    <td><c:out value="${empty p.matricula ? '-' : p.matricula}" /></td>
                    <td><c:out value="${empty p.especialidade ? '-' : p.especialidade}" /></td>
                    <td><c:out value="${p.email}" /></td>
                    <td class="acoes-linha">
                        <a href="${ctx}/professor?acao=registrar&professorId=${p.id}">Registrar</a> |
                        <a href="${ctx}/professor?acao=editar&id=${p.id}">Editar</a> |
                        <a href="${ctx}/professor?acao=excluir&id=${p.id}" class="link-excluir" data-confirmar="Excluir este professor?">Excluir</a>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${empty professores}">
                <tr><td colspan="6" class="vazio">Nenhum professor cadastrado.</td></tr>
            </c:if>
            </tbody>
        </table>
        </div>
    </div>

    <div class="section">
        <div class="section-title"><h3>Últimas Observações e Ocorrências</h3></div>
        <div class="table-wrap">
        <table class="tabela">
            <thead><tr><th>Data</th><th>Aluno</th><th>Tipo</th><th>Professor</th><th>Descrição</th><th>Status</th></tr></thead>
            <tbody>
            <c:forEach var="o" items="${ocorrencias}">
                <tr>
                    <td class="data">${o.data}</td>
                    <td><a href="${ctx}/aluno?acao=historico&id=${o.alunoId}"><c:out value="${o.nomeAluno}" /></a></td>
                    <td><strong><c:out value="${o.tipo}" /></strong></td>
                    <td><c:out value="${empty o.nomeProfessor ? '-' : o.nomeProfessor}" /></td>
                    <td><c:out value="${o.descricao}" /></td>
                    <td><t:status valor="${o.status}" /></td>
                </tr>
            </c:forEach>
            <c:if test="${empty ocorrencias}">
                <tr><td colspan="6" class="vazio">Nenhuma ocorrência registrada.</td></tr>
            </c:if>
            </tbody>
        </table>
        </div>
    </div>

    <div class="section">
        <div class="section-title"><h3>Disciplinas</h3></div>
        <form action="${ctx}/professor" method="post" class="form-inline">
            <input type="hidden" name="acao" value="salvarDisciplina">
            <div class="form-group">
                <label for="nomeDisciplina">Nome</label>
                <input type="text" id="nomeDisciplina" name="nome" maxlength="100" required>
            </div>
            <div class="form-group">
                <label for="descricaoDisciplina">Descrição</label>
                <input type="text" id="descricaoDisciplina" name="descricao">
            </div>
            <button type="submit" class="btn btn-success">Adicionar</button>
        </form>
        <div class="table-wrap" style="margin-top:15px">
        <table class="tabela">
            <thead><tr><th>ID</th><th>Disciplina</th><th>Descrição</th><th></th></tr></thead>
            <tbody>
            <c:forEach var="d" items="${disciplinas}">
                <tr>
                    <td>${d.id}</td>
                    <td><c:out value="${d.nome}" /></td>
                    <td><c:out value="${d.descricao}" /></td>
                    <td><a href="${ctx}/professor?acao=excluirDisciplina&id=${d.id}" class="link-excluir" data-confirmar="Excluir esta disciplina?">Excluir</a></td>
                </tr>
            </c:forEach>
            <c:if test="${empty disciplinas}">
                <tr><td colspan="4" class="vazio">Nenhuma disciplina cadastrada.</td></tr>
            </c:if>
            </tbody>
        </table>
        </div>
    </div>
</div>

</body>
</html>
