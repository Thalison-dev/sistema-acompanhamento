package br.com.thalison.sistema_acompanhamento_Avaliacao;

import java.time.LocalDate;

public class avaliacao {

    private int id;
    private String descricao;
    private double nota;
    private LocalDate data;
    private String observacao;

    public avaliacao() {
    }

    public avaliacao(int id, String descricao, double nota,
                     LocalDate data, String observacao) {
        this.id = id;
        this.descricao = descricao;
        this.nota = nota;
        this.data = data;
        this.observacao = observacao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
}
