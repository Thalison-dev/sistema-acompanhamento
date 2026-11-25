package br.com.thalison.sistema_acompanhamento_Pedagogo;

import br.com.thalison.sistema_acompanhamento_Pessoa.Pessoa;

public class Pedagogo extends Pessoa {

	private String especialidade;

	public Pedagogo() {
		super();
	}

	public Pedagogo(int id, String nome, String especialidade) {
		super();
		setId(id);
		setNome(nome);
		this.especialidade = especialidade;
	}

	public String getEspecialidade() {
		return especialidade;
	}

	public void setEspecialidade(String especialidade) {
		this.especialidade = especialidade;
	}
}