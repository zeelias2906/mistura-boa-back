package com.mistura_boa.mistura_boa.models.filters;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PedidoByUsuarioPageable {
 
    private Long idUsuario;
    private int page;
    private int size; 
}
