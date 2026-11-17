package com.hamburgueria.exception;

import com.hamburgueria.entity.StatusPedido;

public class TransicaoStatusInvalidaException extends RuntimeException {

    public TransicaoStatusInvalidaException(StatusPedido statusAtual, StatusPedido novoStatus) {
        super("Transição de status inválida: " + statusAtual + " -> " + novoStatus);
    }
}
