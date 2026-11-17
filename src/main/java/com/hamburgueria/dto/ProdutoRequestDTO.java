package com.hamburgueria.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Schema(name = "ProdutoRequest", description = "Dados para cadastro ou atualização de produto")
public record ProdutoRequestDTO(

        @Schema(description = "Nome do produto", example = "X-Bacon")
        @NotBlank(message = "O nome do produto é obrigatório.")
        String nome,

        @Schema(description = "Categoria do produto. Informe o ID de uma categoria existente ou o nome para criar/reutilizar.")
        @NotNull(message = "A categoria do produto é obrigatória.")
        CategoriaRequestDTO categoria,

        @Schema(description = "Preço unitário do produto", example = "55.90")
        @NotNull(message = "O preço do produto é obrigatório.")
        @DecimalMin(value = "0.01", message = "O preço deve ser maior que zero.")
        BigDecimal precoUnitario

) {
}
