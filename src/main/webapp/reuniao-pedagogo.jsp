<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Marcar Reunião - Pedagogo</title>
    <link rel="stylesheet" href="${ctx}/css/style.css">
</head>
<body>

<jsp:include page="/WEB-INF/includes/navbar.jsp"><jsp:param name="ativo" value="pedagogos" /></jsp:include>

<div class="container">
    <jsp:include page="/WEB-INF/includes/mensagens.jsp" />

    <h2>Marcar Reunião</h2>

    <form action="${ctx}/pedagogo" method="post">
        <input type="hidden" name="acao" value="agendarReuniao">

        <div class="form-grid">
            <div class="form-group">
                <label for="pedagogoId">Pedagogo responsável</label>
                <select id="pedagogoId" name="pedagogoId">
                    <c:forEach var="p" items="${pedagogos}">
                        <option value="${p.idPedagogo}"><c:out value="${p.pessoa}" /></option>
                    </c:forEach>
                    <c:if test="${empty pedagogos}"><option value="0">-- Nenhum pedagogo cadastrado --</option></c:if>
                </select>
            </div>
            <div class="form-group">
                <label for="publicoAlvo">Reunião com <span class="obrigatorio">*</span></label>
                <select id="publicoAlvo" name="publicoAlvo" required>
                    <option value="Psicólogo">Psicólogo</option>
                    <option value="Responsável Legal">Responsável Legal</option>
                    <option value="Diretor">Diretor</option>
                    <option value="Professor">Professor</option>
                    <option value="Aluno">Aluno</option>
                </select>
            </div>
            <div class="form-group full">
                <label for="alunoId">Aluno Relacionado</label>
                <select id="alunoId" name="alunoId">
                    <option value="0">-- Selecione um aluno (opcional para reunião com Diretor) --</option>
                    <c:forEach var="a" items="${alunos}">
                        <option value="${a.id}" ${param.alunoId == a.id ? 'selected' : ''}>ID ${a.id} - <c:out value="${a.nome}" /></option>
                    </c:forEach>
                </select>
            </div>
            <div class="form-group">
                <label for="dataReuniao">Data da Reunião <span class="obrigatorio">*</span></label>
                <input type="date" id="dataReuniao" name="dataReuniao" required>
            </div>
            <div class="form-group full">
                <label for="motivo">Motivo / Pauta da Reunião <span class="obrigatorio">*</span></label>
                <textarea id="motivo" name="motivo" rows="4" required placeholder="Descreva o assunto da reunião"></textarea>
            </div>
        </div>

        <div class="actions">
            <button type="submit" class="btn btn-success">Confirmar Agendamento</button>
            <a href="${ctx}/pedagogo" class="btn btn-secondary">Cancelar</a>
        </div>
    </form>
</div>

</body>
</html>
