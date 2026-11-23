<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Registros do Aluno</title>
    <link rel="stylesheet" href="${ctx}/css/style.css">
</head>
<body>

<jsp:include page="/WEB-INF/includes/navbar.jsp"><jsp:param name="ativo" value="alunos" /></jsp:include>

<div class="container container-wide">
    <jsp:include page="/WEB-INF/includes/mensagens.jsp" />

    <c:choose>
    <c:when test="${aluno == null}">
        <div class="alert-error">Aluno não encontrado.</div>
        <a href="${ctx}/aluno" class="btn btn-secondary">Voltar</a>
    </c:when>
    <c:otherwise>

    <div class="section-title">
        <h2>Registros Pedagógicos — <c:out value="${aluno.nome}" /></h2>
        <a href="${ctx}/aluno?acao=editar&id=${aluno.id}" class="btn btn-secondary">Editar dados</a>
    </div>

    <div class="ficha">
        <p><strong>ID:</strong> ${aluno.id}</p>
        <p><strong>E-mail:</strong> <c:out value="${aluno.email}" /></p>
        <p><strong>Telefone:</strong> <c:out value="${empty aluno.telefone ? '-' : aluno.telefone}" /></p>
        <p><strong>Nascimento:</strong> <span class="data">${empty aluno.dataNascimento ? '-' : aluno.dataNascimento}</span></p>
        <p><strong>Matrícula:</strong> <c:out value="${empty aluno.matricula ? '-' : aluno.matricula}" /></p>
        <p><strong>Ano Escolar:</strong> <c:out value="${empty aluno.anoEscolar ? '-' : aluno.anoEscolar}" /></p>
        <p><strong>Status:</strong> <t:status valor="${aluno.status}" /></p>
        <p><strong>Responsável:</strong>
            <c:choose>
                <c:when test="${aluno.responsavelId > 0}">
                    <a href="${ctx}/responsavel?acao=detalhe&id=${aluno.responsavelId}"><c:out value="${aluno.nomeResponsavel}" /></a>
                </c:when>
                <c:otherwise>-</c:otherwise>
            </c:choose>
        </p>
    </div>

    <div class="actions">
        <a href="${ctx}/acompanhamento?alunoId=${aluno.id}" class="btn btn-primary">+ Novo Acompanhamento</a>
        <a href="${ctx}/nova-entrevista?alunoId=${aluno.id}" class="btn btn-primary">+ Entrevista</a>
        <a href="${ctx}/professor?acao=registrar&tipo=ocorrencia&alunoId=${aluno.id}" class="btn btn-primary">+ Ocorrência</a>
        <a href="${ctx}/professor?acao=registrar&tipo=avaliacao&alunoId=${aluno.id}" class="btn btn-primary">+ Avaliação</a>
        <a href="${ctx}/professor?acao=registrar&tipo=adaptacao&alunoId=${aluno.id}" class="btn btn-primary">+ Adaptação</a>
        <a href="${ctx}/neurodivergencia?alunoId=${aluno.id}" class="btn btn-primary">+ Neurodivergência</a>
        <a href="${ctx}/apontamento?alunoId=${aluno.id}" class="btn btn-primary">+ Apontamento</a>
        <a href="${ctx}/reuniao?alunoId=${aluno.id}&voltar=/aluno?acao=historico%26id=${aluno.id}" class="btn btn-success">+ Marcar Reunião</a>
    </div>

    <!-- Acompanhamentos -->
    <div class="section">
        <div class="section-title"><h3>Histórico de Acompanhamentos</h3></div>
        <div class="table-wrap">
        <table class="tabela">
            <thead><tr><th>ID</th><th>Início</th><th>Fim</th><th>Objetivo</th><th>Observação</th><th>Status</th><th>Ações</th></tr></thead>
            <tbody>
            <c:forEach var="ac" items="${acompanhamentosAluno}">
                <tr>
                    <td>${ac.id}</td>
                    <td class="data">${ac.dataInicio}</td>
                    <td class="data">${empty ac.dataFim ? 'Em aberto' : ac.dataFim}</td>
                    <td><c:out value="${ac.objetivo}" /></td>
                    <td><c:out value="${empty ac.observacao ? '-' : ac.observacao}" /></td>
                    <td><t:status valor="${ac.status}" /></td>
                    <td><a href="${ctx}/acompanhamento?acao=editar&id=${ac.id}">Editar</a></td>
                </tr>
            </c:forEach>
            <c:if test="${empty acompanhamentosAluno}">
                <tr><td colspan="7" class="vazio">Nenhum acompanhamento registrado para este aluno.</td></tr>
            </c:if>
            </tbody>
        </table>
        </div>
    </div>

    <!-- Neurodivergência -->
    <div class="section">
        <div class="section-title"><h3>Neurodivergência</h3></div>
        <div class="table-wrap">
        <table class="tabela">
            <thead><tr><th>Tipo</th><th>Descrição</th><th>Observações</th><th>Registro</th></tr></thead>
            <tbody>
            <c:forEach var="n" items="${neurodivergencias}">
                <tr>
                    <td><strong><c:out value="${n.tipo}" /></strong></td>
                    <td><c:out value="${n.descricao}" /></td>
                    <td><c:out value="${n.observacoes}" /></td>
                    <td class="data">${n.dataRegistro}</td>
                </tr>
            </c:forEach>
            <c:if test="${empty neurodivergencias}">
                <tr><td colspan="4" class="vazio">Nenhum registro de neurodivergência.</td></tr>
            </c:if>
            </tbody>
        </table>
        </div>
    </div>

    <!-- Apontamentos -->
    <div class="section">
        <div class="section-title"><h3>Apontamentos de Professores</h3></div>
        <div class="table-wrap">
        <table class="tabela">
            <thead><tr><th>Data</th><th>Professor</th><th>Motivo</th></tr></thead>
            <tbody>
            <c:forEach var="ap" items="${apontamentos}">
                <tr>
                    <td class="data">${ap.dataInicio}</td>
                    <td><c:out value="${ap.nomeProfessor}" /></td>
                    <td><c:out value="${ap.motivo}" /></td>
                </tr>
            </c:forEach>
            <c:if test="${empty apontamentos}">
                <tr><td colspan="3" class="vazio">Nenhum apontamento.</td></tr>
            </c:if>
            </tbody>
        </table>
        </div>
    </div>

    <!-- Entrevistas -->
    <div class="section">
        <div class="section-title"><h3>Entrevistas com Psicólogo</h3></div>
        <div class="table-wrap">
        <table class="tabela">
            <thead><tr><th>Data</th><th>Psicólogo</th><th>Motivo</th><th>Situação</th><th>Observações</th><th>Ações</th></tr></thead>
            <tbody>
            <c:forEach var="e" items="${entrevistas}">
                <tr>
                    <td class="data">${e.data}</td>
                    <td><c:out value="${e.psicologo != null ? e.psicologo.nome : '-'}" /></td>
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

    <!-- Ocorrências -->
    <div class="section">
        <div class="section-title"><h3>Observações e Ocorrências (Professor)</h3></div>
        <div class="table-wrap">
        <table class="tabela">
            <thead><tr><th>Data</th><th>Tipo</th><th>Professor</th><th>Descrição</th><th>Providência</th><th>Status</th><th></th></tr></thead>
            <tbody>
            <c:forEach var="o" items="${ocorrencias}">
                <tr>
                    <td class="data">${o.data}</td>
                    <td><strong><c:out value="${o.tipo}" /></strong></td>
                    <td><c:out value="${empty o.nomeProfessor ? '-' : o.nomeProfessor}" /></td>
                    <td><c:out value="${o.descricao}" /></td>
                    <td><c:out value="${empty o.providencia ? '-' : o.providencia}" /></td>
                    <td><t:status valor="${o.status}" /></td>
                    <td><a href="${ctx}/professor?acao=excluirOcorrencia&id=${o.id}&alunoId=${aluno.id}" class="link-excluir" data-confirmar="Excluir este registro?">Excluir</a></td>
                </tr>
            </c:forEach>
            <c:if test="${empty ocorrencias}">
                <tr><td colspan="7" class="vazio">Nenhuma ocorrência registrada.</td></tr>
            </c:if>
            </tbody>
        </table>
        </div>
    </div>

    <!-- Avaliações -->
    <div class="section">
        <div class="section-title"><h3>Avaliações</h3></div>
        <div class="table-wrap">
        <table class="tabela">
            <thead><tr><th>Data</th><th>Disciplina</th><th>Tipo</th><th>Nota</th><th>Observação</th><th></th></tr></thead>
            <tbody>
            <c:forEach var="av" items="${avaliacoes}">
                <tr>
                    <td class="data">${av.data}</td>
                    <td><c:out value="${empty av.nomeDisciplina ? '-' : av.nomeDisciplina}" /></td>
                    <td><c:out value="${av.tipo}" /></td>
                    <td><strong>${av.nota}</strong></td>
                    <td><c:out value="${av.observacao}" /></td>
                    <td><a href="${ctx}/professor?acao=excluirAvaliacao&id=${av.id}&alunoId=${aluno.id}" class="link-excluir" data-confirmar="Excluir esta avaliação?">Excluir</a></td>
                </tr>
            </c:forEach>
            <c:if test="${empty avaliacoes}">
                <tr><td colspan="6" class="vazio">Nenhuma avaliação registrada.</td></tr>
            </c:if>
            </tbody>
        </table>
        </div>
    </div>

    <!-- Adaptações -->
    <div class="section">
        <div class="section-title"><h3>Adaptações Metodológicas</h3></div>
        <div class="table-wrap">
        <table class="tabela">
            <thead><tr><th>Data</th><th>Tipo</th><th>Professor</th><th>Descrição</th><th>Estratégia</th><th>Resultado</th><th>Status</th><th></th></tr></thead>
            <tbody>
            <c:forEach var="ad" items="${adaptacoes}">
                <tr>
                    <td class="data">${ad.dataRegistro}</td>
                    <td><c:out value="${ad.tipo}" /></td>
                    <td><c:out value="${empty ad.nomeProfessor ? '-' : ad.nomeProfessor}" /></td>
                    <td><c:out value="${ad.descricao}" /></td>
                    <td><c:out value="${ad.estrategia}" /></td>
                    <td><c:out value="${ad.resultado}" /></td>
                    <td><t:status valor="${ad.status}" /></td>
                    <td><a href="${ctx}/professor?acao=excluirAdaptacao&id=${ad.id}&alunoId=${aluno.id}" class="link-excluir" data-confirmar="Excluir esta adaptação?">Excluir</a></td>
                </tr>
            </c:forEach>
            <c:if test="${empty adaptacoes}">
                <tr><td colspan="8" class="vazio">Nenhuma adaptação registrada.</td></tr>
            </c:if>
            </tbody>
        </table>
        </div>
    </div>

    <!-- Reuniões -->
    <div class="section">
        <div class="section-title"><h3>Reuniões sobre o Aluno</h3></div>
        <div class="table-wrap">
        <table class="tabela">
            <thead><tr><th>Data</th><th>Solicitante</th><th>Com</th><th>Motivo</th><th>Status</th></tr></thead>
            <tbody>
            <c:forEach var="r" items="${reunioes}">
                <tr>
                    <td class="data">${r.dataReuniao}</td>
                    <td><c:out value="${r.solicitanteTipo}" /><c:if test="${not empty r.nomeSolicitante}"> (<c:out value="${r.nomeSolicitante}" />)</c:if></td>
                    <td><c:out value="${r.publicoAlvo}" /></td>
                    <td><c:out value="${r.motivo}" /></td>
                    <td><t:status valor="${r.status}" /></td>
                </tr>
            </c:forEach>
            <c:if test="${empty reunioes}">
                <tr><td colspan="5" class="vazio">Nenhuma reunião marcada.</td></tr>
            </c:if>
            </tbody>
        </table>
        </div>
    </div>

    </c:otherwise>
    </c:choose>
</div>

</body>
</html>
