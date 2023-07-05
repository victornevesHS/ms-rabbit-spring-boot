package com.msavaliadorcredito.domain.model;


import lombok.Data;

import java.util.List;

@Data
public class SituacaoCliente {
    private DadosCliente cliente;
    private List<CartaoCliente> cartao;
}
