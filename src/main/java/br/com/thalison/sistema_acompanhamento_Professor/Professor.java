package br.com.thalison.sistema_acompanhamento_Professor;

import br.com.thalison.sistema_acompanhamento_Pessoa.Pessoa;

public class Professor extends Pessoa {
    private String formacao;
    private String especialidade;

    public Professor() { super(); }

    public String getFormacao() { return formacao; }
    public void setFormacao(String formacao) { this.formacao = formacao; }
    public String getEspecialidade() { return especialidade; }
    public void setEspecialidade(String especialidade) { this.especialidade = especialidade; }
}