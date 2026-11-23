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

    public void consultarAluno() {
        System.out.println("Consultando aluno.");
    }

    public void registrarObservacao() {
        System.out.println("Observação registrada.");
    }

    public void registrarOcorrencia() {
        System.out.println("Ocorrência registrada.");
    }

    public void registrarAvaliacao() {
        System.out.println("Avaliação registrada.");
    }

    public void registrarRendimento() {
        System.out.println("Rendimento registrado.");
    }

    public void registrarAdaptacao() {
        System.out.println("Adaptação registrada.");
    }

    public void consultarHistorico() {
        System.out.println("Consultando histórico do aluno.");
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
