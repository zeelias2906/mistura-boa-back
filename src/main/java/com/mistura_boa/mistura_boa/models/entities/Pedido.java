package com.mistura_boa.mistura_boa.models.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.mistura_boa.mistura_boa.models.enums.FormaPagamentoEnum;
import com.mistura_boa.mistura_boa.models.enums.StatusPedidoEnum;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Table(name = "PEDIDO")
public class Pedido {
    
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_PEDIDO")
	private Long id;

    @Column(name = "NUMERO_PEDIDO")
    private Long numeroPedido;

    @Column(name = "VALOR_TOTAL")
    private BigDecimal valor;

    @Column(name = "STATUS_PEDIDO")
    @Enumerated(EnumType.STRING)
    private StatusPedidoEnum statusPedido;

    @Column(name = "JUSTIFICATIVA")
    private String justificativa;

    @Column(name = "FORMA_PAGAMENTO")
    @Enumerated(EnumType.STRING)
    private FormaPagamentoEnum formaPagamento;

    @Column(name = "PRECISA_TROCO")
    private Boolean precisaTroco;

    @Column(name = "VALOR_TROCO")
    private BigDecimal valorTroco;

    @Column(name = "DT_PEDIDO")
    private LocalDateTime dataPedido;

    @Column(name = "DT_FECHAMENTO_PEDIDO")
    private LocalDateTime dataFechamentoPedido;

    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_USUARIO", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_ENDERECO")
    private Endereco endereco;

    @OneToMany(mappedBy = "pedido", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProdutoPedido> produtosPedido;



}

// id_pedido int8 primary key auto_increment,
// valor_total decimal(10,2),
// status_pedido varchar(50),
// data_pedido timestamp not null,
// data_fechamento_pedido timestamp,
// id_usuario int8 not null,
// id_endereco int8,