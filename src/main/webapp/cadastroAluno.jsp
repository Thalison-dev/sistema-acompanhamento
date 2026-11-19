<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Cadastro de Aluno</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 0; background-color: #f4f6f9; color: #333; }
        .navbar { background-color: #2c3e50; padding: 14px 30px; display: flex; gap: 20px; align-items: center; }
        .navbar a { color: #ecf0f1; text-decoration: none; font-weight: bold; font-size: 14px; padding: 6px 10px; border-radius: 4px; }
        .navbar a:hover, .navbar a.active { background-color: #007bff; color: #fff; }
        .container { max-width: 650px; margin: 40px auto; background: #fff; padding: 25px; border-radius: 8px; box-shadow: 0 2px 6px rgba(0,0,0,0.1); }
        h2 { margin-top: 0; color: #2c3e50; }
        .form-grid { display: grid; grid-template-columns: 1fr 1fr; gap: 15px; }
        .form-group { margin-bottom: 15px; }
        .form-group.full { grid-column: 1 / -1; }
        label { display: block; margin-bottom: 5px; font-weight: bold; }
        input[type="text"], input[type="email"], input[type="date"], select {
            width: 100%; padding: 10px; border: 1px solid #ccc; border-radius: 4px; box-sizing: border-box;
        }
        .actions { margin-top: 20px; display: flex; gap: 10px; flex-wrap: wrap; }
        .btn { padding: 10px 16px; border: none; border-radius: 4px; font-size: 14px; font-weight: bold; cursor: pointer; text-decoration: none; display: inline-block; }
        .btn-success { background-color: #28a745; color: #fff; }
        .btn-success:hover { background-color: #218838; }
        .btn-primary { background-color: #007bff; color: #fff; }
        .btn-primary:hover { background-color: #0056b3; }
    </style>
</head>
<body>

<div class="navbar">
    <a href="${pageContext.request.contextPath}/index.jsp">Início</a>
    <a href="${pageContext.request.contextPath}/pedagogo">Pedagogos</a>
    <a href="${pageContext.request.contextPath}/cadastroAluno.jsp" class="active">Alunos</a>
    <a href="${pageContext.request.contextPath}/acompanhamento.jsp">Acompanhamentos</a>
</div>

<div class="container">
    <h2>Cadastro de Aluno</h2>

    <form action="${pageContext.request.contextPath}/salvarAluno.jsp" method="post">
        <div class="form-grid">
            <div class="form-group full">
                <label for="nome">Nome completo:</label>
                <input type="text" id="nome" name="nome" maxlength="50" required placeholder="Digite o nome completo">
            </div>

            <div class="form-group">
                <label for="email">E-mail:</label>
                <input type="email" id="email" name="email" maxlength="50" required placeholder="aluno@email.com">
            </div>

            <div class="form-group">
                <label for="cpf">CPF:</label>
                <input type="text" id="cpf" name="cpf" maxlength="14" placeholder="000.000.000-00">
            </div>

            <div class="form-group">
                <label for="telefone">Telefone:</label>
                <input type="text" id="telefone" name="telefone" maxlength="20" placeholder="(00) 00000-0000">
            </div>

            <div class="form-group">
                <label for="dataNascimento">Data de nascimento:</label>
                <input type="date" id="dataNascimento" name="dataNascimento">
            </div>

            <div class="form-group">
                <label for="matricula">Matrícula:</label>
                <input type="text" id="matricula" name="matricula" maxlength="50" placeholder="Número de matrícula">
            </div>

            <div class="form-group">
                <label for="anoEscolar">Ano escolar:</label>
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

            <div class="form-group full">
                <label for="status">Status:</label>
                <select id="status" name="status">
                    <option value="Ativo">Ativo</option>
                    <option value="Inativo">Inativo</option>
                    <option value="Transferido">Transferido</option>
                    <option value="Concluído">Concluído</option>
                </select>
            </div>
        </div>

        <div class="actions">
            <button type="submit" class="btn btn-success">Cadastrar Aluno</button>
            <a href="${pageContext.request.contextPath}/acompanhamento.jsp" class="btn btn-primary">Ver Alunos / Acompanhamento</a>
        </div>
    </form>
</div>

</body>
</html>