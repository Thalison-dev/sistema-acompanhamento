package br.com.thalison.sistema_acompanhamento_Apontamento;

/**
 * Apontamento: indicação, feita por um professor, de que um aluno
 * precisa ser acompanhado. Pode dar origem a um Acompanhamento.
 */
public class Apontamento {

    private int id;
    private int alunoId;       // coluna id_pessoa (o id do aluno é o id da pessoa)
    private String nomeAluno;
    private int professorId;   // coluna id_professor (opcional)
    private String dataInicio;
    private String nomeProfessor;
    private String motivo;

    public Apontamento() {
    }

    public Apontamento(int alunoId, String dataInicio, String nomeProfessor, String motivo) {
        this.alunoId = alunoId;
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

    public String getNomeAluno() {
        return nomeAluno;
    }

    public void setNomeAluno(String nomeAluno) {
        this.nomeAluno = nomeAluno;
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
