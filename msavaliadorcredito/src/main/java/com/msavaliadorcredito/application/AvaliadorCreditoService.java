package com.msavaliadorcredito.application;


import com.msavaliadorcredito.application.ex.DadosClienteNotFoundExeption;
import com.msavaliadorcredito.application.ex.ErroComunicacaoMicroservicesException;
import com.msavaliadorcredito.domain.model.CartaoCliente;
import com.msavaliadorcredito.domain.model.DadosCliente;
import com.msavaliadorcredito.domain.model.SituacaoCliente;
import com.msavaliadorcredito.infra.cliente.CartoesResourceClient;
import com.msavaliadorcredito.infra.cliente.ClienteResourceClient;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AvaliadorCreditoService {

    private final ClienteResourceClient clientesClient;
    private final CartoesResourceClient cartoesClient;
    public SituacaoCliente obterSituacaoCliente(String cpf) throws DadosClienteNotFoundExeption, ErroComunicacaoMicroservicesException {

        try {
            ResponseEntity<DadosCliente> dadosCleinteResponse = clientesClient.dadosCliente(cpf);
            ResponseEntity<List<CartaoCliente>> cartoesResponse = cartoesClient.getCartoesByCliente(cpf);

            return SituacaoCliente
                    .builder()
                    .cliente(dadosCleinteResponse.getBody())
                    .cartao(cartoesResponse.getBody())
                    .build();

        } catch (FeignException.FeignClientException e) {
            int status = e.status();
            if (HttpStatus.NOT_FOUND.value() == status){
                throw new DadosClienteNotFoundExeption();
            }
            throw new ErroComunicacaoMicroservicesException(e.getMessage(), status);
        }

    }
}
