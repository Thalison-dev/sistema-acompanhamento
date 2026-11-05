package br.com.thalison.sistema_acompanhamento_aluno;
 
import br.com.thalison.sistema_acompanhamento_Pessoa.Pessoa;
public class aluno extends Pessoa{
	
	
    private String dataNascimento;
    private String matricula;
    private String anoEscolar;
    private String status;
 
    public aluno() {
    }
 
    public aluno(int id, String nome, String cpf,String telefone, String email,String dataNascimento,
                 String matricula, String anoEscolar, String status) {
    	  super(id, nome, cpf, telefone, email);
        this.dataNascimento = dataNascimento;
        this.matricula = matricula;
        this.anoEscolar = anoEscolar;
        this.status = status;
    }
 
 
 
    public String getDataNascimento() {
        return dataNascimento;
    }
 
    public void setDataNascimento(String dataNascimento) {
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
 
    public String getStatus() {
        return status;
    }
 
    public void setStatus(String status) {
        this.status = status;
    }
 
    public void atualizarDados() {
        System.out.println("Dados do aluno atualizados.");
    }
 
    public void consultarHistorico() {
        System.out.println("Consulta ao histórico do aluno.");
    }
 
    public void consultarAcompanhamento() {
        System.out.println("Consulta ao acompanhamento do aluno.");
    }
}
 
 