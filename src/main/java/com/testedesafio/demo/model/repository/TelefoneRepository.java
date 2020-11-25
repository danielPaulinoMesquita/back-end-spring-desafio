package com.testedesafio.demo.model.repository;

import com.testedesafio.demo.model.Telefone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TelefoneRepository extends JpaRepository<Telefone,Long> {
}
