package br.com.thalison.sistema_acompanhamento_Acompanhamento;


import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

import br.com.thalison.sistema_acompanhamento_Pedagogo.Pedagogo;
import br.com.thalison.sistema_acompanhamento_Psicologo.Psicologo;
import br.com.thalison.sistema_acompanhamento_ResponsavelLegal.ResponsavelLegal;
import br.com.thalison.sistema_acompanhamento_aluno.Aluno;

/**
 * Agregado raiz que representa a ficha de acompanhamento multiprofissional de um aluno neurodivergente.
 */
public class Acompanhamento implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private final Aluno aluno;
    private ResponsavelLegal responsavelLegal;
    private PeriodoAcompanhamento periodo;

    private String objetivo;
    private String observacaoGeral;
    private ParecerTecnico<Psicologo> parecerPsicologico;
    private ParecerTecnico<Pedagogo> parecerPedagogico;
    private StatusAcompanhamento status;


    private Acompanhamento(Builder builder) {
        this.id = builder.id;
        this.aluno = Objects.requireNonNull(builder.aluno, "O aluno é obrigatório no acompanhamento.");
        if (this.aluno.getId() <= 0) {
            throw new IllegalArgumentException("O aluno vinculado deve possuir um identificador válido.");
        }
        this.responsavelLegal = builder.responsavelLegal;
        this.periodo = Objects.requireNonNull(builder.periodo, "O período do acompanhamento é obrigatório.");
        this.objetivo = validarTextoObrigatorio(builder.objetivo, "O objetivo do acompanhamento é obrigatório.");
        this.observacaoGeral = normalizarTextoOpcional(builder.observacaoGeral);
        this.parecerPsicologico = builder.parecerPsicologico;
        this.parecerPedagogico = builder.parecerPedagogico;
        this.status = (builder.status != null) ? builder.status : StatusAcompanhamento.EM_ANDAMENTO;
    }


    public static Builder builder(Aluno aluno, PeriodoAcompanhamento periodo, String objetivo) {
        return new Builder(aluno, periodo, objetivo);
    }

    public void atribuirId(int novoId) {
        if (novoId <= 0) {
            throw new IllegalArgumentException("O identificador do acompanhamento deve ser positivo.");
        }
        if (this.id != null && this.id != novoId) {
            throw new IllegalStateException("Não é permitido alterar o identificador de um acompanhamento já persistido.");
        }
        this.id = novoId;
    }

    public void vincularResponsavelLegal(ResponsavelLegal novoResponsavel) {
        Objects.requireNonNull(novoResponsavel, "O responsável legal não pode ser nulo.");
        if (novoResponsavel.getId() <= 0) {
            throw new IllegalArgumentException("O responsável legal deve possuir um identificador válido.");
        }
        this.responsavelLegal = novoResponsavel;
    }

    public void redefinirPeriodo(PeriodoAcompanhamento novoPeriodo) {
        this.periodo = Objects.requireNonNull(novoPeriodo, "O período não pode ser nulo.");
    }

    public void atualizarObjetivo(String novoObjetivo) {
        this.objetivo = validarTextoObrigatorio(novoObjetivo, "O objetivo do acompanhamento não pode ser vazio.");
    }

    public void atualizarObservacaoGeral(String novaObservacao) {
        this.observacaoGeral = normalizarTextoOpcional(novaObservacao);
    }

    public void registrarParecerPsicologico(Psicologo psicologo, String conteudo, LocalDate dataRegistro) {
        this.parecerPsicologico = new ParecerTecnico<>(psicologo, conteudo, dataRegistro);
    }

    public void registrarParecerPedagogico(Pedagogo pedagogo, String conteudo, LocalDate dataRegistro) {
        this.parecerPedagogico = new ParecerTecnico<>(pedagogo, conteudo, dataRegistro);
    }

    public void alterarStatus(StatusAcompanhamento novoStatus) {
        Objects.requireNonNull(novoStatus, "O status do acompanhamento é obrigatório.");
        this.status = novoStatus;
        if (novoStatus == StatusAcompanhamento.CONCLUIDO && this.periodo.isEmAberto()) {
            this.periodo = this.periodo.encerrarEm(LocalDate.now());
        }
    }

    public boolean isAtivo() {
        return this.status == StatusAcompanhamento.EM_ANDAMENTO;
    }

    public boolean possuiParecerPsicologico() {
        return this.parecerPsicologico != null;
    }

    public boolean possuiParecerPedagogico() {
        return this.parecerPedagogico != null;
    }

    public Integer getId() {
        return id;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public Optional<ResponsavelLegal> getResponsavelLegal() {
        return Optional.ofNullable(responsavelLegal);
    }

    public PeriodoAcompanhamento getPeriodo() {
        return periodo;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public Optional<String> getObservacaoGeral() {
        return Optional.ofNullable(observacaoGeral);
    }

    public Optional<ParecerTecnico<Psicologo>> getParecerPsicologico() {
        return Optional.ofNullable(parecerPsicologico);
    }

    public Optional<ParecerTecnico<Pedagogo>> getParecerPedagogico() {
        return Optional.ofNullable(parecerPedagogico);
    }

    public StatusAcompanhamento getStatus() {
        return status;
    }

    private static String validarTextoObrigatorio(String valor, String mensagemErro) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException(mensagemErro);
        }
        return valor.trim();
    }

    private static String normalizarTextoOpcional(String valor) {
        return (valor == null || valor.isBlank()) ? null : valor.trim();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Acompanhamento that)) return false;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Acompanhamento{id=" + id
                + ", aluno=" + aluno.getNome()
                + ", periodo=" + periodo
                + ", status=" + status + '}';
    }

    public static final class Builder {
        private Integer id;
        private final Aluno aluno;
        private ResponsavelLegal responsavelLegal;
        private final PeriodoAcompanhamento periodo;
        private final String objetivo;
        private String observacaoGeral;
        private ParecerTecnico<Psicologo> parecerPsicologico;
        private ParecerTecnico<Pedagogo> parecerPedagogico;
        private StatusAcompanhamento status = StatusAcompanhamento.EM_ANDAMENTO;

        private Builder(Aluno aluno, PeriodoAcompanhamento periodo, String objetivo) {
            this.aluno = aluno;
            this.periodo = periodo;
            this.objetivo = objetivo;
        }

        public Builder id(Integer id) {
            this.id = id;
            return this;
        }

        public Builder responsavelLegal(ResponsavelLegal responsavelLegal) {
            this.responsavelLegal = responsavelLegal;
            return this;
        }

        public Builder observacaoGeral(String observacaoGeral) {
            this.observacaoGeral = observacaoGeral;
            return this;
        }

        public Builder parecerPsicologico(ParecerTecnico<Psicologo> parecerPsicologico) {
            this.parecerPsicologico = parecerPsicologico;
            return this;
        }

        public Builder parecerPedagogico(ParecerTecnico<Pedagogo> parecerPedagogico) {
            this.parecerPedagogico = parecerPedagogico;
            return this;
        }

        public Builder status(StatusAcompanhamento status) {
            this.status = status;
            return this;
        }

        public Acompanhamento build() {
            return new Acompanhamento(this);
        }
    }
}