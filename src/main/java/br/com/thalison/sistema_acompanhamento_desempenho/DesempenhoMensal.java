package br.com.thalison.sistema_acompanhamento_desempenho;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import br.com.thalison.sistema_acompanhamento_Apontamento.Apontamento;

public class DesempenhoMensal {
    private int id;
    private int alunoId;
    private String nomeAluno;
    private int mesReferencia;
    private int anoReferencia;
    private BigDecimal mediaEscola;
    private BigDecimal notaAluno;
    private String observacaoGeral;
    private List<Apontamento> apontamentosDoMes = new ArrayList<>();

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getAlunoId() { return alunoId; }
    public void setAlunoId(int alunoId) { this.alunoId = alunoId; }
    public String getNomeAluno() { return nomeAluno; }
    public void setNomeAluno(String nomeAluno) { this.nomeAluno = nomeAluno; }
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

    public BigDecimal getDiferencaMedia() {
        if (notaAluno == null || mediaEscola == null) return BigDecimal.ZERO;
        return notaAluno.subtract(mediaEscola);
    }

    public String getSituacaoDesempenho() {
        if (notaAluno == null || mediaEscola == null) return "Sem dados";
        int cmp = notaAluno.compareTo(mediaEscola);
        if (cmp < 0) return "Abaixo da media da escola";
        if (cmp == 0) return "Na media da escola";
        return "Acima da media da escola";
    }
}