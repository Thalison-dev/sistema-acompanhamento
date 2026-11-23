package br.com.thalison.sistema_acompanhamento_ResponsavelLegal;

import br.com.thalison.sistema_acompanhamento_Pessoa.Pessoa;

public class ResponsavelLegal extends Pessoa {

    private String parentesco;

    public ResponsavelLegal() {
    }

    public ResponsavelLegal(int id, String nome, String cpf, String telefone,
                            String email, String parentesco) {
        super(id, nome, cpf, telefone, email);
        this.parentesco = parentesco;
    }

    public void consultarAluno() {
        System.out.println("Consultando aluno.");
    }

    public void consultarAcompanhamento() {
        System.out.println("Consultando acompanhamento.");
    }

    public void consultarRendimento() {
        System.out.println("Consultando rendimento.");
    }

    public void visualizarDocumento() {
        System.out.println("Visualizando documento.");
    }

    public void enviarMensagem() {
        System.out.println("Mensagem enviada.");
    }

    public String getParentesco() {
        return parentesco;
    }

    public void setParentesco(String parentesco) {
        this.parentesco = parentesco;
    }
}
