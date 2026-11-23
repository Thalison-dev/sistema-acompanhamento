package br.com.thalison.sistema_acompanhamento;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Pequenos utilitários compartilhados pelos servlets. */
public final class WebUtil {

    private WebUtil() {
    }

    public static int parseInt(String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            return 0;
        }
        try {
            return Integer.parseInt(valor.trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static double parseDouble(String valor) {
        if (valor == null || valor.trim().isEmpty()) {
            return 0;
        }
        try {
            return Double.parseDouble(valor.trim().replace(',', '.'));
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    /** Retorna o parâmetro sem espaços nas pontas, ou null se estiver vazio. */
    public static String texto(HttpServletRequest req, String nome) {
        String v = req.getParameter(nome);
        return (v == null || v.trim().isEmpty()) ? null : v.trim();
    }

    public static boolean vazio(String valor) {
        return valor == null || valor.trim().isEmpty();
    }

    /** Redireciona para caminho (relativo ao contexto) adicionando ?sucesso= ou ?erro=. */
    public static void redirecionar(HttpServletRequest req, HttpServletResponse resp,
                                    String caminho, String tipo, String mensagem) throws IOException {
        String separador = caminho.contains("?") ? "&" : "?";
        resp.sendRedirect(req.getContextPath() + caminho + separador + tipo + "=" + codificar(mensagem));
    }

    private static String codificar(String texto) {
        try {
            return URLEncoder.encode(texto, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return texto;
        }
    }
}
