package br.com.thalison.sistema_acompanhamento;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import br.com.thalison.sistema_acompanhamento_Pessoa.Pessoa;

@WebFilter("/*")
public class AuthFilter implements Filter {

    private static final Map<String, List<String>> REGRAS_POR_ROTA = new HashMap<>();

    static {
        REGRAS_POR_ROTA.put("/admin", Arrays.asList("ADMIN"));
        REGRAS_POR_ROTA.put("/admin.jsp", Arrays.asList("ADMIN"));

        REGRAS_POR_ROTA.put("/aluno", Arrays.asList("ADMIN", "SECRETARIA"));
        REGRAS_POR_ROTA.put("/alunos.jsp", Arrays.asList("ADMIN", "SECRETARIA"));
        REGRAS_POR_ROTA.put("/responsavel", Arrays.asList("ADMIN", "SECRETARIA"));
        REGRAS_POR_ROTA.put("/responsaveis.jsp", Arrays.asList("ADMIN", "SECRETARIA"));

        REGRAS_POR_ROTA.put("/acompanhamento", Arrays.asList("ADMIN", "SECRETARIA", "PSICOLOGO", "PEDAGOGO", "PROFESSOR"));
        REGRAS_POR_ROTA.put("/acompanhamento.jsp", Arrays.asList("ADMIN", "SECRETARIA", "PSICOLOGO", "PEDAGOGO", "PROFESSOR"));

        REGRAS_POR_ROTA.put("/entrevistas", Arrays.asList("ADMIN", "PSICOLOGO"));
        REGRAS_POR_ROTA.put("/entrevista.jsp", Arrays.asList("ADMIN", "PSICOLOGO"));

        REGRAS_POR_ROTA.put("/apontamentos", Arrays.asList("ADMIN", "PROFESSOR", "PEDAGOGO"));
        REGRAS_POR_ROTA.put("/apontamentos.jsp", Arrays.asList("ADMIN", "PROFESSOR", "PEDAGOGO"));

        REGRAS_POR_ROTA.put("/desempenho", Arrays.asList("ADMIN", "SECRETARIA", "PSICOLOGO", "PEDAGOGO", "PROFESSOR"));
        REGRAS_POR_ROTA.put("/desempenho.jsp", Arrays.asList("ADMIN", "SECRETARIA", "PSICOLOGO", "PEDAGOGO", "PROFESSOR"));
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String contextPath = req.getContextPath();
        String uri = req.getRequestURI();
        String path = uri.startsWith(contextPath) ? uri.substring(contextPath.length()) : uri;

        if (path.equals("/") || path.equals("/index.jsp") || path.equals("/login.jsp") || path.equals("/login")
                || path.startsWith("/css/") || path.startsWith("/js/") || path.startsWith("/images/")) {
            chain.doFilter(request, response);
            return;
        }

        HttpSession session = req.getSession(false);
        Pessoa usuario = (session != null) ? (Pessoa) session.getAttribute("usuarioLogado") : null;

        if (usuario == null) {
            resp.sendRedirect(contextPath + "/login.jsp?erro=Faca+login+para+continuar");
            return;
        }

        for (Map.Entry<String, List<String>> entry : REGRAS_POR_ROTA.entrySet()) {
            if (path.equals(entry.getKey()) || path.startsWith(entry.getKey() + "/")) {
                if (!entry.getValue().contains(usuario.getPerfil())) {
                    resp.sendRedirect(contextPath + "/painel.jsp?erro=Acesso+negado+para+o+perfil+" + usuario.getPerfil());
                    return;
                }
                break;
            }
        }

        chain.doFilter(request, response);
    }
}