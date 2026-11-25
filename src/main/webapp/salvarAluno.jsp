<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.Date" %>
<%@ page import="br.com.thalison.sistema_acompanhamento_aluno.Aluno" %>
<%@ page import="br.com.thalison.sistema_acompanhamento_aluno.AlunoDAO" %>
<%
    request.setCharacterEncoding("UTF-8");

    String nome = request.getParameter("nome");
    String email = request.getParameter("email");
    String cpf = request.getParameter("cpf");
    String telefone = request.getParameter("telefone");
    String dataNascimentoStr = request.getParameter("dataNascimento");
    String matricula = request.getParameter("matricula");
    String anoEscolar = request.getParameter("anoEscolar");
    String tipoNeurodivergencia = request.getParameter("tipoNeurodivergencia");
    String descricaoNeuro = request.getParameter("descricaoNeuro");
    String status = request.getParameter("status");

    Aluno aluno = new Aluno();
    aluno.setNome(nome);
    aluno.setEmail(email);
    aluno.setCpf(cpf);
    aluno.setTelefone(telefone);
    aluno.setMatricula(matricula);
    aluno.setAnoEscolar(anoEscolar);
    aluno.setTipoNeurodivergencia(tipoNeurodivergencia);
    aluno.setDescricaoNeuro(descricaoNeuro);
    aluno.setStatus(status);

    if (dataNascimentoStr != null && !dataNascimentoStr.trim().isEmpty()) {
        aluno.setDataNascimento(Date.valueOf(dataNascimentoStr.trim()));
    }

    AlunoDAO dao = new AlunoDAO();
    boolean salvo = dao.cadastrar(aluno);

    if (salvo) {
        response.sendRedirect(request.getContextPath() + "/aluno?sucesso=" + java.net.URLEncoder.encode("Aluno cadastrado com sucesso!", "UTF-8"));
    } else {
        response.sendRedirect(request.getContextPath() + "/aluno?erro=" + java.net.URLEncoder.encode("Erro ao cadastrar aluno.", "UTF-8"));
    }
%>