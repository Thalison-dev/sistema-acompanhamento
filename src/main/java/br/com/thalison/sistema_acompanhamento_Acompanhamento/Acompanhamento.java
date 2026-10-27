package br.com.thalison.sistema_acompanhamento_Acompanhamento;

public class Acompanhamento{
	
	private  String id;
	private  String dataInicio;
	private  String dataFim;
	private  String objetivo;
	private  String status;
	
	
	public Acompanhamento () {
		
	}
	public Acompanhamento(String id, String dataInicio, String dataFim, String objetivo, 
			String status) {
		this.id = id;
		this.dataInicio = dataInicio;
		this.dataFim = dataFim;
		this.objetivo = objetivo;
		this.status = status;
		
	}
	
	
	
	
	public String getid() {
		return id;
	}
	public void setid(String id) {
		this.id = id;
	}
	public String dataInicio() {
		return dataInicio;
	}
	public void setdataIncio(String dataInicio){
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