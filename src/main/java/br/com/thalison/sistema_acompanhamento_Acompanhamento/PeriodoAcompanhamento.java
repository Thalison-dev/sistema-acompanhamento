package br.com.thalison.sistema_acompanhamento_Acompanhamento;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

/**
 * Value Object imutável que encapsula a vigência de um acompanhamento e suas regras de validação.
 */
public record PeriodoAcompanhamento(
        LocalDate dataInicio,
        LocalDate dataFim
) implements Serializable {

    public PeriodoAcompanhamento {
        Objects.requireNonNull(dataInicio, "A data de início do acompanhamento é obrigatória.");
        if (dataFim != null && dataFim.isBefore(dataInicio)) {
            throw new IllegalArgumentException("A data de término não pode ser anterior à data de início.");
        }
    }

    public static PeriodoAcompanhamento iniciarEm(LocalDate dataInicio) {
        return new PeriodoAcompanhamento(dataInicio, null);
    }

    public PeriodoAcompanhamento encerrarEm(LocalDate dataEncerramento) {
        return new PeriodoAcompanhamento(this.dataInicio, dataEncerramento);
    }

    public boolean isEmAberto() {
        return dataFim == null;
    }

    public long duracaoEmDias() {
        LocalDate fimEfetivo = (dataFim != null) ? dataFim : LocalDate.now();
        return ChronoUnit.DAYS.between(dataInicio, fimEfetivo);
    }
}