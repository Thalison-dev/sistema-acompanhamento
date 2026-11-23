<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>${entrevista != null ? 'Editar' : 'Nova'} Entrevista</title>
    <link rel="stylesheet" href="${ctx}/css/style.css">
</head>
<body>

<jsp:include page="/WEB-INF/includes/navbar.jsp"><jsp:param name="ativo" value="entrevistas" /></jsp:include>

<div class="container">
    <jsp:include page="/WEB-INF/includes/mensagens.jsp" />

    <h2>${entrevista != null ? 'Editar Entrevista' : 'Nova Entrevista'}</h2>

    <c:if test="${empty psicologos}">
        <div class="alert-info">
            Nenhum psicólogo cadastrado. <a href="${ctx}/psicologo?acao=novo">Cadastre um psicólogo</a> para vinculá-lo à entrevista (opcional).
        </div>
    </c:if>

    <form action="${ctx}/${entrevista != null ? 'atualizar-entrevista' : 'inserir-entrevista'}" method="post">
        <input type="hidden" name="id" value="${entrevista.id}">

        <div class="form-grid">
            <div class="form-group">
                <label for="alunoId">Aluno <span class="obrigatorio">*</span></label>
                <select id="alunoId" name="alunoId" required>
                    <option value="">-- Selecione --</option>
                    <c:forEach var="a" items="${alunos}">
                        <option value="${a.id}" ${alunoSelecionado == a.id ? 'selected' : ''}>ID ${a.id} - <c:out value="${a.nome}" /></option>
                    </c:forEach>
                </select>
            </div>
            <div class="form-group">
                <label for="psicologoId">Psicólogo</label>
                <select id="psicologoId" name="psicologoId">
                    <option value="0">-- Selecione --</option>
                    <c:forEach var="p" items="${psicologos}">
                        <option value="${p.id}" ${psicologoSelecionado == p.id ? 'selected' : ''}><c:out value="${p.nome}" /></option>
                    </c:forEach>
                </select>
            </div>
            <div class="form-group">
                <label for="data">Data</label>
                <input type="date" id="data" name="data" value="${entrevista.data}">
            </div>
            <div class="form-group form-check" style="margin-top:28px">
                <input type="checkbox" id="agendada" name="agendada" ${entrevista == null || entrevista.agendada ? 'checked' : ''}>
                <label for="agendada">Ainda está agendada (não realizada)</label>
            </div>
            <div class="form-group full">
                <label for="motivo">Motivo <span class="obrigatorio">*</span></label>
                <input type="text" id="motivo" name="motivo" maxlength="255" required value="<c:out value='${entrevista.motivo}'/>">
            </div>
            <div class="form-group full">
                <label for="observacoes">Observações</label>
                <textarea id="observacoes" name="observacoes" rows="4"><c:out value="${entrevista.observacoes}" /></textarea>
            </div>
        </div>

        <div class="actions">
            <button type="submit" class="btn btn-success">${entrevista != null ? 'Atualizar' : 'Cadastrar'}</button>
            <a href="${ctx}/listar-entrevistas" class="btn btn-secondary">Cancelar</a>
        </div>
    </form>
</div>

</body>
</html>
