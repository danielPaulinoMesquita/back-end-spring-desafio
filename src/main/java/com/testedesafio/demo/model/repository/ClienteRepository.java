package com.testedesafio.demo.model.repository;

import com.testedesafio.demo.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByEmails(String email);

    boolean existsByEmails(String email);

}
