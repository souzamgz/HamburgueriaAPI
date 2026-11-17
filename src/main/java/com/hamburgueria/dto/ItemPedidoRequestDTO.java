package com.hamburgueria.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Schema(name = "ItemPedidoRequest", description = "Item que será incluído no pedido")
public record ItemPedidoRequestDTO(

        @Schema(description = "ID do produto", example = "1")
        @NotNull(message = "O produto do item é obrigatório.")
        Long produtoId,

        @Schema(description = "Quantidade solicitada", example = "2")
        @NotNull(message = "A quantidade do item é obrigatória.")
        @Min(value = 1, message = "A quantidade deve ser maior que zero.")
        Integer quantidade
) {
}
