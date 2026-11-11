<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Cadastrar Apontamento</title>
</head>
<body>
<h1>Cadastrar Apontamento</h1>
<form action="${pageContext.request.contextPath}/apontamento" method="post">
	<input type="hidden" name="acao" valeu="cadastrar">
	
	<label for="dataInicio">Data de inicio</label><br>
	<input type="date" id="dataInicio" name="dataInicio" required>
	<br><br>
	
	<label for="nomeProfessor">Nome do professor</label><br>
	<input type="text" id="nomeProfessor" name="nomeProfessor maxlength="100" required">
	<br><br>
	
	<label for="idPessoa">ID da Pessoa:</label><br>
	<input type="number" id="idPessoa" name="idPessoa" min="1" required>
	<br><br>
	
	<label for="idProfessor">ID do Professor</label><br>
	<input type="number" id="idProfessor" name="idProfessor" min="1" required>
	<br><br>
	
	<button type="submit">Cadastrar</button>	
	
</form>
</body>
</html>