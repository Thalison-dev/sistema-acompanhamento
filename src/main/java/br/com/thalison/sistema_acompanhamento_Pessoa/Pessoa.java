package br.com.thalison.sistema_acompanhamento_Pessoa;

public class Pessoa {

    private int id;
    private String nome;
    private String cpf;
    private String telefone;
    private String email;

    public Pessoa() {
    }

    public Pessoa(int id, String nome, String cpf, String telefone, String email) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.email = email;
    }

    public int getIdPessoa() {
        return id;
    }

    public void setIdAluno(int id) {
        this.id = id;
    }

    public String getNomePessoa() {
        return nome;
    }

    public void setNomePessoa(String nome) {
        this.nome = nome;
    }

    public String getCpfPessoa() {
        return cpf;
    }

    public void setCpfPessoa(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefonePessoa() {
        return telefone;
    }

    public void setTelefonePessoa(String telefone) {
        this.telefone = telefone;
    }

    public String getEmailPessoa() {
        return email;
    }

    public void setEmailPessoa(String email) {
        this.email = email;
    }
}
