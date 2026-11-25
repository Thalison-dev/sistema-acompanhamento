<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="br.com.thalison.sistema_acompanhamento_Pessoa.Pessoa" %>
<%@ page import="br.com.thalison.sistema_acompanhamento_aluno.Aluno" %>
<%@ page import="br.com.thalison.sistema_acompanhamento_ResponsavelLegal.ResponsavelLegal" %>
<%@ page import="br.com.thalison.sistema_acompanhamento_Acompanhamento.Acompanhamento" %>
<%
    Pessoa usuario = (session != null) ? (Pessoa) session.getAttribute("usuarioLogado") : null;
    if (usuario == null) {
        response.sendRedirect(request.getContextPath() + "/login.jsp?erro=Faca+login+para+continuar");
        return;
    }
    String perfil = usuario.getPerfil();

    List<Aluno> alunos = (List<Aluno>) request.getAttribute("alunos");
    List<ResponsavelLegal> responsaveis = (List<ResponsavelLegal>) request.getAttribute("responsaveis");
    List<Acompanhamento> acompanhamentos = (List<Acompanhamento>) request.getAttribute("acompanhamentos");
    Acompanhamento ficha = (Acompanhamento) request.getAttribute("fichaSelecionada");
%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Fichas de Acompanhamento</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>

    <p>
        <a href="<%= request.getContextPath() %>/painel.jsp">Painel</a> |
        <strong>Perfil Logado:</strong> <%= usuario.getNome() %> (<%= perfil %>) |
        <a href="<%= request.getContextPath() %>/logout">Sair</a>
    </p>
    <hr>

    <h1>Fichas de Acompanhamento dos Alunos Neurodivergentes</h1>

    <% if (request.getParameter("sucesso") != null) { %><p><strong>Sucesso:</strong> <%= request.getParameter("sucesso") %></p><% } %>
    <% if (request.getParameter("erro") != null) { %><p><strong>Erro:</strong> <%= request.getParameter("erro") %></p><% } %>

    <%-- BLOCO 1: SOMENTE SECRETARIA (OU ADMIN) ABRE FICHA E VINCULA RESPONSÁVEL LEGAL --%>
    <% if ("SECRETARIA".equals(perfil) || "ADMIN".equals(perfil)) { %>
        <h2>Abrir Nova Ficha e Vincular Responsável Legal (Menor de Idade)</h2>
        <form action="<%= request.getContextPath() %>/acompanhamento" method="post">
            <input type="hidden" name="acao" value="abrirFichaSecretaria">
            <p>
                <label for="alunoId">Aluno Neurodivergente:</label><br>
                <select id="alunoId" name="alunoId" required>
                    <option value="">-- Selecione o Aluno --</option>
                    <% if (alunos != null) { for (Aluno a : alunos) { %>
                        <option value="<%= a.getId() %>"><%= a.getNome() %> (<%= a.getTipoNeurodivergencia() != null ? a.getTipoNeurodivergencia() : "-" %>)</option>
                    <% } } %>
                </select>
            </p>
            <p>
                <label for="responsavelLegalId">Responsável Legal (Obrigatório):</label><br>
                <select id="responsavelLegalId" name="responsavelLegalId" required>
                    <option value="">-- Selecione o Responsável Legal --</option>
                    <% if (responsaveis != null) { for (ResponsavelLegal rl : responsaveis) { %>
                        <option value="<%= rl.getId() %>"><%= rl.getNome() %> - <%= rl.getParentesco() %></option>
                    <% } } %>
                </select>
                <a href="<%= request.getContextPath() %>/responsavel">+ Cadastrar Novo Responsável</a>
            </p>
            <p>
                <label for="dataInicio">Data de Início:</label><br>
                <input type="date" id="dataInicio" name="dataInicio" required>
            </p>
            <p>
                <label for="dataFim">Previsão de Término (Opcional):</label><br>
                <input type="date" id="dataFim" name="dataFim">
            </p>
            <p>
                <label for="objetivo">Motivo / Objetivo Inicial:</label><br>
                <textarea id="objetivo" name="objetivo" rows="3" cols="50" required></textarea>
            </p>
            <p>
                <button type="submit">Abrir Ficha e Vincular Responsável</button>
            </p>
        </form>
        <hr>
    <% } %>

    <%-- BLOCO 2: DETALHES DA FICHA SELECIONADA PARA ATENDIMENTO --%>
    <% if (ficha != null) { %>
        <h2>Ficha do Aluno: <%= ficha.getNomeAluno() %> (ID #<%= ficha.getId() %>)</h2>
        <p><strong>Neurodivergência:</strong> <%= ficha.getTipoNeurodivergencia() != null ? ficha.getTipoNeurodivergencia() : "-" %></p>
        <p><strong>Responsável Legal Vinculado pela Secretaria:</strong> <%= ficha.getNomeResponsavel() %> (<%= ficha.getParentescoResponsavel() %>)</p>
        <p><strong>Status:</strong> <%= ficha.getStatus() %></p>
        <p><strong>Objetivo Atual:</strong> <%= ficha.getObjetivo() %></p>
        <p><strong>Parecer Psicológico (<%= ficha.getNomePsicologo() != null ? ficha.getNomePsicologo() : "Pendente" %>):</strong> <%= ficha.getParecerPsicologico() != null ? ficha.getParecerPsicologico() : "Ainda não registrado." %></p>
        <p><strong>Parecer Pedagógico (<%= ficha.getNomePedagogo() != null ? ficha.getNomePedagogo() : "Pendente" %>):</strong> <%= ficha.getParecerPedagogico() != null ? ficha.getParecerPedagogico() : "Ainda não registrado." %></p>

        <% if ("SECRETARIA".equals(perfil) || "ADMIN".equals(perfil)) { %>
            <h3>Alterar Responsável Legal Vinculado</h3>
            <form action="<%= request.getContextPath() %>/acompanhamento" method="post">
                <input type="hidden" name="acao" value="alterarResponsavel">
                <input type="hidden" name="id" value="<%= ficha.getId() %>">
                <select name="responsavelLegalId" required>
                    <% if (responsaveis != null) { for (ResponsavelLegal rl : responsaveis) { %>
                        <option value="<%= rl.getId() %>" <%= rl.getId() == ficha.getResponsavelLegalId() ? "selected" : "" %>>
                            <%= rl.getNome() %> (<%= rl.getParentesco() %>)
                        </option>
                    <% } } %>
                </select>
                <button type="submit">Atualizar Responsável</button>
            </form>
        <% } %>

        <% if ("PSICOLOGO".equals(perfil) || "ADMIN".equals(perfil)) { %>
            <h3>Registrar Parecer Psicológico</h3>
            <form action="<%= request.getContextPath() %>/acompanhamento" method="post">
                <input type="hidden" name="acao" value="salvarParecerPsicologo">
                <input type="hidden" name="id" value="<%= ficha.getId() %>">
                <p>
                    <textarea name="parecerPsicologico" rows="4" cols="60" required><%= ficha.getParecerPsicologico() != null ? ficha.getParecerPsicologico() : "" %></textarea>
                </p>
                <button type="submit">Salvar Parecer Psicológico</button>
            </form>
        <% } %>

        <% if ("PEDAGOGO".equals(perfil) || "ADMIN".equals(perfil)) { %>
            <h3>Registrar Parecer e Plano Pedagógico</h3>
            <form action="<%= request.getContextPath() %>/acompanhamento" method="post">
                <input type="hidden" name="acao" value="salvarParecerPedagogo">
                <input type="hidden" name="id" value="<%= ficha.getId() %>">
                <p>
                    <label>Objetivo Pedagógico:</label><br>
                    <textarea name="objetivo" rows="2" cols="60" required><%= ficha.getObjetivo() %></textarea>
                </p>
                <p>
                    <label>Parecer / Estratégias Pedagógicas:</label><br>
                    <textarea name="parecerPedagogico" rows="4" cols="60" required><%= ficha.getParecerPedagogico() != null ? ficha.getParecerPedagogico() : "" %></textarea>
                </p>
                <p>
                    <label>Status do Acompanhamento:</label><br>
                    <select name="status">
                        <option value="Em Andamento" <%= "Em Andamento".equals(ficha.getStatus()) ? "selected" : "" %>>Em Andamento</option>
                        <option value="Concluído" <%= "Concluído".equals(ficha.getStatus()) ? "selected" : "" %>>Concluído</option>
                    </select>
                </p>
                <button type="submit">Salvar Parecer Pedagógico</button>
            </form>
        <% } %>
        <hr>
    <% } %>

    <%-- BLOCO 3: LISTAGEM GERAL DAS FICHAS --%>
    <h2>Todas as Fichas de Acompanhamento</h2>
    <table border="1" cellpadding="6" cellspacing="0">
        <thead>
            <tr>
                <th>Ficha</th>
                <th>Aluno</th>
                <th>Neurodivergência</th>
                <th>Responsável Legal</th>
                <th>Psicólogo</th>
                <th>Pedagogo</th>
                <th>Status</th>
                <th>Ações</th>
            </tr>
        </thead>
        <tbody>
            <% if (acompanhamentos != null && !acompanhamentos.isEmpty()) {
                for (Acompanhamento ac : acompanhamentos) { %>
            <tr>
                <td>#<%= ac.getId() %></td>
                <td><%= ac.getNomeAluno() %></td>
                <td><%= ac.getTipoNeurodivergencia() != null ? ac.getTipoNeurodivergencia() : "-" %></td>
                <td><%= ac.getNomeResponsavel() %> (<%= ac.getParentescoResponsavel() %>)</td>
                <td><%= ac.getNomePsicologo() != null ? ac.getNomePsicologo() : "Pendente" %></td>
                <td><%= ac.getNomePedagogo() != null ? ac.getNomePedagogo() : "Pendente" %></td>
                <td><%= ac.getStatus() %></td>
                <td>
                    <a href="<%= request.getContextPath() %>/acompanhamento?acao=ficha&id=<%= ac.getId() %>">Abrir Ficha</a>
                    <% if ("SECRETARIA".equals(perfil) || "ADMIN".equals(perfil)) { %>
                        | <a href="<%= request.getContextPath() %>/acompanhamento?acao=excluir&id=<%= ac.getId() %>"
                             onclick="return confirm('Excluir ficha?');">Excluir</a>
                    <% } %>
                </td>
            </tr>
            <% } } else { %>
            <tr>
                <td colspan="8">Nenhuma ficha registrada. A Secretaria deve abrir a ficha vinculando o aluno e o responsável legal.</td>
            </tr>
            <% } %>
        </tbody>
    </table>

</body>
</html>