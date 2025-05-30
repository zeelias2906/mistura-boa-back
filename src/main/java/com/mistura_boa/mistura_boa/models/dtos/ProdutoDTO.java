package com.mistura_boa.mistura_boa.models.dtos;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProdutoDTO {

	private Long id;
    @NotBlank(message = "Descrição não pode ser vazia")
    private String descricao;
    @NotBlank(message = "Nome não pode ser vazio")
    private String nome;
    private LocalDateTime dataExclusao;
    @NotBlank(message = "Produto precisa ter uma imagem")
    private String imgProduto;
    private Boolean isTamanhoUnico;
    private Float valor;
    private Float menorValor;
    private CategoriaDTO categoria;
    private List<TamanhoPrecoDTO> tamanhoPrecos;
    private Long ordenacao;

}
