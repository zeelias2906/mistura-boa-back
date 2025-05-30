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
public class TamanhoPrecoDTO {

	private Long id;
    @NotBlank(message = "Tamanho não pode ser vazio")
    private String tamanho;
    @NotNull(message = "Valor não pode ser vazio")
    private Float valor;
    
}
