<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Painel do Diretor</title>
    <link rel="stylesheet" href="${ctx}/css/style.css">
</head>
<body>

<jsp:include page="/WEB-INF/includes/navbar.jsp"><jsp:param name="ativo" value="diretor" /></jsp:include>

<div class="container container-wide">
    <jsp:include page="/WEB-INF/includes/mensagens.jsp" />

    <div class="section-title">
        <h2>Painel do Diretor</h2>
        <div class="acoes-linha">
            <a href="${ctx}/diretor?acao=lista" class="btn btn-secondary">Diretores</a>
            <a href="${ctx}/diretor?acao=novo" class="btn btn-success">+ Cadastrar Diretor</a>
        </div>
    </div>

    <p>
        <strong>Diretor em exercício:</strong>
        <c:choose>
            <c:when test="${diretorAtivo != null}">
                <c:out value="${diretorAtivo.nome}" /> — <c:out value="${diretorAtivo.cargo}" />
                <c:if test="${not empty diretorAtivo.escola}"> (<c:out value="${diretorAtivo.escola}" />)</c:if>
            </c:when>
            <c:otherwise>Nenhum diretor cadastrado (<a href="${ctx}/diretor?acao=novo">cadastrar</a>)</c:otherwise>
        </c:choose>
    </p>
    <c:if test="${not empty diretores and diretores.size() > 1}">
        <form action="${ctx}/diretor" method="get" class="form-inline">
            <div class="form-group">
                <label for="diretorId">Trocar diretor</label>
                <select id="diretorId" name="diretorId">
                    <c:forEach var="d" items="${diretores}">
                        <option value="${d.id}" ${diretorAtivo.id == d.id ? 'selected' : ''}><c:out value="${d.nome}" /></option>
                    </c:forEach>
                </select>
            </div>
            <button type="submit" class="btn btn-secondary">Selecionar</button>
        </form>
    </c:if>

    <!-- Indicadores -->
    <div class="stats">
        <c:forEach var="item" items="${totais}">
            <div class="stat"><strong>${item.value}</strong><span><c:out value="${item.key}" /></span></div>
        </c:forEach>
    </div>

    <div class="section">
        <div class="section-title"><h3>Acompanhamentos por Status</h3></div>
        <div class="stats">
            <c:forEach var="item" items="${statusTotais}">
                <div class="stat"><strong>${item.value}</strong><span><t:status valor="${item.key}" /></span></div>
            </c:forEach>
        </div>
    </div>

    <!-- Pendências -->
    <div class="section">
        <div class="section-title"><h3>Pendências: alunos apontados sem acompanhamento ativo</h3></div>
        <div class="table-wrap">
        <table class="tabela">
            <thead><tr><th>Data do Apontamento</th><th>Aluno</th><th>Professor</th><th>Motivo</th><th>Ação</th></tr></thead>
            <tbody>
            <c:forEach var="p" items="${pendentes}">
                <tr>
                    <td class="data">${p.dataInicio}</td>
                    <td><a href="${ctx}/aluno?acao=historico&id=${p.alunoId}"><c:out value="${p.nomeAluno}" /></a></td>
                    <td><c:out value="${p.nomeProfessor}" /></td>
                    <td><c:out value="${p.motivo}" /></td>
                    <td><a href="${ctx}/acompanhamento?alunoId=${p.alunoId}">Abrir Acompanhamento</a></td>
                </tr>
            </c:forEach>
            <c:if test="${empty pendentes}">
                <tr><td colspan="5" class="vazio">Nenhuma pendência. Todos os alunos apontados estão sendo acompanhados.</td></tr>
            </c:if>
            </tbody>
        </table>
        </div>
    </div>

    <!-- Supervisão dos acompanhamentos -->
    <div class="section">
        <div class="section-title"><h3>Supervisão dos Acompanhamentos</h3></div>
        <div class="table-wrap">
        <table class="tabela">
            <thead><tr><th>ID</th><th>Aluno</th><th>Início</th><th>Fim</th><th>Objetivo</th><th>Status</th><th>Decisão do Diretor</th></tr></thead>
            <tbody>
            <c:forEach var="ac" items="${acompanhamentos}">
                <tr>
                    <td>${ac.id}</td>
                    <td><a href="${ctx}/aluno?acao=historico&id=${ac.alunoId}"><c:out value="${ac.nomeAluno}" /></a></td>
                    <td class="data">${ac.dataInicio}</td>
                    <td class="data">${empty ac.dataFim ? 'Em aberto' : ac.dataFim}</td>
                    <td><c:out value="${ac.objetivo}" /></td>
                    <td><t:status valor="${ac.status}" /></td>
                    <td class="acoes-linha">
                        <c:if test="${ac.status != 'Em Andamento'}">
                            <a href="${ctx}/diretor?acao=status&status=Em Andamento&id=${ac.id}" class="btn btn-primary btn-sm">Aprovar</a>
                        </c:if>
                        <c:if test="${ac.status != 'Concluído'}">
                            <a href="${ctx}/diretor?acao=status&status=Concluído&id=${ac.id}" class="btn btn-success btn-sm" data-confirmar="Encerrar este acompanhamento como concluído?">Concluir</a>
                        </c:if>
                        <c:if test="${ac.status != 'Cancelado'}">
                            <a href="${ctx}/diretor?acao=status&status=Cancelado&id=${ac.id}" class="btn btn-danger btn-sm" data-confirmar="Cancelar este acompanhamento?">Cancelar</a>
                        </c:if>
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

    <!-- Reuniões -->
    <div class="section">
        <div class="section-title"><h3>Marcar Reunião da Direção</h3></div>
        <form action="${ctx}/diretor" method="post">
            <input type="hidden" name="acao" value="reuniao">
            <input type="hidden" name="diretorId" value="${diretorAtivo.id}">
            <div class="form-grid">
                <div class="form-group">
                    <label for="publicoAlvo">Reunião com <span class="obrigatorio">*</span></label>
                    <select id="publicoAlvo" name="publicoAlvo" required>
                        <c:forEach var="p" items="Pedagogo,Psicólogo,Professor,Responsável Legal,Aluno">
                            <option value="${p}">${p}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label for="dataReuniao">Data <span class="obrigatorio">*</span></label>
                    <input type="date" id="dataReuniao" name="dataReuniao" required>
                </div>
                <div class="form-group">
                    <label for="acompanhamentoId">Acompanhamento relacionado</label>
                    <select id="acompanhamentoId" name="acompanhamentoId">
                        <option value="0">-- Nenhum --</option>
                        <c:forEach var="ac" items="${acompanhamentos}">
                            <option value="${ac.id}">#${ac.id} - <c:out value="${ac.nomeAluno}" /></option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label for="alunoId">Aluno (se não houver acompanhamento)</label>
                    <select id="alunoId" name="alunoId">
                        <option value="0">-- Nenhum --</option>
                        <c:forEach var="a" items="${alunos}">
                            <option value="${a.id}"><c:out value="${a.nome}" /></option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group full">
                    <label for="motivo">Pauta <span class="obrigatorio">*</span></label>
                    <textarea id="motivo" name="motivo" rows="3" required></textarea>
                </div>
            </div>
            <button type="submit" class="btn btn-success">Agendar Reunião</button>
        </form>

        <div class="table-wrap" style="margin-top:20px">
        <table class="tabela">
            <thead><tr><th>Data</th><th>Solicitante</th><th>Com</th><th>Aluno</th><th>Pauta</th><th>Status</th><th>Ações</th></tr></thead>
            <tbody>
            <c:forEach var="r" items="${reunioes}">
                <tr>
                    <td class="data">${r.dataReuniao}</td>
                    <td><c:out value="${r.solicitanteTipo}" /></td>
                    <td><c:out value="${r.publicoAlvo}" /></td>
                    <td><c:out value="${empty r.nomeAluno ? '-' : r.nomeAluno}" /></td>
                    <td><c:out value="${r.motivo}" /></td>
                    <td><t:status valor="${r.status}" /></td>
                    <td class="acoes-linha">
                        <c:if test="${r.status == 'Agendada'}">
                            <a href="${ctx}/reuniao?acao=status&status=Realizada&id=${r.id}&voltar=/diretor">Realizada</a> |
                            <a href="${ctx}/reuniao?acao=status&status=Cancelada&id=${r.id}&voltar=/diretor">Cancelar</a>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${empty reunioes}">
                <tr><td colspan="7" class="vazio">Nenhuma reunião envolvendo a direção.</td></tr>
            </c:if>
            </tbody>
        </table>
        </div>
    </div>
</div>

</body>
</html>
