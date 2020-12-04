package com.testedesafio.demo.api.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.testedesafio.demo.api.dto.UsuarioDTO;
import com.testedesafio.demo.api.resource.UsuarioResource;
import com.testedesafio.demo.enums.Perfil;
import com.testedesafio.demo.exception.ErroDeAutenticacao;
import com.testedesafio.demo.model.Usuario;
import com.testedesafio.demo.service.ClienteService;
import com.testedesafio.demo.service.UsuarioService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@WebMvcTest(controllers = UsuarioResource.class)
@AutoConfigureMockMvc
public class UsuarioResourceTest {

    static final String API = "/login";
    static final MediaType JSON = MediaType.APPLICATION_JSON;

    @Autowired
    MockMvc mvc;

    @MockBean
    UsuarioService service;

    @MockBean
    ClienteService clienteService;

    @Test
    public void deveAutenticarUmUsuario() throws Exception {

        String usuarioNome = "admin";
        String senha = "123456";
        String perfil = Perfil.ADMIN.name();

        UsuarioDTO dto = new UsuarioDTO();
        dto.setUsuario(usuarioNome);
        dto.setSenha(senha);
        dto.setPerfil(perfil);

        Usuario usuario = new Usuario();
        usuario.setUsuario(usuarioNome);
        usuario.setSenha(senha);
        usuario.setPerfil(Perfil.ADMIN);

        Mockito.when(service.autenticar(usuarioNome,senha)).thenReturn(usuario);

        String json = new ObjectMapper().writeValueAsString(dto);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(API)
                .accept(JSON)
                .contentType(JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("usuario").value(usuario.getUsuario()))
                .andExpect(MockMvcResultMatchers.jsonPath("senha").value(usuario.getSenha()))
                .andExpect(MockMvcResultMatchers.jsonPath("perfil").value(usuario.getPerfil().name()));
    }

    @Test
    public void deveRetornarBadRequestAoObterErroDeAutenticacao() throws Exception {
        String usuarioNome = "adminErrado";
        String senha = "123";
        String perfil = Perfil.ADMIN.name();

        UsuarioDTO dto = new UsuarioDTO();
        dto.setUsuario(usuarioNome);
        dto.setSenha(senha);
        dto.setPerfil(perfil);

        Mockito.when(service.autenticar(usuarioNome,senha)).thenThrow(ErroDeAutenticacao.class);

        String json = new ObjectMapper().writeValueAsString(dto);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(API)
                .accept(JSON)
                .contentType(JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }


}

