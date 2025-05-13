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
@Table(name = "ENDERECO")
public class Endereco {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_ENDERECO")
	private Long id;

	@Column(name = "NM_ENDERECO")
	private String nome;

	@Column(name = "LOGRADOURO")
	private String logradouro;

	@Column(name = "BAIRRO")
	private String bairro;

	@Column(name = "COMPLEMENTO")
	private String complemento;

    @Column(name = "PONTO_REFERENCIA")
	private String pontoReferencia;

    @Column(name = "NUMERO")
	private Integer numero;

	@Column(name = "DT_EXCLUSAO")
	private LocalDateTime dataExclusao;

    @ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_USUARIO", nullable = false)
    private Usuario usuario;

}
