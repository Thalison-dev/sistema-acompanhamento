<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%-- Barra de navegação única. Uso:
     <jsp:include page="/WEB-INF/includes/navbar.jsp"><jsp:param name="ativo" value="alunos"/></jsp:include> --%>
<div class="navbar">
    <a href="${pageContext.request.contextPath}/index.jsp" class="${param.ativo == 'inicio' ? 'active' : ''}">Início</a>
    <a href="${pageContext.request.contextPath}/aluno" class="${param.ativo == 'alunos' ? 'active' : ''}">Alunos</a>
    <a href="${pageContext.request.contextPath}/acompanhamento" class="${param.ativo == 'acompanhamentos' ? 'active' : ''}">Acompanhamentos</a>
    <a href="${pageContext.request.contextPath}/apontamento" class="${param.ativo == 'apontamentos' ? 'active' : ''}">Apontamentos</a>
    <a href="${pageContext.request.contextPath}/neurodivergencia" class="${param.ativo == 'neuro' ? 'active' : ''}">Neurodivergência</a>
    <a href="${pageContext.request.contextPath}/professor" class="${param.ativo == 'professores' ? 'active' : ''}">Professores</a>
    <a href="${pageContext.request.contextPath}/pedagogo" class="${param.ativo == 'pedagogos' ? 'active' : ''}">Pedagogos</a>
    <a href="${pageContext.request.contextPath}/psicologo" class="${param.ativo == 'psicologos' ? 'active' : ''}">Psicólogos</a>
    <a href="${pageContext.request.contextPath}/listar-entrevistas" class="${param.ativo == 'entrevistas' ? 'active' : ''}">Entrevistas</a>
    <a href="${pageContext.request.contextPath}/responsavel" class="${param.ativo == 'responsaveis' ? 'active' : ''}">Responsáveis</a>
    <a href="${pageContext.request.contextPath}/reuniao" class="${param.ativo == 'reunioes' ? 'active' : ''}">Reuniões</a>
    <a href="${pageContext.request.contextPath}/diretor" class="${param.ativo == 'diretor' ? 'active' : ''}">Diretor</a>
</div>
<script src="${pageContext.request.contextPath}/js/script.js"></script>
