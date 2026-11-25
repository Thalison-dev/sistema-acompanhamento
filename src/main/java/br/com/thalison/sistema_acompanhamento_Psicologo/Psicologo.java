package br.com.thalison.sistema_acompanhamento_Psicologo;

import br.com.thalison.sistema_acompanhamento_Pessoa.Pessoa;
import br.com.thalison.sistema_acompanhamento_aluno.Aluno;
import br.com.thalison.sistema_acompanhamento_Pedagogo.Pedagogo;

public class Psicologo extends Pessoa {

	private String crp;
	private String especialidade;

	private Aluno alunoAtendido;

	private Pedagogo pedagogoParceiro;

	public Psicologo() {
		super();
	}

	public String getCrp() {
		return crp;
	}

	public void setCrp(String crp) {
		this.crp = crp;
	}

	public String getEspecialidade() {
		return especialidade;
	}

	public void setEspecialidade(String especialidade) {
		this.especialidade = especialidade;
	}

	public Aluno getAlunoAtendido() {
		return alunoAtendido;
	}

	public void setAlunoAtendido(Aluno alunoAtendido) {
		this.alunoAtendido = alunoAtendido;
	}

	public Pedagogo getPedagogoParceiro() {
		return pedagogoParceiro;
	}

	public void setPedagogoParceiro(Pedagogo pedagogoParceiro) {
		this.pedagogoParceiro = pedagogoParceiro;
	}
}