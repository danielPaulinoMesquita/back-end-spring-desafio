package com.testedesafio.demo.api.dto;

import com.testedesafio.demo.enums.Tipo;

public class TelefoneDTO {

    private Tipo tipoTelefone;

    private String numero;

    public TelefoneDTO() {
    }

    public Tipo getTipoTelefone() {
        return tipoTelefone;
    }

    public void setTipoTelefone(Tipo tipoTelefone) {
        this.tipoTelefone = tipoTelefone;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
}
