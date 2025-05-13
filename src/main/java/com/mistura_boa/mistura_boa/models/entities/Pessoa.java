package com.mistura_boa.mistura_boa.models.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
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
@Table(name = "PESSOA")
public class Pessoa {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_PESSOA")
	private Long id;

	@Column(name = "NM_PESSOA")
	private String nome;

	@Column(name = "CPF", unique = true)
	private String cpf;

	@Column(name = "DT_NASCIMENTO")
	private LocalDate dataNascimento;

	@Column(name = "TELEFONE")
	private String telefone;

	@Column(name = "DT_EXCLUSAO")
	private LocalDateTime dataExclusao;

    @OneToOne(mappedBy = "pessoa", fetch = FetchType.EAGER)
	private Usuario usuario;

}
