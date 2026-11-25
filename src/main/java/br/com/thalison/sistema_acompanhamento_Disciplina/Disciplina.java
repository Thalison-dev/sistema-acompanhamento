package br.com.thalison.sistema_acompanhamento_Disciplina;

public class Disciplina {

    private int id;
    private String nome;
    private String descricao;

    public Disciplina() {
    }

    public Disciplina(int id, String nome, String descricao) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
    }

    public void consultarDisciplina() {
        System.out.println("Consultando disciplina.");
    }

    public void atualizarDisciplina() {
        System.out.println("Disciplina atualizada.");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
