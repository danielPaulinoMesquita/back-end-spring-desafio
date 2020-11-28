package com.testedesafio.demo.service;

import com.testedesafio.demo.api.dto.ClienteDTO;
import com.testedesafio.demo.model.Cliente;

import java.util.List;
import java.util.Optional;

public interface ClienteService {

    void deletar(Long id);

    Cliente salvarCliente(Cliente cliente);

    Cliente atualizar(Cliente cliente);

    List<Cliente> obterClientes();

    Optional<Cliente> obterClientePorId(Long id);
}
