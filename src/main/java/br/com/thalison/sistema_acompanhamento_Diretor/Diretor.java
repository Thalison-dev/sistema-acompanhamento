package br.com.thalison.sistema_acompanhamento_Diretor;

import br.com.thalison.sistema_acompanhamento_Pessoa.Pessoa;

/**
 * Diretor da escola. Como Aluno, Professor e Psicólogo, é uma Pessoa
 * (diretor.diretorId -> pessoa.id). Supervisiona todos os acompanhamentos:
 * aprova, suspende ou encerra, acompanha os indicadores e participa de reuniões.
 */
public class Diretor extends Pessoa {

    private String cargo;
    private String escola;
    private String formacao;

    public Diretor() {
    }

    public Diretor(int id, String nome, String cpf, String telefone, String email,
                   String cargo, String escola, String formacao) {
        super(id, nome, cpf, telefone, email);
        this.cargo = cargo;
        this.escola = escola;
        this.formacao = formacao;
    }

    /** Mantido por compatibilidade: o id do diretor é o id da pessoa. */
    public int getIdDiretor() {
        return getId();
    }

    public void setIdDiretor(int idDiretor) {
        setId(idDiretor);
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getEscola() {
        return escola;
    }

    public void setEscola(String escola) {
        this.escola = escola;
    }

    public String getFormacao() {
        return formacao;
    }

    public void setFormacao(String formacao) {
        this.formacao = formacao;
    }
}
