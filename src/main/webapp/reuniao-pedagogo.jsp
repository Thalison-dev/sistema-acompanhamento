<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="br.com.thalison.sistema_acompanhamento_Pedagogo.Pedagogo" %>
<%@ page import="br.com.thalison.sistema_acompanhamento_Pedagogo.PedagogoDAO" %>
<%@ page import="br.com.thalison.sistema_acompanhamento_aluno.Aluno" %>
<%@ page import="br.com.thalison.sistema_acompanhamento_aluno.AlunoDAO" %>
<%
    List<Aluno> alunos = (request.getAttribute("alunos") instanceof List<?>)
            ? (List<Aluno>) request.getAttribute("alunos") : new AlunoDAO().listarTodos();

    List<Pedagogo> pedagogos = (request.getAttribute("pedagogos") instanceof List<?>)
            ? (List<Pedagogo>) request.getAttribute("pedagogos") : new PedagogoDAO().listar();

    int pedagogoId = !pedagogos.isEmpty() ? pedagogos.get(0).getIdPedagogo() : 1;
    String alunoIdParam = request.getParameter("alunoId");
    if (alunoIdParam == null) alunoIdParam = "";
%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Marcar Reunião - Pedagogo</title>
</head>
<body>

    <p>
        <a href="${pageContext.request.contextPath}/pedagogo">Voltar ao Painel do Pedagogo</a>
    </p>
    <hr>

    <h2>Marcar Reunião</h2>

    <form action="${pageContext.request.contextPath}/pedagogo" method="post">
        <input type="hidden" name="acao" value="agendarReuniao">
        <input type="hidden" name="pedagogoId" value="<%= pedagogoId %>">

        <p>
            <label for="publicoAlvo">Reunião com:</label><br>
            <select id="publicoAlvo" name="publicoAlvo" required>
                <option value="Psicólogo">Psicólogo</option>
                <option value="Responsável Legal">Responsável Legal</option>
                <option value="Diretor">Diretor</option>
                <option value="Aluno">Aluno</option>
            </select>
        </p>

        <p>
            <label for="alunoId">Aluno Relacionado:</label><br>
            <select id="alunoId" name="alunoId">
                <option value="0">-- Selecione um aluno (opcional para reunião com Diretor) --</option>
                <% for (Aluno a : alunos) {
                    String idStr = String.valueOf(a.getId());
                    String selected = idStr.equals(alunoIdParam) ? "selected" : ""; %>
                    <option value="<%= idStr %>" <%= selected %>>ID <%= idStr %> - <%= a.getNome() %></option>
                <% } %>
            </select>
        </p>

        <p>
            <label for="dataReuniao">Data da Reunião:</label><br>
            <input type="date" id="dataReuniao" name="dataReuniao" required>
        </p>

        <p>
            <label for="motivo">Motivo / Pauta da Reunião:</label><br>
            <textarea id="motivo" name="motivo" rows="4" cols="45" required placeholder="Descreva o assunto da reunião"></textarea>
        </p>

        <p>
            <button type="submit">Confirmar Agendamento</button>
            <a href="${pageContext.request.contextPath}/pedagogo">Cancelar</a>
        </p>
    </form>

</body>
</html>