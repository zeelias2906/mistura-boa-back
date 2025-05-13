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
public class CancelarPedidoDTO {

    @NotNull(message = "Deve ser informado o pedido")
    private Long idPedido;
    @NotBlank(message = "Justificativa deve ser preenchida")
    private String justificativa;
}
