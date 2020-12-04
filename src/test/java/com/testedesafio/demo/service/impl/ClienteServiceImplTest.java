package com.testedesafio.demo.service.impl;

import com.testedesafio.demo.exception.RegraDeNegocioException;
import com.testedesafio.demo.model.Cliente;
import com.testedesafio.demo.model.repository.ClienteRepository;
import com.testedesafio.demo.model.repository.ClienteRepositoryTest;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class ClienteServiceImplTest {

    @MockBean
    ClienteRepository clienteRepository;

    @SpyBean
    ClienteServiceImpl service;

    @Test
    public void deveSalvarUmCliente(){
        Cliente clienteASalvar = ClienteRepositoryTest.getCliente();

        Cliente clienteSalvo = ClienteRepositoryTest.getCliente();
        clienteSalvo.setId(1l);

        Mockito.when(clienteRepository.save(clienteASalvar)).thenReturn(clienteSalvo);

        Cliente cliente = service.salvarCliente(clienteASalvar);

        Assertions.assertThat(cliente.getId()).isEqualTo(clienteSalvo.getId());
        Assert.assertNotNull(cliente.getEndereco());
    }

    @Test
    public void deveAtualizarUmCliente(){
        // cenário
        Cliente clienteSalvo = ClienteRepositoryTest.getCliente();
        clienteSalvo.setId(1l);

        Mockito.when(clienteRepository.save(clienteSalvo)).thenReturn(clienteSalvo);

        // execução
        service.salvarCliente(clienteSalvo);

        // verificação
        Mockito.verify(clienteRepository, Mockito.times(1)).save(clienteSalvo);
    }

    @Test
    public void deveLancarErroAoTentarAtualizarUmClienteQueAindaNaoFoiSalvo(){
        // cenário
        Cliente clienteASalvar = ClienteRepositoryTest.getCliente();
        clienteASalvar.setId(null);

        // execução e verificação
        Assertions.catchThrowable(()-> service.atualizar(clienteASalvar));

        Mockito.verify(clienteRepository, Mockito.never()).save(clienteASalvar);
    }

    @Test
    public void deveDeletarUmCliente(){
        // cenário
        Cliente clienteASalvar = ClienteRepositoryTest.getCliente();

        // execução

        Mockito.doNothing().when(service).existe(clienteASalvar.getId());

        service.deletar(clienteASalvar.getId());

        // verificação
        Mockito.verify(clienteRepository).deleteById(clienteASalvar.getId());
    }

    @Test
    public void deveLancarErroAoTentarDeletarUmClienteQueAindaNaoFoiSalvo(){
        // cenário
        Cliente clienteADeletar = ClienteRepositoryTest.getCliente();

        // execução
        Assertions.catchThrowableOfType(()-> service.deletar(clienteADeletar.getId()), RegraDeNegocioException.class);


        Mockito.verify(clienteRepository, Mockito.never()).delete(clienteADeletar);
    }
}
