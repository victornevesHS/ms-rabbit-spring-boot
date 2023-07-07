package com.msavaliadorcredito.application.ex;

public class DadosClienteNotFoundExeption extends Exception {
    public DadosClienteNotFoundExeption() {
        super("Dados do cliente não encontrados para o cpf informado.");
    }
}
