package com.mistura_boa.mistura_boa.models.filters;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PedidoFilter {
    private LocalDate dataInicio;
    private LocalDate dataFim;
}
