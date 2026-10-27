package br.com.thalison.sistema_acompanhamento_aluno;

public class aluno{

    private int id;
    private String nome;
    private String dataNascimento;
    private String matricula;
    private String anoEscolar;
    private String status;

    public aluno() {
    }

    public aluno(int id, String nome, String dataNascimento,
                 String matricula, String anoEscolar, String status) {
        this.id = id;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.matricula = matricula;
        this.anoEscolar = anoEscolar;
        this.status = status;
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
