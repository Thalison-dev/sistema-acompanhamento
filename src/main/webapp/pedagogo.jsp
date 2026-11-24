<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="br.com.thalison.sistema_acompanhamento_Pedagogo.Pedagogo" %>
<%@ page import="br.com.thalison.sistema_acompanhamento_Pedagogo.PedagogoDAO" %>
<%@ page import="br.com.thalison.sistema_acompanhamento_aluno.Aluno" %>
<%@ page import="br.com.thalison.sistema_acompanhamento_aluno.AlunoDAO" %>
<%@ page import="br.com.thalison.sistema_acompanhamento_Acompanhamento.Acompanhamento" %>
<%@ page import="br.com.thalison.sistema_acompanhamento_Acompanhamento.AcompanhamentoDAO" %>
<%
    PedagogoDAO pDao = new PedagogoDAO();

    List<Pedagogo> pedagogos = (request.getAttribute("pedagogos") instanceof List<?>)
            ? (List<Pedagogo>) request.getAttribute("pedagogos") : pDao.listar();
    if (pedagogos == null) {
        pedagogos = new ArrayList<>();
    }

    List<Aluno> alunos = (request.getAttribute("alunos") instanceof List<?>)
            ? (List<Aluno>) request.getAttribute("alunos") : new AlunoDAO().listarTodos();
    if (alunos == null) {
        alunos = new ArrayList<>();
    }

    List<Acompanhamento> acompanhamentos = (request.getAttribute("acompanhamentos") instanceof List<?>)
            ? (List<Acompanhamento>) request.getAttribute("acompanhamentos")
            : new AcompanhamentoDAO().listarTodos();
    if (acompanhamentos == null) {
        acompanhamentos = new ArrayList<>();
    }

    Map<Integer, String> nomesAlunos = new HashMap<>();
    for (Aluno al : alunos) {
        nomesAlunos.put(al.getId(), al.getNome());
    }

    Pedagogo pedagogoAtivo = !pedagogos.isEmpty() ? pedagogos.get(0) : null;
    String msgSucesso = request.getParameter("sucesso");
    String msgErro = request.getParameter("erro");
%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Painel do Pedagogo</title>
</head>
<body>

    <h1>Painel do Pedagogo</h1>
    <p>
        <strong>Pedagogo em exercício:</strong>
        <% if (pedagogoAtivo != null) { %>
            <%= pedagogoAtivo.getNome() %> (<%= pedagogoAtivo.getEspecialidade() != null ? pedagogoAtivo.getEspecialidade() : "Pedagogia" %>)
        <% } else { %>
            Não identificado (<a href="${pageContext.request.contextPath}/pedagogo?acao=perfil">Definir meu perfil</a>)
        <% } %>
    </p>

    <p>
        <a href="${pageContext.request.contextPath}/painel.jsp">Painel Geral</a> |
        <a href="${pageContext.request.contextPath}/aluno">Alunos</a> |
        <a href="${pageContext.request.contextPath}/acompanhamento">Fichas de Acompanhamento</a> |
        <a href="${pageContext.request.contextPath}/desempenho">Desempenho Mensal</a> |
        <a href="${pageContext.request.contextPath}/pedagogo?acao=perfil">Meu Perfil</a> |
        <a href="${pageContext.request.contextPath}/logout">Sair</a>
    </p>
    <hr>

    <% if (msgSucesso != null && !msgSucesso.trim().isEmpty()) { %>
        <p><strong>Sucesso:</strong> <%= msgSucesso %></p>
    <% } %>
    <% if (msgErro != null && !msgErro.trim().isEmpty()) { %>
        <p><strong>Erro:</strong> <%= msgErro %></p>
    <% } %>

    <h2>1. Alunos Cadastrados</h2>
    <p><a href="${pageContext.request.contextPath}/aluno">+ Gerenciar Alunos</a></p>

    <table border="1" cellpadding="6" cellspacing="0">
        <thead>
            <tr>
                <th>ID</th>
                <th>Nome</th>
                <th>Matrícula</th>
                <th>Ano Escolar</th>
                <th>Neurodivergência</th>
                <th>Status</th>
                <th>Ações do Pedagogo</th>
            </tr>
        </thead>
        <tbody>
            <% if (!alunos.isEmpty()) {
                for (Aluno a : alunos) { %>
            <tr>
                <td><%= a.getId() %></td>
                <td><%= a.getNome() != null ? a.getNome() : "-" %></td>
                <td><%= a.getMatricula() != null ? a.getMatricula() : "-" %></td>
                <td><%= a.getAnoEscolar() != null ? a.getAnoEscolar() : "-" %></td>
                <td><%= a.getTipoNeurodivergencia() != null ? a.getTipoNeurodivergencia() : "-" %></td>
                <td><%= a.getStatus() != null ? a.getStatus() : "-" %></td>
                <td>
                    <a href="${pageContext.request.contextPath}/acompanhamento?alunoId=<%= a.getId() %>">Acompanhar Aluno</a> |
                    <a href="${pageContext.request.contextPath}/desempenho">Desempenho Mensal</a>
                </td>
            </tr>
            <%  }
               } else { %>
            <tr>
                <td colspan="7">Nenhum aluno cadastrado. <a href="${pageContext.request.contextPath}/aluno">Cadastre um aluno</a>.</td>
            </tr>
            <% } %>
        </tbody>
    </table>

    <hr>

    <h2>2. Acompanhamentos Pedagógicos Registrados</h2>
    <p><a href="${pageContext.request.contextPath}/acompanhamento">+ Abrir / Atualizar Ficha de Acompanhamento</a></p>

    <table border="1" cellpadding="6" cellspacing="0">
        <thead>
            <tr>
                <th>ID</th>
                <th>Aluno</th>
                <th>Início</th>
                <th>Fim</th>
                <th>Objetivo</th>
                <th>Parecer Pedagógico</th>
                <th>Status</th>
            </tr>
        </thead>
        <tbody>
            <% if (!acompanhamentos.isEmpty()) {
                for (Acompanhamento ac : acompanhamentos) {
                    String nomeAluno = nomesAlunos.containsKey(ac.getAlunoId())
                            ? nomesAlunos.get(ac.getAlunoId())
                            : "Aluno ID " + ac.getAlunoId(); %>
            <tr>
                <td><%= ac.getId() %></td>
                <td><%= nomeAluno %> (ID <%= ac.getAlunoId() %>)</td>
                <td><%= ac.getDataInicio() != null ? ac.getDataInicio() : "-" %></td>
                <td><%= ac.getDataFim() != null ? ac.getDataFim() : "Em aberto" %></td>
                <td><%= ac.getObjetivo() != null ? ac.getObjetivo() : "-" %></td>
                <td><%= ac.getParecerPedagogico() != null ? ac.getParecerPedagogico() : "Pendente" %></td>
                <td><%= ac.getStatus() != null ? ac.getStatus() : "-" %></td>
            </tr>
            <%  }
               } else { %>
            <tr>
                <td colspan="7">Nenhum acompanhamento registrado.</td>
            </tr>
            <% } %>
        </tbody>
    </table>

</body>
</html>