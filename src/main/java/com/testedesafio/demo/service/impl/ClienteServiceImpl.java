package com.testedesafio.demo.service.impl;


import com.testedesafio.demo.api.dto.ClienteDTO;
import com.testedesafio.demo.api.dto.TelefoneDTO;
import com.testedesafio.demo.exception.RegraDeNegocioException;
import com.testedesafio.demo.model.Cliente;
import com.testedesafio.demo.model.Endereco;
import com.testedesafio.demo.model.Telefone;
import com.testedesafio.demo.model.repository.ClienteRepository;
import com.testedesafio.demo.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.MaskFormatter;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    @Transactional
    public Cliente salvarCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @Override
    public Cliente atualizar(Cliente cliente) {
        Objects.requireNonNull(cliente.getId());
        return clienteRepository.save(cliente);
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
        existe(id);
        clienteRepository.deleteById(id);
    }

    @Override
    public void existe(Long id){
        boolean existe = false;

        if(id != null){
            existe = clienteRepository.existsById(id);
        }

        if (!existe){
            throw new RegraDeNegocioException("Cliente não existe");
        }
    }

    public static ClienteDTO converte(Cliente cliente){
        Set<TelefoneDTO> telefoneDTOS = cliente.getTelefones()
                .stream()
                .map(telefone -> {
                    TelefoneDTO telefoneDTO = new TelefoneDTO();
                    if (telefone.getTipoTelefone().name().equals("CELULAR")){
                        telefoneDTO.setNumero(formatarString(telefone.getNumero(),"(##)#####-####"));
                    } else{
                        telefoneDTO.setNumero(formatarString(telefone.getNumero(),"(##)####-####"));
                    }
                    telefoneDTO.setTipoTelefone(telefone.getTipoTelefone());
                    return telefoneDTO;

                }).collect(Collectors.toSet());

        ClienteDTO clienteDTO = new ClienteDTO(
                cliente.getId(),
                cliente.getNome(),
                formatarString(cliente.getCpf(), "###.###.###-##"),
                formatarString(cliente.getEndereco().getCep(), "##.###-###"),
                cliente.getEndereco().getLogradouro(),
                cliente.getEndereco().getBairro(),
                cliente.getEndereco().getCidade(),
                cliente.getEndereco().getUf(),
                cliente.getEndereco().getComplemento(),
                telefoneDTOS,
                cliente.getEmails());

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
        cliente.setEmails(clienteDTO.getEmails());
        cliente.setCpf(clienteDTO.getCpf());
        cliente.setEndereco(endereco);
        cliente.setTelefones(telefones);
        cliente.getTelefones().forEach(telefone -> telefone.setCliente(cliente));

        return cliente;

    }

    public static String formatarString(String texto, String mascara)  {
        try {
            MaskFormatter mf = new MaskFormatter(mascara);
            mf.setValueContainsLiteralCharacters(false);
            return mf.valueToString(texto);
        }catch (ParseException e){
            return e.getMessage();
        }
    }
}
