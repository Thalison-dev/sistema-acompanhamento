package br.com.thalison.sistema_acompanhamento_aluno;

import br.com.thalison.sistema_acompanhamento_Pessoa.Pessoa;

public class Aluno extends Pessoa {
    private String dataNascimento;
    private String matricula;
    private String anoEscolar;
    private String status;
    private String tipoNeurodivergencia;
    private String descricaoNeuro;

    public Aluno() { super(); }

    public String getDataNascimento() { return dataNascimento; }
    public void setDataNascimento(String dataNascimento) { this.dataNascimento = dataNascimento; }
    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }
    public String getAnoEscolar() { return anoEscolar; }
    public void setAnoEscolar(String anoEscolar) { this.anoEscolar = anoEscolar; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getTipoNeurodivergencia() { return tipoNeurodivergencia; }
    public void setTipoNeurodivergencia(String tipoNeurodivergencia) { this.tipoNeurodivergencia = tipoNeurodivergencia; }
    public String getDescricaoNeuro() { return descricaoNeuro; }
    public void setDescricaoNeuro(String descricaoNeuro) { this.descricaoNeuro = descricaoNeuro; }
}