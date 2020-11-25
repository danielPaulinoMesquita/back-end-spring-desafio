package com.testedesafio.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.testedesafio.demo.enums.Tipo;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * Telefones:
 * Podem ser cadastrados múltiplos telefones;
 * Pelo menos um telefone deve ser cadastrado;
 * No cadastro de telefone, deve ser informado o tipo de telefone (residencial, comercial e celular) e o número;
 * A máscara de telefone deve ser de acordo com o tipo de telefone (celular possui um digito a mais);
 * o telefone deve ser mostrado com máscara;
 * o telefone deve ser persistido sem máscara.
 */

@Entity(name = "telefone")
public class Telefone implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tipo")
    @Enumerated(value = EnumType.STRING)
    private Tipo tipoTelefone;

    @Column(name = "numero")
    private String numero;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    public Telefone(Tipo tipoTelefone, String numero) {
        this.tipoTelefone = tipoTelefone;
        this.numero = numero;
    }

    public Telefone() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Telefone telefone = (Telefone) o;
        return Objects.equals(id, telefone.id) &&
                tipoTelefone == telefone.tipoTelefone &&
                Objects.equals(numero, telefone.numero);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tipoTelefone, numero);
    }
}
