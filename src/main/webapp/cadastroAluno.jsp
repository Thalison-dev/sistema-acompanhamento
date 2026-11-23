<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Cadastro de Aluno</title>
</head>
<body>

    <p>
        <a href="${pageContext.request.contextPath}/index.jsp">Início</a> |
        <a href="${pageContext.request.contextPath}/cadastroAluno.jsp">Cadastrar Aluno</a> |
        <a href="${pageContext.request.contextPath}/responsavel">Responsáveis Legais</a> |
        <a href="${pageContext.request.contextPath}/acompanhamento">Acompanhamentos</a>
    </p>
    <hr>

    <h2>Cadastrar Novo Aluno</h2>

    <form action="${pageContext.request.contextPath}/salvarAluno.jsp" method="post">
        <p>
            <label for="nome">Nome completo:</label><br>
            <input type="text" id="nome" name="nome" maxlength="100" required>
        </p>
        <p>
            <label for="email">E-mail:</label><br>
            <input type="email" id="email" name="email" maxlength="100" required>
        </p>
        <p>
            <label for="cpf">CPF:</label><br>
            <input type="text" id="cpf" name="cpf" maxlength="14" placeholder="000.000.000-00">
        </p>
        <p>
            <label for="telefone">Telefone:</label><br>
            <input type="text" id="telefone" name="telefone" maxlength="20">
        </p>
        <p>
            <label for="dataNascimento">Data de nascimento:</label><br>
            <input type="date" id="dataNascimento" name="dataNascimento">
        </p>
        <p>
            <label for="matricula">Matrícula:</label><br>
            <input type="text" id="matricula" name="matricula" maxlength="50">
        </p>
        <p>
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
        </p>
        <p>
            <label for="status">Status:</label><br>
            <select id="status" name="status">
                <option value="Ativo">Ativo</option>
                <option value="Inativo">Inativo</option>
                <option value="Transferido">Transferido</option>
                <option value="Concluído">Concluído</option>
            </select>
        </p>
        <p>
            <button type="submit">Cadastrar Aluno</button>
            <a href="${pageContext.request.contextPath}/index.jsp">Cancelar</a>
        </p>
    </form>

</body>
</html>