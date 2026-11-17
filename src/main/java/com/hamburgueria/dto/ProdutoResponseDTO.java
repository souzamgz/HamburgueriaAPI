package com.hamburgueria.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(name = "ProdutoResponse", description = "Dados de retorno de um produto")
public record ProdutoResponseDTO(

        @Schema(description = "ID do produto", example = "1")
        Long id,

        @Schema(description = "Nome do produto", example = "X-Bacon")
        String nome,

        @Schema(description = "Categoria do produto")
        CategoriaResponseDTO categoria,

        @Schema(description = "Preço unitário do produto", example = "55.90")
        BigDecimal precoUnitario,

        @Schema(description = "Indica se o produto está disponível no cardápio", example = "true")
        boolean disponivel) {
}
