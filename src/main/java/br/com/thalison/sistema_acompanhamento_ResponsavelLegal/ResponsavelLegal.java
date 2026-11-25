package br.com.thalison.sistema_acompanhamento_ResponsavelLegal;

import br.com.thalison.sistema_acompanhamento_Pessoa.Pessoa;
import br.com.thalison.sistema_acompanhamento_aluno.Aluno;

public class ResponsavelLegal extends Pessoa {

	private Pessoa pessoa;
	private Aluno aluno;
	private String parentesco;

	public ResponsavelLegal() {
		super();
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public String getParentesco() {
		return parentesco;
	}

	public void setParentesco(String parentesco) {
		this.parentesco = parentesco;
	}
}