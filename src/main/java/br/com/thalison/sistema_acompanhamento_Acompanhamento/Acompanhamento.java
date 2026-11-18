
package br.com.thalison.sistema_acompanhamento_Acompanhamento;

public class Acompanhamento {

    private int id;
    private int alunoId;
    private String dataInicio;
    private String dataFim;
    private String objetivo;
    private String status;

    public Acompanhamento() {
    }

    public Acompanhamento(int alunoId, String dataInicio, String dataFim,
                          String objetivo, String status) {
        this.alunoId = alunoId;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.objetivo = objetivo;
        this.status = status;
    }

    public int getAcompanhamentoId() {
        return id;
    }

    public void setAcompanhamentoId(int id) {
        this.id = id;
    }

    public int getAlunoId() {
        return alunoId;
    }

    public void setAlunoId(int alunoId) {
        this.alunoId = alunoId;
    }

    public String getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(String dataInicio) {
        this.dataInicio = dataInicio;
    }

    public String getDataFim() {
        return dataFim;
    }

    public void setDataFim(String dataFim) {
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

    

    public String getid() {
        return String.valueOf(id);
    }

    public void setid(String id) {
        this.id = Integer.parseInt(id);
    }

    public String dataInicio() {
        return dataInicio;
    }

    public void setdataIncio(String dataInicio) {
        this.dataInicio = dataInicio;
    }

    public String getdataFim() {
        return dataFim;
    }

    public void setdataFim(String dataFim) {
        this.dataFim = dataFim;
    }

    public String getobjetivo() {
        return objetivo;
    }

    public void setobjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public String getstatus() {
        return status;
    }

    public void setstatus(String status) {
        this.status = status;
    }
}
