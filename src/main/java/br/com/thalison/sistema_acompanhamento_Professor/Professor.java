package br.com.thalison.sistema_acompanhamento_Professor;

import br.com.thalison.sistema_acompanhamento_Pessoa.Pessoa;
import br.com.thalison.sistema_acompanhamento_aluno.Aluno;
import br.com.thalison.sistema_acompanhamento_Disciplina.Disciplina;

public class Professor extends Pessoa {

	private String formacao;
	private String especialidade;

	private Aluno alunoObservado;
	private Disciplina disciplina;

	public Professor() {
		super();
	}

	public String getFormacao() {
		return formacao;
	}

	public void setFormacao(String formacao) {
		this.formacao = formacao;
	}

	public String getEspecialidade() {
		return especialidade;
	}

	public void setEspecialidade(String especialidade) {
		this.especialidade = especialidade;
	}

	public Aluno getAlunoObservado() {
		return alunoObservado;
	}

	public void setAlunoObservado(Aluno alunoObservado) {
		this.alunoObservado = alunoObservado;
	}

	public Disciplina getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(Disciplina disciplina) {
		this.disciplina = disciplina;
	}
}