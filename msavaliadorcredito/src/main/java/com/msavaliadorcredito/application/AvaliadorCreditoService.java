package com.msavaliadorcredito.application;


import com.msavaliadorcredito.application.ex.DadosClienteNotFoundExeption;
import com.msavaliadorcredito.application.ex.ErroComunicacaoMicroservicesException;
import com.msavaliadorcredito.domain.model.*;
import com.msavaliadorcredito.infra.cliente.CartoesResourceClient;
import com.msavaliadorcredito.infra.cliente.ClienteResourceClient;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

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

    public RetornoAvaliacaoCliente relizarAvaliacao(String cpf, Long renda)
            throws DadosClienteNotFoundExeption, ErroComunicacaoMicroservicesException {
        try {
            ResponseEntity<DadosCliente> dadosCleinteResponse = clientesClient.dadosCliente(cpf);
            ResponseEntity<List<Cartao>> cartoesResponse = cartoesClient.getCartoesRendaAteh(renda);

            List<Cartao> carotes = cartoesResponse.getBody();
            var listaCartoesAprovados = carotes.stream().map(cartao -> {

                DadosCliente dadosCliente = dadosCleinteResponse.getBody();

                BigDecimal limiteBasico = cartao.getLimiteBasico();
                BigDecimal idadeBD = BigDecimal.valueOf(dadosCliente.getIdade());
                var fator = idadeBD.divide(BigDecimal.valueOf(10));
                BigDecimal limiteAprovado = fator.multiply(limiteBasico);

                CartaoAprovado aprovado = new CartaoAprovado();
                aprovado.setCartao(cartao.getName());
                aprovado.setBandeira(cartao.getBandeira());
                aprovado.setLimiteAprovado(limiteAprovado);

                return aprovado;

            }).collect(Collectors.toList());

            return new RetornoAvaliacaoCliente(listaCartoesAprovados);

        } catch (FeignException.FeignClientException e) {
            int status = e.status();
            if (HttpStatus.NOT_FOUND.value() == status){
                throw new DadosClienteNotFoundExeption();
            }
            throw new ErroComunicacaoMicroservicesException(e.getMessage(), status);
        }

    }

}
