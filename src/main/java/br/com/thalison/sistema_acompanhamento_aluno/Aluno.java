package br.com.thalison.sistema_acompanhamento_aluno;

import java.time.LocalDate;
import br.com.thalison.sistema_acompanhamento_Pessoa.Pessoa;

public class Aluno extends Pessoa {

	private Pessoa pessoa;
	private LocalDate dataNascimento;
	private String matricula;
	private String anoEscolar;
	private String tipoNeurodivergencia;
	private String descricaoNeuro;
	private String status;

	public Aluno() {
		super();
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getAnoEscolar() {
		return anoEscolar;
	}

	public void setAnoEscolar(String anoEscolar) {
		this.anoEscolar = anoEscolar;
	}

	public String getTipoNeurodivergencia() {
		return tipoNeurodivergencia;
	}

	public void setTipoNeurodivergencia(String tipoNeurodivergencia) {
		this.tipoNeurodivergencia = tipoNeurodivergencia;
	}

	public String getDescricaoNeuro() {
		return descricaoNeuro;
	}

	public void setDescricaoNeuro(String descricaoNeuro) {
		this.descricaoNeuro = descricaoNeuro;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}