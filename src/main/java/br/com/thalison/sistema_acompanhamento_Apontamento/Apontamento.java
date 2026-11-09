package br.com.thalison.sistema_acompanhamento_Apontamento;

public class Apontamento {
	
	private String dataInicio;
	private String Nomeprofessor;
	private String Motivo;
	
	
	public Apontamento() {
	}

	
	public Apontamento(String dataInicio, String Nomeprofessor, String Motivo) {
		this.dataInicio = dataInicio;		
		this.Nomeprofessor = Nomeprofessor;
		this.Motivo = Motivo;
	}

	
	public String getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(String dataInicio) {
		this.dataInicio = dataInicio;
	}

	public String getNomeprofessor() {
		return Nomeprofessor;
	}

	public void setNomeprofessor(String Nomeprofessor) {
		this.Nomeprofessor = Nomeprofessor;
	}

	public String getMotivo() {
		return Motivo;
	}

	public void setMotivo(String Motivo) {
		this.Motivo = Motivo;
	}
}