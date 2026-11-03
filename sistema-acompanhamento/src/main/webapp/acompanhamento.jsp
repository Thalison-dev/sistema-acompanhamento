<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Cadastro de Acompanhamento</title>
</head>
<body>
    <h1>Formulário de Acompanhamento</h1>

    <form action="salvarAcompanhamento.jsp" method="post">
        <div>
            <label for="id">ID:</label><br>
            <input type="text" id="id" name="id" placeholder="Digite o ID" required>
        </div>
        <br>
        <div>
            <label for="dataInicio">Data de Início:</label><br>
            <input type="date" id="dataInicio" name="dataInicio" required>
        </div>
        <br>
        <div>
            <label for="dataFim">Data de Fim:</label><br>
            <input type="date" id="dataFim" name="dataFim">
        </div>
        <br>
        <div>
            <label for="objetivo">Objetivo:</label><br>
            <textarea id="objetivo" name="objetivo" rows="4" cols="40" placeholder="Descreva o objetivo" required></textarea>
        </div>
        <br>
        <div>
            <label for="status">Status:</label><br>
            <select id="status" name="status">
                <option value="Pendente">Pendente</option>
                <option value="Em Andamento">Em Andamento</option>
                <option value="Concluído">Concluído</option>
                <option value="Cancelado">Cancelado</option>
            </select>
        </div>
        <br>
        <button type="submit">Cadastrar</button>
    </form>
</body>
</html>