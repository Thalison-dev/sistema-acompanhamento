<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="br.com.thalison.sistema_acompanhamento_aluno.Aluno" %>
<%@ page import="br.com.thalison.sistema_acompanhamento_aluno.AlunoDAO" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Cadastro de Aluno</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

    <p>
        <a href="${pageContext.request.contextPath}/pedagogo">Painel do Pedagogo</a> |
        <a href="${pageContext.request.contextPath}/cadastroAluno.jsp">Cadastrar Outro Aluno</a> |
        <a href="${pageContext.request.contextPath}/acompanhamento">Acompanhamentos</a>
    </p>
    <hr>

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
        aluno.setCpf((cpf != null && !cpf.trim().isEmpty()) ? cpf.trim() : null);
        aluno.setTelefone((telefone != null && !telefone.trim().isEmpty()) ? telefone.trim() : null);
        aluno.setDataNascimento((dataNascimento != null && !dataNascimento.trim().isEmpty()) ? dataNascimento.trim() : null);
        aluno.setMatricula((matricula != null && !matricula.trim().isEmpty()) ? matricula.trim() : null);
        aluno.setAnoEscolar((anoEscolar != null && !anoEscolar.trim().isEmpty()) ? anoEscolar.trim() : null);
        aluno.setStatus((status != null && !status.trim().isEmpty()) ? status.trim() : "Ativo");

        AlunoDAO dao = new AlunoDAO();

        if (dao.cadastrar(aluno)) {
%>
            <h2>Aluno cadastrado com sucesso!</h2>
            <p><strong>ID:</strong> <%= aluno.getId() %></p>
            <p><strong>Nome:</strong> <%= aluno.getNome() %></p>
            <p>
                <a href="${pageContext.request.contextPath}/acompanhamento?alunoId=<%= aluno.getId() %>">Iniciar Acompanhamento deste Aluno</a> |
                <a href="${pageContext.request.contextPath}/pedagogo">Voltar ao Painel do Pedagogo</a>
            </p>
<%
        } else {
%>
            <h2>Não foi possível cadastrar o aluno.</h2>
            <p>Verifique no MySQL se as tabelas <strong>pessoa</strong> e <strong>aluno</strong> existem.</p>
            <p><a href="${pageContext.request.contextPath}/cadastroAluno.jsp">Tentar Novamente</a></p>
<%
        }
    } catch (Exception e) {
%>
        <h2>Erro: <%= e.getMessage() %></h2>
        <p><a href="${pageContext.request.contextPath}/cadastroAluno.jsp">Voltar</a></p>
<%
    }
%>

</body>
</html>