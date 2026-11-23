package br.com.thalison.sistema_acompanhamento_adaptacao;

/** Adaptação metodológica aplicada a um aluno (tabela adaptacao_metodologica). */
public class adaptacao_metodologica {

    private int id;
    private int alunoId;
    private int professorId;
    private String nomeProfessor;
    private String tipo;
    private String descricao;
    private String estrategia;
    private String dataRegistro;
    private String resultado;
    private String status;

    public adaptacao_metodologica() {
    }

    public adaptacao_metodologica(int id, String descricao, String estrategia, String dataRegistro,
                                  String resultado, String status) {
        this.id = id;
        this.descricao = descricao;
        this.estrategia = estrategia;
        this.dataRegistro = dataRegistro;
        this.resultado = resultado;
        this.status = status;
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

    public String getNomeProfessor() {
        return nomeProfessor;
    }

    public void setNomeProfessor(String nomeProfessor) {
        this.nomeProfessor = nomeProfessor;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getEstrategia() {
        return estrategia;
    }

    public void setEstrategia(String estrategia) {
        this.estrategia = estrategia;
    }

    public String getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(String dataRegistro) {
        this.dataRegistro = dataRegistro;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
