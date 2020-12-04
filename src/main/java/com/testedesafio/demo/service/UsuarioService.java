package com.testedesafio.demo.service;

import com.testedesafio.demo.model.Usuario;

import java.util.Optional;

public interface UsuarioService {

    Usuario autenticar (String usuario, String senha);

    Optional<Usuario> findByUsuario(String usuario);

}
