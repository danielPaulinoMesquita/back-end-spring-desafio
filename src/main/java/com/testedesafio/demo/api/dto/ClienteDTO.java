package com.testedesafio.demo.api.dto;

import com.testedesafio.demo.enums.Perfil;

import java.util.HashSet;
import java.util.Set;

public class ClienteDTO {

    private Long id;

    private String nome;

    private String cpf;

    private String senha;

    private String cep;

    private String logradouro;

    private String bairro;

    private String cidade;

    private String uf;

    private String complemento;

    private Set<TelefoneDTO> telefones = new HashSet<>();

    private Set<String> emails = new HashSet<>();

    private Set<Perfil> perfis = new HashSet<>();

    public ClienteDTO() {
    }

    public ClienteDTO(Long id, String nome, String cpf, String senha, String cep,
                      String logradouro,String bairro, String cidade, String uf,
                      String complemento, Set<TelefoneDTO> telefones, Set<String> emails,
                      Set<Perfil> perfis) {
        this.id =id;
        this.nome = nome;
        this.cpf = cpf;
        this.senha = senha;
        this.cep = cep;
        this.logradouro = logradouro;
        this.bairro = bairro;
        this.cidade = cidade;
        this.uf = uf;
        this.complemento = complemento;
        this.telefones = telefones;
        this.emails = emails;
        this.perfis = perfis;
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

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public Set<TelefoneDTO> getTelefones() {
        return telefones;
    }

    public void setTelefones(Set<TelefoneDTO> telefones) {
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
}
