package com.mistura_boa.mistura_boa.models.entities;

import java.time.LocalDateTime;

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

    @Column(name = "VALOR")
    private Float valor;
    
    @ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_CATEGORIA", nullable = false)
    private Categoria categoria;


}