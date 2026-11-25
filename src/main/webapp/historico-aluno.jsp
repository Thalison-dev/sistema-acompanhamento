<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="br.com.thalison.sistema_acompanhamento_aluno.Aluno" %>
<%@ page import="br.com.thalison.sistema_acompanhamento_Acompanhamento.Acompanhamento" %>
<%
    Aluno aluno = (Aluno) request.getAttribute("aluno");
    List<Acompanhamento> acompanhamentos = (request.getAttribute("acompanhamentosAluno") instanceof List<?>)
            ? (List<Acompanhamento>) request.getAttribute("acompanhamentosAluno") : new ArrayList<>();
%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Registros do Aluno</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>

    <p>
        <a href="<%= request.getContextPath() %>/painel.jsp">Voltar ao Painel</a> |
        <a href="<%= request.getContextPath() %>/acompanhamento">Fichas de Acompanhamento</a>
    </p>
    <hr>

    <% if (aluno != null) { %>
        <h2>Registros Pedagógicos do Aluno</h2>
        <p><strong>ID:</strong> <%= aluno.getId() %></p>
        <p><strong>Nome:</strong> <%= aluno.getNome() %></p>
        <p><strong>E-mail:</strong> <%= aluno.getEmail() != null ? aluno.getEmail() : "-" %></p>
        <p><strong>Telefone:</strong> <%= aluno.getTelefone() != null ? aluno.getTelefone() : "-" %></p>
        <p><strong>Matrícula:</strong> <%= aluno.getMatricula() != null ? aluno.getMatricula() : "-" %></p>
        <p><strong>Ano Escolar:</strong> <%= aluno.getAnoEscolar() != null ? aluno.getAnoEscolar() : "-" %></p>
        <p><strong>Neurodivergência:</strong> <%= aluno.getTipoNeurodivergencia() != null ? aluno.getTipoNeurodivergencia() : "-" %></p>
        <p><strong>Status:</strong> <%= aluno.getStatus() != null ? aluno.getStatus() : "-" %></p>

        <h3>Histórico de Acompanhamentos</h3>
        <table border="1" cellpadding="6" cellspacing="0">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Data Início</th>
                    <th>Data Fim</th>
                    <th>Objetivo</th>
                    <th>Parecer Pedagógico</th>
                    <th>Status</th>
                </tr>
            </thead>
            <tbody>
                <% if (!acompanhamentos.isEmpty()) {
                    for (Acompanhamento ac : acompanhamentos) { %>
                <tr>
                    <td><%= ac.getId() %></td>
                    <td><%= ac.getDataInicio() != null ? ac.getDataInicio() : "-" %></td>
                    <td><%= ac.getDataFim() != null ? ac.getDataFim() : "Em aberto" %></td>
                    <td><%= ac.getObjetivo() != null ? ac.getObjetivo() : "-" %></td>
                    <td><%= ac.getParecerPedagogico() != null ? ac.getParecerPedagogico() : "Pendente" %></td>
                    <td><%= ac.getStatus() != null ? ac.getStatus() : "-" %></td>
                </tr>
                <%  }
                   } else { %>
                <tr>
                    <td colspan="6">Nenhum acompanhamento registrado para este aluno.</td>
                </tr>
                <% } %>
            </tbody>
        </table>
    <% } else { %>
        <p>Aluno não encontrado.</p>
    <% } %>

</body>
</html>