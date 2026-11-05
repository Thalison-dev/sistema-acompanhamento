package br.com.thalison.sistema_acompanhamento_adaptacao;

import javax.xml.crypto.Data;

public class adaptacao_metodologica {
	private int id;
	private String descricao;
	private String estrategia;
	private Data dataRegistro;
	private String resultado;
	private String status;
	
	public adaptacao_metodologica() {
}
	
	public adaptacao_metodologica( int id, String descricao, String estrategia, Data dataRegistro, String resultado, String status) {
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
 	public Data getdataRegistro() {
 		return dataRegistro;
 	}
 	public void  setdataRegistro(Data dataRegistro) { 
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