<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="br.com.thalison.sistema_acompanhamento_aluno.Aluno" %>
<%@ page import="br.com.thalison.sistema_acompanhamento_aluno.AlunoDAO" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Resultado do Cadastro de Aluno</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 0; background-color: #f4f6f9; color: #333; }
        .navbar { background-color: #2c3e50; padding: 14px 30px; display: flex; gap: 20px; align-items: center; }
        .navbar a { color: #ecf0f1; text-decoration: none; font-weight: bold; font-size: 14px; padding: 6px 10px; border-radius: 4px; }
        .navbar a:hover, .navbar a.active { background-color: #007bff; color: #fff; }
        .container { max-width: 600px; margin: 40px auto; background: #fff; padding: 25px; border-radius: 8px; box-shadow: 0 2px 6px rgba(0,0,0,0.1); }
        .alert-success { background-color: #d4edda; color: #155724; padding: 15px; border-radius: 4px; border: 1px solid #c3e6cb; }
        .alert-error { background-color: #f8d7da; color: #721c24; padding: 15px; border-radius: 4px; border: 1px solid #f5c6cb; }
        .actions { margin-top: 20px; display: flex; gap: 10px; flex-wrap: wrap; }
        .btn { padding: 10px 16px; border-radius: 4px; font-size: 14px; font-weight: bold; text-decoration: none; display: inline-block; }
        .btn-primary { background-color: #007bff; color: #fff; }
        .btn-success { background-color: #28a745; color: #fff; }
        .btn-secondary { background-color: #6c757d; color: #fff; }
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
<%
    request.setCharacterEncoding("UTF-8");

    try {
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String cpf = request.getParameter("cpf");
        String telefone = request.getParameter("telefone");
        String dataNascimento = request.getParameter("dataNascimento");
        String matricula = request.getParameter("matricula");
        String anoEscolar = request.getParameter("anoEscolar");
        String status = request.getParameter("status");

        if (nome == null || nome.trim().isEmpty() || email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome e e-mail são obrigatórios.");
        }

        Aluno aluno = new Aluno();
        aluno.setNome(nome.trim());
        aluno.setEmail(email.trim());
        aluno.setCpf(cpf);
        aluno.setTelefone(telefone);
        aluno.setDataNascimento(dataNascimento);
        aluno.setMatricula(matricula);
        aluno.setAnoEscolar(anoEscolar);
        aluno.setStatus(status);

        AlunoDAO dao = new AlunoDAO();

        if (dao.cadastrar(aluno)) {
%>
            <div class="alert-success">
                <h2>Aluno cadastrado com sucesso!</h2>
                <p><strong>ID do aluno:</strong> <%= aluno.getId() %></p>
                <p><strong>Nome:</strong> <%= aluno.getNome() %></p>
                <p><strong>Matrícula:</strong> <%= (matricula != null && !matricula.isEmpty()) ? matricula : "Não informada" %></p>
            </div>
            <div class="actions">
                <a href="${pageContext.request.contextPath}/acompanhamento.jsp?alunoId=<%= aluno.getId() %>" class="btn btn-primary">Iniciar Acompanhamento deste Aluno</a>
                <a href="${pageContext.request.contextPath}/cadastroAluno.jsp" class="btn btn-success">Cadastrar Outro Aluno</a>
            </div>
<%
        } else {
%>
            <div class="alert-error">
                <h2>Não foi possível cadastrar o aluno.</h2>
                <p>Verifique o Console do Eclipse para consultar o erro.</p>
            </div>
            <div class="actions">
                <a href="${pageContext.request.contextPath}/cadastroAluno.jsp" class="btn btn-secondary">Voltar ao Cadastro</a>
            </div>
<%
        }
    } catch (IllegalArgumentException e) {
%>
        <div class="alert-error">
            <h2>Dados inválidos</h2>
            <p><%= e.getMessage() %></p>
        </div>
        <div class="actions">
            <a href="${pageContext.request.contextPath}/cadastroAluno.jsp" class="btn btn-secondary">Voltar</a>
        </div>
<%
    } catch (Exception e) {
        application.log("Erro ao salvar aluno", e);
%>
        <div class="alert-error">
            <h2>Erro ao processar cadastro.</h2>
            <p>Consulte o Console do Eclipse.</p>
        </div>
        <div class="actions">
            <a href="${pageContext.request.contextPath}/cadastroAluno.jsp" class="btn btn-secondary">Voltar</a>
        </div>
<%
    }
%>
</div>

</body>
</html>