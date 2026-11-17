package com.hamburgueria.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

@Schema(name = "PedidoRequest", description = "Dados para criação de um pedido")
public record PedidoRequestDTO(

        @Schema(description = "Nome do cliente", example = "João Silva")
        @NotBlank(message = "O nome do cliente é obrigatório.")
        String cliente,

        @Schema(description = "Itens do pedido")
        @NotEmpty(message = "O pedido deve conter pelo menos um item.")
        @Valid
        List<ItemPedidoRequestDTO> itens
) {
}
