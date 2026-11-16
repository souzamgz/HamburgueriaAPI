package com.hamburgueria.dto;

import java.math.BigDecimal;

public record ProdutoRequestDTO(
        String nome,
        String categoria,
        BigDecimal precounitario) {

}
