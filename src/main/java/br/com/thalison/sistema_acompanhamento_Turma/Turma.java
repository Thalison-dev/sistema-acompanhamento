package br.com.thalison.sistema_acompanhamento_Turma;

public class Turma {
    
    private int id;
    private String nome;
    private String anoLetivo;
    private String turno;
    
    public Turma() {
    }

    public Turma(int id, String nome, String anoLetivo, String turno) {
        this.id = id;
        this.nome = nome;
        this.anoLetivo = anoLetivo;
        this.turno = turno;
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

    public String getAnoLetivo() {
        return anoLetivo;
    }

    public void setAnoLetivo(String anoLetivo) {
        this.anoLetivo = anoLetivo;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }
}