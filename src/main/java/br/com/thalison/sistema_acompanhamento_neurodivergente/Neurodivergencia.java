package br.com.thalison.sistema_acompanhamento_neurodivergente;
 
import java.time.LocalDate;
 
import br.com.thalison.sistema_acompanhamento_aluno.Aluno;
 
public class Neurodivergencia {
 
	private int id;
	private Aluno aluno;
	private String tipo;
	private String descricao;
	private String observacoes;
	private LocalDate dataRegistro;
 
	public Neurodivergencia() {
	}
 
	public int getId() {
		return id;
	}
 
	public void setId(int id) {
		this.id = id;
	}
 
	public Aluno getAluno() {
		return aluno;
	}
 
	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}
 
	public String getTipo() {
		return tipo;
	}
 
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
 
	public String getDescricao() {
		return descricao;
	}
 
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
 
	public String getObservacoes() {
		return observacoes;
	}
 
	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}
 
	public LocalDate getDataRegistro() {
		return dataRegistro;
	}
 
	public void setDataRegistro(LocalDate dataRegistro) {
		this.dataRegistro = dataRegistro;
	}
}