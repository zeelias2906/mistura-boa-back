package com.mistura_boa.mistura_boa.models.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.mistura_boa.mistura_boa.models.enums.FormaPagamentoEnum;
import com.mistura_boa.mistura_boa.models.enums.StatusPedidoEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PedidoDTO {

    private Long id;
    private Long numeroPedido;
    private BigDecimal valor;
    private StatusPedidoEnum statusPedido;
    private String justificativa;
    private FormaPagamentoEnum formaPagamento;
    private Boolean precisaTroco;
    private BigDecimal valorTroco;
    private LocalDateTime dataPedido;
    private LocalDateTime dataFechamentoPedido;
    private UsuarioDTO usuario;
    private EnderecoDTO endereco;
    private List<ProdutoPedidoDTO> produtosPedido;
}
