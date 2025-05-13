package com.mistura_boa.mistura_boa.models.filters;

import java.util.List;

import lombok.Data;

@Data
public class FilterSimple {

    private String nome;
    private List<Long> idsCategoria;
}
