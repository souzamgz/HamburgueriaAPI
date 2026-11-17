package com.hamburgueria.dto;

import jakarta.validation.constraints.NotNull;

public record ProdutoDisponibilidadeRequestDTO(

        @NotNull(message = "A disponibilidade do produto é obrigatória.")
        Boolean disponivel
) {
}
