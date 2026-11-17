package com.hamburgueria.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(name = "ProdutoDisponibilidadeRequest", description = "Dados para ativar ou desativar um produto")
public record ProdutoDisponibilidadeRequestDTO(

        @Schema(description = "Disponibilidade do produto no cardápio", example = "false")
        @NotNull(message = "A disponibilidade do produto é obrigatória.")
        Boolean disponivel
) {
}
