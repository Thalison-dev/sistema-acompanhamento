
package br.com.thalison.sistema_acompanhamento_neurodivergente;

public class Neurodivergente {

    private int id;
    private int alunoId;
    private String tipo;
    private String descricao;
    private String observacoes;
    private String dataRegistro;

    public Neurodivergente() {
    }

    public Neurodivergente(int id, int alunoId, String tipo,
            String descricao, String observacoes, String dataRegistro) {

        this.id = id;
        this.alunoId = alunoId;
        this.tipo = tipo;
        this.descricao = descricao;
        this.observacoes = observacoes;
        this.dataRegistro = dataRegistro;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAlunoId() {
        return alunoId;
    }

    public void setAlunoId(int alunoId) {
        this.alunoId = alunoId;
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

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public String getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(String dataRegistro) {
        this.dataRegistro = dataRegistro;
    }
}
