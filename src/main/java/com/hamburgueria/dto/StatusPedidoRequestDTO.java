package com.hamburgueria.dto;

import com.hamburgueria.entity.StatusPedido;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(name = "StatusPedidoRequest", description = "Novo status do pedido")
public record StatusPedidoRequestDTO(

        @Schema(
                description = "Status desejado. A sequência permitida é RECEBIDO -> EM_PREPARO -> PRONTO -> ENTREGUE.",
                example = "EM_PREPARO",
                allowableValues = {"RECEBIDO", "EM_PREPARO", "PRONTO", "ENTREGUE"}
        )
        @NotNull(message = "O status do pedido é obrigatório.")
        StatusPedido status
) {
}
