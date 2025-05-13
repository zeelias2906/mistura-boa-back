package com.mistura_boa.mistura_boa.models.enums;

import lombok.Getter;

@Getter
public enum FormaPagamentoEnum {
    PIX("Pix"),
    DINHEIRO("Dinheiro"),
    CARTAO("Cartão de crédito/débito");

    private String descricao;

    FormaPagamentoEnum(String descricacao){
        this.descricao = descricacao;
    }
}
