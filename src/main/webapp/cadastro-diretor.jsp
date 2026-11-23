<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>${diretor != null ? 'Editar' : 'Cadastrar'} Diretor</title>
    <link rel="stylesheet" href="${ctx}/css/style.css">
</head>
<body>

<jsp:include page="/WEB-INF/includes/navbar.jsp"><jsp:param name="ativo" value="diretor" /></jsp:include>

<div class="container">
    <jsp:include page="/WEB-INF/includes/mensagens.jsp" />

    <h2>${diretor != null ? 'Editar Diretor' : 'Cadastrar Diretor'}</h2>

    <form action="${ctx}/diretor" method="post">
        <input type="hidden" name="acao" value="salvar">
        <input type="hidden" name="id" value="${diretor.id}">
        <div class="form-grid">
            <div class="form-group full">
                <label for="nome">Nome completo <span class="obrigatorio">*</span></label>
                <input type="text" id="nome" name="nome" maxlength="50" required placeholder="Digite o nome do diretor" value="<c:out value='${diretor.nome}'/>">
            </div>
            <div class="form-group">
                <label for="email">E-mail <span class="obrigatorio">*</span></label>
                <input type="email" id="email" name="email" maxlength="50" required value="<c:out value='${diretor.email}'/>">
            </div>
            <div class="form-group">
                <label for="cargo">Cargo <span class="obrigatorio">*</span></label>
                <input type="text" id="cargo" name="cargo" maxlength="100" required list="cargos" placeholder="Ex.: Diretor(a) escolar" value="<c:out value='${diretor.cargo}'/>">
                <datalist id="cargos">
                    <option value="Diretor(a) escolar">
                    <option value="Vice-diretor(a)">
                    <option value="Diretor(a) pedagógico(a)">
                    <option value="Coordenador(a) geral">
                </datalist>
            </div>
            <div class="form-group">
                <label for="escola">Escola / Unidade</label>
                <input type="text" id="escola" name="escola" maxlength="150" value="<c:out value='${diretor.escola}'/>">
            </div>
            <div class="form-group">
                <label for="formacao">Formação</label>
                <input type="text" id="formacao" name="formacao" maxlength="150" value="<c:out value='${diretor.formacao}'/>">
            </div>
            <div class="form-group">
                <label for="telefone">Telefone</label>
                <input type="text" id="telefone" name="telefone" maxlength="20" value="<c:out value='${diretor.telefone}'/>">
            </div>
            <div class="form-group">
                <label for="cpf">CPF</label>
                <input type="text" id="cpf" name="cpf" maxlength="14" value="<c:out value='${diretor.cpf}'/>">
            </div>
        </div>
        <div class="actions">
            <button type="submit" class="btn btn-success">${diretor != null ? 'Salvar Alterações' : 'Cadastrar Diretor'}</button>
            <a href="${ctx}/diretor?acao=lista" class="btn btn-secondary">Cancelar</a>
        </div>
    </form>
</div>

</body>
</html>
