package br.com.thalison.sistema_acompanhamento_Acompanhamento;

import java.time.LocalDate;

import br.com.thalison.sistema_acompanhamento_aluno.Aluno;

public class Acompanhamento {

    private int id;
    private Aluno aluno;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private String objetivo;
    private String status;

    public Acompanhamento() {
    }

    public Acompanhamento(Aluno aluno, LocalDate dataInicio, LocalDate dataFim,
                          String objetivo, String status) {
        this.aluno = aluno;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.objetivo = objetivo;
        this.status = status;
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

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
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