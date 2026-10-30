package br.com.thalison.sistema_acompanhamento_mensagem;

import br.com.thalison.sistema_acompanhamento_Pessoa.Pessoa;

class mensagem extends Pessoa{
	
	long id;
	String conteudo;
	String dataEnvio;
	Boolean lida;
	
	
	public mensagem(){	
	}
	public mensagem(long id, String conteudo, String dataEnvio, boolean lida) {
	
	
	this.id = id;
	this.conteudo = conteudo;
	this.dataEnvio = dataEnvio;
	this.lida = lida;
		
	}
	
	public long getgid() {
		return id;
	}
	public void setid(long id) {
		this.id = id;
	}
	public String getconteudo() {
		return conteudo;
	}
	public void setconteudo(String conteudo) {
		this.conteudo = conteudo;
	}
	public String getdataEnvio() {
		return dataEnvio;
	}
	public void setdataEnvio(String dataEnvio) {
		this.dataEnvio = dataEnvio;
	}
	public Boolean getlida() {
		return lida;
	}
	public void setlida(Boolean lida) {
		this.lida = lida;
	}
		
}