package com.mscartoes.application.representation;


import com.mscartoes.domain.BandeiraCartao;
import com.mscartoes.domain.Cartao;
import lombok.Data;
import org.hibernate.type.StringNVarcharType;

import java.math.BigDecimal;

@Data
public class CartaoSaveRequest {
    private String nome;
    private BandeiraCartao bandeira;
    private BigDecimal renda;
    private BigDecimal limite;

    public Cartao toModel() {
        return new Cartao(nome, bandeira, renda, limite);
    }
}
