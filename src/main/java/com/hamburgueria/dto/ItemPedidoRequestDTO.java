package com.hamburgueria.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ItemPedidoRequestDTO(

        @NotNull(message = "O produto do item é obrigatório.")
        Long produtoId,

        @NotNull(message = "A quantidade do item é obrigatória.")
        @Min(value = 1, message = "A quantidade deve ser maior que zero.")
        Integer quantidade
) {
}
