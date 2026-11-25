package br.com.thalison.sistema_acompanhamento_mensagem;

import java.sql.Timestamp;

public class Mensagem {
    private int id;
    private int remetenteId;
    private int destinatarioId;
    private String conteudoMensagem;
    private Timestamp dataEnvioMensagem;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getRemetenteId() { return remetenteId; }
    public void setRemetenteId(int remetenteId) { this.remetenteId = remetenteId; }
    public int getDestinatarioId() { return destinatarioId; }
    public void setDestinatarioId(int destinatarioId) { this.destinatarioId = destinatarioId; }
    public String getConteudoMensagem() { return conteudoMensagem; }
    public void setConteudoMensagem(String conteudoMensagem) { this.conteudoMensagem = conteudoMensagem; }
    public Timestamp getDataEnvioMensagem() { return dataEnvioMensagem; }
    public void setDataEnvioMensagem(Timestamp dataEnvioMensagem) { this.dataEnvioMensagem = dataEnvioMensagem; }
}