package br.com.thalison.sistema_acompanhamento_Apontamento;

import java.time.LocalDate;

import br.com.thalison.sistema_acompanhamento_Professor.Professor;
import br.com.thalison.sistema_acompanhamento_aluno.Aluno;

public class Apontamento {
    private int id;
    private Professor professor;
    private Aluno aluno;
    private LocalDate data;
    private String motivo;

    public Apontamento() {
    }

    public Apontamento(Professor professor, Aluno aluno, LocalDate data, String motivo) {
        this.professor = professor;
        this.aluno = aluno;
        this.data = data;
        this.motivo = motivo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
}