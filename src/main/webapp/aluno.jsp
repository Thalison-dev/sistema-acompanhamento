<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- Página antiga de cadastro: o formulário completo agora é o cadastroAluno.jsp (via servlet /aluno). --%>
<%
    response.sendRedirect(request.getContextPath() + "/aluno?acao=novo");
%>
