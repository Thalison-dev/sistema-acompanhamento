
package br.com.thalison.sistema_acompanhamento_neurodivergente;

public class Neurodivergente {
    private int id;
    private int alunoId;
    private String tipo;
    private String descricao;
    private String observacoes;
    private String dataRegistro;

<<<<<<< HEAD
    public Neurodivergente() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getAlunoId() { return alunoId; }
    public void setAlunoId(int alunoId) { this.alunoId = alunoId; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getObservacoes() { return observacoes; }
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }

    public String getDataRegistro() { return dataRegistro; }
    public void setDataRegistro(String dataRegistro) {
        this.dataRegistro = dataRegistro;
    }
}
=======
public class Neurodivergente {
	
	private int id;
	private String tipo;
	private String descricao;
	private String observacoes;
	private String dataRegistro;
	
	public Neurodivergente() {
}
	public Neurodivergente( int id, String tipo, String descricao, String observacoes, String DataRegistro) {
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
	     
	     
    	
	    
>>>>>>> 79e5fc96ab0a86fc5f03ab7aaf4acf544dd1cd5d
