package com.mistura_boa.mistura_boa.models.enums;

import java.util.Arrays;
import java.util.regex.Pattern;

import lombok.Getter;

@Getter
public enum PublicRouteEnum {
    LOGIN("/auth/login", "POST"),
    RESET_SENHA("/auth/reset-password", "POST"),
    NOVO_USUARIO("/auth/new-user", "POST"),
    GET_ALL_PRODUTO("/produto/", "GET"),
    GET_PRODUTO_BY_ID("/produto/{id}", "GET"),
    GET_ALL_CATEGORIA("/categoria/", "GET"),
    SEARCH_PRODUTO("/produto/search", "POST"),
    FIND_PRODUTO_BY_CATEGORIA("/produto/produto-by-categoria/{idCategoria}", "GET"),
    SEARCH_CATEGORIA("/categoria/search", "POST");

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
