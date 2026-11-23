<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>${responsavel != null ? 'Editar' : 'Cadastrar'} Responsável</title>
    <link rel="stylesheet" href="${ctx}/css/style.css">
</head>
<body>

<jsp:include page="/WEB-INF/includes/navbar.jsp"><jsp:param name="ativo" value="responsaveis" /></jsp:include>

<div class="container">
    <jsp:include page="/WEB-INF/includes/mensagens.jsp" />

    <h2>${responsavel != null ? 'Editar Responsável Legal' : 'Cadastrar Responsável Legal'}</h2>

    <form action="${ctx}/responsavel" method="post">
        <input type="hidden" name="id" value="${responsavel.id}">
        <div class="form-grid">
            <div class="form-group full">
                <label for="nome">Nome completo <span class="obrigatorio">*</span></label>
                <input type="text" id="nome" name="nome" maxlength="50" required value="<c:out value='${responsavel.nome}'/>">
            </div>
            <div class="form-group">
                <label for="email">E-mail <span class="obrigatorio">*</span></label>
                <input type="email" id="email" name="email" maxlength="50" required value="<c:out value='${responsavel.email}'/>">
            </div>
            <div class="form-group">
                <label for="parentesco">Parentesco</label>
                <select id="parentesco" name="parentesco">
                    <c:forEach var="p" items="Mãe,Pai,Avó,Avô,Tia,Tio,Irmã,Irmão,Tutor legal,Outro">
                        <option value="${p}" ${responsavel.parentesco == p ? 'selected' : ''}>${p}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="form-group">
                <label for="telefone">Telefone</label>
                <input type="text" id="telefone" name="telefone" maxlength="20" value="<c:out value='${responsavel.telefone}'/>">
            </div>
            <div class="form-group">
                <label for="cpf">CPF</label>
                <input type="text" id="cpf" name="cpf" maxlength="14" value="<c:out value='${responsavel.cpf}'/>">
            </div>
        </div>
        <div class="actions">
            <button type="submit" class="btn btn-success">Salvar</button>
            <a href="${ctx}/responsavel" class="btn btn-secondary">Cancelar</a>
        </div>
    </form>
</div>

</body>
</html>
