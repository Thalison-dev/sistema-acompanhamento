package br.com.thalison.sistema_acompanhamento_Apontamento;

public class Apontamento {
    private int id;
    private int alunoId;
    private int professorId;
    private String dataInicio;
    private String nomeProfessor;
    private String motivo;

    public Apontamento() {
    }

    public Apontamento(int alunoId, int professorId, String dataInicio, String nomeProfessor, String motivo) {
        this.alunoId = alunoId;
        this.professorId = professorId;
        this.dataInicio = dataInicio;
        this.nomeProfessor = nomeProfessor;
        this.motivo = motivo;
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

    public int getProfessorId() {
        return professorId;
    }

    public void setProfessorId(int professorId) {
        this.professorId = professorId;
    }

    public String getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(String dataInicio) {
        this.dataInicio = dataInicio;
    }

    public String getNomeProfessor() {
        return nomeProfessor;
    }

    public void setNomeProfessor(String nomeProfessor) {
        this.nomeProfessor = nomeProfessor;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
}
