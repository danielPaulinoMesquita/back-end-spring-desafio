package com.testedesafio.demo.api.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.testedesafio.demo.api.dto.ClienteDTO;
import com.testedesafio.demo.api.dto.UsuarioDTO;
import com.testedesafio.demo.api.resource.ClienteResource;
import com.testedesafio.demo.api.resource.UsuarioResource;
import com.testedesafio.demo.enums.Perfil;
import com.testedesafio.demo.exception.ErroDeAutenticacao;
import com.testedesafio.demo.model.Cliente;
import com.testedesafio.demo.model.Usuario;
import com.testedesafio.demo.model.repository.ClienteRepositoryTest;
import com.testedesafio.demo.service.ClienteService;
import com.testedesafio.demo.service.UsuarioService;
import com.testedesafio.demo.service.impl.ClienteServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@WebMvcTest(controllers = ClienteResource.class)
@AutoConfigureMockMvc
public class ClienteResourceTest {

    static final String API = "/clientes";
    static final MediaType JSON = MediaType.APPLICATION_JSON;

    @Autowired
    MockMvc mvc;

    @MockBean
    ClienteService clienteService;

    @Test
    public void deveObterUmClientePeloId() throws Exception {

        // Cenário
        Cliente cliente = ClienteRepositoryTest.getCliente();
        ClienteDTO dto = ClienteServiceImpl.converte(cliente);

        Mockito.when(clienteService.obterClientePorId(dto.getId())).thenReturn(Optional.of(cliente));

        String json = new ObjectMapper().writeValueAsString(dto);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(API.concat("/1"))
                .accept(JSON)
                .contentType(JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("cpf").value(dto.getCpf()))
                .andExpect(MockMvcResultMatchers.jsonPath("nome").value(dto.getNome()))
                .andExpect(MockMvcResultMatchers.jsonPath("id").value(dto.getId()));
    }

    @Test
    public void deveRetornarBadRequestAoObterErroDeAutenticacao() throws Exception {
        Cliente cliente = ClienteRepositoryTest.getCliente();
        ClienteDTO dto = ClienteServiceImpl.converte(cliente);

        Mockito.when(clienteService.obterClientePorId(dto.getId())).thenReturn(Optional.empty());

        String json = new ObjectMapper().writeValueAsString(dto);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(API.concat("/2"))
                .accept(JSON)
                .contentType(JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

}
