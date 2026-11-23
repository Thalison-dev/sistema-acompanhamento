package br.com.thalison.sistema_acompanhamento_mensagem;

import java.time.LocalDateTime;

public class Mensagem {

    private long id;
    private String conteudo;
    private LocalDateTime dataEnvio;
    private Boolean lida;

    public Mensagem() {
    }

    public Mensagem(long id, String conteudo, LocalDateTime dataEnvio, Boolean lida) {
        this.id = id;
        this.conteudo = conteudo;
        this.dataEnvio = dataEnvio;
        this.lida = lida;
    }

    public void enviar() {
        System.out.println("Mensagem enviada.");
    }

    public void receber() {
        System.out.println("Mensagem recebida.");
    }

    public void marcarComoLida() {
        this.lida = true;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public LocalDateTime getDataEnvio() {
        return dataEnvio;
    }

    public void setDataEnvio(LocalDateTime dataEnvio) {
        this.dataEnvio = dataEnvio;
    }

    public Boolean getLida() {
        return lida;
    }

    public void setLida(Boolean lida) {
        this.lida = lida;
    }
}
