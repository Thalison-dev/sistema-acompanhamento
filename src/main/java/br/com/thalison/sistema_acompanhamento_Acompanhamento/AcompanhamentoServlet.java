package br.com.thalison.sistema_acompanhamento_Acompanhamento;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.com.thalison.sistema_acompanhamento_Pedagogo.Pedagogo;
import br.com.thalison.sistema_acompanhamento_Pessoa.Pessoa;
import br.com.thalison.sistema_acompanhamento_Psicologo.Psicologo;
import br.com.thalison.sistema_acompanhamento_ResponsavelLegal.ResponsavelLegal;
import br.com.thalison.sistema_acompanhamento_ResponsavelLegal.ResponsavelLegalDAO;
import br.com.thalison.sistema_acompanhamento_aluno.Aluno;
import br.com.thalison.sistema_acompanhamento_aluno.AlunoDAO;

/**
 * Controlador Web responsável pelo fluxo multiprofissional de {@link Acompanhamento}.
 */
@WebServlet("/acompanhamento")
public class AcompanhamentoServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(AcompanhamentoServlet.class.getName());

    private static final String ROTA_BASE = "/acompanhamento";
    private static final String VIEW_JSP = "/acompanhamento.jsp";
    private static final String ATTR_USUARIO_LOGADO = "usuarioLogado";

    private static final Set<String> PERFIS_SECRETARIA = Set.of("ADMIN", "SECRETARIA");
    private static final Set<String> PERFIS_PSICOLOGO = Set.of("ADMIN", "PSICOLOGO");
    private static final Set<String> PERFIS_PEDAGOGO = Set.of("ADMIN", "PEDAGOGO");

    private final AcompanhamentoDAO acompanhamentoDAO;
    private final AlunoDAO alunoDAO;
    private final ResponsavelLegalDAO responsavelDAO;

    public AcompanhamentoServlet() {
        this(new AcompanhamentoDAO(), new AlunoDAO(), new ResponsavelLegalDAO());
    }

    public AcompanhamentoServlet(AcompanhamentoDAO acompanhamentoDAO,
                                 AlunoDAO alunoDAO,
                                 ResponsavelLegalDAO responsavelDAO) {
        this.acompanhamentoDAO = acompanhamentoDAO;
        this.alunoDAO = alunoDAO;
        this.responsavelDAO = responsavelDAO;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());

        if (obterUsuarioLogado(req).isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp?erro=" + encode("Faça login para continuar."));
            return;
        }

        if ("ficha".equals(req.getParameter("acao"))) {
            parseInt(req.getParameter("id"))
                    .filter(id -> id > 0)
                    .flatMap(acompanhamentoDAO::buscarPorId)
                    .ifPresent(ficha -> req.setAttribute("fichaSelecionada", ficha));
        }

        Optional<Integer> alunoIdFiltro = parseInt(req.getParameter("alunoId")).filter(id -> id > 0);
        List<Acompanhamento> acompanhamentos = alunoIdFiltro
                .map(acompanhamentoDAO::listarPorAluno)
                .orElseGet(acompanhamentoDAO::listarTodos);

        req.setAttribute("alunos", alunoDAO.listarTodos());
        req.setAttribute("responsaveis", responsavelDAO.listarTodos());
        req.setAttribute("acompanhamentos", acompanhamentos);
        req.getRequestDispatcher(VIEW_JSP).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding(StandardCharsets.UTF_8.name());
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());

        Optional<Pessoa> usuarioOpt = obterUsuarioLogado(req);
        if (usuarioOpt.isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp?erro=" + encode("Sessão expirada. Faça login novamente."));
            return;
        }

        Pessoa usuario = usuarioOpt.get();
        String acao = Optional.ofNullable(req.getParameter("acao")).orElse("");

        try {
            switch (acao) {
                case "abrirFichaSecretaria" -> abrirFicha(req, resp, usuario);
                case "alterarResponsavel" -> alterarResponsavel(req, resp, usuario);
                case "salvarParecerPsicologo" -> salvarParecerPsicologico(req, resp, usuario);
                case "salvarParecerPedagogo" -> salvarParecerPedagogico(req, resp, usuario);
                case "excluir" -> excluirFicha(req, resp, usuario);
                default -> resp.sendRedirect(req.getContextPath() + ROTA_BASE);
            }
        } catch (SecurityException e) {
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
            redirectComMensagem(resp, req.getContextPath(), ROTA_BASE, "erro", e.getMessage());
        } catch (IllegalArgumentException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            redirectComMensagem(resp, req.getContextPath(), ROTA_BASE, "erro", e.getMessage());
        } catch (DataAccessException e) {
            LOGGER.log(Level.SEVERE, "Falha de persistência no módulo de acompanhamento", e);
            redirectComMensagem(resp, req.getContextPath(), ROTA_BASE, "erro", "Erro ao gravar os dados no banco.");
        }
    }

    private void abrirFicha(HttpServletRequest req, HttpServletResponse resp, Pessoa usuario) throws IOException {
        exigirPerfil(usuario, PERFIS_SECRETARIA, "Apenas Secretaria ou Administração pode abrir fichas.");

        int alunoId = exigirIntPositivo(req.getParameter("alunoId"), "Selecione um aluno válido.");
        int responsavelId = exigirIntPositivo(req.getParameter("responsavelLegalId"), "Selecione um responsável legal válido.");
        LocalDate dataInicio = exigirData(req.getParameter("dataInicio"), "Informe uma data de início válida.");
        LocalDate dataFim = parseData(req.getParameter("dataFim")).orElse(null);
        String objetivo = exigirTexto(req.getParameter("objetivo"), "O motivo/objetivo inicial é obrigatório.");

        Aluno aluno = new Aluno();
        aluno.setId(alunoId);

        ResponsavelLegal responsavel = new ResponsavelLegal();
        responsavel.setId(responsavelId);

        PeriodoAcompanhamento periodo = new PeriodoAcompanhamento(dataInicio, dataFim);

        Acompanhamento acompanhamento = Acompanhamento.builder(aluno, periodo, objetivo)
                .responsavelLegal(responsavel)
                .observacaoGeral(req.getParameter("observacao"))
                .build();

        acompanhamentoDAO.salvar(acompanhamento);
        redirectComMensagem(resp, req.getContextPath(), ROTA_BASE, "sucesso",
                "Ficha #" + acompanhamento.getId() + " aberta e vinculada ao responsável legal!");
    }

    private void alterarResponsavel(HttpServletRequest req, HttpServletResponse resp, Pessoa usuario) throws IOException {
        exigirPerfil(usuario, PERFIS_SECRETARIA, "Sem permissão para alterar o responsável legal.");

        int fichaId = exigirIntPositivo(req.getParameter("id"), "Ficha inválida.");
        int responsavelId = exigirIntPositivo(req.getParameter("responsavelLegalId"), "Selecione um responsável legal válido.");

        Acompanhamento ficha = acompanhamentoDAO.buscarPorId(fichaId)
                .orElseThrow(() -> new IllegalArgumentException("Ficha de acompanhamento não encontrada."));

        ResponsavelLegal novoResponsavel = new ResponsavelLegal();
        novoResponsavel.setId(responsavelId);
        ficha.vincularResponsavelLegal(novoResponsavel);

        boolean ok = acompanhamentoDAO.atualizar(ficha);
        String destino = ROTA_BASE + "?acao=ficha&id=" + fichaId;
        redirectComMensagem(resp, req.getContextPath(), destino,
                ok ? "sucesso" : "erro",
                ok ? "Responsável legal atualizado!" : "Não foi possível atualizar o responsável.");
    }

    private void salvarParecerPsicologico(HttpServletRequest req, HttpServletResponse resp, Pessoa usuario) throws IOException {
        exigirPerfil(usuario, PERFIS_PSICOLOGO, "Apenas Psicólogos ou Administradores podem registrar parecer psicológico.");

        int fichaId = exigirIntPositivo(req.getParameter("id"), "Ficha inválida.");
        String conteudo = exigirTexto(req.getParameter("parecerPsicologico"), "O parecer psicológico é obrigatório.");

        Acompanhamento ficha = acompanhamentoDAO.buscarPorId(fichaId)
                .orElseThrow(() -> new IllegalArgumentException("Ficha de acompanhamento não encontrada."));

        Psicologo psicologo = new Psicologo();
        psicologo.setId(usuario.getId());
        psicologo.setNome(usuario.getNome());

        ficha.registrarParecerPsicologico(psicologo, conteudo, LocalDate.now());

        boolean ok = acompanhamentoDAO.atualizar(ficha);
        String destino = ROTA_BASE + "?acao=ficha&id=" + fichaId;
        redirectComMensagem(resp, req.getContextPath(), destino,
                ok ? "sucesso" : "erro",
                ok ? "Parecer psicológico salvo!" : "Erro ao salvar parecer psicológico.");
    }

    private void salvarParecerPedagogico(HttpServletRequest req, HttpServletResponse resp, Pessoa usuario) throws IOException {
        exigirPerfil(usuario, PERFIS_PEDAGOGO, "Apenas Pedagogos ou Administradores podem registrar parecer pedagógico.");

        int fichaId = exigirIntPositivo(req.getParameter("id"), "Ficha inválida.");
        String objetivo = exigirTexto(req.getParameter("objetivo"), "O objetivo pedagógico é obrigatório.");
        String conteudo = exigirTexto(req.getParameter("parecerPedagogico"), "O parecer pedagógico é obrigatório.");

        Acompanhamento ficha = acompanhamentoDAO.buscarPorId(fichaId)
                .orElseThrow(() -> new IllegalArgumentException("Ficha de acompanhamento não encontrada."));

        Pedagogo pedagogo = new Pedagogo();
        pedagogo.setId(usuario.getId());
        pedagogo.setNome(usuario.getNome());

        ficha.atualizarObjetivo(objetivo);
        ficha.registrarParecerPedagogico(pedagogo, conteudo, LocalDate.now());

        String statusParam = req.getParameter("status");
        if (statusParam != null && !statusParam.isBlank()) {
            ficha.alterarStatus(StatusAcompanhamento.fromValor(statusParam));
        }

        boolean ok = acompanhamentoDAO.atualizar(ficha);
        String destino = ROTA_BASE + "?acao=ficha&id=" + fichaId;
        redirectComMensagem(resp, req.getContextPath(), destino,
                ok ? "sucesso" : "erro",
                ok ? "Parecer pedagógico salvo!" : "Erro ao salvar parecer pedagógico.");
    }

    private void excluirFicha(HttpServletRequest req, HttpServletResponse resp, Pessoa usuario) throws IOException {
        exigirPerfil(usuario, PERFIS_SECRETARIA, "Sem permissão para excluir fichas.");

        int fichaId = exigirIntPositivo(req.getParameter("id"), "ID de ficha inválido.");
        boolean ok = acompanhamentoDAO.excluir(fichaId);

        redirectComMensagem(resp, req.getContextPath(), ROTA_BASE,
                ok ? "sucesso" : "erro",
                ok ? "Ficha excluída com sucesso!" : "Ficha não encontrada para exclusão.");
    }

    private Optional<Pessoa> obterUsuarioLogado(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute(ATTR_USUARIO_LOGADO) instanceof Pessoa pessoa) {
            return Optional.of(pessoa);
        }
        return Optional.empty();
    }

    private void exigirPerfil(Pessoa usuario, Set<String> permitidos, String mensagemErro) {
        if (usuario.getPerfil() == null || !permitidos.contains(usuario.getPerfil())) {
            throw new SecurityException(mensagemErro);
        }
    }

    private Optional<Integer> parseInt(String valor) {
        if (valor == null || valor.isBlank()) {
            return Optional.empty();
        }
        try {
            return Optional.of(Integer.parseInt(valor.trim()));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    private int exigirIntPositivo(String valor, String mensagemErro) {
        return parseInt(valor)
                .filter(n -> n > 0)
                .orElseThrow(() -> new IllegalArgumentException(mensagemErro));
    }

    private Optional<LocalDate> parseData(String valor) {
        if (valor == null || valor.isBlank()) {
            return Optional.empty();
        }
        try {
            return Optional.of(LocalDate.parse(valor.trim()));
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Data em formato inválido. Utilize AAAA-MM-DD.");
        }
    }

    private LocalDate exigirData(String valor, String mensagemErro) {
        return parseData(valor).orElseThrow(() -> new IllegalArgumentException(mensagemErro));
    }

    private String exigirTexto(String valor, String mensagemErro) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException(mensagemErro);
        }
        return valor.trim();
    }

    private void redirectComMensagem(HttpServletResponse resp, String contextPath, String rota,
                                     String tipo, String mensagem) throws IOException {
        String separador = rota.contains("?") ? "&" : "?";
        resp.sendRedirect(contextPath + rota + separador + tipo + "=" + encode(mensagem));
    }

    private String encode(String texto) {
        return URLEncoder.encode(texto, StandardCharsets.UTF_8);
    }
}