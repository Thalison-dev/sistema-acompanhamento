package br.com.thalison.sistema_Acompanhamento_entrevista;

import java.time.LocalDate;

public class Entrevista {
    private int id;
    private int alunoId;
    private String nomeAluno;
    private int psicologoId;
    private String nomePsicologo;
    private String motivo;
    private boolean agendada;
    private LocalDate data;
    private String observacoes;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getAlunoId() { return alunoId; }
    public void setAlunoId(int alunoId) { this.alunoId = alunoId; }
    public String getNomeAluno() { return nomeAluno; }
    public void setNomeAluno(String nomeAluno) { this.nomeAluno = nomeAluno; }
    public int getPsicologoId() { return psicologoId; }
    public void setPsicologoId(int psicologoId) { this.psicologoId = psicologoId; }
    public String getNomePsicologo() { return nomePsicologo; }
    public void setNomePsicologo(String nomePsicologo) { this.nomePsicologo = nomePsicologo; }
    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }
    public boolean isAgendada() { return agendada; }
    public void setAgendada(boolean agendada) { this.agendada = agendada; }
    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }
    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }
}