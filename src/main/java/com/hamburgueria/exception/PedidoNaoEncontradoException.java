package com.hamburgueria.exception;

public class PedidoNaoEncontradoException extends RuntimeException {

    public PedidoNaoEncontradoException(Long id) {
        super("Pedido não encontrado com o ID: " + id);
    }
}
