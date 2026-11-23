<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="voltar" value="${empty param.voltar ? '/reuniao' : param.voltar}" />
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Reuniões</title>
    <link rel="stylesheet" href="${ctx}/css/style.css">
</head>
<body>

<jsp:include page="/WEB-INF/includes/navbar.jsp"><jsp:param name="ativo" value="reunioes" /></jsp:include>

<div class="container container-wide">
    <jsp:include page="/WEB-INF/includes/mensagens.jsp" />

    <h2>Agendar Reunião<c:if test="${not empty perfil}"> — <c:out value="${perfil}" /></c:if></h2>

    <form action="${ctx}/reuniao" method="post">
        <input type="hidden" name="voltar" value="<c:out value='${voltar}'/>">
        <input type="hidden" name="solicitanteId" value="${solicitanteId}">

        <div class="form-grid">
            <div class="form-group">
                <label for="solicitanteTipo">Quem está marcando <span class="obrigatorio">*</span></label>
                <select id="solicitanteTipo" name="solicitanteTipo" required>
                    <c:forEach var="p" items="Pedagogo,Psicólogo,Professor,Diretor,Responsável Legal">
                        <option value="${p}" ${perfil == p ? 'selected' : ''}>${p}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="form-group">
                <label for="publicoAlvo">Reunião com <span class="obrigatorio">*</span></label>
                <select id="publicoAlvo" name="publicoAlvo" required>
                    <c:forEach var="p" items="Pedagogo,Psicólogo,Professor,Diretor,Responsável Legal,Aluno">
                        <option value="${p}">${p}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="form-group">
                <label for="dataReuniao">Data <span class="obrigatorio">*</span></label>
                <input type="date" id="dataReuniao" name="dataReuniao" required>
            </div>
            <div class="form-group">
                <label for="alunoId">Aluno relacionado</label>
                <select id="alunoId" name="alunoId">
                    <option value="0">-- Nenhum --</option>
                    <c:forEach var="a" items="${alunos}">
                        <option value="${a.id}" ${alunoSelecionado == a.id ? 'selected' : ''}>ID ${a.id} - <c:out value="${a.nome}" /></option>
                    </c:forEach>
                </select>
            </div>
            <div class="form-group full">
                <label for="acompanhamentoId">Acompanhamento relacionado</label>
                <select id="acompanhamentoId" name="acompanhamentoId">
                    <option value="0">-- Nenhum --</option>
                    <c:forEach var="ac" items="${acompanhamentos}">
                        <option value="${ac.id}" ${acompanhamentoSelecionado == ac.id ? 'selected' : ''}>
                            #${ac.id} - <c:out value="${ac.nomeAluno}" /> - <c:out value="${ac.status}" />
                        </option>
                    </c:forEach>
                </select>
            </div>
            <div class="form-group full">
                <label for="motivo">Motivo / Pauta <span class="obrigatorio">*</span></label>
                <textarea id="motivo" name="motivo" rows="3" required></textarea>
            </div>
        </div>
        <div class="actions">
            <button type="submit" class="btn btn-success">Agendar</button>
            <a href="${ctx}${voltar}" class="btn btn-secondary">Voltar</a>
        </div>
    </form>

    <div class="section">
        <div class="section-title">
            <h3>Reuniões<c:if test="${not empty perfil}"> envolvendo <c:out value="${perfil}" /></c:if></h3>
            <c:if test="${not empty perfil}"><a href="${ctx}/reuniao">Ver todas</a></c:if>
        </div>
        <div class="table-wrap">
        <table class="tabela">
            <thead><tr><th>Data</th><th>Solicitante</th><th>Com</th><th>Aluno</th><th>Acomp.</th><th>Motivo</th><th>Status</th><th>Ações</th></tr></thead>
            <tbody>
            <c:forEach var="r" items="${reunioes}">
                <tr>
                    <td class="data">${r.dataReuniao}</td>
                    <td><c:out value="${r.solicitanteTipo}" /><c:if test="${not empty r.nomeSolicitante}"><br><small class="muted"><c:out value="${r.nomeSolicitante}" /></small></c:if></td>
                    <td><c:out value="${r.publicoAlvo}" /></td>
                    <td>
                        <c:choose>
                            <c:when test="${r.alunoId > 0}"><a href="${ctx}/aluno?acao=historico&id=${r.alunoId}"><c:out value="${r.nomeAluno}" /></a></c:when>
                            <c:otherwise>-</c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <c:choose>
                            <c:when test="${r.acompanhamentoId > 0}"><a href="${ctx}/acompanhamento?acao=editar&id=${r.acompanhamentoId}">#${r.acompanhamentoId}</a></c:when>
                            <c:otherwise>-</c:otherwise>
                        </c:choose>
                    </td>
                    <td><c:out value="${r.motivo}" /></td>
                    <td><t:status valor="${r.status}" /></td>
                    <td class="acoes-linha">
                        <c:if test="${r.status == 'Agendada'}">
                            <a href="${ctx}/reuniao?acao=status&status=Realizada&id=${r.id}&voltar=/reuniao">Realizada</a> |
                            <a href="${ctx}/reuniao?acao=status&status=Cancelada&id=${r.id}&voltar=/reuniao">Cancelar</a> |
                        </c:if>
                        <a href="${ctx}/reuniao?acao=excluir&id=${r.id}&voltar=/reuniao" class="link-excluir" data-confirmar="Excluir esta reunião?">Excluir</a>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${empty reunioes}">
                <tr><td colspan="8" class="vazio">Nenhuma reunião agendada.</td></tr>
            </c:if>
            </tbody>
        </table>
        </div>
    </div>
</div>

</body>
</html>
