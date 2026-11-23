package br.com.thalison.sistema_acompanhamento_Professor;

import br.com.thalison.sistema_acompanhamento_Pessoa.Pessoa;

public class Professor extends Pessoa {

    private String matricula;
    private String especialidade;

    public Professor() {
    }

    public Professor(int id, String nome, String cpf, String telefone,
                     String email, String matricula, String especialidade) {
        super(id, nome, cpf, telefone, email);
        this.matricula = matricula;
        this.especialidade = especialidade;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }
}
