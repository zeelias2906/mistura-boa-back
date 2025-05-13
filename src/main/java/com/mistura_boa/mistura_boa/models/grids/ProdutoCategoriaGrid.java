package com.mistura_boa.mistura_boa.models.grids;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProdutoCategoriaGrid {

    private Long idProduto;
    private String descricaoProduto;
    private String nomeProduto;
    private String imgProduto;
    private Float valorProduto;
    private Long idCategoria;
    private String descricaoCategoria;
    private String nomeCategoria;
    private String iconeCategoria;
}
