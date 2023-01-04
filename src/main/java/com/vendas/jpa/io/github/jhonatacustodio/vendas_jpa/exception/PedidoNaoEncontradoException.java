package com.vendas.jpa.io.github.jhonatacustodio.vendas_jpa.exception;

public class PedidoNaoEncontradoException extends RuntimeException {

    public PedidoNaoEncontradoException() {
        super("Pedido não encontrado!");
    }
    
}
