
package com.hamburgueria.dto;

import java.math.BigDecimal;

public record ProdutoResponseDTO(
        Long id,
        String nome,
        String categoria,
        BigDecimal precoUnitario,
        boolean disponivel) {
}
