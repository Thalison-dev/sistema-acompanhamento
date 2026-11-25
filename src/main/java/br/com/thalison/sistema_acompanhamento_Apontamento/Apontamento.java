package br.com.thalison.sistema_acompanhamento_Apontamento;

import java.sql.Date;

public class Apontamento {
    private int id;
    private int alunoId;
    private int professorId;
    private Date data;
    private String motivo;
    private String nomeAluno;
    private String nomeProfessor;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getAlunoId() { return alunoId; }
    public void setAlunoId(int alunoId) { this.alunoId = alunoId; }
    public int getProfessorId() { return professorId; }
    public void setProfessorId(int professorId) { this.professorId = professorId; }
    public Date getData() { return data; }
    public void setData(Date data) { this.data = data; }
    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }
    public String getNomeAluno() { return nomeAluno; }
    public void setNomeAluno(String nomeAluno) { this.nomeAluno = nomeAluno; }
    public String getNomeProfessor() { return nomeProfessor; }
    public void setNomeProfessor(String nomeProfessor) { this.nomeProfessor = nomeProfessor; }
}