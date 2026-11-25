package br.com.thalison.sistema_Acompanhamento_entrevista;

import java.time.LocalDate;

import br.com.thalison.sistema_acompanhamento_Psicologo.Psicologo;
import br.com.thalison.sistema_acompanhamento_aluno.Aluno;

public class Entrevista {

	private int id;
	private Aluno aluno;
	private Psicologo psicologo;
	private LocalDate data;
	private String motivo;
	private boolean agendada;
	private String observacoes;
	private String nomeAluno;
	private String nomePsicologo;

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

	public Psicologo getPsicologo() {
		return psicologo;
	}

	public void setPsicologo(Psicologo psicologo) {
		this.psicologo = psicologo;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public boolean isAgendada() {
		return agendada;
	}

	public void setAgendada(boolean agendada) {
		this.agendada = agendada;
	}

	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

	public String getNomeAluno() {
		return nomeAluno;
	}

	public void setNomeAluno(String nomeAluno) {
		this.nomeAluno = nomeAluno;
	}

	public String getNomePsicologo() {
		return nomePsicologo;
	}

	public void setNomePsicologo(String nomePsicologo) {
		this.nomePsicologo = nomePsicologo;
	}
}