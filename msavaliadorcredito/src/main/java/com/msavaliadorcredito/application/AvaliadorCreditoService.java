package com.msavaliadorcredito.application;


import com.msavaliadorcredito.domain.model.DadosCliente;
import com.msavaliadorcredito.domain.model.SituacaoCliente;
import com.msavaliadorcredito.infra.cliente.ClienteResourceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AvaliadorCreditoService {

    private final ClienteResourceClient clientesClient;
    public SituacaoCliente obterSituacaoCliente(String cpf) {

       ResponseEntity<DadosCliente> dadosCleinteResponse = clientesClient.dadosCliente(cpf);

       return SituacaoCliente
               .builder()
               .cliente(dadosCleinteResponse.getBody())
               .build();
    }

}
