package com.msavaliadorcredito.application;

import com.msavaliadorcredito.domain.model.SituacaoCliente;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("avaliacoes-credito")
@RequiredArgsConstructor
public class AvaliadorCreditoController {

    private final AvaliadorCreditoService avaliadorCreditoService;
    @GetMapping
    public String status() {
        return "ok";
    }

    @GetMapping(value = "situacao-cliente", params = "cpf")
    public ResponseEntity<SituacaoCliente> consultaSituacaoCliente (@RequestMapping("cpf") String cpf) {
        SituacaoCliente situacaoCliente = avaliadorCreditoService.obterSituacaoCliente(cpf);
    }

}
