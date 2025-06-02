package com.mistura_boa.mistura_boa.models.grids;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CategoriaGrid {
    
    private Long id;
    private String descricao;
    private String nome;
    private String icone;
    private Long ordenacao;

}
