<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="br.com.thalison.sistema_acompanhamento_Pedagogo.Pedagogo" %>
<%@ page import="br.com.thalison.sistema_acompanhamento_Pedagogo.PedagogoDAO" %>
<%
    List<Pedagogo> pedagogos = (request.getAttribute("pedagogos") instanceof List<?>)
            ? (List<Pedagogo>) request.getAttribute("pedagogos") : new PedagogoDAO().listar();
    Pedagogo atual = !pedagogos.isEmpty() ? pedagogos.get(0) : null;
%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Meu Perfil de Pedagogo</title>
</head>
<body>

    <p>
        <a href="${pageContext.request.contextPath}/index.jsp">Voltar ao Início</a>
    </p>
    <hr>

    <h2>Meu Perfil de Pedagogo</h2>

    <form action="${pageContext.request.contextPath}/pedagogo" method="post">
        <input type="hidden" name="acao" value="salvarPerfil">

        <p>
            <label for="idPedagogo">ID do Pedagogo:</label><br>
            <input type="number" id="idPedagogo" name="idPedagogo" min="1" required
                   value="<%= atual != null ? atual.getIdPedagogo() : 1 %>">
        </p>

        <p>
            <label for="pessoa">Seu Nome:</label><br>
            <input type="text" id="pessoa" name="pessoa" required
                   value="<%= atual != null ? atual.getPessoa() : "" %>"
                   placeholder="Digite seu nome">
        </p>

        <p>
            <label for="especialidade">Sua Especialidade:</label><br>
            <input type="text" id="especialidade" name="especialidade" required
                   value="<%= atual != null ? atual.getEspecialidade() : "" %>"
                   placeholder="Ex: Psicopedagogia">
        </p>

        <p>
            <button type="submit">Salvar Perfil</button>
            <a href="${pageContext.request.contextPath}/index.jsp">Cancelar</a>
        </p>
    </form>

</body>
</html>