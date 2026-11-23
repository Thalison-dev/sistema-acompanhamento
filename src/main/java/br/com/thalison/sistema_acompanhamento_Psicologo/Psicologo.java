package br.com.thalison.sistema_acompanhamento_Psicologo;

// Igual ao diagrama e ao Pedagogo: "pessoa" é o nome e id é o id da pessoa (tabela psicologo.pessoaId).
public class Psicologo {

    private int id;
    private String pessoa;
    private String especialidade;

    public Psicologo() {
    }

    public Psicologo(int id, String pessoa, String especialidade) {
        this.id = id;
        this.pessoa = pessoa;
        this.especialidade = especialidade;
    }

    public void consultarAluno() {
        System.out.println("Consultando aluno.");
    }

    public void marcarReuniaoComAluno() {
        System.out.println("Reunião com aluno agendada.");
    }

    public void marcarReuniaoComPedagogo() {
        System.out.println("Reunião com pedagogo agendada.");
    }

    public void atualizarRegistroDoAluno() {
        System.out.println("Registro do aluno atualizado.");
    }

    public void verRegistroDoAluno() {
        System.out.println("Consultando registro do aluno.");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPessoa() {
        return pessoa;
    }

    public void setPessoa(String pessoa) {
        this.pessoa = pessoa;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }
}
