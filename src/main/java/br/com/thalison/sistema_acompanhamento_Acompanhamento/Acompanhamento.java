package br.com.thalison.sistema_acompanhamento_Acompanhamento;

import java.sql.Date;

public class Acompanhamento {
    private int id;
    private int alunoId;
    private int responsavelLegalId;
    private Integer secretariaId;
    private Integer psicologoId;
    private Integer pedagogoId;

    private Date dataInicio;
    private Date dataFim;
    private String objetivo;
    private String parecerPsicologico;
    private String parecerPedagogico;
    private String status;

    private String nomeAluno;
    private String tipoNeurodivergencia;
    private String nomeResponsavel;
    private String parentescoResponsavel;
    private String nomePsicologo;
    private String nomePedagogo;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getAlunoId() { return alunoId; }
    public void setAlunoId(int alunoId) { this.alunoId = alunoId; }
    public int getResponsavelLegalId() { return responsavelLegalId; }
    public void setResponsavelLegalId(int responsavelLegalId) { this.responsavelLegalId = responsavelLegalId; }
    public Integer getSecretariaId() { return secretariaId; }
    public void setSecretariaId(Integer secretariaId) { this.secretariaId = secretariaId; }
    public Integer getPsicologoId() { return psicologoId; }
    public void setPsicologoId(Integer psicologoId) { this.psicologoId = psicologoId; }
    public Integer getPedagogoId() { return pedagogoId; }
    public void setPedagogoId(Integer pedagogoId) { this.pedagogoId = pedagogoId; }
    public Date getDataInicio() { return dataInicio; }
    public void setDataInicio(Date dataInicio) { this.dataInicio = dataInicio; }
    public Date getDataFim() { return dataFim; }
    public void setDataFim(Date dataFim) { this.dataFim = dataFim; }
    public String getObjetivo() { return objetivo; }
    public void setObjetivo(String objetivo) { this.objetivo = objetivo; }
    public String getParecerPsicologico() { return parecerPsicologico; }
    public void setParecerPsicologico(String parecerPsicologico) { this.parecerPsicologico = parecerPsicologico; }
    public String getParecerPedagogico() { return parecerPedagogico; }
    public void setParecerPedagogico(String parecerPedagogico) { this.parecerPedagogico = parecerPedagogico; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getNomeAluno() { return nomeAluno; }
    public void setNomeAluno(String nomeAluno) { this.nomeAluno = nomeAluno; }
    public String getTipoNeurodivergencia() { return tipoNeurodivergencia; }
    public void setTipoNeurodivergencia(String tipoNeurodivergencia) { this.tipoNeurodivergencia = tipoNeurodivergencia; }
    public String getNomeResponsavel() { return nomeResponsavel; }
    public void setNomeResponsavel(String nomeResponsavel) { this.nomeResponsavel = nomeResponsavel; }
    public String getParentescoResponsavel() { return parentescoResponsavel; }
    public void setParentescoResponsavel(String parentescoResponsavel) { this.parentescoResponsavel = parentescoResponsavel; }
    public String getNomePsicologo() { return nomePsicologo; }
    public void setNomePsicologo(String nomePsicologo) { this.nomePsicologo = nomePsicologo; }
    public String getNomePedagogo() { return nomePedagogo; }
    public void setNomePedagogo(String nomePedagogo) { this.nomePedagogo = nomePedagogo; }
}