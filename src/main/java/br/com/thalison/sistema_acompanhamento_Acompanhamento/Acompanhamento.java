package br.com.thalison.sistema_acompanhamento_Acompanhamento;

public class Acompanhamento {

    private int id;
    private int alunoId;
    private String dataInicio;
    private String dataFim;
    private String objetivo;
    private String observacao;
    private String status;

    public Acompanhamento() {
    }

    public Acompanhamento(int alunoId, String dataInicio, String dataFim,
                          String objetivo, String status) {
        this.alunoId = alunoId;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.objetivo = objetivo;
        this.status = status;
    }

    public void criar() {
        System.out.println("Acompanhamento criado.");
    }

    public void atualizar() {
        System.out.println("Acompanhamento atualizado.");
    }

    public void finalizar() {
        this.status = "Concluído";
    }

    public void consultarHistorico() {
        System.out.println("Consultando histórico do acompanhamento.");
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAcompanhamentoId() {
        return id;
    }

    public void setAcompanhamentoId(int id) {
        this.id = id;
    }

    public int getAlunoId() {
        return alunoId;
    }

    public void setAlunoId(int alunoId) {
        this.alunoId = alunoId;
    }

    public String getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(String dataInicio) {
        this.dataInicio = dataInicio;
    }

    public String getDataFim() {
        return dataFim;
    }

    public void setDataFim(String dataFim) {
        this.dataFim = dataFim;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}