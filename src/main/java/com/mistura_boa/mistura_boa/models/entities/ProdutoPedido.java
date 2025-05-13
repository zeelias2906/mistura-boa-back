package com.mistura_boa.mistura_boa.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "PRODUTO_PEDIDO")
public class ProdutoPedido {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_PRODUTO_PEDIDO")
	private Long id;

    @Column(name = "OBSERVACAO")
    private String observacao;

    @ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_PRODUTO", nullable = false)
    private Produto produto;
    
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_PEDIDO", nullable = false)
    private Pedido pedido;

}
