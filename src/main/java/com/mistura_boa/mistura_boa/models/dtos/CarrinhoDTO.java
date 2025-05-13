package com.mistura_boa.mistura_boa.models.dtos;

import java.math.BigDecimal;
import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CarrinhoDTO {

	private Long id;
    private BigDecimal valorTotal;
    private UsuarioDTO usuario;
    private List<ProdutoCarrinhoDTO> produtosCarrinho;
}
