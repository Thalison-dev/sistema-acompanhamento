<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%-- Mostra ?sucesso=... e ?erro=... enviados pelos servlets --%>
<c:if test="${not empty param.sucesso}">
    <div class="alert-success" data-auto-fechar><strong>Sucesso:</strong> <c:out value="${param.sucesso}" /></div>
</c:if>
<c:if test="${not empty param.erro}">
    <div class="alert-error"><strong>Erro:</strong> <c:out value="${param.erro}" /></div>
</c:if>
