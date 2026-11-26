<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="br.com.thalison.sistema_acompanhamento_aluno.Aluno" %>
<%@ page import="br.com.thalison.sistema_acompanhamento_desempenho.DesempenhoMensal" %>
<%@ page import="br.com.thalison.sistema_acompanhamento_Apontamento.Apontamento" %>
<%
    List<Aluno> alunos = (List<Aluno>) request.getAttribute("alunos");
    List<DesempenhoMensal> desempenhos = (List<DesempenhoMensal>) request.getAttribute("desempenhos");
%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Desempenho Mensal do Aluno</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>

    <p>
        <a href="<%= request.getContextPath() %>/painel.jsp">Painel</a> |
        <a href="<%= request.getContextPath() %>/apontamentos">Apontamentos do Professor</a> |
        <a href="<%= request.getContextPath() %>/acompanhamento">Fichas de Acompanhamento</a> |
        <a href="<%= request.getContextPath() %>/logout">Sair</a>
    </p>
    <hr>

    <h1>Desempenho Mensal: Nota vs. Media da Escola e Apontamentos</h1>

    <% if (request.getParameter("sucesso") != null) { %><p><strong>Sucesso:</strong> <%= request.getParameter("sucesso") %></p><% } %>
    <% if (request.getParameter("erro") != null) { %><p><strong>Erro:</strong> <%= request.getParameter("erro") %></p><% } %>

    <h2>Registrar / Atualizar Desempenho do Mes</h2>
    <form action="<%= request.getContextPath() %>/desempenho" method="post">
        <p>
            <label for="alunoId">Aluno Neurodivergente:</label><br>
            <select id="alunoId" name="alunoId" required>
                <option value="">-- Selecione o Aluno --</option>
                <% if (alunos != null) { for (Aluno a : alunos) { %>
                    <option value="<%= a.getId() %>"><%= a.getNome() %> (<%= a.getTipoNeurodivergencia() != null ? a.getTipoNeurodivergencia() : "Sem registro" %>)</option>
                <% } } %>
            </select>
        </p>
        <p>
            <label for="mesReferencia">Mes de Referencia:</label><br>
            <select id="mesReferencia" name="mesReferencia" required>
                <option value="1">Janeiro (01)</option>
                <option value="2">Fevereiro (02)</option>
                <option value="3">Marco (03)</option>
                <option value="4">Abril (04)</option>
                <option value="5">Maio (05)</option>
                <option value="6">Junho (06)</option>
                <option value="7">Julho (07)</option>
                <option value="8">Agosto (08)</option>
                <option value="9">Setembro (09)</option>
                <option value="10">Outubro (10)</option>
                <option value="11" selected>Novembro (11)</option>
                <option value="12">Dezembro (12)</option>
            </select>
        </p>
        <p>
            <label for="anoReferencia">Ano:</label><br>
            <input type="number" id="anoReferencia" name="anoReferencia" value="2026" min="2020" max="2100" required>
        </p>
        <p>
            <label for="mediaEscola">Media da Escola:</label><br>
            <input type="text" id="mediaEscola" name="mediaEscola" value="7.00" required>
        </p>
        <p>
            <label for="notaAluno">Nota / Rendimento do Aluno no Mes:</label><br>
            <input type="text" id="notaAluno" name="notaAluno" placeholder="Ex: 6.50" required>
        </p>
        <p>
            <label for="observacaoGeral">Sintese Pedagogica / Psicologica do Mes:</label><br>
            <textarea id="observacaoGeral" name="observacaoGeral" rows="3" cols="50"></textarea>
        </p>
        <p>
            <button type="submit">Salvar Desempenho Mensal</button>
        </p>
    </form>

    <hr>
    <h2>Analise Mensal por Aluno</h2>
    <table border="1" cellpadding="6" cellspacing="0">
        <thead>
            <tr>
                <th>Periodo</th>
                <th>Aluno</th>
                <th>Media Escola</th>
                <th>Nota Aluno</th>
                <th>Diferenca</th>
                <th>Diagnostico</th>
                <th>Apontamentos do Professor no Mes</th>
                <th>Acao</th>
            </tr>
        </thead>
        <tbody>
            <% if (desempenhos != null && !desempenhos.isEmpty()) {
                for (DesempenhoMensal d : desempenhos) { %>
            <tr>
                <td><%= String.format("%02d/%d", d.getMesReferencia(), d.getAnoReferencia()) %></td>
                <td><%= d.getNomeAluno() %></td>
                <td><%= d.getMediaEscola() %></td>
                <td><%= d.getNotaAluno() %></td>
                <td><%= d.getDiferencaMedia() %></td>
                <td><%= d.getSituacaoDesempenho() %></td>
                <td>
                    <% if (d.getApontamentosDoMes() != null && !d.getApontamentosDoMes().isEmpty()) { %>
                        <ul>
                            <% for (Apontamento ap : d.getApontamentosDoMes()) { %>
                                <li><strong><%= ap.getData() %></strong> (<%= ap.getNomeProfessor() %>): <%= ap.getMotivo() %></li>
                            <% } %>
                        </ul>
                    <% } else { %>
                        Nenhum apontamento registrado neste mes.
                    <% } %>
                </td>
                <td>
                    <a href="<%= request.getContextPath() %>/desempenho?acao=excluir&id=<%= d.getId() %>"
                       onclick="return confirm('Excluir este registro de desempenho?');">Excluir</a>
                </td>
            </tr>
            <% } } else { %>
            <tr>
                <td colspan="8">Nenhum desempenho mensal cadastrado ainda.</td>
            </tr>
            <% } %>
        </tbody>
    </table>

</body>
</html>