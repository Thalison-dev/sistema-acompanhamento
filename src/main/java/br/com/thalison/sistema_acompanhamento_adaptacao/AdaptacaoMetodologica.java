package br.com.thalison.sistema_acompanhamento_adaptacao;

import java.time.LocalDate;

public class AdaptacaoMetodologica {

    private int id;
    private String descricao;
    private String estrategia;
    private LocalDate dataRegistro;
    private String resultado;
    private String status;

    public AdaptacaoMetodologica() {
    }

    public AdaptacaoMetodologica(int id, String descricao, String estrategia, LocalDate dataRegistro,
                                 String resultado, String status) {
        this.id = id;
        this.descricao = descricao;
        this.estrategia = estrategia;
        this.dataRegistro = dataRegistro;
        this.resultado = resultado;
        this.status = status;
    }

    public void registrar() {
        System.out.println("Adaptação metodológica registrada.");
    }

    public void atualizar() {
        System.out.println("Adaptação metodológica atualizada.");
    }

    public void consultar() {
        System.out.println("Consultando adaptação metodológica.");
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

    public String getEstrategia() {
        return estrategia;
    }

    public void setEstrategia(String estrategia) {
        this.estrategia = estrategia;
    }

    public LocalDate getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(LocalDate dataRegistro) {
        this.dataRegistro = dataRegistro;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
