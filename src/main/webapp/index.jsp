<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="br.com.thalison.sistema_acompanhamento_Pedagogo.Pedagogo" %>
<%@ page import="br.com.thalison.sistema_acompanhamento_Pedagogo.PedagogoDAO" %>
<%@ page import="br.com.thalison.sistema_acompanhamento_aluno.Aluno" %>
<%@ page import="br.com.thalison.sistema_acompanhamento_aluno.AlunoDAO" %>
<%@ page import="br.com.thalison.sistema_acompanhamento_ResponsavelLegal.ResponsavelLegal" %>
<%@ page import="br.com.thalison.sistema_acompanhamento_ResponsavelLegal.ResponsavelLegalDAO" %>
<%@ page import="br.com.thalison.sistema_acompanhamento_Acompanhamento.Acompanhamento" %>
<%@ page import="br.com.thalison.sistema_acompanhamento_Acompanhamento.AcompanhamentoDAO" %>
<%
    PedagogoDAO pDao = new PedagogoDAO();
    List<Pedagogo> pedagogos = (request.getAttribute("pedagogos") instanceof List<?>)
            ? (List<Pedagogo>) request.getAttribute("pedagogos") : pDao.listar();

    List<Aluno> alunos = (request.getAttribute("alunos") instanceof List<?>)
            ? (List<Aluno>) request.getAttribute("alunos") : new AlunoDAO().listarTodos();

    List<ResponsavelLegal> responsaveis = (request.getAttribute("responsaveis") instanceof List<?>)
            ? (List<ResponsavelLegal>) request.getAttribute("responsaveis") : new ResponsavelLegalDAO().listarTodos();

    List<Acompanhamento> acompanhamentos = (request.getAttribute("acompanhamentos") instanceof List<?>)
            ? (List<Acompanhamento>) request.getAttribute("acompanhamentos") : new AcompanhamentoDAO().listar();

    List<String[]> reunioes = (request.getAttribute("reunioes") instanceof List<?>)
            ? (List<String[]>) request.getAttribute("reunioes") : pDao.listarReunioes();

    Map<Integer, String> nomesAlunos = new HashMap<>();
    for (Aluno al : alunos) {
        nomesAlunos.put(al.getId(), al.getNome());
    }

    Map<Integer, String> nomesResponsaveis = new HashMap<>();
    for (ResponsavelLegal rl : responsaveis) {
        String label = rl.getNome() + (rl.getParentesco() != null ? " (" + rl.getParentesco() + ")" : "");
        nomesResponsaveis.put(rl.getId(), label);
    }

    Pedagogo pedagogoAtivo = !pedagogos.isEmpty() ? pedagogos.get(0) : null;
    String msgSucesso = request.getParameter("sucesso");
    String msgErro = request.getParameter("erro");
%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Início - Painel do Pedagogo</title>
</head>
<body>

    <h1>Painel do Pedagogo</h1>
    <p>
        <strong>Pedagogo em exercício:</strong>
        <% if (pedagogoAtivo != null) { %>
            <%= pedagogoAtivo.getPessoa() %> (<%= pedagogoAtivo.getEspecialidade() %>)
        <% } else { %>
            Não identificado (<a href="${pageContext.request.contextPath}/pedagogo?acao=perfil">Definir meu perfil</a>)
        <% } %>
    </p>

    <p>
        <a href="${pageContext.request.contextPath}/index.jsp">Início</a> |
        <a href="${pageContext.request.contextPath}/cadastroAluno.jsp">Cadastrar Aluno</a> |
        <a href="${pageContext.request.contextPath}/responsavel">Responsáveis Legais</a> |
        <a href="${pageContext.request.contextPath}/acompanhamento">Acompanhamentos</a> |
        <a href="${pageContext.request.contextPath}/pedagogo?acao=reuniao">Marcar Reunião</a> |
        <a href="${pageContext.request.contextPath}/pedagogo?acao=perfil">Meu Perfil</a>
    </p>
    <hr>

    <% if (msgSucesso != null && !msgSucesso.isEmpty()) { %>
        <p><strong>Sucesso:</strong> <%= msgSucesso %></p>
    <% } %>
    <% if (msgErro != null && !msgErro.isEmpty()) { %>
        <p><strong>Erro:</strong> <%= msgErro %></p>
    <% } %>

    <h2>1. Alunos Cadastrados</h2>
    <p><a href="${pageContext.request.contextPath}/cadastroAluno.jsp">+ Cadastrar Novo Aluno</a></p>

    <table border="1" cellpadding="6" cellspacing="0">
        <thead>
            <tr>
                <th>ID</th>
                <th>Nome</th>
                <th>Matrícula</th>
                <th>Ano Escolar</th>
                <th>Status</th>
                <th>Ações do Pedagogo</th>
            </tr>
        </thead>
        <tbody>
            <% if (!alunos.isEmpty()) {
                for (Aluno a : alunos) { %>
            <tr>
                <td><%= a.getId() %></td>
                <td><%= a.getNome() %></td>
                <td><%= a.getMatricula() != null ? a.getMatricula() : "-" %></td>
                <td><%= a.getAnoEscolar() != null ? a.getAnoEscolar() : "-" %></td>
                <td><%= a.getStatus() != null ? a.getStatus() : "-" %></td>
                <td>
                    <a href="${pageContext.request.contextPath}/acompanhamento?alunoId=<%= a.getId() %>">Acompanhar Aluno</a> |
                    <a href="${pageContext.request.contextPath}/pedagogo?acao=historicoAluno&alunoId=<%= a.getId() %>">Consultar Registros</a> |
                    <a href="${pageContext.request.contextPath}/pedagogo?acao=reuniao&alunoId=<%= a.getId() %>">Marcar Reunião</a>
                </td>
            </tr>
            <%  }
               } else { %>
            <tr>
                <td colspan="6">Nenhum aluno cadastrado. <a href="${pageContext.request.contextPath}/cadastroAluno.jsp">Cadastre o primeiro aluno</a>.</td>
            </tr>
            <% } %>
        </tbody>
    </table>

    <hr>

    <h2>2. Acompanhamentos Pedagógicos Registrados</h2>
    <p><a href="${pageContext.request.contextPath}/acompanhamento">+ Novo Acompanhamento</a></p>

    <table border="1" cellpadding="6" cellspacing="0">
        <thead>
            <tr>
                <th>ID</th>
                <th>Aluno</th>
                <th>Responsável Legal</th>
                <th>Início</th>
                <th>Fim</th>
                <th>Objetivo</th>
                <th>Status</th>
                <th>Gerenciar</th>
            </tr>
        </thead>
        <tbody>
            <% if (!acompanhamentos.isEmpty()) {
                for (Acompanhamento ac : acompanhamentos) {
                    String nomeAluno = nomesAlunos.containsKey(ac.getAlunoId())
                            ? nomesAlunos.get(ac.getAlunoId())
                            : "Aluno ID " + ac.getAlunoId();

                    String nomeResp = (ac.getResponsavelLegalId() > 0)
                            ? (nomesResponsaveis.containsKey(ac.getResponsavelLegalId())
                                ? nomesResponsaveis.get(ac.getResponsavelLegalId())
                                : "ID " + ac.getResponsavelLegalId())
                            : "Não vinculado"; %>
            <tr>
                <td><%= ac.getAcompanhamentoId() %></td>
                <td><%= nomeAluno %> (ID <%= ac.getAlunoId() %>)</td>
                <td><%= nomeResp %></td>
                <td><%= ac.getDataInicio() != null ? ac.getDataInicio() : "-" %></td>
                <td><%= (ac.getDataFim() != null && !ac.getDataFim().trim().isEmpty()) ? ac.getDataFim() : "Em aberto" %></td>
                <td><%= ac.getObjetivo() %></td>
                <td><%= ac.getStatus() %></td>
                <td>
                    <a href="${pageContext.request.contextPath}/acompanhamento?acao=editar&id=<%= ac.getAcompanhamentoId() %>">Editar</a> |
                    <a href="${pageContext.request.contextPath}/acompanhamento?acao=excluir&id=<%= ac.getAcompanhamentoId() %>"
                       onclick="return confirm('Excluir este acompanhamento?');">Excluir</a>
                </td>
            </tr>
            <%  }
               } else { %>
            <tr>
                <td colspan="8">Nenhum acompanhamento registrado.</td>
            </tr>
            <% } %>
        </tbody>
    </table>

    <hr>

    <h2>3. Reuniões Agendadas</h2>
    <p><a href="${pageContext.request.contextPath}/pedagogo?acao=reuniao">+ Agendar Reunião (Psicólogo / Responsável / Diretor / Aluno)</a></p>

    <table border="1" cellpadding="6" cellspacing="0">
        <thead>
            <tr>
                <th>ID</th>
                <th>Detalhes da Reunião</th>
                <th>Data de Registro</th>
            </tr>
        </thead>
        <tbody>
            <% if (!reunioes.isEmpty()) {
                for (String[] r : reunioes) { %>
            <tr>
                <td><%= r[0] %></td>
                <td><%= r[3] %></td>
                <td><%= r[4] %></td>
            </tr>
            <%  }
               } else { %>
            <tr>
                <td colspan="3">Nenhuma reunião agendada até o momento.</td>
            </tr>
            <% } %>
        </tbody>
    </table>

</body>
</html>