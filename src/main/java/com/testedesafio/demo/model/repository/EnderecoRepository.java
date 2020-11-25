package com.testedesafio.demo.model.repository;

import com.testedesafio.demo.model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
}
