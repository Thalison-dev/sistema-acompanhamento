<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Lista de Acompanhamentos</title>
</head>
<body>
    <h1>Acompanhamentos Cadastrados</h1>

    <p><a href="${pageContext.request.contextPath}/novo-acompanhamento">Novo Acompanhamento</a></p>

    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
            <tr>
                <th>ID</th>
                <th>Data de Início</th>
                <th>Data de Fim</th>
                <th>Objetivo</th>
                <th>Status</th>
                <th>Ações</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="a" items="${acompanhamentos}">
                <tr>
                    <td>${a.id}</td>
                    <td>${a.dataInicio}</td>
                    <td>${a.dataFim}</td>
                    <td>${a.objetivo}</td>
                    <td>${a.status}</td>
                    <td>
                        <a href="${pageContext.request.contextPath}/editar?id=${a.id}">Editar</a> | 
                        <a href="${pageContext.request.contextPath}/deletar?id=${a.id}" onclick="return confirm('Tem certeza que deseja excluir?');">Excluir</a>
                    </td>
                </tr>
            </c:forEach>
            <c:if test="${empty acompanhamentos}">
                <tr>
                    <td colspan="6" style="text-align: center;">Nenhum acompanhamento encontrado.</td>
                </tr>
            </c:if>
        </tbody>
    </table>
</body>
</html>