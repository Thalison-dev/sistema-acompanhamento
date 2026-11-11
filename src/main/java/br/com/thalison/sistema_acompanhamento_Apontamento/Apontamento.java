package br.com.thalison.sistema_acompanhamento_Apontamento;

import br.com.thalison.sistema_acompanhamento_Pessoa.Pessoa;

public class Apontamento extends Pessoa {
    private String idProfessor;
    private String idPessoa;
    private String dataInicio;
    private String nomeProfessor;
    private String motivo;

    public Apontamento() {
        super();
    }

    public Apontamento(String nome, String cpf, String dataInicio, String nomeProfessor, String motivo) {
        super(nome, cpf); 
        this.dataInicio = dataInicio;
        this.nomeProfessor = nomeProfessor;
        this.motivo = motivo;
    }

    public String getIdProfessor() {
        return idProfessor;
    }

    public void setIdProfessor(String idProfessor) {
        this.idProfessor = idProfessor;
    }

    public String getIdPessoa() {
        return idPessoa;
    }

    public void setIdPessoa(String idPessoa) {
        this.idPessoa = idPessoa;
    }

    public String getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(String dataInicio) {
        this.dataInicio = dataInicio;
    }

    public String getNomeProfessor() {
        return nomeProfessor;
    }

    public void setNomeProfessor(String nomeProfessor) {
        this.nomeProfessor = nomeProfessor;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
}