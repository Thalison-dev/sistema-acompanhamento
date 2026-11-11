<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${acompanhamento != null ? 'Editar' : 'Novo'} Acompanhamento</title>
</head>
<body>
    <h1>${acompanhamento != null ? 'Editar' : 'Novo'} Acompanhamento</h1>

    <form action="${pageContext.request.contextPath}/${acompanhamento != null ? 'atualizar' : 'inserir'}" method="post">
        
        <c:if test="${acompanhamento != null}">
            <input type="hidden" name="id" value="${acompanhamento.id}" />
        </c:if>

        <div>
            <label for="dataInicio">Data de Início:</label><br>
            <input type="date" id="dataInicio" name="dataInicio" value="${acompanhamento.dataInicio}" required>
        </div>
        <br>
        <div>
            <label for="dataFim">Data de Fim:</label><br>
            <input type="date" id="dataFim" name="dataFim" value="${acompanhamento.dataFim}">
        </div>
        <br>
        <div>
            <label for="objetivo">Objetivo:</label><br>
            <textarea id="objetivo" name="objetivo" rows="4" cols="40" placeholder="Descreva o objetivo" required>${acompanhamento.objetivo}</textarea>
        </div>
        <br>
        <div>
            <label for="status">Status:</label><br>
            <select id="status" name="status" required>
                <option value="Pendente" ${acompanhamento.status == 'Pendente' ? 'selected' : ''}>Pendente</option>
                <option value="Em Andamento" ${acompanhamento.status == 'Em Andamento' ? 'selected' : ''}>Em Andamento</option>
                <option value="Concluído" ${acompanhamento.status == 'Concluído' ? 'selected' : ''}>Concluído</option>
                <option value="Cancelado" ${acompanhamento.status == 'Cancelado' ? 'selected' : ''}>Cancelado</option>
            </select>
        </div>
        <br>
        <button type="submit">${acompanhamento != null ? 'Atualizar' : 'Cadastrar'}</button>
        <a href="${pageContext.request.contextPath}/listar">Cancelar</a>
    </form>
</body>
</html>