
package br.com.thalison.sistema_acompanhamento_Apontamento;

<<<<<<< HEAD
import br.com.thalison.sistema_acompanhamento_Pessoa.Pessoa;

public class Apontamento extends Pessoa {

    private String idProfessor;
    private int idPessoa;
=======
public class Apontamento {
    private int id;
    private int alunoId;
    private int professorId;
>>>>>>> 469c46827651996e1bde5e5a6948eec01f28e222
    private String dataInicio;
    private String nomeProfessor;
    private String motivo;

    public Apontamento() {
    }

<<<<<<< HEAD
    public Apontamento(String nome, String cpf, String dataInicio,
                       String nomeProfessor, String motivo) {

        super(nome, cpf);

=======
    public Apontamento(int alunoId, int professorId, String dataInicio, String nomeProfessor, String motivo) {
        this.alunoId = alunoId;
        this.professorId = professorId;
>>>>>>> 469c46827651996e1bde5e5a6948eec01f28e222
        this.dataInicio = dataInicio;
        this.nomeProfessor = nomeProfessor;
        this.motivo = motivo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

<<<<<<< HEAD
    public int getIdPessoa() {
        return idPessoa;
    }

    public void setIdPessoa(int idPessoa) {
        this.idPessoa = idPessoa;
=======
    public int getAlunoId() {
        return alunoId;
    }

    public void setAlunoId(int alunoId) {
        this.alunoId = alunoId;
    }

    public int getProfessorId() {
        return professorId;
    }

    public void setProfessorId(int professorId) {
        this.professorId = professorId;
>>>>>>> 469c46827651996e1bde5e5a6948eec01f28e222
    }

    public String getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(String dataInicio) {
        this.dataInicio = dataInicio;
    }

    public String getNomeProfessor() {
        return nomeProfessor;
    }

    public void setNomeProfessor(String nomeProfessor) {
        this.nomeProfessor = nomeProfessor;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
}
