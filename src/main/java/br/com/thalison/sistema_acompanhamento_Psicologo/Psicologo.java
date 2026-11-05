package br.com.thalison.sistema_acompanhamento_Psicologo;
 
import br.com.thalison.sistema_acompanhamento_Pessoa.Pessoa;
 
public class Psicologo extends Pessoa {
 
	private String nome;
	private String especialidade;
	public Psicologo() {
    }
 
    private int id;
	public Psicologo(int id, String nome, String cpf, String telefone, String email) {
		super(id, nome, cpf, telefone, email);
   
    }
 
    public int getId() {
        return id;
    }
 
    public void setId(int id) {
        this.id = id;
    }
 
    public String getNome() {
        return nome;
    }
 
    public void setNome(String nome) {
        this.nome = nome;
    }
 
 
    public String getEspecialidade() {
        return especialidade;
    }
 
    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }
}
 
 