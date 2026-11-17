package com.hamburgueria.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "CategoriaRequest", description = "Dados para cadastro ou atualização de categoria")
public record CategoriaRequestDTO(

        @Schema(description = "ID da categoria", example = "1")
        Long id,

        @Schema(description = "Nome da categoria", example = "Lanche")
        String nome,

        @Schema(description = "Descrição da categoria", example = "Hambúrgueres artesanais")
        String descricao
) {
}
