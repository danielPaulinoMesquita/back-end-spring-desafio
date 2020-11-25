package com.testedesafio.demo.api.resource;

import com.testedesafio.demo.api.dto.ClienteDTO;
import com.testedesafio.demo.exception.RegraDeNegocioException;
import com.testedesafio.demo.model.Cliente;
import com.testedesafio.demo.service.ClienteService;
import com.testedesafio.demo.service.impl.ClienteServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

import static com.testedesafio.demo.service.impl.ClienteServiceImpl.converte;

@RestController
@RequestMapping("/clientes")
public class ClienteResource {

    private ClienteService clienteService;

    public ClienteResource(ClienteService clienteService){
        this.clienteService = clienteService;
    }

    @GetMapping
    public ResponseEntity obterClientes(){
        List<Cliente> clientes = clienteService.obterClientes();
        List<ClienteDTO> clienteDTOS = clientes.stream().map(ClienteServiceImpl::converte).collect(Collectors.toList());

        return ResponseEntity.ok(clienteDTOS);
    }

    @GetMapping(value = "{id}")
    public ResponseEntity obterClientePorId(@PathVariable("id") Long id){
        return clienteService.obterClientePorId(id)
                .map(cliente -> new ResponseEntity(converte(cliente),HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity salvar(@RequestBody ClienteDTO clienteDTO){
        try {
            Cliente cliente = ClienteServiceImpl.converte(clienteDTO);
            cliente = clienteService.salvarCliente(cliente);
            return new ResponseEntity(ClienteServiceImpl.converte(cliente), HttpStatus.CREATED);
        }catch (RegraDeNegocioException re){
            return ResponseEntity.badRequest().body(re.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody ClienteDTO clienteDTO){
        return clienteService.obterClientePorId(id).map(cliente -> {
            try {
                Cliente cliente1 = ClienteServiceImpl.converte(clienteDTO);
                cliente1.setId(cliente.getId());
                clienteService.atualizar(cliente1);
                return ResponseEntity.ok(cliente1);
            }catch (RegraDeNegocioException re){
                return ResponseEntity.badRequest().body(re.getMessage());
            }
        }).orElseGet(() -> new ResponseEntity("Cliente não encontrado na base de Dados.", HttpStatus.BAD_REQUEST));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletar(@PathVariable("id") Long id){
        clienteService.deletar(id);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

}
