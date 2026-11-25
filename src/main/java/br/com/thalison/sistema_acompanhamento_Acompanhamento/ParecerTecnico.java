package br.com.thalison.sistema_acompanhamento_Acompanhamento;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import br.com.thalison.sistema_acompanhamento_Pessoa.Pessoa;


public record ParecerTecnico<T extends Pessoa>(
        T profissional,
        String conteudo,
        LocalDate dataRegistro
) implements Serializable {

    public ParecerTecnico {
        Objects.requireNonNull(profissional, "O profissional responsável pelo parecer é obrigatório.");
        if (conteudo == null || conteudo.isBlank()) {
            throw new IllegalArgumentException("O conteúdo do parecer técnico não pode ser vazio.");
        }
        conteudo = conteudo.trim();
        dataRegistro = (dataRegistro != null) ? dataRegistro : LocalDate.now();
    }

    public String nomeProfissional() {
        String nome = profissional.getNome();
        return (nome != null && !nome.isBlank()) ? nome : "Profissional #" + profissional.getId();
    }
}