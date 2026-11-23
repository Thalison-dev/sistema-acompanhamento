package br.com.thalison.sistema_acompanhamento_Professor;

/** Observação, ocorrência ou registro de rendimento feito pelo professor (tabela ocorrencia_pedagogica). */
public class OcorrenciaPedagogica {

    private int id;
    private int alunoId;
    private String nomeAluno;
    private int professorId;
    private String nomeProfessor;
    private String data;
    private String tipo;
    private String descricao;
    private String providencia;
    private String status;

    public OcorrenciaPedagogica() {
    }

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

    public String getData() { return data; }
    public void setData(String data) { this.data = data; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getProvidencia() { return providencia; }
    public void setProvidencia(String providencia) { this.providencia = providencia; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
