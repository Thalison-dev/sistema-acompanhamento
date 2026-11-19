<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Sistema de Acompanhamento Pedagógico</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 0; background-color: #f4f6f9; color: #333; }
        .navbar { background-color: #2c3e50; padding: 14px 30px; display: flex; gap: 20px; align-items: center; }
        .navbar a { color: #ecf0f1; text-decoration: none; font-weight: bold; font-size: 14px; padding: 6px 10px; border-radius: 4px; }
        .navbar a:hover, .navbar a.active { background-color: #007bff; color: #fff; }
        .container { max-width: 900px; margin: 40px auto; background: #fff; padding: 30px; border-radius: 8px; box-shadow: 0 2px 6px rgba(0,0,0,0.1); }
        h2 { margin-top: 0; color: #2c3e50; }
        .cards { display: grid; grid-template-columns: repeat(auto-fit, minmax(240px, 1fr)); gap: 20px; margin-top: 25px; }
        .card { border: 1px solid #e1e4e8; border-radius: 8px; padding: 20px; background: #fafbfc; display: flex; flex-direction: column; justify-content: space-between; }
        .card h3 { margin-top: 0; color: #2c3e50; }
        .card p { font-size: 14px; color: #555; line-height: 1.5; }
        .btn { display: inline-block; text-align: center; padding: 10px 14px; border-radius: 4px; text-decoration: none; font-weight: bold; font-size: 14px; margin-top: 10px; }
        .btn-primary { background-color: #007bff; color: #fff; }
        .btn-primary:hover { background-color: #0056b3; }
        .btn-success { background-color: #28a745; color: #fff; }
        .btn-success:hover { background-color: #218838; }
    </style>
</head>
<body>

<div class="navbar">
    <a href="${pageContext.request.contextPath}/index.jsp" class="active">Início</a>
    <a href="${pageContext.request.contextPath}/pedagogo">Pedagogos</a>
    <a href="${pageContext.request.contextPath}/cadastroAluno.jsp">Alunos</a>
    <a href="${pageContext.request.contextPath}/acompanhamento.jsp">Acompanhamentos</a>
</div>

<div class="container">
    <h2>Sistema de Acompanhamento Pedagógico</h2>
    <p>Selecione um módulo abaixo para gerenciar pedagogos, cadastrar alunos ou registrar acompanhamentos.</p>

    <div class="cards">
        <div class="card">
            <div>
                <h3>Pedagogos</h3>
                <p>Consulte, cadastre, edite ou remova os pedagogos responsáveis pelo acompanhamento.</p>
            </div>
            <a href="${pageContext.request.contextPath}/pedagogo" class="btn btn-primary">Gerenciar Pedagogos</a>
        </div>

        <div class="card">
            <div>
                <h3>Alunos</h3>
                <p>Cadastre novos alunos com dados escolares, matrícula e status para habilitá-los ao acompanhamento.</p>
            </div>
            <a href="${pageContext.request.contextPath}/cadastroAluno.jsp" class="btn btn-success">Cadastrar Aluno</a>
        </div>

        <div class="card">
            <div>
                <h3>Acompanhamentos</h3>
                <p>Visualize os alunos cadastrados, selecione o estudante e registre objetivos e prazos de acompanhamento.</p>
            </div>
            <a href="${pageContext.request.contextPath}/acompanhamento.jsp" class="btn btn-primary">Ir para Acompanhamento</a>
        </div>
    </div>
</div>

</body>
</html>