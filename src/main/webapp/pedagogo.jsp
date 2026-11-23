<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Painel do Pedagogo</title>
    <link rel="stylesheet" href="${ctx}/css/style.css">
</head>
<body>

<jsp:include page="/WEB-INF/includes/navbar.jsp"><jsp:param name="ativo" value="pedagogos" /></jsp:include>

<div class="container container-wide">
    <jsp:include page="/WEB-INF/includes/mensagens.jsp" />

    <h2>Painel do Pedagogo</h2>
    <p>
        <strong>Pedagogo em exercício:</strong>
        <c:choose>
            <c:when test="${pedagogoAtivo != null}">
                <c:out value="${pedagogoAtivo.pessoa}" /> (<c:out value="${pedagogoAtivo.especialidade}" />)
            </c:when>
            <c:otherwise>
                Não identificado (<a href="${ctx}/pedagogo?acao=perfil">Definir meu perfil</a>)
            </c:otherwise>
        </c:choose>
    </p>

    <div class="actions">
        <a href="${ctx}/aluno?acao=novo" class="btn btn-success">Cadastrar Novo Aluno</a>
        <a href="${ctx}/acompanhamento?acao=novo" class="btn btn-primary">Registrar Acompanhamento</a>
        <a href="${ctx}/pedagogo?acao=reuniao" class="btn btn-primary">Marcar Reunião</a>
        <a href="${ctx}/pedagogo?acao=perfil" class="btn btn-secondary">Meu Perfil</a>
        <a href="${ctx}/pedagogo?acao=lista" class="btn btn-secondary">Pedagogos Cadastrados</a>
    </div>

    <div class="section">
        <div class="section-title">
            <h3>1. Alunos Cadastrados</h3>
            <a href="${ctx}/aluno?acao=novo">+ Cadastrar Novo Aluno</a>
        </div>
        <div class="table-wrap">
        <table class="tabela">
            <thead>
                <tr><th>ID</th><th>Nome</th><th>Matrícula</th><th>Ano Escolar</th><th>Status</th><th>Ações do Pedagogo</th></tr>
            </thead>
            <tbody>
                <c:forEach var="a" items="${alunos}">
                <tr>
                    <td>${a.id}</td>
                    <td><c:out value="${a.nome}" /></td>
                    <td><c:out value="${empty a.matricula ? '-' : a.matricula}" /></td>
                    <td><c:out value="${empty a.anoEscolar ? '-' : a.anoEscolar}" /></td>
                    <td><t:status valor="${a.status}" /></td>
                    <td class="acoes-linha">
                        <a href="${ctx}/acompanhamento?alunoId=${a.id}">Acompanhar Aluno</a> |
                        <a href="${ctx}/pedagogo?acao=historicoAluno&alunoId=${a.id}">Consultar Registros</a> |
                        <a href="${ctx}/pedagogo?acao=reuniao&alunoId=${a.id}">Marcar Reunião</a>
                    </td>
                </tr>
                </c:forEach>
                <c:if test="${empty alunos}">
                <tr><td colspan="6" class="vazio">Nenhum aluno cadastrado. <a href="${ctx}/aluno?acao=novo">Cadastre o primeiro aluno</a>.</td></tr>
                </c:if>
            </tbody>
        </table>
        </div>
    </div>

    <div class="section">
        <div class="section-title">
            <h3>2. Acompanhamentos Pedagógicos Registrados</h3>
            <a href="${ctx}/acompanhamento?acao=novo">+ Novo Acompanhamento</a>
        </div>
        <div class="table-wrap">
        <table class="tabela">
            <thead>
                <tr><th>ID</th><th>Aluno</th><th>Início</th><th>Fim</th><th>Objetivo</th><th>Status</th><th>Gerenciar</th></tr>
            </thead>
            <tbody>
                <c:forEach var="ac" items="${acompanhamentos}">
                <tr>
                    <td>${ac.id}</td>
                    <td><c:out value="${ac.nomeAluno}" /> (ID ${ac.alunoId})</td>
                    <td class="data">${empty ac.dataInicio ? '-' : ac.dataInicio}</td>
                    <td class="data">${empty ac.dataFim ? 'Em aberto' : ac.dataFim}</td>
                    <td><c:out value="${ac.objetivo}" /></td>
                    <td><t:status valor="${ac.status}" /></td>
                    <td class="acoes-linha">
                        <a href="${ctx}/acompanhamento?acao=editar&id=${ac.id}">Editar</a> |
                        <a href="${ctx}/acompanhamento?acao=excluir&id=${ac.id}" class="link-excluir" data-confirmar="Excluir este acompanhamento?">Excluir</a>
                    </td>
                </tr>
                </c:forEach>
                <c:if test="${empty acompanhamentos}">
                <tr><td colspan="7" class="vazio">Nenhum acompanhamento registrado.</td></tr>
                </c:if>
            </tbody>
        </table>
        </div>
    </div>

    <div class="section">
        <div class="section-title">
            <h3>3. Apontamentos dos Professores</h3>
            <a href="${ctx}/apontamento">Ver todos</a>
        </div>
        <div class="table-wrap">
        <table class="tabela">
            <thead><tr><th>Data</th><th>Aluno</th><th>Professor</th><th>Motivo</th><th></th></tr></thead>
            <tbody>
                <c:forEach var="ap" items="${apontamentos}">
                <tr>
                    <td class="data">${ap.dataInicio}</td>
                    <td><c:out value="${ap.nomeAluno}" /></td>
                    <td><c:out value="${ap.nomeProfessor}" /></td>
                    <td><c:out value="${ap.motivo}" /></td>
                    <td><a href="${ctx}/acompanhamento?alunoId=${ap.alunoId}">Iniciar Acompanhamento</a></td>
                </tr>
                </c:forEach>
                <c:if test="${empty apontamentos}">
                <tr><td colspan="5" class="vazio">Nenhum apontamento.</td></tr>
                </c:if>
            </tbody>
        </table>
        </div>
    </div>

    <div class="section">
        <div class="section-title">
            <h3>4. Reuniões Agendadas</h3>
            <a href="${ctx}/pedagogo?acao=reuniao">+ Agendar Reunião (Psicólogo / Responsável / Diretor / Aluno)</a>
        </div>
        <div class="table-wrap">
        <table class="tabela">
            <thead><tr><th>ID</th><th>Data</th><th>Solicitante</th><th>Com</th><th>Aluno</th><th>Assunto</th><th>Status</th></tr></thead>
            <tbody>
                <c:forEach var="r" items="${reunioes}">
                <tr>
                    <td>${r.id}</td>
                    <td class="data">${r.dataReuniao}</td>
                    <td><c:out value="${r.solicitanteTipo}" /></td>
                    <td><c:out value="${r.publicoAlvo}" /></td>
                    <td><c:out value="${empty r.nomeAluno ? '-' : r.nomeAluno}" /></td>
                    <td><c:out value="${r.motivo}" /></td>
                    <td><t:status valor="${r.status}" /></td>
                </tr>
                </c:forEach>
                <c:if test="${empty reunioes}">
                <tr><td colspan="7" class="vazio">Nenhuma reunião agendada até o momento.</td></tr>
                </c:if>
            </tbody>
        </table>
        </div>
    </div>
</div>

</body>
</html>
