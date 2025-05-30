package com.mistura_boa.mistura_boa.models.entities;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "PRODUTO")
public class Produto {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_PRODUTO")
	private Long id;

    @Column(name = "DS_PRODUTO")
    private String descricao;

    @Column(name = "NM_PRODUTO")
    private String nome;

    @Column(name = "DT_EXCLUSAO")
    private LocalDateTime dataExclusao;

    @Column(name = "IMG_PRODUTO")
    private String imgProduto;

    @Column(name = "IS_TAMANHO_UNICO")
    private Boolean isTamanhoUnico;

    @Column(name = "VALOR")
    private Float valor;

    @Column(name = "MENOR_VALOR")
    private Float menorValor;
    
    @ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_CATEGORIA", nullable = false)
    private Categoria categoria;

    @OneToMany(mappedBy = "produto", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<TamanhoPreco> tamanhoPrecos;

    @Column(name = "ORDENACAO")
	private Long ordenacao;

}