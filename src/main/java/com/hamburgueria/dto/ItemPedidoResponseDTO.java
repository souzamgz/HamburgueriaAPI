package com.hamburgueria.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(name = "ItemPedidoResponse", description = "Item retornado em um pedido")
public record ItemPedidoResponseDTO(

        @Schema(description = "ID do item do pedido", example = "1")
        Long id,

        @Schema(description = "ID do produto", example = "1")
        Long produtoId,

        @Schema(description = "Nome do produto", example = "X-Bacon")
        String produtoNome,

        @Schema(description = "Quantidade solicitada", example = "2")
        Integer quantidade,

        @Schema(description = "Subtotal do item calculado no servidor", example = "111.80")
        BigDecimal precoSubtotal
) {
}
