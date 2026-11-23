
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Apontamentos</title>
</head>

<body>

    <h1>Gerenciamento de Apontamentos</h1>

    <h2>Registrar apontamento</h2>

    <form action="${pageContext.request.contextPath}/apontamento"
          method="post">

        <input type="hidden" name="acao" value="salvar">

        <label>ID do aluno:</label>
        <input type="number" name="idPessoa" required>
        <br><br>

        <label>Data:</label>
        <input type="date" name="dataInicio" required>
        <br><br>

        <label>Profissional responsável:</label>
        <input type="text" name="nomeProfessor" required>
        <br><br>

        <label>Motivo:</label>
        <br>
        <textarea name="motivo" rows="5" cols="40"
                  required></textarea>
        <br><br>

        <button type="submit">Registrar apontamento</button>

    </form>

    <hr>

    <h2>Apontamentos registrados</h2>

    <table border="1" cellpadding="8">
        <thead>
            <tr>
                <th>ID do aluno</th>
                <th>Data</th>
                <th>Profissional</th>
                <th>Motivo</th>
            </tr>
        </thead>

        <tbody>
            <c:forEach var="apontamento" items="${apontamentos}">
                <tr>
                    <td><c:out value="${apontamento.idPessoa}"/></td>
                    <td><c:out value="${apontamento.dataInicio}"/></td>
                    <td><c:out value="${apontamento.nomeProfessor}"/></td>
                    <td><c:out value="${apontamento.motivo}"/></td>
                </tr>
            </c:forEach>

            <c:if test="${empty apontamentos}">
                <tr>
                    <td colspan="4">Nenhum apontamento registrado.</td>
                </tr>
            </c:if>
        </tbody>
    </table>

</body>
</html>
