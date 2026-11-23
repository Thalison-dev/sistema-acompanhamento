<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="alunoForm" value="${apontamento != null ? apontamento.alunoId : alunoSelecionado}" />
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Apontamentos</title>
    <link rel="stylesheet" href="${ctx}/css/style.css">
</head>
<body>

<jsp:include page="/WEB-INF/includes/navbar.jsp"><jsp:param name="ativo" value="apontamentos" /></jsp:include>

<div class="container container-wide">
    <jsp:include page="/WEB-INF/includes/mensagens.jsp" />

    <h2>${apontamento != null ? 'Editar Apontamento' : 'Novo Apontamento'}</h2>
    <p class="texto-ajuda">O professor aponta o aluno que precisa de atenção. Depois, a equipe inicia o acompanhamento a partir da lista abaixo.</p>

    <form action="${ctx}/apontamento" method="post">
        <input type="hidden" name="id" value="${apontamento.id}">
        <div class="form-grid">
            <div class="form-group">
                <label for="alunoId">Aluno <span class="obrigatorio">*</span></label>
                <select id="alunoId" name="alunoId" required>
                    <option value="">-- Selecione --</option>
                    <c:forEach var="a" items="${alunos}">
                        <option value="${a.id}" ${alunoForm == a.id ? 'selected' : ''}>ID ${a.id} - <c:out value="${a.nome}" /></option>
                    </c:forEach>
                </select>
            </div>
            <div class="form-group">
                <label for="professorId">Professor</label>
                <select id="professorId" name="professorId">
                    <option value="0">-- Selecione (opcional) --</option>
                    <c:forEach var="p" items="${professores}">
                        <option value="${p.id}" ${apontamento.professorId == p.id ? 'selected' : ''}><c:out value="${p.nome}" /></option>
                    </c:forEach>
                </select>
            </div>
            <div class="form-group">
                <label for="nomeProfessor">Nome do professor (se não estiver na lista)</label>
                <input type="text" id="nomeProfessor" name="nomeProfessor" maxlength="100" value="<c:out value='${apontamento.nomeProfessor}'/>">
            </div>
            <div class="form-group">
                <label for="dataInicio">Data <span class="obrigatorio">*</span></label>
                <input type="date" id="dataInicio" name="dataInicio" required value="${apontamento.dataInicio}">
            </div>
            <div class="form-group full">
                <label for="motivo">Motivo <span class="obrigatorio">*</span></label>
                <textarea id="motivo" name="motivo" rows="3" required placeholder="O que foi observado em sala?"><c:out value="${apontamento.motivo}" /></textarea>
            </div>
        </div>
        <div class="actions">
            <button type="submit" class="btn btn-success">${apontamento != null ? 'Salvar Alterações' : 'Registrar Apontamento'}</button>
            <c:if test="${apontamento != null}"><a href="${ctx}/apontamento" class="btn btn-secondary">Cancelar</a></c:if>
        </div>
    </form>

    <div class="section">
        <div class="section-title"><h3>Apontamentos Registrados</h3></div>
        <div class="table-wrap">
            <table class="tabela">
                <thead><tr><th>ID</th><th>Data</th><th>Aluno</th><th>Professor</th><th>Motivo</th><th>Ações</th></tr></thead>
                <tbody>
                <c:forEach var="ap" items="${apontamentos}">
                    <tr>
                        <td>${ap.id}</td>
                        <td class="data">${ap.dataInicio}</td>
                        <td><a href="${ctx}/aluno?acao=historico&id=${ap.alunoId}"><c:out value="${ap.nomeAluno}" /></a></td>
                        <td><c:out value="${empty ap.nomeProfessor ? '-' : ap.nomeProfessor}" /></td>
                        <td><c:out value="${ap.motivo}" /></td>
                        <td class="acoes-linha">
                            <a href="${ctx}/acompanhamento?alunoId=${ap.alunoId}">Iniciar Acompanhamento</a> |
                            <a href="${ctx}/apontamento?acao=editar&id=${ap.id}">Editar</a> |
                            <a href="${ctx}/apontamento?acao=excluir&id=${ap.id}" class="link-excluir" data-confirmar="Excluir este apontamento?">Excluir</a>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty apontamentos}">
                    <tr><td colspan="6" class="vazio">Nenhum apontamento registrado.</td></tr>
                </c:if>
                </tbody>
            </table>
        </div>
    </div>
</div>

</body>
</html>
