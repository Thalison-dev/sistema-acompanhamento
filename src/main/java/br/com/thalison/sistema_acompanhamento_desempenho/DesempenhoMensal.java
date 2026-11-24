package br.com.thalison.sistema_acompanhamento_desempenho;

import java.math.BigDecimal;
import java.util.List;
import br.com.thalison.sistema_acompanhamento_Apontamento.Apontamento;

public class DesempenhoMensal {
    private int id;
    private int alunoId;
    private String nomeAluno;
    private String tipoNeurodivergencia;
    private int mesReferencia;
    private int anoReferencia;
    private BigDecimal mediaEscola;
    private BigDecimal notaAluno;
    private String observacaoGeral;
    private List<Apontamento> apontamentosDoMes;

    public BigDecimal getDiferencaMedia() {
        if (notaAluno == null || mediaEscola == null) {
            return BigDecimal.ZERO;
        }
        return notaAluno.subtract(mediaEscola);
    }

    public String getSituacaoDesempenho() {
        BigDecimal dif = getDiferencaMedia();
        if (dif.compareTo(BigDecimal.ZERO) >= 0) {
            return "Na media ou acima";
        }
        if (dif.compareTo(new BigDecimal("-1.50")) >= 0) {
            return "Levemente abaixo da media (acompanhar)";
        }
        return "Abaixo da media (atencao prioritaria)";
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getAlunoId() { return alunoId; }
    public void setAlunoId(int alunoId) { this.alunoId = alunoId; }
    public String getNomeAluno() { return nomeAluno; }
    public void setNomeAluno(String nomeAluno) { this.nomeAluno = nomeAluno; }
    public String getTipoNeurodivergencia() { return tipoNeurodivergencia; }
    public void setTipoNeurodivergencia(String tipoNeurodivergencia) { this.tipoNeurodivergencia = tipoNeurodivergencia; }
    public int getMesReferencia() { return mesReferencia; }
    public void setMesReferencia(int mesReferencia) { this.mesReferencia = mesReferencia; }
    public int getAnoReferencia() { return anoReferencia; }
    public void setAnoReferencia(int anoReferencia) { this.anoReferencia = anoReferencia; }
    public BigDecimal getMediaEscola() { return mediaEscola; }
    public void setMediaEscola(BigDecimal mediaEscola) { this.mediaEscola = mediaEscola; }
    public BigDecimal getNotaAluno() { return notaAluno; }
    public void setNotaAluno(BigDecimal notaAluno) { this.notaAluno = notaAluno; }
    public String getObservacaoGeral() { return observacaoGeral; }
    public void setObservacaoGeral(String observacaoGeral) { this.observacaoGeral = observacaoGeral; }
    public List<Apontamento> getApontamentosDoMes() { return apontamentosDoMes; }
    public void setApontamentosDoMes(List<Apontamento> apontamentosDoMes) { this.apontamentosDoMes = apontamentosDoMes; }
}