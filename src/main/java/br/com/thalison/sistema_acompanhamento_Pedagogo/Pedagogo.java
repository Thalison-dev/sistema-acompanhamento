package br.com.thalison.sistema_acompanhamento_Pedagogo;

/**
 * Pedagogo. O id é o mesmo da tabela pessoa (pedagogo.pessoa -> pessoa.id)
 * e o campo "pessoa" guarda o nome para exibição.
 */
public class Pedagogo {

    private int idPedagogo;
    private String pessoa;
    private String especialidade;
    private String email;
    private String telefone;
    private String cpf;

    public Pedagogo() {
    }

    public Pedagogo(int idPedagogo, String pessoa, String especialidade) {
        this.idPedagogo = idPedagogo;
        this.pessoa = pessoa;
        this.especialidade = especialidade;
    }

    public void marcarReuniaoComPsicologo() {
        System.out.println("Reunião com psicólogo agendada.");
    }

    public void marcarReuniaoComResponsavel() {
        System.out.println("Reunião com responsável agendada.");
    }

    public void marcarReuniaoComDiretor() {
        System.out.println("Reunião com diretor agendada.");
    }

    public void consultarRegistroAluno() {
        System.out.println("Consultando registros do aluno.");
    }

    public void reuniaoComAluno() {
        System.out.println("Reunião com aluno iniciada.");
    }

    public int getIdPedagogo() {
        return idPedagogo;
    }

    public void setIdPedagogo(int idPedagogo) {
        this.idPedagogo = idPedagogo;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
