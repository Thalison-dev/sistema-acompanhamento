package br.com.thalison.sistema_Acompanhamento_entrevista;

import br.com.thalison.sistema_acompanhamento_Psicologo.Psicologo;
import br.com.thalison.sistema_acompanhamento_aluno.Aluno;
import java.util.Date;

public class Entrevista {

    private Aluno aluno;
    private Psicologo psicologo;
    private int id;
    private String motivo;
    private boolean agendada;
    private Date data;
    private String observacoes;

    public Entrevista() {
    }

    public Entrevista(Aluno aluno, Psicologo psicologo, int id, String motivo, boolean agendada, Date data, String observacoes) {
        this.aluno = aluno;
        this.psicologo = psicologo;
        this.id = id;
        this.motivo = motivo;
        this.agendada = agendada;
        this.data = data;
        this.observacoes = observacoes;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public int getAlunoId() {
        return aluno.getId();
    }
    
    public Psicologo getPsicologo() {
        return psicologo;
    }

    public void setPsicologo(Psicologo psicologo) {
        this.psicologo = psicologo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public boolean isAgendada() {
        return agendada;
    }

    public void setAgendada(boolean agendada) {
        this.agendada = agendada;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }
    
   
}