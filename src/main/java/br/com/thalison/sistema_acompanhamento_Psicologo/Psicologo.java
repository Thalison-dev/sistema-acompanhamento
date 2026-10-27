package br.com.thalison.sistema_acompanhamento_Psicologo;

public class Psicologo {

    private int id;
    private String nome;
    private String crp;
    private String especialidade;

    public Psicologo() {
    }

    public Psicologo(int id, String nome, String crp, String especialidade) {
        this.id = id;
        this.nome = nome;
        this.crp = crp;
        this.especialidade = especialidade;
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

    public String getCrp() {
        return crp;
    }

    public void setCrp(String crp) {
        this.crp = crp;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }
}
