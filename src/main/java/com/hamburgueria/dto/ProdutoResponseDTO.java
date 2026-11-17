package com.hamburgueria.dto;

import java.math.BigDecimal;

public record ProdutoResponseDTO(
        Long id,
        String nome,
        CategoriaResponseDTO categoria,
        BigDecimal precoUnitario,
        boolean disponivel) {
}
