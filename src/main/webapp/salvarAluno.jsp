
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>

<%@ page import="br.com.thalison.sistema_acompanhamento_aluno.Aluno" %>
<%@ page import="br.com.thalison.sistema_acompanhamento_aluno.AlunoDAO" %>

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

        if (nome == null || nome.trim().isEmpty()
                || email == null || email.trim().isEmpty()) {

            throw new IllegalArgumentException(
                "Nome e e-mail são obrigatórios."
            );
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

            <h1>Aluno cadastrado com sucesso!</h1>

            <p>ID do aluno: <%= aluno.getId() %></p>
            <p>Nome: <%= aluno.getNome() %></p>

            <a href="cadastroAluno.jsp">Cadastrar outro aluno</a>
            <br><br>
            <a href="acompanhamento.jsp">Cadastrar acompanhamento</a>

<%
        } else {
%>

            <h1>Não foi possível cadastrar o aluno.</h1>
            <p>Verifique o Console do Eclipse para consultar o erro.</p>

            <a href="cadastroAluno.jsp">Voltar ao cadastro</a>

<%
        }

    } catch (IllegalArgumentException e) {
%>

        <h1>Dados inválidos</h1>
        <p><%= e.getMessage() %></p>
        <a href="cadastroAluno.jsp">Voltar</a>

<%
    } catch (Exception e) {
        application.log("Erro ao salvar aluno", e);
%>

        <h1>Erro ao processar cadastro.</h1>
        <p>Consulte o Console do Eclipse.</p>
        <a href="cadastroAluno.jsp">Voltar</a>

<%
    }
%>
