package br.com.thalison.sistema_acompanhamento_Secretaria;

import br.com.thalison.sistema_acompanhamento_Pessoa.Pessoa;

public class Secretaria extends Pessoa {
    private String setor;

    public Secretaria() { super(); }

    public String getSetor() { return setor; }
    public void setSetor(String setor) { this.setor = setor; }
}