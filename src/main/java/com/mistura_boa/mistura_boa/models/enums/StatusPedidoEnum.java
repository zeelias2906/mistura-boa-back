package com.mistura_boa.mistura_boa.models.enums;

import lombok.Getter;

@Getter
public enum StatusPedidoEnum {
    AGUARDANDO_CONFIRMACAO("Aguardando pedido ser confirmado pelo restaurante"),
	CANCELADO("Pedido Cancelado"),
	RECUSADO("Recusado pelo restaurante"),
    EM_PREPARACAO("Restaurante está preparando o pedido"),
	ENVIADO("Pedido enviado"),
	A_SER_RETIRADO("Pedido aguardando retirada"),
	FINALIZADO("Pedido entregue");

	private String descricao;

	StatusPedidoEnum(String descricao) {
		this.descricao = descricao;
	}
}

