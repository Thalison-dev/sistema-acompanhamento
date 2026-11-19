package br.com.thalison.sistema_acompanhamento_Acompanhamento;

import br.com.thalison.sistema_acompanhamento_aluno.Aluno;

public class Acompanhamento {

    private String id;
    private Aluno aluno;
    private String dataInicio;
    private String dataFim;
    private String objetivo;
    private String status;

    public Acompanhamento() {
    }

    public Acompanhamento(String id, Aluno aluno, String dataInicio, String dataFim,
                          String objetivo, String status) {
        this.id = id;
        this.aluno = aluno;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.objetivo = objetivo;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
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