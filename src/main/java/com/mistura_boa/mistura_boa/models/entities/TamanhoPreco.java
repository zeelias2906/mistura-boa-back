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
@Table(name = "TAMANHO_PRECO")
public class TamanhoPreco {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_TAMANHO_PRECO")
	private Long id;

    @Column(name = "TAMANHO")
    private String tamanho;

    @Column(name = "VALOR")
    private Float valor;

    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_PRODUTO", nullable = false)
    private Produto produto;

}
