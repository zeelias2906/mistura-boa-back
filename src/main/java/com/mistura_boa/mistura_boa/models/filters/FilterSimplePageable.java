package com.mistura_boa.mistura_boa.models.filters;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FilterSimplePageable {
    
    private FilterSimple filter;
    private int page;
    private int size; 
}
