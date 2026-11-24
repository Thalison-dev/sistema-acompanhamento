package br.com.thalison.sistema_acompanhamento_ResponsavelLegal;

import br.com.thalison.sistema_acompanhamento_Pessoa.Pessoa;

public class ResponsavelLegal extends Pessoa {
    private String parentesco;

    public ResponsavelLegal() { super(); }

    public String getParentesco() { return parentesco; }
    public void setParentesco(String parentesco) { this.parentesco = parentesco; }
}