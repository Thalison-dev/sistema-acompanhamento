package br.com.thalison.sistema_Acompanhamento_entrevista;

import java.sql.Date;

public class Entrevista {
	private int id;
	private int alunoId;
	private int psicologoId;
	private Date data;
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

	public int getAlunoId() {
		return alunoId;
	}

	public void setAlunoId(int alunoId) {
		this.alunoId = alunoId;
	}

	public int getPsicologoId() {
		return psicologoId;
	}

	public void setPsicologoId(int psicologoId) {
		this.psicologoId = psicologoId;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
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