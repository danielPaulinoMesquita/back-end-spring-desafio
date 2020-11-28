package com.testedesafio.demo.service.impl;

import com.testedesafio.demo.api.dto.UsuarioDTO;
import com.testedesafio.demo.enums.Perfil;
import com.testedesafio.demo.exception.ErroDeAutenticacao;
import com.testedesafio.demo.exception.RegraDeNegocioException;
import com.testedesafio.demo.model.Usuario;
import com.testedesafio.demo.service.UsuarioService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Override
    public Usuario autenticar(String usuario, String senha) {
        Optional<Usuario> usuario1 = findByUsuario(usuario);

        if(!usuario1.isPresent()){
            throw new RegraDeNegocioException("Usuario não encontrado");
        }

        if(!usuario1.get().getSenha().equals(senha)){
            throw new ErroDeAutenticacao("Senha incorreta");
        }

        return usuario1.get();
    }


    private Optional<Usuario> findByUsuario(String usuario){
        return usuarioRepositoryMockado()
                .stream().filter(user -> user.getUsuario().equals(usuario)).findAny();
    }


    private List<Usuario> usuarioRepositoryMockado(){
        Usuario admin = new Usuario();
        admin.setUsuario("admin");
        admin.setSenha("123456");
        admin.setPerfil(Perfil.ADMIN);

        Usuario comum = new Usuario();
        comum.setUsuario("comum");
        comum.setSenha("123456");
        comum.setPerfil(Perfil.COMUM);

        List<Usuario> usuarios = new ArrayList<Usuario>(Arrays.asList(admin, comum));

        return usuarios;
    }

    public static UsuarioDTO converte(Usuario usuario){
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setUsuario(usuario.getUsuario());
        usuarioDTO.setSenha(usuario.getSenha());
        usuarioDTO.setPerfil(usuario.getPerfil().toString());

        return usuarioDTO;
    }

}
