package com.hamburgueria.dto;

import com.hamburgueria.entity.StatusPedido;
import jakarta.validation.constraints.NotNull;

public record StatusPedidoRequestDTO(

        @NotNull(message = "O status do pedido é obrigatório.")
        StatusPedido status
) {
}
