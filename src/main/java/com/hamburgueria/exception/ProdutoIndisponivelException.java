package com.hamburgueria.exception;

public class ProdutoIndisponivelException extends RuntimeException {

    public ProdutoIndisponivelException(Long id) {
        super("Produto indisponível para pedido. ID: " + id);
    }
}
