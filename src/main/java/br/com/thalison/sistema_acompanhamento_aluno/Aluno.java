package br.com.thalison.sistema_acompanhamento_aluno;

import java.sql.Date;
import br.com.thalison.sistema_acompanhamento_Pessoa.Pessoa;

public class Aluno extends Pessoa {

    private int pessoaId;
    private Date dataNascimento;
    private String matricula;
    private String anoEscolar;
    private String tipoNeurodivergencia;
    private String descricaoNeuro;
    private String status;

    public Aluno() {
        super();
    }

    public int getPessoaId() { return pessoaId; }
    public void setPessoaId(int pessoaId) { this.pessoaId = pessoaId; }
    public Date getDataNascimento() { return dataNascimento; }
    public void setDataNascimento(Date dataNascimento) { this.dataNascimento = dataNascimento; }
    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }
    public String getAnoEscolar() { return anoEscolar; }
    public void setAnoEscolar(String anoEscolar) { this.anoEscolar = anoEscolar; }
    public String getTipoNeurodivergencia() { return tipoNeurodivergencia; }
    public void setTipoNeurodivergencia(String tipoNeurodivergencia) { this.tipoNeurodivergencia = tipoNeurodivergencia; }
    public String getDescricaoNeuro() { return descricaoNeuro; }
    public void setDescricaoNeuro(String descricaoNeuro) { this.descricaoNeuro = descricaoNeuro; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}