package br.com.thalison.sistema_acompanhamento_adaptacao;

import java.time.LocalDate;

public class AdaptacaoMetodologica {
	private int id;
	private String descricao;
	private String estrategia;
	private LocalDate dataRegistro;
	private String resultado;
	private String status;
	
	public AdaptacaoMetodologica() {
}
	
	public AdaptacaoMetodologica( int id, String descricao, String estrategia, LocalDate dataRegistro, String resultado, String status) {
		this.id = id;
		this.descricao = descricao;
		this.estrategia = estrategia;
		this.dataRegistro = dataRegistro;
		this.resultado = resultado;
		this.status = status;
	}
	
	public int getid() {
		return id;
	}
	public void setId(int id) {
	this.id = id;
	}

 public String getdescricao() {	
	 return descricao;
 }
 	public void setdescricao( String descricao) {
 		this.descricao = descricao;
 	}
 	
 	public String getestrategia() {
 		return estrategia;
 	}
 	public void setestrategia(String estrategia) {
 		this.estrategia = estrategia;
 	}
 	public LocalDate getdataRegistro() {
 		return dataRegistro;
 	}
 	public void  setLocalDateRegistro(LocalDate dataRegistro) { 
 		this.dataRegistro = dataRegistro;
 	}
 	public String getresultado() {
 		return resultado;
 	}
 	public void setresultado(String resultado) {
 		this.resultado = resultado;
 	}
 	public String getstatus() { 
 		return status;	
 	}
 	public void setstatus(String status) {
 		this.status = status;
 	}
}