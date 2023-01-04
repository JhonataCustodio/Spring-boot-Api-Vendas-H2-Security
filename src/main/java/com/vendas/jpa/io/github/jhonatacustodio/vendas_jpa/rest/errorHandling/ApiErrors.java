package com.vendas.jpa.io.github.jhonatacustodio.vendas_jpa.rest.errorHandling;

import java.util.Arrays;
import java.util.List;


import lombok.Getter;

public class ApiErrors {
    public ApiErrors(List<String> errors) {
        this.errors = errors;
    }

    @Getter
    private List<String> errors;

    public ApiErrors(String mensageError){
        this.errors = Arrays.asList(mensageError);
    }
}
