package br.com.thalison.sistema_acompanhamento_Psicologo;

import br.com.thalison.sistema_acompanhamento_Pessoa.Pessoa;

/**
 * Psicólogo. Os campos id e nome vêm de Pessoa (antes eram duplicados aqui,
 * o que fazia o id do construtor se perder).
 */
public class Psicologo extends Pessoa {

    private String especialidade;

    public Psicologo() {
    }

    public Psicologo(int id, String nome, String cpf, String telefone, String email) {
        super(id, nome, cpf, telefone, email);
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }
}
