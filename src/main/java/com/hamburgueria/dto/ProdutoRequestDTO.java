package com.hamburgueria.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProdutoRequestDTO(

        @NotBlank(message = "O nome do produto é obrigatório.")
        String nome,

        @NotNull(message = "A categoria do produto é obrigatória.")
        CategoriaRequestDTO categoria,

        @NotNull(message = "O preço do produto é obrigatório.")
        @DecimalMin(value = "0.01", message = "O preço deve ser maior que zero.")
        BigDecimal precoUnitario

) {
}
