package com.mistura_boa.mistura_boa.models.dtos;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CategoriaDTO {

	private Long id;
    @NotBlank(message = "Descrição não pode ser vazia")
    private String descricao;
    @NotBlank(message = "Nome não pode ser vazio")
    private String nome;
    private String icone;
    private LocalDateTime dataExclusao;
}
