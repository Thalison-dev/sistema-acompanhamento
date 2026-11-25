<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="br.com.thalison.sistema_acompanhamento_Pessoa.Pessoa"%>
<%
Pessoa usuario = (session != null) ? (Pessoa) session.getAttribute("usuarioLogado") : null;
if (usuario == null) {
	response.sendRedirect(request.getContextPath() + "/login.jsp?erro=Faca+login+para+continuar");
	return;
}
String perfil = usuario.getPerfil();
%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
<meta charset="UTF-8">
<title>Painel Principal</title>
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/style.css">
</head>
<body>

	<h1>Sistema de Apoio para Alunos Neurodivergentes</h1>
	<p>
		<strong>Usuário:</strong>
		<%=usuario.getNome()%>
		| <strong>Perfil:</strong>
		<%=perfil%>
		| <a href="<%=request.getContextPath()%>/logout">Sair</a>
	</p>
	<hr>

	<%
	if (request.getParameter("erro") != null) {
	%>
	<p>
		<strong>Aviso:</strong>
		<%=request.getParameter("erro")%></p>
	<%
	}
	%>

	<h2>Módulos Disponíveis para o seu Perfil</h2>
	<ul>
		<%
		if ("ADMIN".equals(perfil)) {
		%>
		<li><a href="<%=request.getContextPath()%>/admin">Administração
				da Escola (Cadastrar Equipe e Usuários)</a></li>
		<%
		}
		%>

		<%
		if ("ADMIN".equals(perfil) || "SECRETARIA".equals(perfil)) {
		%>
		<li><a href="<%=request.getContextPath()%>/aluno">Secretaria:
				Cadastrar Alunos Neurodivergentes</a></li>
		<li><a href="<%=request.getContextPath()%>/responsavel">Secretaria:
				Cadastrar Responsáveis Legais</a></li>
		<%
		}
		%>

		<li><a href="<%=request.getContextPath()%>/acompanhamento">Fichas
				de Acompanhamento dos Alunos</a></li>

		<%
		if ("ADMIN".equals(perfil) || "PSICOLOGO".equals(perfil)) {
		%>
		<li><a href="<%=request.getContextPath()%>/entrevistas">Psicologia:
				Entrevistas e Atendimentos</a></li>
		<%
		}
		%>

		<%
		if ("ADMIN".equals(perfil) || "PROFESSOR".equals(perfil) || "PEDAGOGO".equals(perfil)) {
		%>
		<li><a href="<%=request.getContextPath()%>/apontamentos">Sala
				de Aula: Apontamentos do Professor</a></li>
		<%
		}
		%>
		<li><a href="<%=request.getContextPath()%>/desempenho">Desempenho
				Mensal (Media vs. Apontamentos)</a></li>

		<%
		if ("ADMIN".equals(perfil) || "DIRETOR".equals(perfil)) {
		%>
		<li><a href="<%=request.getContextPath()%>/diretor">Painel
				do Diretor (Indicadores Gerais)</a></li>
		<%
		}
		%>
	</ul>

</body>
</html>