package br.com.thalison.sistema_acompanhamento_neurodivergencia;

public class neurodivergencia {
	private int id;
	private String tipo;
	private String descricao;
	private String observacoes;
	private String dataRegistro;
	
	public neurodivergencia() {
}
	public neurodivergencia( int id, String tipo, String descricao, String observacoes, String DataRegistro) {
		this.id = id;
		this.tipo = tipo;
		this.descricao = descricao;
		this.observacoes = observacoes;
		this.dataRegistro = DataRegistro;
	}
	  public int getId() {
	        return id;
	    }

	    public void setId(int id) {
	        this.id = id;
	    }
      public String gettipo() {
    	 return tipo; 
      }
	   public void settipo(String tipo) { 
		 this.tipo = tipo;  
	   }
	    public String getdescricao() {
	    	return descricao;
	    }
	    
	    public void setdescricao(String descricao) {
	    	this.descricao = descricao;
	    }
	    public String getobservacoes() {
	    	return observacoes;
	    }
	    
	    public void setobservacoes(String observacoes) {
	    	this.observacoes = observacoes;
	    }
	    public String getDataRegistro() {
	    	return dataRegistro;
	    }
	    public void setDataRegistro(String dataRegistro) {
	        this.dataRegistro = dataRegistro;
	    }
	    	}
	     
	     
    	
	    