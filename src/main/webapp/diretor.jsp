
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ page import="java.util.List, java.util.Map" %>
<%@ page import="br.com.thalison.sistema_acompanhamento_Diretor.Diretor" %>

<%!
    private String escapar(String texto) {
        if (texto == null) return "";

        return texto.replace("&", "&amp;")
                    .replace("<", "&lt;")
                    .replace(">", "&gt;")
                    .replace("\"", "&quot;")
                    .replace("'", "&#39;");
    }
%>

<%
    List<Diretor> diretores =
        (List<Diretor>) request.getAttribute("diretores");

    Map<String, Integer> indicadores =
        (Map<String, Integer>) request.getAttribute("indicadores");

    String ctx = request.getContextPath();
%>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Painel do Diretor - EduConnect</title>

<style>
* {
    box-sizing: border-box;
}

body {
    margin: 0;
    font-family: Arial, sans-serif;
    background: #f3f5f9;
    color: #263449;
}

header {
    background: #244b78;
    color: white;
    padding: 22px 30px;
}

header h1 {
    margin: 0 0 7px;
    font-size: 26px;
}

header p {
    margin: 0;
    color: #e0eaf5;
    font-size: 14px;
}

nav {
    background: white;
    padding: 14px 30px;
    border-bottom: 1px solid #ddd;
}

nav a {
    color: #244b78;
    text-decoration: none;
    margin-right: 22px;
    font-size: 14px;
}

nav a:hover {
    text-decoration: underline;
}

main {
    max-width: 1200px;
    margin: 25px auto;
    padding: 0 22px;
}

h2 {
    font-size: 19px;
    margin: 0 0 17px;
    color: #244b78;
}

.indicadores {
    display: grid;
    grid-template-columns: repeat(3, 1fr);
    gap: 16px;
    margin-bottom: 28px;
}

.card {
    background: white;
    padding: 20px;
    border: 1px solid #e1e6ed;
    border-radius: 8px;
}

.card p {
    color: #6b7789;
    font-size: 14px;
    margin: 0 0 12px;
}

.numero {
    font-size: 27px;
    font-weight: bold;
    color: #244b78;
}

.linha {
    display: grid;
    grid-template-columns: 1fr 1.5fr;
    gap: 20px;
    margin-bottom: 25px;
}

label {
    display: block;
    font-size: 14px;
    margin: 12px 0 6px;
}

input {
    width: 100%;
    padding: 10px;
    border: 1px solid #ccd4df;
    border-radius: 5px;
    font-size: 14px;
}

button {
    padding: 10px 15px;
    background: #244b78;
    color: white;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    margin-top: 14px;
}

button:hover {
    background: #193858;
}

.excluir {
    background: #b53d3d;
    margin: 0;
}

.excluir:hover {
    background: #8f2929;
}

table {
    width: 100%;
    border-collapse: collapse;
}

th, td {
    padding: 12px 10px;
    text-align: left;
    border-bottom: 1px solid #e7ebf0;
    font-size: 13px;
}

th {
    background: #f7f9fc;
    color: #526176;
}

.aviso {
    padding: 12px;
    margin-bottom: 18px;
    border-radius: 5px;
    background: #e2f5e8;
    color: #22663a;
}

.rodape {
    text-align: center;
    color: #7c8797;
    font-size: 12px;
    padding: 20px;
}

@media (max-width: 750px) {
    .indicadores {
        grid-template-columns: repeat(2, 1fr);
    }

    .linha {
        grid-template-columns: 1fr;
    }

    nav a {
        display: inline-block;
        margin: 6px 12px 6px 0;
    }

    main {
        padding: 0 12px;
    }

    .rolagem {
        overflow-x: auto;
    }
}
</style>
</head>

<body>

<header>
    <h1>EduConnect</h1>
    <p>Painel administrativo do diretor escolar</p>
</header>

<nav>
    <a href="<%= ctx %>/diretor">Painel</a>
    <a href="<%= ctx %>/aluno">Alunos</a>
    <a href="<%= ctx %>/acompanhamento">Acompanhamentos</a>
    <a href="<%= ctx %>/professor">Professores</a>
    <a href="<%= ctx %>/pedagogo">Pedagogos</a>
</nav>

<main>

    <h2>Visão geral da escola</h2>

    <section class="indicadores">

        <div class="card">
            <p>Alunos cadastrados</p>
            <div class="numero">
                <%= indicadores != null ?
                    indicadores.getOrDefault("alunos", 0) : 0 %>
            </div>
        </div>

        <div class="card">
            <p>Acompanhamentos</p>
            <div class="numero">
                <%= indicadores != null ?
                    indicadores.getOrDefault("acompanhamentos", 0) : 0 %>
            </div>
        </div>

        <div class="card">
            <p>Ocorrências pedagógicas</p>
            <div class="numero">
                <%= indicadores != null ?
                    indicadores.getOrDefault("ocorrencias", 0) : 0 %>
            </div>
        </div>

        <div class="card">
            <p>Adaptações metodológicas</p>
            <div class="numero">
                <%= indicadores != null ?
                    indicadores.getOrDefault("adaptacoes", 0) : 0 %>
            </div>
        </div>

        <div class="card">
            <p>Turmas</p>
            <div class="numero">
                <%= indicadores != null ?
                    indicadores.getOrDefault("turmas", 0) : 0 %>
            </div>
        </div>

        <div class="card">
            <p>Professores</p>
            <div class="numero">
                <%= indicadores != null ?
                    indicadores.getOrDefault("professores", 0) : 0 %>
            </div>
        </div>

    </section>

    <section class="linha">

        <div class="card">
            <h2>Cadastrar diretor</h2>

            <p>Vincule uma pessoa já cadastrada ao perfil de diretor.</p>

            <form action="<%= ctx %>/diretor" method="post">

                <input type="hidden" name="acao" value="cadastrar">

                <label for="id">ID da pessoa</label>
                <input type="number" id="id" name="id"
                       min="1" required>

                <label for="matricula">Matrícula</label>
                <input type="text" id="matricula" name="matricula"
                       maxlength="50" required>

                <button type="submit">Cadastrar diretor</button>

            </form>
        </div>

        <div class="card">
            <h2>Diretores cadastrados</h2>

            <% if (diretores != null && !diretores.isEmpty()) { %>

                <div class="rolagem">
                    <table>
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Nome</th>
                                <th>Matrícula</th>
                                <th>Ação</th>
                            </tr>
                        </thead>

                        <tbody>
                        <% for (Diretor d : diretores) { %>
                            <tr>
                                <td><%= d.getId() %></td>
                                <td><%= escapar(d.getNome()) %></td>
                                <td><%= escapar(d.getMatricula()) %></td>
                                <td>
                                    <form action="<%= ctx %>/diretor"
                                          method="post"
                                          onsubmit="return confirm('Deseja excluir este diretor?');">

                                        <input type="hidden"
                                               name="acao" value="excluir">

                                        <input type="hidden"
                                               name="id" value="<%= d.getId() %>">

                                        <button class="excluir" type="submit">
                                            Excluir
                                        </button>
                                    </form>
                                </td>
                            </tr>
                        <% } %>
                        </tbody>
                    </table>
                </div>

            <% } else { %>
                <p>Nenhum diretor cadastrado.</p>
            <% } %>
        </div>

    </section>

</main>

<footer class="rodape">
    EduConnect - Sistema de acompanhamento escolar
</footer>

</body>
</html>
