
package br.com.thalison.sistema_acompanhamento_Pessoa;

public class Pessoa {

    private int id;
    private String nome;
    private String cpf;
    private String telefone;
    private String email;
    private String senha;
    private Boolean ativo = true;

    public Pessoa() {

    }

    // Construtor completo
    public Pessoa(int id, String nome, String cpf,
                  String telefone, String email) {

        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.email = email;
    }

<<<<<<< HEAD
    // Construtor para nome e CPF
    public Pessoa(String nome, String cpf) {

        this.nome = nome;
        this.cpf = cpf;
=======
    public Boolean realizarLogin(String email, String senha) {
        return Boolean.TRUE.equals(ativo)
                && this.email != null && this.email.equals(email)
                && this.senha != null && this.senha.equals(senha);
    }

    public void alterarSenha(String novaSenha) {
        this.senha = novaSenha;
    }

    public void encerrarSessao() {
        System.out.println("Sessão encerrada.");
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
>>>>>>> 469c46827651996e1bde5e5a6948eec01f28e222
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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
