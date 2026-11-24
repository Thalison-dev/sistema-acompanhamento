<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="br.com.thalison.sistema_acompanhamento_Pedagogo.Pedagogo" %>
<%@ page import="br.com.thalison.sistema_acompanhamento_Pedagogo.PedagogoDAO" %>
<%@ page import="br.com.thalison.sistema_acompanhamento_Pessoa.Pessoa" %>
<%
    Pessoa usuarioLogado = (Pessoa) session.getAttribute("usuarioLogado");
    List<Pedagogo> pedagogos = (request.getAttribute("pedagogos") instanceof List<?>)
            ? (List<Pedagogo>) request.getAttribute("pedagogos")
            : new PedagogoDAO().listar();

    Pedagogo atual = null;
    if (usuarioLogado != null) {
        for (Pedagogo p : pedagogos) {
            if (p.getId() == usuarioLogado.getId()) {
                atual = p;
                break;
            }
        }
    }
    if (atual == null && !pedagogos.isEmpty()) {
        atual = pedagogos.get(0);
    }

    int idExibido = (atual != null) ? atual.getId() : (usuarioLogado != null ? usuarioLogado.getId() : 4);
    String nomeExibido = (atual != null && atual.getNome() != null)
            ? atual.getNome()
            : (usuarioLogado != null && usuarioLogado.getNome() != null ? usuarioLogado.getNome() : "");
    String espExibida = (atual != null && atual.getEspecialidade() != null) ? atual.getEspecialidade() : "";
%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Meu Perfil de Pedagogo</title>
</head>
<body>

    <p>
        <a href="${pageContext.request.contextPath}/painel.jsp">Voltar ao Painel</a> |
        <a href="${pageContext.request.contextPath}/logout">Sair</a>
    </p>
    <hr>

    <h2>Meu Perfil de Pedagogo</h2>

    <form action="${pageContext.request.contextPath}/pedagogo" method="post">
        <p>
            <label for="idPedagogo">ID do Pedagogo:</label><br>
            <input type="number" id="idPedagogo" name="idPedagogo" min="1" required value="<%= idExibido %>">
        </p>

        <p>
            <label for="pessoa">Seu Nome:</label><br>
            <input type="text" id="pessoa" name="pessoa" required value="<%= nomeExibido %>" placeholder="Digite seu nome">
        </p>

        <p>
            <label for="especialidade">Sua Especialidade:</label><br>
            <input type="text" id="especialidade" name="especialidade" required value="<%= espExibida %>" placeholder="Ex: Educação Especial">
        </p>

        <p>
            <button type="submit">Salvar Perfil</button>
            <a href="${pageContext.request.contextPath}/painel.jsp">Cancelar</a>
        </p>
    </form>

</body>
</html>