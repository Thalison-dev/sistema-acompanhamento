package br.com.thalison.sistema_acompanhamento_mensagem;

/** Mensagem trocada entre pessoas do sistema (tabela mensagem). */
public class mensagem {

    private long id;
    private int remetenteId;
    private int destinatarioId;
    private String conteudo;
    private String dataEnvio;
    private boolean lida;

    public mensagem() {
    }

    public mensagem(long id, String conteudo, String dataEnvio, boolean lida) {
        this.id = id;
        this.conteudo = conteudo;
        this.dataEnvio = dataEnvio;
        this.lida = lida;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getRemetenteId() {
        return remetenteId;
    }

    public void setRemetenteId(int remetenteId) {
        this.remetenteId = remetenteId;
    }

    public int getDestinatarioId() {
        return destinatarioId;
    }

    public void setDestinatarioId(int destinatarioId) {
        this.destinatarioId = destinatarioId;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public String getDataEnvio() {
        return dataEnvio;
    }

    public void setDataEnvio(String dataEnvio) {
        this.dataEnvio = dataEnvio;
    }

    public boolean isLida() {
        return lida;
    }

    public void setLida(boolean lida) {
        this.lida = lida;
    }
}
