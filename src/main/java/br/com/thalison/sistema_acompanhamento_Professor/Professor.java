package br.com.thalison.sistema_acompanhamento_Professor;

import br.com.thalison.sistema_acompanhamento_Pessoa.Pessoa;

public class Professor extends Pessoa {

    private String formacao;
    private String especialidade;

    public Professor() {
    }

    public Professor(int id, String nome, String cpf, String telefone,
                     String email, String formacao, String especialidade) {
        super(id, nome, cpf, telefone, email);
        this.formacao = formacao;
        this.especialidade = especialidade;
    }

    public String getFormacao() {
        return formacao;
    }

    public void setFormacao(String formacao) {
        this.formacao = formacao;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }
}
