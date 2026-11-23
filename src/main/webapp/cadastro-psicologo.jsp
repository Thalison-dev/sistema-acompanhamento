<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>${psicologo != null ? 'Editar' : 'Cadastrar'} Psicólogo</title>
    <link rel="stylesheet" href="${ctx}/css/style.css">
</head>
<body>

<jsp:include page="/WEB-INF/includes/navbar.jsp"><jsp:param name="ativo" value="psicologos" /></jsp:include>

<div class="container">
    <jsp:include page="/WEB-INF/includes/mensagens.jsp" />

    <h2>${psicologo != null ? 'Editar Psicólogo' : 'Cadastrar Psicólogo'}</h2>

    <form action="${ctx}/psicologo" method="post">
        <input type="hidden" name="id" value="${psicologo.id}">
        <div class="form-grid">
            <div class="form-group full">
                <label for="nome">Nome completo <span class="obrigatorio">*</span></label>
                <input type="text" id="nome" name="nome" maxlength="50" required value="<c:out value='${psicologo.nome}'/>">
            </div>
            <div class="form-group">
                <label for="email">E-mail <span class="obrigatorio">*</span></label>
                <input type="email" id="email" name="email" maxlength="50" required value="<c:out value='${psicologo.email}'/>">
            </div>
            <div class="form-group">
                <label for="especialidade">Especialidade</label>
                <input type="text" id="especialidade" name="especialidade" placeholder="Ex.: Psicologia escolar" value="<c:out value='${psicologo.especialidade}'/>">
            </div>
            <div class="form-group">
                <label for="telefone">Telefone</label>
                <input type="text" id="telefone" name="telefone" maxlength="20" value="<c:out value='${psicologo.telefone}'/>">
            </div>
            <div class="form-group">
                <label for="cpf">CPF</label>
                <input type="text" id="cpf" name="cpf" maxlength="14" value="<c:out value='${psicologo.cpf}'/>">
            </div>
        </div>
        <div class="actions">
            <button type="submit" class="btn btn-success">Salvar</button>
            <a href="${ctx}/psicologo" class="btn btn-secondary">Cancelar</a>
        </div>
    </form>
</div>

</body>
</html>
