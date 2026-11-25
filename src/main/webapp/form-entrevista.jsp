<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Formulário de Entrevista</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>

    <p>
        <a href="<%= request.getContextPath() %>/painel.jsp">Painel</a> |
        <a href="<%= request.getContextPath() %>/entrevistas">Entrevistas</a>
    </p>
    <hr>

    <h2>Formulário de Entrevista</h2>

    <form action="<%= request.getContextPath() %>/entrevistas" method="post">
        <p>
            <label for="data">Data:</label><br>
            <input type="date" id="data" name="data" required>
        </p>
        <p>
            <label for="motivo">Motivo:</label><br>
            <input type="text" id="motivo" name="motivo" required>
        </p>
        <p>
            <label for="observacoes">Observações:</label><br>
            <textarea id="observacoes" name="observacoes" rows="3" cols="50"></textarea>
        </p>
        <button type="submit">Salvar</button>
    </form>

</body>
</html>