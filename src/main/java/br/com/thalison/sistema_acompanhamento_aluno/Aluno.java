package br.com.thalison.sistema_acompanhamento_aluno;

import br.com.thalison.sistema_acompanhamento_Pessoa.Pessoa;

public class Aluno extends Pessoa {

    private String dataNascimento;
    private String matricula;
    private String anoEscolar;
    private String status;
    private int responsavelId;
    private String nomeResponsavel;

    public Aluno() {
        super();
    }

    public Aluno(int id, String nome, String cpf, String telefone,
                 String email, String dataNascimento, String matricula,
                 String anoEscolar, String status) {
        super(id, nome, cpf, telefone, email);
        this.dataNascimento = dataNascimento;
        this.matricula = matricula;
        this.anoEscolar = anoEscolar;
        this.status = status;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getAnoEscolar() {
        return anoEscolar;
    }

    public void setAnoEscolar(String anoEscolar) {
        this.anoEscolar = anoEscolar;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getResponsavelId() {
        return responsavelId;
    }

    public void setResponsavelId(int responsavelId) {
        this.responsavelId = responsavelId;
    }

    public String getNomeResponsavel() {
        return nomeResponsavel;
    }

    public void setNomeResponsavel(String nomeResponsavel) {
        this.nomeResponsavel = nomeResponsavel;
    }
}
