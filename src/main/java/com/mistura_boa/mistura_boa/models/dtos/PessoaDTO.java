package com.mistura_boa.mistura_boa.models.dtos;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PessoaDTO {

	private Long id;
	@NotBlank(message = "Nome deve ser preenchido")
	private String nome;
	@NotBlank(message = "CPF deve ser preenchido")
	private String cpf;
	@NotNull(message = "Data de nascimento deve ser preenchida")
	private LocalDate dataNascimento;
	@NotBlank(message = "Telefone deve ser preenchido")
	private String telefone;
	@JsonIgnore
	private UsuarioDTO usuario;

}
