<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Registro do Professor</title>
    <link rel="stylesheet" href="${ctx}/css/style.css">
</head>
<body>

<jsp:include page="/WEB-INF/includes/navbar.jsp"><jsp:param name="ativo" value="professores" /></jsp:include>

<div class="container">
    <jsp:include page="/WEB-INF/includes/mensagens.jsp" />

    <h2>
        <c:choose>
            <c:when test="${tipoRegistro == 'avaliacao'}">Registrar Avaliação</c:when>
            <c:when test="${tipoRegistro == 'adaptacao'}">Registrar Adaptação Metodológica</c:when>
            <c:otherwise>Registrar Observação / Ocorrência</c:otherwise>
        </c:choose>
    </h2>

    <div class="actions" style="margin-top:0; margin-bottom:20px">
        <a href="${ctx}/professor?acao=registrar&tipo=ocorrencia&alunoId=${alunoSelecionado}" class="btn btn-sm ${tipoRegistro == 'ocorrencia' ? 'btn-primary' : 'btn-secondary'}">Observação / Ocorrência</a>
        <a href="${ctx}/professor?acao=registrar&tipo=avaliacao&alunoId=${alunoSelecionado}" class="btn btn-sm ${tipoRegistro == 'avaliacao' ? 'btn-primary' : 'btn-secondary'}">Avaliação</a>
        <a href="${ctx}/professor?acao=registrar&tipo=adaptacao&alunoId=${alunoSelecionado}" class="btn btn-sm ${tipoRegistro == 'adaptacao' ? 'btn-primary' : 'btn-secondary'}">Adaptação</a>
    </div>

    <form action="${ctx}/professor" method="post">
        <c:choose>
            <c:when test="${tipoRegistro == 'avaliacao'}"><input type="hidden" name="acao" value="registrarAvaliacao"></c:when>
            <c:when test="${tipoRegistro == 'adaptacao'}"><input type="hidden" name="acao" value="registrarAdaptacao"></c:when>
            <c:otherwise><input type="hidden" name="acao" value="registrarOcorrencia"></c:otherwise>
        </c:choose>

        <div class="form-grid">
            <div class="form-group">
                <label for="alunoId">Aluno <span class="obrigatorio">*</span></label>
                <select id="alunoId" name="alunoId" required>
                    <option value="">-- Selecione --</option>
                    <c:forEach var="a" items="${alunos}">
                        <option value="${a.id}" ${alunoSelecionado == a.id ? 'selected' : ''}>ID ${a.id} - <c:out value="${a.nome}" /></option>
                    </c:forEach>
                </select>
            </div>

            <c:if test="${tipoRegistro != 'avaliacao'}">
            <div class="form-group">
                <label for="professorId">Professor</label>
                <select id="professorId" name="professorId">
                    <option value="0">-- Selecione --</option>
                    <c:forEach var="p" items="${professores}">
                        <option value="${p.id}" ${professorSelecionado == p.id ? 'selected' : ''}><c:out value="${p.nome}" /></option>
                    </c:forEach>
                </select>
            </div>
            </c:if>

            <c:choose>
            <c:when test="${tipoRegistro == 'avaliacao'}">
                <div class="form-group">
                    <label for="disciplinaId">Disciplina</label>
                    <select id="disciplinaId" name="disciplinaId">
                        <option value="0">-- Selecione --</option>
                        <c:forEach var="d" items="${disciplinas}">
                            <option value="${d.id}"><c:out value="${d.nome}" /></option>
                        </c:forEach>
                    </select>
                    <c:if test="${empty disciplinas}"><small class="muted">Cadastre disciplinas na <a href="${ctx}/professor">Área do Professor</a>.</small></c:if>
                </div>
                <div class="form-group">
                    <label for="tipo">Tipo de avaliação</label>
                    <select id="tipo" name="tipo">
                        <c:forEach var="ta" items="Prova,Trabalho,Atividade adaptada,Avaliação diagnóstica,Participação">
                            <option value="${ta}">${ta}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label for="nota">Nota</label>
                    <input type="number" id="nota" name="nota" step="0.1" min="0" max="10">
                </div>
                <div class="form-group">
                    <label for="data">Data</label>
                    <input type="date" id="data" name="data">
                </div>
                <div class="form-group full">
                    <label for="observacao">Observação</label>
                    <textarea id="observacao" name="observacao" rows="3"></textarea>
                </div>
            </c:when>

            <c:when test="${tipoRegistro == 'adaptacao'}">
                <div class="form-group">
                    <label for="tipo">Tipo de adaptação</label>
                    <select id="tipo" name="tipo">
                        <c:forEach var="ta" items="Curricular,Avaliativa,De acesso,Metodológica,Temporal">
                            <option value="${ta}">${ta}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label for="dataRegistro">Data</label>
                    <input type="date" id="dataRegistro" name="dataRegistro">
                </div>
                <div class="form-group full">
                    <label for="descricao">Descrição <span class="obrigatorio">*</span></label>
                    <textarea id="descricao" name="descricao" rows="3" required></textarea>
                </div>
                <div class="form-group full">
                    <label for="estrategia">Estratégia</label>
                    <textarea id="estrategia" name="estrategia" rows="2"></textarea>
                </div>
                <div class="form-group">
                    <label for="resultado">Resultado</label>
                    <input type="text" id="resultado" name="resultado">
                </div>
                <div class="form-group">
                    <label for="status">Status</label>
                    <select id="status" name="status">
                        <c:forEach var="s" items="Em aplicação,Eficaz,Ineficaz,Concluído">
                            <option value="${s}">${s}</option>
                        </c:forEach>
                    </select>
                </div>
            </c:when>

            <c:otherwise>
                <div class="form-group">
                    <label for="tipo">Tipo <span class="obrigatorio">*</span></label>
                    <select id="tipo" name="tipo" required>
                        <c:forEach var="ta" items="Observação,Ocorrência,Rendimento,Comportamento">
                            <option value="${ta}">${ta}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="form-group">
                    <label for="data">Data</label>
                    <input type="date" id="data" name="data">
                </div>
                <div class="form-group full">
                    <label for="descricao">Descrição <span class="obrigatorio">*</span></label>
                    <textarea id="descricao" name="descricao" rows="4" required></textarea>
                </div>
                <div class="form-group full">
                    <label for="providencia">Providência tomada</label>
                    <textarea id="providencia" name="providencia" rows="2"></textarea>
                </div>
                <div class="form-group">
                    <label for="status">Status</label>
                    <select id="status" name="status">
                        <c:forEach var="s" items="Aberta,Em análise,Resolvida">
                            <option value="${s}">${s}</option>
                        </c:forEach>
                    </select>
                </div>
            </c:otherwise>
            </c:choose>
        </div>

        <div class="actions">
            <button type="submit" class="btn btn-success">Registrar</button>
            <a href="${ctx}/professor" class="btn btn-secondary">Cancelar</a>
        </div>
    </form>
</div>

</body>
</html>
