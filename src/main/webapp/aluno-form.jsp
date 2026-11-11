<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Cadastro de Aluno</title>
</head>
<body>

    <h1>Formulário de Aluno</h1>

    <!-- Aponta para a Servlet mapeada como "/aluno" -->
    <form action="aluno" method="post">
        
        <!-- Diz à Servlet que a ação do doPost é "cadastrar" -->
        <input type="hidden" name="acao" value="cadastrar">

        <!-- Campos de Pessoa -->
        <div>
            <label for="nome">Nome:</label><br>
            <input type="text" id="nome" name="nome" placeholder="Digite o nome" required>
        </div>
        <br>
        
        <div>
            <label for="cpf">CPF:</label><br>
            <input type="text" id="cpf" name="cpf" placeholder="Digite o CPF" required>
        </div>
        <br>

        <div>
            <label for="email">E-mail:</label><br>
            <input type="email" id="email" name="email" placeholder="Digite o e-mail" required>
        </div>
        <br>

        <div>
            <label for="telefone">Telefone:</label><br>
            <input type="text" id="telefone" name="telefone" placeholder="Digite o telefone" required>
        </div>
        <br>

        <!-- Campos de Aluno -->
        <div>
            <label for="dataNascimento">Data de Nascimento:</label><br>
            <input type="date" id="dataNascimento" name="dataNascimento" required>
        </div>
        <br>

        <div>
            <label for="matricula">Matrícula:</label><br>
            <input type="text" id="matricula" name="matricula" placeholder="Digite a matrícula" required>
        </div>
        <br>

        <div>
            <label for="anoEscolar">Ano Escolar:</label><br>
            <input type="text" id="anoEscolar" name="anoEscolar" placeholder="Digite o ano escolar" required>
        </div>
        <br>

        <div>
            <label for="status">Status:</label><br>
            <select id="status" name="status">
                <option value="Ativo">Ativo</option>
                <option value="Inativo">Inativo</option>
                <option value="Transferido">Transferido</option>
                <option value="Concluído">Concluído</option>
            </select>
        </div>
        <br>

        <button type="submit">Cadastrar</button>

    </form>

</body>
</html>