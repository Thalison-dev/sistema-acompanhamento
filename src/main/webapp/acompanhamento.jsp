<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="br.com.thalison.sistema_acompanhamento_aluno.Aluno" %>
<%@ page import="br.com.thalison.sistema_acompanhamento_aluno.AlunoDAO" %>
<%@ page import="br.com.thalison.sistema_acompanhamento_ResponsavelLegal.ResponsavelLegal" %>
<%@ page import="br.com.thalison.sistema_acompanhamento_ResponsavelLegal.ResponsavelLegalDAO" %>
<%@ page import="br.com.thalison.sistema_acompanhamento_Acompanhamento.Acompanhamento" %>
<%@ page import="br.com.thalison.sistema_acompanhamento_Acompanhamento.AcompanhamentoDAO" %>
<%
    List<Aluno> listaAlunos = (request.getAttribute("alunos") instanceof List<?>)
            ? (List<Aluno>) request.getAttribute("alunos") : new AlunoDAO().listarTodos();

    List<ResponsavelLegal> listaResponsaveis = (request.getAttribute("responsaveis") instanceof List<?>)
            ? (List<ResponsavelLegal>) request.getAttribute("responsaveis") : new ResponsavelLegalDAO().listarTodos();

    List<Acompanhamento> listaAcompanhamentos = (request.getAttribute("acompanhamentos") instanceof List<?>)
            ? (List<Acompanhamento>) request.getAttribute("acompanhamentos") : new AcompanhamentoDAO().listar();

    Map<Integer, String> mapaNomesAlunos = new HashMap<>();
    for (Aluno al : listaAlunos) {
        mapaNomesAlunos.put(al.getId(), al.getNome());
    }

    Map<Integer, String> mapaNomesResponsaveis = new HashMap<>();
    for (ResponsavelLegal rl : listaResponsaveis) {
        String label = rl.getNome() + (rl.getParentesco() != null ? " (" + rl.getParentesco() + ")" : "");
        mapaNomesResponsaveis.put(rl.getId(), label);
    }

    Object attrEdit = request.getAttribute("acompanhamentoEdit");
    Acompanhamento acompEdit = (attrEdit instanceof Acompanhamento) ? (Acompanhamento) attrEdit : null;
    boolean isEdicao = (acompEdit != null);

    String alunoIdSelecionado = request.getParameter("alunoId");
    String respIdSelecionado = request.getParameter("responsavelLegalId");

    if (isEdicao) {
        alunoIdSelecionado = String.valueOf(acompEdit.getAlunoId());
        respIdSelecionado = String.valueOf(acompEdit.getResponsavelLegalId());
    }
    if (alunoIdSelecionado == null) alunoIdSelecionado = "";
    if (respIdSelecionado == null) respIdSelecionado = "";

    String msgSucesso = request.getParameter("sucesso");
    String msgErro = request.getParameter("erro");
%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Acompanhamento Pedagógico</title>
</head>
<body>

    <p>
        <a href="${pageContext.request.contextPath}/index.jsp">Início</a> |
        <a href="${pageContext.request.contextPath}/cadastroAluno.jsp">Cadastrar Aluno</a> |
        <a href="${pageContext.request.contextPath}/responsavel">Responsáveis Legais</a> |
        <a href="${pageContext.request.contextPath}/acompanhamento">Acompanhamentos</a>
    </p>
    <hr>

    <h2><%= isEdicao ? "Editar Acompanhamento #" + acompEdit.getAcompanhamentoId() : "Registrar Acompanhamento Pedagógico" %></h2>

    <% if (msgSucesso != null && !msgSucesso.isEmpty()) { %>
        <p><strong>Sucesso:</strong> <%= msgSucesso %></p>
    <% } %>

    <% if (msgErro != null && !msgErro.isEmpty()) { %>
        <p><strong>Erro:</strong> <%= msgErro %></p>
    <% } %>

    <form action="${pageContext.request.contextPath}/acompanhamento" method="post">
        <input type="hidden" name="acao" value="<%= isEdicao ? "atualizar" : "cadastrar" %>">
        <% if (isEdicao) { %>
            <input type="hidden" name="id" value="<%= acompEdit.getAcompanhamentoId() %>">
        <% } %>

        <p>
            <label for="alunoId">Aluno:</label><br>
            <select id="alunoId" name="alunoId" required>
                <option value="">-- Selecione um aluno --</option>
                <% for (Aluno aluno : listaAlunos) {
                    String idStr = String.valueOf(aluno.getId());
                    String selected = idStr.equals(alunoIdSelecionado) ? "selected" : ""; %>
                    <option value="<%= idStr %>" <%= selected %>>
                        ID <%= idStr %> - <%= aluno.getNome() %> (Matrícula: <%= aluno.getMatricula() != null ? aluno.getMatricula() : "-" %>)
                    </option>
                <% } %>
            </select>
        </p>

        <p>
            <label for="responsavelLegalId">Responsável Legal Vinculado:</label><br>
            <select id="responsavelLegalId" name="responsavelLegalId">
                <option value="0">-- Nenhum responsável selecionado --</option>
                <% for (ResponsavelLegal rl : listaResponsaveis) {
                    String idStr = String.valueOf(rl.getId());
                    String selected = idStr.equals(respIdSelecionado) ? "selected" : ""; %>
                    <option value="<%= idStr %>" <%= selected %>>
                        ID <%= idStr %> - <%= rl.getNome() %> (<%= rl.getParentesco() != null ? rl.getParentesco() : "Responsável" %>)
                    </option>
                <% } %>
            </select>
            <a href="${pageContext.request.contextPath}/responsavel">+ Cadastrar Novo Responsável</a>
        </p>

        <p>
            <label for="dataInicio">Data de Início:</label><br>
            <input type="date" id="dataInicio" name="dataInicio" required
                   value="<%= (isEdicao && acompEdit.getDataInicio() != null) ? acompEdit.getDataInicio() : "" %>">
        </p>

        <p>
            <label for="dataFim">Data de Fim (opcional):</label><br>
            <input type="date" id="dataFim" name="dataFim"
                   value="<%= (isEdicao && acompEdit.getDataFim() != null) ? acompEdit.getDataFim() : "" %>">
        </p>

        <p>
            <label for="objetivo">Objetivo:</label><br>
            <textarea id="objetivo" name="objetivo" rows="4" cols="45" required><%= (isEdicao && acompEdit.getObjetivo() != null) ? acompEdit.getObjetivo() : "" %></textarea>
        </p>

        <p>
            <label for="status">Status:</label><br>
            <% String statusAtual = (isEdicao && acompEdit.getStatus() != null) ? acompEdit.getStatus() : "Pendente"; %>
            <select id="status" name="status" required>
                <option value="Pendente" <%= "Pendente".equals(statusAtual) ? "selected" : "" %>>Pendente</option>
                <option value="Em Andamento" <%= "Em Andamento".equals(statusAtual) ? "selected" : "" %>>Em Andamento</option>
                <option value="Concluído" <%= "Concluído".equals(statusAtual) ? "selected" : "" %>>Concluído</option>
                <option value="Cancelado" <%= "Cancelado".equals(statusAtual) ? "selected" : "" %>>Cancelado</option>
            </select>
        </p>

        <p>
            <button type="submit"><%= isEdicao ? "Salvar Alterações" : "Registrar Acompanhamento" %></button>
            <% if (isEdicao) { %>
                <a href="${pageContext.request.contextPath}/acompanhamento">Cancelar Edição</a> |
            <% } %>
            <a href="${pageContext.request.contextPath}/index.jsp">Voltar ao Início</a>
        </p>
    </form>

    <hr>

    <h3>Acompanhamentos Cadastrados</h3>
    <table border="1" cellpadding="6" cellspacing="0">
        <thead>
            <tr>
                <th>ID</th>
                <th>Aluno</th>
                <th>Responsável Legal</th>
                <th>Data de Início</th>
                <th>Data de Fim</th>
                <th>Objetivo</th>
                <th>Status</th>
                <th>Ações</th>
            </tr>
        </thead>
        <tbody>
            <% if (!listaAcompanhamentos.isEmpty()) {
                for (Acompanhamento ac : listaAcompanhamentos) {
                    String nomeAluno = mapaNomesAlunos.containsKey(ac.getAlunoId())
                            ? mapaNomesAlunos.get(ac.getAlunoId())
                            : "Aluno ID " + ac.getAlunoId();

                    String nomeResp = (ac.getResponsavelLegalId() > 0)
                            ? (mapaNomesResponsaveis.containsKey(ac.getResponsavelLegalId())
                                ? mapaNomesResponsaveis.get(ac.getResponsavelLegalId())
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
                       onclick="return confirm('Deseja realmente excluir?');">Excluir</a>
                </td>
            </tr>
            <%  }
               } else { %>
            <tr>
                <td colspan="8">Nenhum acompanhamento registrado até o momento.</td>
            </tr>
            <% } %>
        </tbody>
    </table>

</body>
</html>