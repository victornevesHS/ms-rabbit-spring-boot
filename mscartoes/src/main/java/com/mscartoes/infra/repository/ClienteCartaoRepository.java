package com.mscartoes.infra.repository;

import com.mscartoes.domain.ClienteCartao;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.Id;
import java.util.List;

public interface ClienteCartaoRepository extends JpaRepository <ClienteCartao, Long> {
    List<ClienteCartao> findByCpf(String cpf);
}
