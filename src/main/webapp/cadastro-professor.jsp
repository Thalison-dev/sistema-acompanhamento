<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>${professor != null ? 'Editar' : 'Cadastrar'} Professor</title>
    <link rel="stylesheet" href="${ctx}/css/style.css">
</head>
<body>

<jsp:include page="/WEB-INF/includes/navbar.jsp"><jsp:param name="ativo" value="professores" /></jsp:include>

<div class="container">
    <jsp:include page="/WEB-INF/includes/mensagens.jsp" />

    <h2>${professor != null ? 'Editar Professor' : 'Cadastrar Professor'}</h2>

    <form action="${ctx}/professor" method="post">
        <input type="hidden" name="acao" value="salvar">
        <input type="hidden" name="id" value="${professor.id}">
        <div class="form-grid">
            <div class="form-group full">
                <label for="nome">Nome completo <span class="obrigatorio">*</span></label>
                <input type="text" id="nome" name="nome" maxlength="50" required value="<c:out value='${professor.nome}'/>">
            </div>
            <div class="form-group">
                <label for="email">E-mail <span class="obrigatorio">*</span></label>
                <input type="email" id="email" name="email" maxlength="50" required value="<c:out value='${professor.email}'/>">
            </div>
            <div class="form-group">
                <label for="matricula">Matrícula</label>
                <input type="text" id="matricula" name="matricula" maxlength="50" value="<c:out value='${professor.matricula}'/>">
            </div>
            <div class="form-group">
                <label for="especialidade">Especialidade / Disciplina</label>
                <input type="text" id="especialidade" name="especialidade" maxlength="100" value="<c:out value='${professor.especialidade}'/>">
            </div>
            <div class="form-group">
                <label for="telefone">Telefone</label>
                <input type="text" id="telefone" name="telefone" maxlength="20" value="<c:out value='${professor.telefone}'/>">
            </div>
            <div class="form-group">
                <label for="cpf">CPF</label>
                <input type="text" id="cpf" name="cpf" maxlength="14" value="<c:out value='${professor.cpf}'/>">
            </div>
        </div>
        <div class="actions">
            <button type="submit" class="btn btn-success">Salvar</button>
            <a href="${ctx}/professor" class="btn btn-secondary">Cancelar</a>
        </div>
    </form>
</div>

</body>
</html>
