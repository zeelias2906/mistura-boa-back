package com.mistura_boa.mistura_boa.models.grids;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProdutoGrid {

    private Long id;
    private String descricao;
    private String nome;
    private LocalDateTime dataExclusao;
    private Float valor;
    private Long idCategoria;
    private String nomeCategoria;
    
}
