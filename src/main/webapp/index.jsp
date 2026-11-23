<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Sistema de Acompanhamento Pedagógico</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<jsp:include page="/WEB-INF/includes/navbar.jsp"><jsp:param name="ativo" value="inicio" /></jsp:include>

<div class="container container-wide">
    <jsp:include page="/WEB-INF/includes/mensagens.jsp" />

    <h2>Sistema de Acompanhamento Pedagógico</h2>
    <p class="texto-ajuda">
        Tudo gira em torno do <strong>acompanhamento do aluno</strong>: o professor faz o apontamento,
        a equipe abre o acompanhamento, registra entrevistas, ocorrências, avaliações e adaptações,
        marca reuniões e o diretor supervisiona tudo. Selecione um módulo abaixo.
    </p>

    <div class="cards">
        <div class="card">
            <div>
                <h3>Alunos</h3>
                <p>Cadastre alunos com dados escolares, matrícula, status e responsável legal. Cada aluno tem um histórico completo.</p>
            </div>
            <a href="${pageContext.request.contextPath}/aluno" class="btn btn-success">Gerenciar Alunos</a>
        </div>

        <div class="card">
            <div>
                <h3>Acompanhamentos</h3>
                <p>Selecione o estudante e registre objetivos, prazos, observações e status do acompanhamento.</p>
            </div>
            <a href="${pageContext.request.contextPath}/acompanhamento" class="btn btn-primary">Ir para Acompanhamento</a>
        </div>

        <div class="card">
            <div>
                <h3>Apontamentos</h3>
                <p>Indicações dos professores sobre alunos que precisam de atenção. Daqui nasce um novo acompanhamento.</p>
            </div>
            <a href="${pageContext.request.contextPath}/apontamento" class="btn btn-primary">Ver Apontamentos</a>
        </div>

        <div class="card">
            <div>
                <h3>Neurodivergência</h3>
                <p>Registre laudos e condições (TEA, TDAH, dislexia...) que orientam o acompanhamento do aluno.</p>
            </div>
            <a href="${pageContext.request.contextPath}/neurodivergencia" class="btn btn-primary">Registros</a>
        </div>

        <div class="card">
            <div>
                <h3>Professores</h3>
                <p>Cadastro de professores, ocorrências pedagógicas, avaliações, disciplinas e adaptações metodológicas.</p>
            </div>
            <a href="${pageContext.request.contextPath}/professor" class="btn btn-primary">Área do Professor</a>
        </div>

        <div class="card">
            <div>
                <h3>Pedagogos</h3>
                <p>Painel do pedagogo: alunos, acompanhamentos, consulta de registros e reuniões.</p>
            </div>
            <a href="${pageContext.request.contextPath}/pedagogo" class="btn btn-primary">Painel do Pedagogo</a>
        </div>

        <div class="card">
            <div>
                <h3>Psicólogos e Entrevistas</h3>
                <p>Cadastro de psicólogos e entrevistas com os alunos acompanhados.</p>
            </div>
            <div class="actions" style="margin-top:0">
                <a href="${pageContext.request.contextPath}/psicologo" class="btn btn-primary">Psicólogos</a>
                <a href="${pageContext.request.contextPath}/listar-entrevistas" class="btn btn-secondary">Entrevistas</a>
            </div>
        </div>

        <div class="card">
            <div>
                <h3>Responsáveis Legais</h3>
                <p>Pais e responsáveis vinculados aos alunos, para contato e reuniões.</p>
            </div>
            <a href="${pageContext.request.contextPath}/responsavel" class="btn btn-primary">Responsáveis</a>
        </div>

        <div class="card">
            <div>
                <h3>Reuniões</h3>
                <p>Agenda única de reuniões entre pedagogo, psicólogo, professor, diretor, aluno e responsáveis.</p>
            </div>
            <a href="${pageContext.request.contextPath}/reuniao" class="btn btn-primary">Agenda de Reuniões</a>
        </div>

        <div class="card">
            <div>
                <h3>Diretor</h3>
                <p>Indicadores da escola, aprovação e encerramento de acompanhamentos, pendências e reuniões da direção.</p>
            </div>
            <a href="${pageContext.request.contextPath}/diretor" class="btn btn-success">Painel do Diretor</a>
        </div>
    </div>
</div>

</body>
</html>
