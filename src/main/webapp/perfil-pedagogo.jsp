<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="atual" value="${empty pedagogos ? null : pedagogos[0]}" />
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Meu Perfil de Pedagogo</title>
    <link rel="stylesheet" href="${ctx}/css/style.css">
</head>
<body>

<jsp:include page="/WEB-INF/includes/navbar.jsp"><jsp:param name="ativo" value="pedagogos" /></jsp:include>

<div class="container">
    <jsp:include page="/WEB-INF/includes/mensagens.jsp" />

    <h2>Meu Perfil de Pedagogo</h2>
    <p class="texto-ajuda">
        <c:choose>
            <c:when test="${atual != null}">Você está identificado como <strong><c:out value="${atual.pessoa}" /></strong> (ID ${atual.idPedagogo}).</c:when>
            <c:otherwise>Nenhum pedagogo cadastrado ainda. Preencha seus dados para começar.</c:otherwise>
        </c:choose>
    </p>

    <form action="${ctx}/pedagogo" method="post">
        <input type="hidden" name="acao" value="salvarPerfil">
        <input type="hidden" name="idPedagogo" value="${atual.idPedagogo}">

        <div class="form-grid">
            <div class="form-group full">
                <label for="pessoa">Seu Nome <span class="obrigatorio">*</span></label>
                <input type="text" id="pessoa" name="pessoa" maxlength="50" required placeholder="Digite seu nome" value="<c:out value='${atual.pessoa}'/>">
            </div>
            <div class="form-group">
                <label for="email">E-mail <span class="obrigatorio">*</span></label>
                <input type="email" id="email" name="email" maxlength="50" required value="<c:out value='${atual.email}'/>">
            </div>
            <div class="form-group">
                <label for="especialidade">Sua Especialidade <span class="obrigatorio">*</span></label>
                <input type="text" id="especialidade" name="especialidade" maxlength="100" required placeholder="Ex: Psicopedagogia" value="<c:out value='${atual.especialidade}'/>">
            </div>
            <div class="form-group">
                <label for="telefone">Telefone</label>
                <input type="text" id="telefone" name="telefone" maxlength="20" value="<c:out value='${atual.telefone}'/>">
            </div>
            <div class="form-group">
                <label for="cpf">CPF</label>
                <input type="text" id="cpf" name="cpf" maxlength="14" value="<c:out value='${atual.cpf}'/>">
            </div>
        </div>

        <div class="actions">
            <button type="submit" class="btn btn-success">Salvar Perfil</button>
            <a href="${ctx}/pedagogo" class="btn btn-secondary">Cancelar</a>
        </div>
    </form>
</div>

</body>
</html>
