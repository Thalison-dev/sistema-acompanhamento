package br.com.thalisson.sistema_documento;

import java.util.Date;

public class Documento {

    private int id;
    private String nomeArquivo;
    private String tipo;
    private Date dataUpload;
    private String versao;
    private String caminhoArquivo;

    public Documento() {
    }

    public Documento(int id, String nomeArquivo, String tipo,
                     Date dataUpload, String versao, String caminhoArquivo) {
        this.id = id;
        this.nomeArquivo = nomeArquivo;
        this.tipo = tipo;
        this.dataUpload = dataUpload;
        this.versao = versao;
        this.caminhoArquivo = caminhoArquivo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Date getDataUpload() {
        return dataUpload;
    }

    public void setDataUpload(Date dataUpload) {
        this.dataUpload = dataUpload;
    }

    public String getVersao() {
        return versao;
    }

    public void setVersao(String versao) {
        this.versao = versao;
    }

    public String getCaminhoArquivo() {
        return caminhoArquivo;
    }

    public void setCaminhoArquivo(String caminhoArquivo) {
        this.caminhoArquivo = caminhoArquivo;
    }
}