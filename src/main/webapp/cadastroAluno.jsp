<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>${aluno != null ? 'Editar' : 'Cadastro de'} Aluno</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<jsp:include page="/WEB-INF/includes/navbar.jsp"><jsp:param name="ativo" value="alunos" /></jsp:include>

<div class="container">
    <jsp:include page="/WEB-INF/includes/mensagens.jsp" />

    <h2>${aluno != null ? 'Editar Aluno' : 'Cadastrar Novo Aluno'}</h2>

    <form action="${pageContext.request.contextPath}/aluno" method="post">
        <input type="hidden" name="id" value="${aluno.id}">

        <div class="form-grid">
            <div class="form-group full">
                <label for="nome">Nome completo <span class="obrigatorio">*</span></label>
                <input type="text" id="nome" name="nome" maxlength="50" required value="<c:out value='${aluno.nome}'/>">
            </div>
            <div class="form-group">
                <label for="email">E-mail <span class="obrigatorio">*</span></label>
                <input type="email" id="email" name="email" maxlength="50" required value="<c:out value='${aluno.email}'/>">
            </div>
            <div class="form-group">
                <label for="cpf">CPF</label>
                <input type="text" id="cpf" name="cpf" maxlength="14" placeholder="000.000.000-00" value="<c:out value='${aluno.cpf}'/>">
            </div>
            <div class="form-group">
                <label for="telefone">Telefone</label>
                <input type="text" id="telefone" name="telefone" maxlength="20" value="<c:out value='${aluno.telefone}'/>">
            </div>
            <div class="form-group">
                <label for="dataNascimento">Data de nascimento</label>
                <input type="date" id="dataNascimento" name="dataNascimento" value="${aluno.dataNascimento}">
            </div>
            <div class="form-group">
                <label for="matricula">Matrícula</label>
                <input type="text" id="matricula" name="matricula" maxlength="50" value="<c:out value='${aluno.matricula}'/>">
            </div>
            <div class="form-group">
                <label for="anoEscolar">Ano escolar</label>
                <select id="anoEscolar" name="anoEscolar">
                    <option value="">Selecione</option>
                    <c:forEach var="ano" items="1º ano,2º ano,3º ano,4º ano,5º ano,6º ano,7º ano,8º ano,9º ano,1ª série EM,2ª série EM,3ª série EM">
                        <option value="${ano}" ${aluno.anoEscolar == ano ? 'selected' : ''}>${ano}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="form-group">
                <label for="status">Status</label>
                <select id="status" name="status">
                    <c:forEach var="s" items="Ativo,Inativo,Transferido,Concluído">
                        <option value="${s}" ${aluno.status == s ? 'selected' : ''}>${s}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="form-group">
                <label for="responsavelId">Responsável legal</label>
                <select id="responsavelId" name="responsavelId">
                    <option value="0">-- Nenhum --</option>
                    <c:forEach var="r" items="${responsaveis}">
                        <option value="${r.id}" ${aluno.responsavelId == r.id ? 'selected' : ''}>
                            <c:out value="${r.nome}" /> (<c:out value="${r.parentesco}" />)
                        </option>
                    </c:forEach>
                </select>
                <small class="muted">Não está na lista? <a href="${pageContext.request.contextPath}/responsavel?acao=novo">Cadastre o responsável</a>.</small>
            </div>
        </div>

        <div class="actions">
            <button type="submit" class="btn btn-success">${aluno != null ? 'Salvar Alterações' : 'Cadastrar Aluno'}</button>
            <a href="${pageContext.request.contextPath}/aluno" class="btn btn-secondary">Cancelar</a>
        </div>
    </form>
</div>

</body>
</html>
