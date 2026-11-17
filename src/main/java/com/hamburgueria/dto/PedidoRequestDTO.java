package com.hamburgueria.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record PedidoRequestDTO(

        @NotBlank(message = "O nome do cliente é obrigatório.")
        String cliente,

        @NotEmpty(message = "O pedido deve conter pelo menos um item.")
        @Valid
        List<ItemPedidoRequestDTO> itens
) {
}
