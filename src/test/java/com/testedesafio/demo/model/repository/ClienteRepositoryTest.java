package com.testedesafio.demo.model.repository;

import com.testedesafio.demo.enums.Perfil;
import com.testedesafio.demo.enums.Tipo;
import com.testedesafio.demo.model.Cliente;
import com.testedesafio.demo.model.Endereco;
import com.testedesafio.demo.model.Telefone;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ClienteRepositoryTest {

    @Autowired
    ClienteRepository repository;

    @Autowired
    TestEntityManager entityManager;

    @Test
    public void deveSalvarUmCliente(){
        Cliente cliente = getCliente();

        cliente = repository.save(cliente);

        Assertions.assertThat(cliente.getId()).isNotNull();
    }

    @Test
    public void deveDeletarUmCliente(){
        Cliente cliente = criarEPersistirUmCliente();

        cliente = entityManager.find(Cliente.class, cliente.getId());

        repository.delete(cliente);

        Cliente clienteInexistente = entityManager.find(Cliente.class, cliente.getId());
        Assertions.assertThat(clienteInexistente).isNull();
    }

    @Test
    public void deveAtualizarUmCliente(){
        Cliente cliente = criarEPersistirUmCliente();

        cliente.setNome("Teste Atualizar");
        cliente.setCpf("888888888");

        repository.save(cliente);

        Cliente clienteAtualizado = entityManager.find(Cliente.class, cliente.getId());

        Assertions.assertThat(clienteAtualizado.getNome()).isEqualTo("Teste Atualizar");
        Assertions.assertThat(clienteAtualizado.getCpf()).isEqualTo("888888888");

    }

    @Test
    public void deveBuscarUmClientePorId(){
        Cliente cliente = criarEPersistirUmCliente();

        Optional<Cliente> clienteEncontrado = repository.findById(cliente.getId());

        Assertions.assertThat(clienteEncontrado.isPresent()).isTrue();
    }

    private Cliente criarEPersistirUmCliente() {
        Cliente cliente = getCliente();
        cliente.setId(null);
        entityManager.persist(cliente);
        return cliente;
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
        cliente.setEndereco(endereco);
        cliente.setTelefones(telefones);

        return cliente;
    }
}
