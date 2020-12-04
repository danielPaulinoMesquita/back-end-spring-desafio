package com.testedesafio.demo.model.repository;

import com.testedesafio.demo.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByEmails(String email);

    boolean existsByEmails(String email);

}
