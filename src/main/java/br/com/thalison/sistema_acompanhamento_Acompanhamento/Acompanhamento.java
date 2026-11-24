package br.com.thalison.sistema_acompanhamento_Acompanhamento;

import java.time.LocalDate;

public class Acompanhamento {
    private int id;
    private int alunoId;
    private String nomeAluno;
    private String tipoNeurodivergencia;
    private int responsavelLegalId;
    private String nomeResponsavel;
    private String parentescoResponsavel;
    private int psicologoId;
    private String nomePsicologo;
    private int pedagogoId;
    private String nomePedagogo;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private String objetivo;
    private String parecerPsicologico;
    private String parecerPedagogico;
    private String status;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getAlunoId() { return alunoId; }
    public void setAlunoId(int alunoId) { this.alunoId = alunoId; }
    public String getNomeAluno() { return nomeAluno; }
    public void setNomeAluno(String nomeAluno) { this.nomeAluno = nomeAluno; }
    public String getTipoNeurodivergencia() { return tipoNeurodivergencia; }
    public void setTipoNeurodivergencia(String tipoNeurodivergencia) { this.tipoNeurodivergencia = tipoNeurodivergencia; }
    public int getResponsavelLegalId() { return responsavelLegalId; }
    public void setResponsavelLegalId(int responsavelLegalId) { this.responsavelLegalId = responsavelLegalId; }
    public String getNomeResponsavel() { return nomeResponsavel; }
    public void setNomeResponsavel(String nomeResponsavel) { this.nomeResponsavel = nomeResponsavel; }
    public String getParentescoResponsavel() { return parentescoResponsavel; }
    public void setParentescoResponsavel(String parentescoResponsavel) { this.parentescoResponsavel = parentescoResponsavel; }
    public int getPsicologoId() { return psicologoId; }
    public void setPsicologoId(int psicologoId) { this.psicologoId = psicologoId; }
    public String getNomePsicologo() { return nomePsicologo; }
    public void setNomePsicologo(String nomePsicologo) { this.nomePsicologo = nomePsicologo; }
    public int getPedagogoId() { return pedagogoId; }
    public void setPedagogoId(int pedagogoId) { this.pedagogoId = pedagogoId; }
    public String getNomePedagogo() { return nomePedagogo; }
    public void setNomePedagogo(String nomePedagogo) { this.nomePedagogo = nomePedagogo; }
    public LocalDate getDataInicio() { return dataInicio; }
    public void setDataInicio(LocalDate dataInicio) { this.dataInicio = dataInicio; }
    public LocalDate getDataFim() { return dataFim; }
    public void setDataFim(LocalDate dataFim) { this.dataFim = dataFim; }
    public String getObjetivo() { return objetivo; }
    public void setObjetivo(String objetivo) { this.objetivo = objetivo; }
    public String getParecerPsicologico() { return parecerPsicologico; }
    public void setParecerPsicologico(String parecerPsicologico) { this.parecerPsicologico = parecerPsicologico; }
    public String getParecerPedagogico() { return parecerPedagogico; }
    public void setParecerPedagogico(String parecerPedagogico) { this.parecerPedagogico = parecerPedagogico; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}