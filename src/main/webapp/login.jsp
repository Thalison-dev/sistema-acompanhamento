<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Login - Apoio ao Aluno Neurodivergente</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>

    <h1>Sistema de Apoio para Alunos Neurodivergentes</h1>
    <p>Informe seu e-mail institucional e senha para acessar conforme seu perfil.</p>

    <% if (request.getParameter("erro") != null) { %>
        <p><strong>Erro:</strong> <%= request.getParameter("erro") %></p>
    <% } %>

    <form action="<%= request.getContextPath() %>/login" method="post">
        <p>
            <label for="email">E-mail:</label><br>
            <input type="email" id="email" name="email" required>
        </p>
        <p>
            <label for="senha">Senha:</label><br>
            <input type="password" id="senha" name="senha" required>
        </p>
        <p>
            <button type="submit">Entrar</button>
        </p>
    </form>

    <hr>
    <h3>Contas de Teste (Senha: 123456)</h3>
    <ul>
        <li><strong>Escola (Admin):</strong> admin@escola.com</li>
        <li><strong>Secretaria:</strong> secretaria@escola.com</li>
        <li><strong>Psicólogo:</strong> psicologo@escola.com</li>
        <li><strong>Pedagogo:</strong> pedagogo@escola.com</li>
        <li><strong>Professor:</strong> professor@escola.com</li>
    </ul>

</body>
</html>