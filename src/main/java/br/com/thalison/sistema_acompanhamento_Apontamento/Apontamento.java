package br.com.thalison.sistema_acompanhamento_Apontamento;

import java.time.LocalDate;

public class Apontamento {
    private int id;
    private int alunoId;
    private String nomeAluno;
    private int professorId;
    private String nomeProfessor;
    private LocalDate data;
    private String motivo;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getAlunoId() { return alunoId; }
    public void setAlunoId(int alunoId) { this.alunoId = alunoId; }
    public String getNomeAluno() { return nomeAluno; }
    public void setNomeAluno(String nomeAluno) { this.nomeAluno = nomeAluno; }
    public int getProfessorId() { return professorId; }
    public void setProfessorId(int professorId) { this.professorId = professorId; }
    public String getNomeProfessor() { return nomeProfessor; }
    public void setNomeProfessor(String nomeProfessor) { this.nomeProfessor = nomeProfessor; }
    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }
    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }
}