package com.testedesafio.demo.exception;

public class ErroDeAutenticacao extends RuntimeException{

    public ErroDeAutenticacao(String mensagem){
        super(mensagem);
    }
}
