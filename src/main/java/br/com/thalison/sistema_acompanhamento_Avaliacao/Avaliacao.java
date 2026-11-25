package br.com.thalison.sistema_acompanhamento_Avaliacao;

import java.time.LocalDate;

public class Avaliacao {

    private Long id;
    private Double nota;
    private LocalDate data;
    private String tipo;
    private String descricao;
    private String observacao;

    public Avaliacao() {
    }

    public Avaliacao(Long id, Double nota, LocalDate data, String tipo, String descricao, String observacao) {
        this.id = id;
        this.nota = nota;
        this.data = data;
        this.tipo = tipo;
        this.descricao = descricao;
        this.observacao = observacao;
    }

    public void registrarAvaliacao() {
        System.out.println("Avaliação registrada.");
    }

    public void atualizarAvaliacao() {
        System.out.println("Avaliação atualizada.");
    }

    public void consultarAvaliacao() {
        System.out.println("Consultando avaliação.");
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getNota() {
        return nota;
    }

    public void setNota(Double nota) {
        this.nota = nota;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
}