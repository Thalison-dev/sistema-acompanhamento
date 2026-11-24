package br.com.thalison.sistema_acompanhamento_Psicologo;

import br.com.thalison.sistema_acompanhamento_Pessoa.Pessoa;

public class Psicologo extends Pessoa {
    private String crp;
    private String especialidade;

    public Psicologo() { super(); }

    public String getCrp() { return crp; }
    public void setCrp(String crp) { this.crp = crp; }
    public String getEspecialidade() { return especialidade; }
    public void setEspecialidade(String especialidade) { this.especialidade = especialidade; }
}