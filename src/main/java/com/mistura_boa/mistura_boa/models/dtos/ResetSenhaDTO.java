package com.mistura_boa.mistura_boa.models.dtos;

import lombok.Data;

@Data
public class ResetSenhaDTO {

    private String email;
	private String nome;
	private String cpf;
	private String novaSenha;

}
