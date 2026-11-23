
package br.com.thalison.sistema_acompanhamento_Diretor;

import br.com.thalison.sistema_acompanhamento_Pessoa.Pessoa;

public class Diretor extends Pessoa {

    private String matricula;

    public Diretor() {
    }

    public Diretor(int id, String nome, String cpf, String telefone,
                   String email, String matricula) {
        super(id, nome, cpf, telefone, email);
        this.matricula = matricula;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }
}
