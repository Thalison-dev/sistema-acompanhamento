<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.ResultSetMetaData" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.HashSet" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Set" %>
<%@ page import="br.com.thalison.sistema_acompanhamento.Conexao" %>
<%
    String alunoIdSelecionado = request.getParameter("alunoId");
    if (alunoIdSelecionado == null) {
        alunoIdSelecionado = "";
    }

    // Indices de aluno: [0]=id, [1]=nome, [2]=matricula, [3]=anoEscolar, [4]=status
    List<String[]> listaAlunos = new ArrayList<>();
    Map<String, String> mapaNomesAlunos = new HashMap<>();

    // Indices de acompanhamento: [0]=id, [1]=alunoId, [2]=nomeAluno, [3]=dataInicio, [4]=dataFim, [5]=objetivo, [6]=status
    List<String[]> listaAcompanhamentos = new ArrayList<>();

    // Carrega os alunos cadastrados para o pedagogo visualizar e selecionar
    try (Connection conn = Conexao.conectar();
         PreparedStatement ps = conn.prepareStatement("SELECT * FROM aluno");
         ResultSet rs = ps.executeQuery()) {

        ResultSetMetaData meta = rs.getMetaData();
        Set<String> colunas = new HashSet<>();
        for (int i = 1; i <= meta.getColumnCount(); i++) {
            colunas.add(meta.getColumnLabel(i).toLowerCase());
        }

        while (rs.next()) {
            String id = String.valueOf(rs.getInt(1));
            String nome = colunas.contains("nome") ? rs.getString("nome") : "Aluno #" + id;
            String matricula = (colunas.contains("matricula") && rs.getString("matricula") != null) ? rs.getString("matricula") : "-";
            String anoEscolar = (colunas.contains("anoescolar") && rs.getString("anoEscolar") != null) ? rs.getString("anoEscolar") : "-";
            String status = (colunas.contains("status") && rs.getString("status") != null) ? rs.getString("status") : "Ativo";

            listaAlunos.add(new String[]{ id, nome, matricula, anoEscolar, status });
            mapaNomesAlunos.put(id, nome);
        }
    } catch (Exception e) {
        application.log("Erro ao carregar lista de alunos no acompanhamento", e);
    }

    // Carrega os acompanhamentos já cadastrados vinculando ao nome do aluno
    try (Connection conn = Conexao.conectar();
         PreparedStatement ps = conn.prepareStatement("SELECT * FROM acompanhamento");
         ResultSet rs = ps.executeQuery()) {

        ResultSetMetaData meta = rs.getMetaData();
        Set<String> colunas = new HashSet<>();
        for (int i = 1; i <= meta.getColumnCount(); i++) {
            colunas.add(meta.getColumnLabel(i).toLowerCase());
        }

        while (rs.next()) {
            String idAcomp = String.valueOf(rs.getInt(1));
            String alId = colunas.contains("alunoid") ? String.valueOf(rs.getInt("alunoId"))
                        : (colunas.contains("aluno_id") ? String.valueOf(rs.getInt("aluno_id")) : "-");
            String nomeAluno = mapaNomesAlunos.containsKey(alId) ? mapaNomesAlunos.get(alId) : "ID " + alId;
            String dtInicio = colunas.contains("datainicio") ? rs.getString("dataInicio")
                            : (colunas.contains("data_inicio") ? rs.getString("data_inicio") : "-");
            String dtFim = colunas.contains("datafim") ? rs.getString("dataFim")
                         : (colunas.contains("data_fim") ? rs.getString("data_fim") : "-");
            String objetivo = colunas.contains("objetivo") ? rs.getString("objetivo") : "-";
            String status = colunas.contains("status") ? rs.getString("status") : "-";

            listaAcompanhamentos.add(new String[]{
                idAcomp,
                alId,
                nomeAluno,
                (dtInicio != null ? dtInicio : "-"),
                ((dtFim != null && !dtFim.isEmpty()) ? dtFim : "Em aberto"),
                (objetivo != null ? objetivo : "-"),
                (status != null ? status : "-")
            });
        }
    } catch (Exception e) {
        application.log("Erro ao carregar acompanhamentos", e);
    }
%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Acompanhamento Pedagógico</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 0; background-color: #f4f6f9; color: #333; }
        .navbar { background-color: #2c3e50; padding: 14px 30px; display: flex; gap: 20px; align-items: center; }
        .navbar a { color: #ecf0f1; text-decoration: none; font-weight: bold; font-size: 14px; padding: 6px 10px; border-radius: 4px; }
        .navbar a:hover, .navbar a.active { background-color: #007bff; color: #fff; }
        .container { max-width: 950px; margin: 30px auto; background: #fff; padding: 25px; border-radius: 8px; box-shadow: 0 2px 6px rgba(0,0,0,0.1); }
        h2, h3 { margin-top: 0; color: #2c3e50; }
        .form-grid { display: grid; grid-template-columns: 1fr 1fr; gap: 15px; }
        .form-group { margin-bottom: 15px; }
        .form-group.full { grid-column: 1 / -1; }
        label { display: block; margin-bottom: 5px; font-weight: bold; }
        input[type="date"], select, textarea {
            width: 100%; padding: 10px; border: 1px solid #ccc; border-radius: 4px; box-sizing: border-box; font-family: inherit;
        }
        .actions { margin-top: 10px; display: flex; gap: 10px; flex-wrap: wrap; }
        .btn { padding: 9px 15px; border: none; border-radius: 4px; font-size: 14px; font-weight: bold; cursor: pointer; text-decoration: none; display: inline-block; }
        .btn-success { background-color: #28a745; color: #fff; }
        .btn-success:hover { background-color: #218838; }
        .btn-primary { background-color: #007bff; color: #fff; }
        .btn-primary:hover { background-color: #0056b3; }
        .btn-sm { padding: 5px 10px; font-size: 12px; }
        table { width: 100%; border-collapse: collapse; margin-top: 10px; }
        th, td { padding: 10px 12px; border-bottom: 1px solid #ddd; text-align: left; font-size: 14px; }
        th { background-color: #f8f9fa; color: #495057; }
        tr:hover { background-color: #f1f1f1; }
        .empty { text-align: center; padding: 18px; color: #777; }
        .badge { padding: 3px 8px; border-radius: 12px; font-size: 12px; font-weight: bold; background: #e9ecef; color: #333; }
    </style>
    <script>
        function selecionarAluno(id) {
            var select = document.getElementById("alunoId");
            if (select) {
                select.value = id;
                select.scrollIntoView({ behavior: "smooth", block: "center" });
                select.focus();
            }
        }
    </script>
</head>
<body>

<div class="navbar">
    <a href="${pageContext.request.contextPath}/index.jsp">Início</a>
    <a href="${pageContext.request.contextPath}/pedagogo">Pedagogos</a>
    <a href="${pageContext.request.contextPath}/cadastroAluno.jsp">Alunos</a>
    <a href="${pageContext.request.contextPath}/acompanhamento.jsp" class="active">Acompanhamentos</a>
</div>

<div class="container">
    <h2>Novo Acompanhamento Pedagógico</h2>