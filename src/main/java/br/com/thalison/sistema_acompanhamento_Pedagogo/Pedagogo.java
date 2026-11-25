

package br.com.thalison.sistema_acompanhamento_Pedagogo;

import java.time.LocalDate;

import br.com.thalison.sistema_acompanhamento_Pessoa.Pessoa;

/**
 * Pedagogo(a) responsável pelo acompanhamento pedagógico dos alunos
 * neurodivergentes na educação básica.
 *
 * Herda de Pessoa: id, nome, email, senha, telefone, cpf, perfil e ativo.
 */
public class Pedagogo extends Pessoa {

    /** Ex.: "Educação Especial e Inclusiva", "Psicopedagogia". */
    private String especialidade;

    /** Formação acadêmica. Ex.: "Pedagogia; Pós em Educação Inclusiva". */
    private String formacao;

    /** Matrícula ou registro funcional na escola/rede de ensino. */
    private String registroFuncional;

    /**
     * Etapa da educação básica em que atua:
     * EDUCACAO_INFANTIL, FUNDAMENTAL_I, FUNDAMENTAL_II ou ENSINO_MEDIO.
     */
    private String segmentoAtuacao;

    /** Indica se atua no Atendimento Educacional Especializado (AEE / Sala de Recursos). */
    private boolean atendeAEE;

    /** Neurodivergências com as quais tem experiência. Ex.: "TEA, TDAH, Dislexia". */
    private String neurodivergenciasAtendidas;

    /** MANHA, TARDE, NOITE ou INTEGRAL. */
    private String turno;

    /** Carga horária semanal, em horas. */
    private int cargaHorariaSemanal;

    private LocalDate dataAdmissao;

    public Pedagogo() { super(); }

    public String getEspecialidade() { return especialidade; }
    public void setEspecialidade(String especialidade) { this.especialidade = especialidade; }

    public String getFormacao() { return formacao; }
    public void setFormacao(String formacao) { this.formacao = formacao; }

    public String getRegistroFuncional() { return registroFuncional; }
    public void setRegistroFuncional(String registroFuncional) { this.registroFuncional = registroFuncional; }

    public String getSegmentoAtuacao() { return segmentoAtuacao; }
    public void setSegmentoAtuacao(String segmentoAtuacao) { this.segmentoAtuacao = segmentoAtuacao; }

    public boolean isAtendeAEE() { return atendeAEE; }
    public void setAtendeAEE(boolean atendeAEE) { this.atendeAEE = atendeAEE; }

    public String getNeurodivergenciasAtendidas() { return neurodivergenciasAtendidas; }
    public void setNeurodivergenciasAtendidas(String neurodivergenciasAtendidas) { this.neurodivergenciasAtendidas = neurodivergenciasAtendidas; }

    public String getTurno() { return turno; }
    public void setTurno(String turno) { this.turno = turno; }

    public int getCargaHorariaSemanal() { return cargaHorariaSemanal; }
    public void setCargaHorariaSemanal(int cargaHorariaSemanal) { this.cargaHorariaSemanal = cargaHorariaSemanal; }

    public LocalDate getDataAdmissao() { return dataAdmissao; }
    public void setDataAdmissao(LocalDate dataAdmissao) { this.dataAdmissao = dataAdmissao; }
}
