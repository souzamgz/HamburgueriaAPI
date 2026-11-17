package com.hamburgueria.entity;

public enum StatusPedido {

    RECEBIDO,
    EM_PREPARO,
    PRONTO,
    ENTREGUE;

    public StatusPedido proximo() {
        return switch (this) {
            case RECEBIDO -> EM_PREPARO;
            case EM_PREPARO -> PRONTO;
            case PRONTO -> ENTREGUE;
            case ENTREGUE -> null;
        };
    }

    public boolean podeTransicionarPara(StatusPedido novoStatus) {
        return novoStatus != null && this.proximo() == novoStatus;
    }
}
