package com.testedesafio.demo.service.impl;


import com.testedesafio.demo.api.dto.ClienteDTO;
import com.testedesafio.demo.api.dto.TelefoneDTO;
import com.testedesafio.demo.enums.Perfil;
import com.testedesafio.demo.enums.Tipo;
import com.testedesafio.demo.exception.ErroDeAutenticacao;
import com.testedesafio.demo.exception.RegraDeNegocioException;
import com.testedesafio.demo.model.Cliente;
import com.testedesafio.demo.model.Endereco;
import com.testedesafio.demo.model.Telefone;
import com.testedesafio.demo.model.repository.ClienteRepository;
import com.testedesafio.demo.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    @Transactional
    public Cliente salvarCliente(Cliente cliente) {
        cliente.getEmails().forEach(this::validarEmail);
        return clienteRepository.save(cliente);
    }

    @Override
    public Cliente atualizar(Cliente cliente) {
        Objects.requireNonNull(cliente.getId());
        return clienteRepository.save(cliente);
    }

    @Override
    public void validarEmail(String email) {
        // Validar outros campos
        boolean existe = clienteRepository.existsByEmails(email);

        if(existe){
            throw new RegraDeNegocioException("Email já existe");
        }
    }

    @Override
    public List<Cliente> obterClientes() {
        return clienteRepository.findAll();

    }

    @Override
    public Optional<Cliente> obterClientePorId(Long id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);
        return cliente;
    }

    @Override
    public void deletar(Long id) {

        boolean existe = false;

        if(id != null){
            existe = clienteRepository.existsById(id);
        }

        if (!existe){
            throw new RegraDeNegocioException("Cliente não existe");
        }

        clienteRepository.deleteById(id);
    }

    /**
     * Método para retornar cliente mockado
     * @return
     */
    public Cliente getCliente(){
        Endereco endereco = new Endereco("221221","quebrada","recanto","Brasília","DF","perto de tal");
        Telefone telefone1 = new Telefone(Tipo.RESIDENCIAL,"4322-5555");
        Telefone telefone2 = new Telefone(Tipo.CELULAR,"99322-5555");
        String email= "alguma@gmail.com";
        Set<String> emails= new HashSet<>();
        emails.add(email);
        Set<Perfil> perfils = new HashSet<>();
        perfils.add(Perfil.ADMIN);

        Set<Telefone> telefones = new HashSet<>();
        telefones.addAll(Arrays.asList(telefone1,telefone2));

        Cliente cliente = new Cliente();
        cliente.setId(1l);
        cliente.setNome("Fulano");
        cliente.setCpf("3131313131");
        cliente.setEmails(emails);
        cliente.setPerfis(perfils);
        cliente.setEndereco(endereco);
        cliente.setTelefones(telefones);
        cliente.setSenha("123");

        return cliente;
    }

    public static ClienteDTO converte(Cliente cliente){
        Set<TelefoneDTO> telefoneDTOS = cliente.getTelefones()
                .stream()
                .map(telefone -> {
                    TelefoneDTO telefoneDTO = new TelefoneDTO();
                    telefoneDTO.setNumero(telefone.getNumero());
                    telefoneDTO.setTipoTelefone(telefone.getTipoTelefone());
                    return telefoneDTO;

                }).collect(Collectors.toSet());

        ClienteDTO clienteDTO = new ClienteDTO(
                cliente.getId(),
                cliente.getNome(),
                cliente.getCpf(),
                cliente.getSenha(),
                cliente.getEndereco().getCep(),
                cliente.getEndereco().getLogradouro(),
                cliente.getEndereco().getBairro(),
                cliente.getEndereco().getCidade(),
                cliente.getEndereco().getUf(),
                cliente.getEndereco().getComplemento(),
                telefoneDTOS,
                cliente.getEmails(),
                cliente.getPerfis());

        return clienteDTO;
    }

    public static Cliente converte(ClienteDTO clienteDTO){
        Endereco endereco = new Endereco();
        endereco.setBairro(clienteDTO.getBairro());
        endereco.setCidade(clienteDTO.getCidade());
        endereco.setCep(clienteDTO.getCep());
        endereco.setComplemento(clienteDTO.getComplemento());
        endereco.setLogradouro(clienteDTO.getLogradouro());
        endereco.setUf(clienteDTO.getUf());

        Set<Telefone> telefones = clienteDTO.getTelefones().stream().map(telefoneDTO -> {
            Telefone telefone = new Telefone();
            telefone.setNumero(telefoneDTO.getNumero());
            telefone.setTipoTelefone(telefoneDTO.getTipoTelefone());
            return telefone;
        }).collect(Collectors.toSet());

        Cliente cliente = new Cliente();
        if (clienteDTO.getId()!=null) {
            cliente.setId(clienteDTO.getId());
        }
        cliente.setNome(clienteDTO.getNome());
        cliente.setSenha(clienteDTO.getSenha());
        cliente.setEmails(clienteDTO.getEmails());
        cliente.setCpf(clienteDTO.getCpf());
        cliente.setEndereco(endereco);
        cliente.setPerfis(clienteDTO.getPerfis());
        cliente.setTelefones(telefones);
        cliente.getTelefones().forEach(telefone -> telefone.setCliente(cliente));

        return cliente;

    }
}
