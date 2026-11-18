
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Cadastro de Aluno</title>
</head>
<body>

    <h1>Cadastro de Aluno</h1>

    <form action="salvarAluno.jsp" method="post">

        <div>
            <label for="nome">Nome completo:</label><br>
            <input type="text" id="nome" name="nome"
                   maxlength="50" required>
        </div>

        <br>

        <div>
            <label for="email">E-mail:</label><br>
            <input type="email" id="email" name="email"
                   maxlength="50" required>
        </div>

        <br>

        <div>
            <label for="cpf">CPF:</label><br>
            <input type="text" id="cpf" name="cpf"
                   maxlength="14" placeholder="000.000.000-00">
        </div>

        <br>

        <div>
            <label for="telefone">Telefone:</label><br>
            <input type="text" id="telefone" name="telefone"
                   maxlength="20">
        </div>

        <br>

        <div>
            <label for="dataNascimento">Data de nascimento:</label><br>
            <input type="date" id="dataNascimento"
                   name="dataNascimento">
        </div>

        <br>

        <div>
            <label for="matricula">Matrícula:</label><br>
            <input type="text" id="matricula" name="matricula"
                   maxlength="50">
        </div>

        <br>

        <div>
            <label for="anoEscolar">Ano escolar:</label><br>
            <select id="anoEscolar" name="anoEscolar">
                <option value="">Selecione</option>
                <option value="1º ano">1º ano</option>
                <option value="2º ano">2º ano</option>
                <option value="3º ano">3º ano</option>
                <option value="4º ano">4º ano</option>
                <option value="5º ano">5º ano</option>
                <option value="6º ano">6º ano</option>
                <option value="7º ano">7º ano</option>
                <option value="8º ano">8º ano</option>
                <option value="9º ano">9º ano</option>
            </select>
        </div>

        <br>

        <div>
            <label for="status">Status:</label><br>
            <select id="status" name="status">
                <option value="Ativo">Ativo</option>
                <option value="Inativo">Inativo</option>
            </select>
        </div>

        <br>

        <button type="submit">Cadastrar Aluno</button>

    </form>

    <br>

    <a href="acompanhamento.jsp">Ir para acompanhamento</a>

</body>
</html>
