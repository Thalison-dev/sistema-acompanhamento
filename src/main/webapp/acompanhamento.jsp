<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>${acompanhamento != null ? 'Editar' : 'Novo'} Acompanhamento</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<jsp:include page="/WEB-INF/includes/navbar.jsp"><jsp:param name="ativo" value="acompanhamentos" /></jsp:include>

<div class="container">
    <jsp:include page="/WEB-INF/includes/mensagens.jsp" />

    <h2>${acompanhamento != null ? 'Editar' : 'Novo'} Acompanhamento</h2>

    <c:if test="${empty alunos}">
        <div class="alert-info">
            Nenhum aluno cadastrado ainda. <a href="${pageContext.request.contextPath}/aluno?acao=novo">Cadastre um aluno</a> para iniciar um acompanhamento.
        </div>
    </c:if>

    <form action="${pageContext.request.contextPath}/acompanhamento" method="post">
        <input type="hidden" name="id" value="${acompanhamento.id}" />

        <div class="form-grid">
            <div class="form-group full">
                <label for="alunoId">Aluno <span class="obrigatorio">*</span></label>
                <select id="alunoId" name="alunoId" required>
                    <option value="">-- Selecione o aluno --</option>
                    <c:forEach var="a" items="${alunos}">
                        <option value="${a.id}" ${alunoSelecionado == a.id ? 'selected' : ''}>
                            ID ${a.id} - <c:out value="${a.nome}" /><c:if test="${not empty a.matricula}"> (matrícula <c:out value="${a.matricula}" />)</c:if>
                        </option>
                    </c:forEach>
                </select>
            </div>

            <div class="form-group">
                <label for="dataInicio">Data de Início <span class="obrigatorio">*</span></label>
                <input type="date" id="dataInicio" name="dataInicio" value="${acompanhamento.dataInicio}" required>
            </div>

            <div class="form-group">
                <label for="dataFim">Data de Fim</label>
                <input type="date" id="dataFim" name="dataFim" value="${acompanhamento.dataFim}">
            </div>

            <div class="form-group full">
                <label for="objetivo">Objetivo <span class="obrigatorio">*</span></label>
                <textarea id="objetivo" name="objetivo" rows="4" placeholder="Descreva o objetivo" required><c:out value="${acompanhamento.objetivo}" /></textarea>
            </div>

            <div class="form-group full">
                <label for="observacao">Observações</label>
                <textarea id="observacao" name="observacao" rows="3" placeholder="Estratégias, combinados com a família, etc."><c:out value="${acompanhamento.observacao}" /></textarea>
            </div>

            <div class="form-group">
                <label for="status">Status <span class="obrigatorio">*</span></label>
                <select id="status" name="status" required>
                    <c:forEach var="s" items="Pendente,Em Andamento,Concluído,Cancelado">
                        <option value="${s}" ${acompanhamento.status == s ? 'selected' : ''}>${s}</option>
                    </c:forEach>
                </select>
            </div>
        </div>

        <div class="actions">
            <button type="submit" class="btn btn-success">${acompanhamento != null ? 'Atualizar' : 'Cadastrar'}</button>
            <a href="${pageContext.request.contextPath}/acompanhamento" class="btn btn-secondary">Cancelar</a>
            <c:if test="${acompanhamento != null}">
                <a href="${pageContext.request.contextPath}/aluno?acao=historico&id=${acompanhamento.alunoId}" class="btn btn-primary">Histórico do Aluno</a>
            </c:if>
        </div>
    </form>
</div>

</body>
</html>
