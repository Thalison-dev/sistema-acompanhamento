<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="br.com.thalison.sistema_acompanhamento_Acompanhamento.Acompanhamento" %>
<%@ page import="br.com.thalison.sistema_acompanhamento_Acompanhamento.AcompanhamentoDAO" %>
<%@ page import="java.sql.*" %>
<%@ page import="br.com.thalison.sistema_acompanhamento.Conexao" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Resultado do Acompanhamento</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
        body { font-family: Arial, sans-serif; margin: 0; background-color: #f4f6f9; color: #333; }
        .navbar { background-color: #2c3e50; padding: 14px 30px; display: flex; gap: 20px; align-items: center; }
        .navbar a { color: #ecf0f1; text-decoration: none; font-weight: bold; font-size: 14px; padding: 6px 10px; border-radius: 4px; }
        .navbar a:hover, .navbar a.active { background-color: #007bff; color: #fff; }
        .container { max-width: 600px; margin: 40px auto; background: #fff; padding: 25px; border-radius: 8px; box-shadow: 0 2px 6px rgba(0,0,0,0.1); }
        .alert-success { background-color: #d4edda; color: #155724; padding: 15px; border-radius: 4px; border: 1px solid #c3e6cb; }
        .alert-error { background-color: #f8d7da; color: #721c24; padding: 15px; border-radius: 4px; border: 1px solid #f5c6cb; }
        .actions { margin-top: 20px; display: flex; gap: 10px; flex-wrap: wrap; }
        .btn { padding: 10px 16px; border-radius: 4px; font-size: 14px; font-weight: bold; text-decoration: none; display: inline-block; }
        .btn-primary { background-color: #007bff; color: #fff; }
        .btn-secondary { background-color: #6c757d; color: #fff; }
    </style>
</head>
<body>

<div class="navbar">
    <a href="${pageContext.request.contextPath}/index.jsp">Início</a>
    <a href="${pageContext.request.contextPath}/pedagogo">Pedagogos</a>
    <a href="${pageContext.request.contextPath}/cadastroAluno.jsp">Alunos</a>
    <a href="${pageContext.request.contextPath}/acompanhamento.jsp" class="active">Acompanhamentos</a>
</div>

<div class="container">
<%
    request.setCharacterEncoding("UTF-8");

    try {
        String alunoIdTexto = request.getParameter("alunoId");
        String dataInicio = request.getParameter("dataInicio");
        String dataFim = request.getParameter("dataFim");
        String objetivo = request.getParameter("objetivo");
        String status = request.getParameter("status");

        if (alunoIdTexto == null || alunoIdTexto.trim().isEmpty()
                || dataInicio == null || dataInicio.trim().isEmpty()
                || objetivo == null || objetivo.trim().isEmpty()
                || status == null || status.trim().isEmpty()) {
            throw new IllegalArgumentException("Preencha todos os campos obrigatórios.");
        }

        int alunoId = Integer.parseInt(alunoIdTexto);

        if (alunoId <= 0) {
            throw new IllegalArgumentException("ID do aluno inválido.");
        }

        if (dataFim != null && !dataFim.isEmpty() && dataFim.compareTo(dataInicio) < 0) {
            throw new IllegalArgumentException("A data de fim não pode ser anterior à data de início.");
        }

        Acompanhamento acompanhamento = new Acompanhamento();
        acompanhamento.setAlunoId(alunoId);
        acompanhamento.setDataInicio(dataInicio);
        acompanhamento.setDataFim(dataFim);
        acompanhamento.setObjetivo(objetivo);
        acompanhamento.setStatus(status);

        AcompanhamentoDAO dao = new AcompanhamentoDAO();

        if (dao.cadastrar(acompanhamento)) {
            String nomeAluno = "";
            try (Connection conn = Conexao.conectar();
                 PreparedStatement ps = conn.prepareStatement("SELECT nome FROM aluno WHERE id = ?")) {
                ps.setInt(1, alunoId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        nomeAluno = " - " + rs.getString("nome");
                    }
                }
            } catch (Exception ignored) {
            }
%>
            <div class="alert-success">
                <h2>Acompanhamento cadastrado com sucesso!</h2>
                <p><strong>Aluno:</strong> ID <%= alunoId %><%= nomeAluno %></p>
                <p><strong>Data de início:</strong> <%= dataInicio %></p>
                <p><strong>Data de fim:</strong> <%= (dataFim == null || dataFim.isEmpty()) ? "Não informada" : dataFim %></p>
                <p><strong>Status:</strong> <%= status %></p>
            </div>
            <div class="actions">
                <a href="${pageContext.request.contextPath}/acompanhamento.jsp" class="btn btn-primary">Voltar para Acompanhamentos</a>
                <a href="${pageContext.request.contextPath}/pedagogo" class="btn btn-secondary">Ver Pedagogos</a>
            </div>
<%
        } else {
%>
            <div class="alert-error">
                <h2>Não foi possível cadastrar o acompanhamento.</h2>
                <p>Verifique se o aluno existe e se a conexão com o banco está funcionando.</p>
            </div>
            <div class="actions">
                <a href="${pageContext.request.contextPath}/acompanhamento.jsp" class="btn btn-secondary">Voltar ao Formulário</a>
            </div>
<%
        }
    } catch (NumberFormatException e) {
%>
        <div class="alert-error">
            <h2>ID do aluno inválido.</h2>
        </div>
        <div class="actions">
            <a href="${pageContext.request.contextPath}/acompanhamento.jsp" class="btn btn-secondary">Voltar</a>
        </div>
<%
    } catch (IllegalArgumentException e) {
%>
        <div class="alert-error">
            <h2>Dados inválidos.</h2>
            <p><%= e.getMessage() %></p>
        </div>
        <div class="actions">
            <a href="${pageContext.request.contextPath}/acompanhamento.jsp" class="btn btn-secondary">Voltar</a>
        </div>
<%
    } catch (Exception e) {
        application.log("Erro ao salvar acompanhamento", e);
%>
        <div class="alert-error">
            <h2>Erro ao processar o cadastro.</h2>
            <p>Entre em contato com o responsável pelo sistema.</p>
        </div>
        <div class="actions">
            <a href="${pageContext.request.contextPath}/acompanhamento.jsp" class="btn btn-secondary">Voltar</a>
        </div>
<%
    }
%>
</div>

</body>
</html>