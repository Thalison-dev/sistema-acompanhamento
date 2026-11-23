package br.com.thalison.sistema_acompanhamento_Reuniao;

/**
 * Reunião agendada por qualquer profissional (Pedagogo, Psicólogo, Professor,
 * Diretor ou Responsável Legal), opcionalmente ligada a um aluno e a um acompanhamento.
 */
public class Reuniao {

    private int id;
    private String solicitanteTipo;
    private int solicitanteId;
    private String nomeSolicitante;
    private String publicoAlvo;
    private int alunoId;
    private String nomeAluno;
    private int acompanhamentoId;
    private String dataReuniao;
    private String motivo;
    private String status;
    private String dataRegistro;

    public Reuniao() {
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getSolicitanteTipo() { return solicitanteTipo; }
    public void setSolicitanteTipo(String solicitanteTipo) { this.solicitanteTipo = solicitanteTipo; }

    public int getSolicitanteId() { return solicitanteId; }
    public void setSolicitanteId(int solicitanteId) { this.solicitanteId = solicitanteId; }

    public String getNomeSolicitante() { return nomeSolicitante; }
    public void setNomeSolicitante(String nomeSolicitante) { this.nomeSolicitante = nomeSolicitante; }

    public String getPublicoAlvo() { return publicoAlvo; }
    public void setPublicoAlvo(String publicoAlvo) { this.publicoAlvo = publicoAlvo; }

    public int getAlunoId() { return alunoId; }
    public void setAlunoId(int alunoId) { this.alunoId = alunoId; }

    public String getNomeAluno() { return nomeAluno; }
    public void setNomeAluno(String nomeAluno) { this.nomeAluno = nomeAluno; }

    public int getAcompanhamentoId() { return acompanhamentoId; }
    public void setAcompanhamentoId(int acompanhamentoId) { this.acompanhamentoId = acompanhamentoId; }

    public String getDataReuniao() { return dataReuniao; }
    public void setDataReuniao(String dataReuniao) { this.dataReuniao = dataReuniao; }

    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getDataRegistro() { return dataRegistro; }
    public void setDataRegistro(String dataRegistro) { this.dataRegistro = dataRegistro; }
}
