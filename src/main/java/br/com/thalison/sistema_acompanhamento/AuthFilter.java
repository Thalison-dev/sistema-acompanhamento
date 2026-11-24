package br.com.thalison.sistema_acompanhamento;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
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

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Inicialização padrão do filtro
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String contextPath = req.getContextPath();
        String requestUri = req.getRequestURI();
        String path = (requestUri != null && requestUri.startsWith(contextPath))
                ? requestUri.substring(contextPath.length())
                : requestUri;

        if (path == null || path.isEmpty()) {
            path = "/";
        }

        if (path.equals("/")
                || path.equals("/index.jsp")
                || path.equals("/login.jsp")
                || path.equals("/login")
                || path.equals("/logout")) {
            chain.doFilter(request, response);
            return;
        }

        HttpSession sessao = req.getSession(false);
        Pessoa usuario = (sessao != null) ? (Pessoa) sessao.getAttribute("usuarioLogado") : null;

        if (usuario == null) {
            resp.sendRedirect(contextPath + "/login.jsp?erro=Faca+login+para+continuar");
            return;
        }

        String perfil = (usuario.getPerfil() != null) ? usuario.getPerfil().trim().toUpperCase() : "";

        if ((path.startsWith("/admin") || path.equals("/admin.jsp"))
                && !"ADMIN".equals(perfil)) {
            resp.sendRedirect(contextPath + "/painel.jsp?erro=Acesso+restrito+a+Direcao+Admin");
            return;
        }

        if ((path.startsWith("/aluno") || path.startsWith("/responsavel")
                || path.equals("/alunos.jsp") || path.equals("/responsaveis.jsp"))
                && !("ADMIN".equals(perfil) || "SECRETARIA".equals(perfil))) {
            resp.sendRedirect(contextPath + "/painel.jsp?erro=Acesso+restrito+a+Secretaria+ou+Admin");
            return;
        }

        if ((path.startsWith("/entrevistas") || path.equals("/entrevistas.jsp"))
                && !("ADMIN".equals(perfil) || "PSICOLOGO".equals(perfil))) {
            resp.sendRedirect(contextPath + "/painel.jsp?erro=Acesso+restrito+ao+Psicologo");
            return;
        }

        if ((path.startsWith("/desempenho") || path.equals("/desempenho.jsp"))
                && !("ADMIN".equals(perfil) || "PEDAGOGO".equals(perfil)
                || "PSICOLOGO".equals(perfil) || "PROFESSOR".equals(perfil))) {
            resp.sendRedirect(contextPath + "/painel.jsp?erro=Acesso+restrito+a+equipe+pedagogica+e+clinica");
            return;
        }

        if ((path.startsWith("/apontamentos") || path.equals("/apontamentos.jsp"))
                && !("ADMIN".equals(perfil) || "PROFESSOR".equals(perfil) || "PEDAGOGO".equals(perfil))) {
            resp.sendRedirect(contextPath + "/painel.jsp?erro=Acesso+restrito+a+Professores+e+Pedagogos");
            return;
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // Liberação de recursos ao encerrar o filtro
    }
}