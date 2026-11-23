
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Painel do Professor</title>

    <style>
        * {
            box-sizing: border-box;
        }

        body {
            margin: 0;
            padding: 30px;
            font-family: Arial, sans-serif;
            background-color: #f4f6f9;
            color: #263238;
        }

        .container {
            max-width: 1100px;
            margin: auto;
        }

        header {
            background-color: #244b68;
            color: white;
            padding: 25px;
            border-radius: 12px;
            margin-bottom: 25px;
        }

        header h1 {
            margin: 0 0 8px;
        }

        .card {
            background-color: white;
            padding: 24px;
            margin-bottom: 22px;
            border-radius: 12px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.06);
        }

        h2 {
            color: #244b68;
            margin-top: 0;
        }

        .form-grid {
            display: grid;
            grid-template-columns: repeat(2, 1fr);
            gap: 18px;
        }

        .campo {
            display: flex;
            flex-direction: column;
            gap: 8px;
        }

        label {
            font-weight: bold;
        }

        input, select, textarea {
            width: 100%;
            padding: 12px;
            border: 1px solid #ccd3da;
            border-radius: 7px;
            font-size: 15px;
        }

        textarea {
            min-height: 110px;
            resize: vertical;
        }

        .campo-completo {
            grid-column: 1 / -1;
        }

        button {
            background-color: #244b68;
            color: white;
            border: none;
            border-radius: 7px;
            padding: 12px 20px;
            cursor: pointer;
            font-size: 15px;
            margin-top: 12px;
        }

        button:hover {
            background-color: #18364d;
        }

        .registros {
            display: grid;
            grid-template-columns: repeat(2, 1fr);
            gap: 20px;
        }

        .registro {
            border: 1px solid #e0e5e9;
            border-radius: 10px;
            padding: 18px;
        }

        .registro h3 {
            margin-top: 0;
            color: #244b68;
        }

        .mensagem {
            color: #687782;
            font-size: 14px;
        }

        @media (max-width: 700px) {
            body {
                padding: 15px;
            }

            .form-grid, .registros {
                grid-template-columns: 1fr;
            }
        }
    </style>
</head>

<body>

<div class="container">

    <header>
        <h1>Painel do Professor</h1>
        <p>Sistema de Acompanhamento Escolar</p>
    </header>

    <!-- CONSULTA DE ALUNOS -->
    <section class="card">
        <h2>Consultar alunos</h2>

        <form action="${pageContext.request.contextPath}/consultarAluno"
              method="get">

            <button type="submit">Carregar alunos</button>
        </form>

        <c:choose>
            <c:when test="${not empty alunos}">
                <div class="campo">
                    <label for="alunoSelecionado">Alunos encontrados:</label>

                    <select id="alunoSelecionado" name="alunoSelecionado">
                        <c:forEach var="aluno" items="${alunos}">
                            <option value="${aluno.id}">
                                <c:out value="${aluno.nome}"/>
                                - Matrícula:
                                <c:out value="${aluno.matricula}"/>
                            </option>
                        </c:forEach>
                    </select>
                </div>
            </c:when>

            <c:otherwise>
                <p class="mensagem">
                    Os alunos serão exibidos aqui após a integração com o Servlet.
                </p>
            </c:otherwise>
        </c:choose>
    </section>

    <!-- IDENTIFICAÇÃO DO ALUNO -->
    <section class="card">
        <h2>Identificação do aluno</h2>

        <p class="mensagem">
            Informe o ID do aluno para realizar um registro ou consultar seu histórico.
        </p>

        <form action="${pageContext.request.contextPath}/consultarHistorico"
              method="get">

            <div class="campo">
                <label for="idConsulta">ID do aluno:</label>
                <input type="number" id="idConsulta" name="id"
                       min="1" required>
            </div>

            <button type="submit">Consultar histórico</button>
        </form>
    </section>

    <!-- REGISTROS PEDAGÓGICOS -->
    <section class="card">
        <h2>Registros pedagógicos</h2>

        <div class="registros">

            <!-- OBSERVAÇÃO -->
            <div class="registro">
                <h3>Observação</h3>

                <form action="${pageContext.request.contextPath}/registrarObservacao"
                      method="post">

                    <div class="campo">
                        <label>ID do aluno:</label>
                        <input type="number" name="id" min="1" required>

                        <label>Descrição:</label>
                        <textarea name="descricao" required></textarea>
                    </div>

                    <button type="submit">Registrar observação</button>
                </form>
            </div>

            <!-- OCORRÊNCIA -->
            <div class="registro">
                <h3>Ocorrência pedagógica</h3>

                <form action="${pageContext.request.contextPath}/registrarOcorrencia"
                      method="post">

                    <div class="campo">
                        <label>ID do aluno:</label>
                        <input type="number" name="id" min="1" required>

                        <label>Descrição:</label>
                        <textarea name="descricao" required></textarea>
                    </div>

                    <button type="submit">Registrar ocorrência</button>
                </form>
            </div>

            <!-- AVALIAÇÃO -->
            <div class="registro">
                <h3>Avaliação</h3>

                <form action="${pageContext.request.contextPath}/registrarAvaliacao"
                      method="post">

                    <div class="campo">
                        <label>ID do aluno:</label>
                        <input type="number" name="id" min="1" required>

                        <label>Descrição:</label>
                        <textarea name="descricao" required></textarea>
                    </div>

                    <button type="submit">Registrar avaliação</button>
                </form>
            </div>

            <!-- RENDIMENTO -->
            <div class="registro">
                <h3>Rendimento escolar</h3>

                <form action="${pageContext.request.contextPath}/registrarRendimento"
                      method="post">

                    <div class="campo">
                        <label>ID do aluno:</label>
                        <input type="number" name="id" min="1" required>

                        <label>Descrição:</label>
                        <textarea name="descricao" required></textarea>
                    </div>

                    <button type="submit">Registrar rendimento</button>
                </form>
            </div>

            <!-- ADAPTAÇÃO -->
            <div class="registro">
                <h3>Adaptação metodológica</h3>

                <form action="${pageContext.request.contextPath}/registrarAdaptacao"
                      method="post">

                    <div class="campo">
                        <label>ID do aluno:</label>
                        <input type="number" name="id" min="1" required>

                        <label>Descrição:</label>
                        <textarea name="descricao" required></textarea>
                    </div>

                    <button type="submit">Registrar adaptação</button>
                </form>
            </div>

        </div>
    </section>

    <!-- HISTÓRICO -->
    <section class="card">
        <h2>Histórico do aluno</h2>

        <p class="mensagem">
            Selecione um aluno e consulte seus registros pedagógicos.
        </p>

        <form action="${pageContext.request.contextPath}/consultarHistorico"
              method="get">

            <div class="campo">
                <label for="idHistorico">ID do aluno:</label>
                <input type="number" id="idHistorico" name="id"
                       min="1" required>
            </div>

            <button type="submit">Visualizar histórico</button>
        </form>
    </section>

</div>

</body>
</html>
