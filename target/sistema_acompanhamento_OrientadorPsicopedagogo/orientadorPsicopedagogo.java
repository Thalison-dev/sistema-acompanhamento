package br.com.thalison.sistema_acompanhamento_OrientadorPsicopedagogo;

import br.com.thalison.sistema_acompanhamento_Pessoa.Pessoa;

public class orientadorPsicopedagogo extends Pessoa {

    private String registroProfissional;

    public orientadorPsicopedagogo() {
    }

    public orientadorPsicopedagogo(int id, String nome, String cpf,
                                   String telefone, String email,
                                   String registroProfissional) {
        super(id, nome, cpf, telefone, email);
        this.registroProfissional = registroProfissional;
    }

    public String getRegistroProfissional() {
        return registroProfissional;
    }

    public void setRegistroProfissional(String registroProfissional) {
        this.registroProfissional = registroProfissional;
    }
}
