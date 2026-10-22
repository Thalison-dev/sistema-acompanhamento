package br.com.thalison.sistema_acompanhamento_aluno;

public class aluno {
	
	private String id;
	private String nome;
	private String dataNascimento;
	private String matricula;
	private String anoEscolar;
	private String status;
	
	public void Aluno(String id, String nome, String dataNascimento, String matricula, 
			String anoEscolar, String status) {
	
	this.id = id;  
	this.nome = nome;
	this.dataNascimento = dataNascimento;
	this.matricula = matricula;
	this.anoEscolar = anoEscolar;
	this.status = status;
			
	}

	public void consultarDados() {
		
		System.out.println("Dados do aluno atualizados.");
	}
	 
	public void consultarHistorico() {
		
		System.out.println("Consultar proprio dados");
	}
	
	public void consultarAcompanhamento() {
		
		System.out.println("Consultar proprio acompanhamento");
	}
	
	public String getid() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		
		this.nome = nome;
	}
	public String getDataNascimento() {
		return dataNascimento;
	}
	
	public void setdataNascimento(String dataNascimento) {
		
		this.dataNascimento = dataNascimento;
	}
	public String getmatricula() {
		
		return matricula;
	}
	public void setmatricula(String matricula) {
		this.matricula = matricula;
	}
	public String getanoEscolar() {
		
		return anoEscolar;
	}
	public void setanoEscolar(String anoEscolar) {
		this.anoEscolar = anoEscolar;
		
	}public String getstatus() {
		return status;
	}
	public void setstatus(String status) {
		this.status = status;
	}
	
	}	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	