package com.mistura_boa.mistura_boa.models.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProdutoPedidoDTO {

    private Long id;
    private String observacao;
    private ProdutoDTO produto;
    @JsonIgnore
    private PedidoDTO pedido;

}
