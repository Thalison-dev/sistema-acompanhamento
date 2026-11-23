<%@ tag pageEncoding="UTF-8" body-content="empty" %>
<%@ attribute name="valor" required="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%-- Etiqueta colorida de status: <t:status valor="${a.status}"/> --%>
<c:choose>
    <c:when test="${valor == 'Pendente'}"><c:set var="classe" value="badge-pendente" /></c:when>
    <c:when test="${valor == 'Agendada'}"><c:set var="classe" value="badge-agendada" /></c:when>
    <c:when test="${valor == 'Aberta'}"><c:set var="classe" value="badge-aberta" /></c:when>
    <c:when test="${valor == 'Em Andamento' || valor == 'Em aplicação' || valor == 'Em análise'}"><c:set var="classe" value="badge-andamento" /></c:when>
    <c:when test="${valor == 'Concluído' || valor == 'Realizada' || valor == 'Resolvida' || valor == 'Ativo' || valor == 'Eficaz'}"><c:set var="classe" value="badge-concluido" /></c:when>
    <c:when test="${valor == 'Cancelado' || valor == 'Cancelada' || valor == 'Inativo' || valor == 'Ineficaz'}"><c:set var="classe" value="badge-cancelado" /></c:when>
    <c:otherwise><c:set var="classe" value="" /></c:otherwise>
</c:choose>
<span class="badge ${classe}"><c:out value="${empty valor ? '-' : valor}" /></span>
