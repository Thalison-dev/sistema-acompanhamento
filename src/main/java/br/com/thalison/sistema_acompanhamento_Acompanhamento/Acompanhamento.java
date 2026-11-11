package br.com.thalison.sistema_acompanhamento_Acompanhamento;

import java.sql.Date;

public class Acompanhamento {
    
    private int id;
    private Date dataInicio;
    private Date dataFim;
    private String objetivo;
    private String status;
    
    public Acompanhamento() {
    }

    public Acompanhamento(int id, Date dataInicio, Date dataFim, String objetivo, String status) {
        this.id = id;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.objetivo = objetivo;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}