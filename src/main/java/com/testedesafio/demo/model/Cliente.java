package com.testedesafio.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.testedesafio.demo.enums.Perfil;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity(name = "cliente")
public class Cliente implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "cpf")
    private String cpf;

    @Column(name = "senha")
    private String senha;

    @OneToOne(cascade = CascadeType.ALL)
    private Endereco endereco;

    @JsonManagedReference
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private Set<Telefone> telefones;

    @ElementCollection
    @CollectionTable(name = "email")
    private Set<String> emails = new HashSet<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "perfis")
    private Set<Perfil> perfis = new HashSet<>();

    public Cliente(String nome,
                   String cpf,
                   String senha) {
        this.nome = nome;
        this.cpf = cpf;
        this.senha = senha;
    }

    public Cliente() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Set<Telefone> getTelefones() {
        return telefones;
    }

    public void setTelefones(Set<Telefone> telefones) {
        this.telefones = telefones;
    }

    public Set<String> getEmails() {
        return emails;
    }

    public void setEmails(Set<String> emails) {
        this.emails = emails;
    }

    public Set<Perfil> getPerfis() {
        return perfis;
    }

    public void setPerfis(Set<Perfil> perfis) {
        this.perfis = perfis;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(id, cliente.id) &&
                Objects.equals(cpf, cliente.cpf);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cpf);
    }

    /**  Nome:
            * Mínimo de 3 caracteres;
            * Máximo de 100 caracteres;
            * Campo obrigatório;
             * Permite apenas letras, espaços e números.
        CPF:
            * Sempre deve ser mostrado com máscara;
            * Deve ser persistido na base sem máscara;
            * É um campo obrigatório.

      Endereço:
            * Obrigatório preenchimento de CEP, logradouro, bairro, cidade e uf;
            * Opcional complemento;
            * Outros dados não devem ser preenchidos;
            * Deve estar integrado com um serviço de consulta de CEP. Sugestão: https://viacep.com.br/
            * O usuário pode alterar os dados que vieram do serviço de consulta de CEP;
            * O CEP deve ser mostrado com máscara;
            * O CEP deve ser persistido sem máscara.
      Telefones:
            * Podem ser cadastrados múltiplos telefones;
            * Pelo menos um telefone deve ser cadastrado;
            * No cadastro de telefone, deve ser informado o tipo de telefone (residencial, comercial e celular) e o número;
            * A máscara de telefone deve ser de acordo com o tipo de telefone (celular possui um digito a mais);
            * o telefone deve ser mostrado com máscara;
            * o telefone deve ser persistido sem máscara.
      E-mail:
         * Podem ser cadastrados múltiplos e-mails;
         * Pelo menos um e-mail deve ser cadastrado;
         * Deve ser um e-mail válido.
   * */

}
