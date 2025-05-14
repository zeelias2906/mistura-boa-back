package com.mistura_boa.mistura_boa.models.enums;

import java.util.Arrays;
import java.util.regex.Pattern;

import lombok.Getter;

@Getter
public enum PublicRouteEnum {
    LOGIN("/auth/login", "POST"),
    RESET_SENHA("/auth/reset-password", "POST"),
    NOVO_USUARIO("/auth/new-user", "POST"),
    GET_PRODUTO_BY_ID("/produto/{id}", "GET"),
    SEARCH_PRODUTO_ACTIVE("/produto/search-active", "POST"),
    SEARCH_GRID("/produto/search-grid", "POST");

    private final String rota;
    private final String metodo;

    PublicRouteEnum(String rota, String metodo){
        this.rota = rota;
        this.metodo = metodo;
    }

    public static boolean isPublicRoute(String uri, String metodoHttp) {
        String basePath = "/mistura-boa/api";
    
        return Arrays.stream(values())
            .anyMatch(route -> {
                String fullPathRegex = (basePath + route.rota)
                    .replaceAll("\\{[^/]+}", "[^/]+"); // transforma {id} em [^/]+
    
                return Pattern.matches(fullPathRegex, uri)
                    && route.metodo.equalsIgnoreCase(metodoHttp);
            });
    }

    public static String[] getRoutesArray() {
        return Arrays.stream(values()).map(PublicRouteEnum::getRota).toArray(String[]::new);
    }

}
