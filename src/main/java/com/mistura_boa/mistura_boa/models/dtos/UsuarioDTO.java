package com.mistura_boa.mistura_boa.models.dtos;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mistura_boa.mistura_boa.models.enums.RoleUsuarioEnum;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UsuarioDTO {

	private Long id;
	@NotBlank(message = "E-mail deve ser preenchido")
	private String email;
	@JsonIgnore
	private String senha;
	private RoleUsuarioEnum roleUsuario;
	private PessoaDTO pessoa;
}
