package br.com.thalison.sistema_acompanhamento_Acompanhamento;

import java.util.Arrays;

public enum StatusAcompanhamento {

    EM_ANDAMENTO("Em Andamento"),
    CONCLUIDO("Concluído"),
    SUSPENSO("Suspenso"),
    CANCELADO("Cancelado");

    private final String descricao;

    StatusAcompanhamento(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public boolean isEncerrado() {
        return this == CONCLUIDO || this == CANCELADO;
    }

    public static StatusAcompanhamento fromValor(String valor) {
        if (valor == null || valor.isBlank()) {
            return EM_ANDAMENTO;
        }
        String normalizado = valor.trim();
        return Arrays.stream(values())
                .filter(status -> status.name().equalsIgnoreCase(normalizado)
                        || status.descricao.equalsIgnoreCase(normalizado))
                .findFirst()
                .orElse(EM_ANDAMENTO);
    }
}