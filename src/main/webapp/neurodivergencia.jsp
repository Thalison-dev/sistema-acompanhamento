<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="alunoForm" value="${registro != null ? registro.alunoId : alunoSelecionado}" />
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Neurodivergência</title>
    <link rel="stylesheet" href="${ctx}/css/style.css">
</head>
<body>

<jsp:include page="/WEB-INF/includes/navbar.jsp"><jsp:param name="ativo" value="neuro" /></jsp:include>

<div class="container container-wide">
    <jsp:include page="/WEB-INF/includes/mensagens.jsp" />

    <h2>${registro != null ? 'Editar Registro de Neurodivergência' : 'Registrar Neurodivergência'}</h2>

    <form action="${ctx}/neurodivergencia" method="post">
        <input type="hidden" name="id" value="${registro.id}">
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
                <label for="tipo">Tipo <span class="obrigatorio">*</span></label>
                <input type="text" id="tipo" name="tipo" list="tipos" maxlength="100" required value="<c:out value='${registro.tipo}'/>" placeholder="Ex.: TEA, TDAH, Dislexia">
                <datalist id="tipos">
                    <option value="TEA (Transtorno do Espectro Autista)">
                    <option value="TDAH">
                    <option value="Dislexia">
                    <option value="Discalculia">
                    <option value="Altas Habilidades / Superdotação">
                    <option value="Síndrome de Down">
                    <option value="TOD (Transtorno Opositor Desafiador)">
                </datalist>
            </div>
            <div class="form-group">
                <label for="dataRegistro">Data do registro</label>
                <input type="date" id="dataRegistro" name="dataRegistro" value="${registro.dataRegistro}">
            </div>
            <div class="form-group full">
                <label for="descricao">Descrição</label>
                <textarea id="descricao" name="descricao" rows="3" placeholder="Laudo, diagnóstico, características"><c:out value="${registro.descricao}" /></textarea>
            </div>
            <div class="form-group full">
                <label for="observacoes">Observações para o acompanhamento</label>
                <textarea id="observacoes" name="observacoes" rows="3"><c:out value="${registro.observacoes}" /></textarea>
            </div>
        </div>
        <div class="actions">
            <button type="submit" class="btn btn-success">${registro != null ? 'Salvar Alterações' : 'Registrar'}</button>
            <c:if test="${registro != null}"><a href="${ctx}/neurodivergencia" class="btn btn-secondary">Cancelar</a></c:if>
        </div>
    </form>

    <div class="section">
        <div class="section-title"><h3>Registros</h3></div>
        <div class="table-wrap">
            <table class="tabela">
                <thead><tr><th>ID</th><th>Aluno</th><th>Tipo</th><th>Descrição</th><th>Data</th><th>Ações</th></tr></thead>
                <tbody>
                <c:forEach var="n" items="${registros}">
                    <tr>
                        <td>${n.id}</td>
                        <td><a href="${ctx}/aluno?acao=historico&id=${n.alunoId}"><c:out value="${n.nomeAluno}" /></a></td>
                        <td><strong><c:out value="${n.tipo}" /></strong></td>
                        <td><c:out value="${n.descricao}" /></td>
                        <td class="data">${n.dataRegistro}</td>
                        <td class="acoes-linha">
                            <a href="${ctx}/acompanhamento?alunoId=${n.alunoId}">Acompanhar</a> |
                            <a href="${ctx}/neurodivergencia?acao=editar&id=${n.id}">Editar</a> |
                            <a href="${ctx}/neurodivergencia?acao=excluir&id=${n.id}" class="link-excluir" data-confirmar="Excluir este registro?">Excluir</a>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty registros}">
                    <tr><td colspan="6" class="vazio">Nenhum registro de neurodivergência.</td></tr>
                </c:if>
                </tbody>
            </table>
        </div>
    </div>
</div>

</body>
</html>
