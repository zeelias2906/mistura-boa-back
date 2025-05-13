package com.mistura_boa.mistura_boa.models.dtos;


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
public class EnderecoDTO {

	private Long id;
    @NotBlank(message = "Nome do endereço não pode ser vazio")
	private String nome;
    @NotBlank(message = "Logradouro não pode ser vazio")
	private String logradouro;
    @NotBlank(message = "Bairro não pode ser vazio")
	private String bairro;
	private String complemento;
	private String pontoReferencia;
    @NotNull(message = "Número não pode ser vazio")
	private Integer numero;
    private UsuarioDTO usuario;

}
