package com.vendas.jpa.io.github.jhonatacustodio.vendas_jpa.exception;

public class ValidationFilds extends RuntimeException {
    
    public ValidationFilds(){
        super("Este campo precisa ser preenchido!");
    }
}
