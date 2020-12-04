package com.testedesafio.demo.service.impl;

import com.testedesafio.demo.api.dto.UsuarioDTO;
import com.testedesafio.demo.enums.Perfil;
import com.testedesafio.demo.exception.ErroDeAutenticacao;
import com.testedesafio.demo.exception.RegraDeNegocioException;
import com.testedesafio.demo.model.Usuario;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class UsuarioServiceImplTest {

    private static String USUARIO = "admin";
    private static String SENHA = "123456";
    private static Perfil PERFILADMIN = Perfil.ADMIN;

    @SpyBean
    UsuarioServiceImpl service;

    @Test
    public void deveAutenticar(){
        UsuarioDTO usuarioDTO = getUsuarioDTO();
        Usuario usuario = getUsuario();

        Usuario usuarioAutenticado = service.autenticar(usuarioDTO.getUsuario(), usuarioDTO.getSenha());

        Assertions.assertThat(usuarioAutenticado.getUsuario()).isEqualTo(usuario.getUsuario());
        Assertions.assertThat(usuarioAutenticado.getSenha()).isEqualTo(usuario.getSenha());
        Assertions.assertThat(usuarioAutenticado.getPerfil()).isEqualTo(usuario.getPerfil());

    }

    @Test
    public void deveLancarErroAoAutenticarUsuario(){
        UsuarioDTO usuarioDTO = getUsuarioDTO();
        usuarioDTO.setUsuario("adminErrado");

        Assertions.catchThrowableOfType(()->service.autenticar(usuarioDTO.getUsuario(),usuarioDTO.getSenha()), RegraDeNegocioException.class);
    }

    @Test
    public void deveLancarErroAoAutenticarSenha(){
        UsuarioDTO usuarioDTO = getUsuarioDTO();
        usuarioDTO.setSenha("1234");

        Assertions.catchThrowableOfType(()->service.autenticar(usuarioDTO.getUsuario(),usuarioDTO.getSenha()), ErroDeAutenticacao.class);
    }

    public static Usuario getUsuario(){
        Usuario usuario = new Usuario();
        usuario.setUsuario(USUARIO);
        usuario.setSenha(SENHA);
        usuario.setPerfil(PERFILADMIN);

        return usuario;
    }

    public static UsuarioDTO getUsuarioDTO(){
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setUsuario(USUARIO);
        usuarioDTO.setSenha(SENHA);

        return usuarioDTO;
    }
}
