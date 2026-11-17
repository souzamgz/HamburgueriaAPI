package com.hamburgueria.dto;

import com.hamburgueria.entity.StatusPedido;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.List;

@Schema(name = "PedidoResponse", description = "Dados de retorno de um pedido")
public record PedidoResponseDTO(

        @Schema(description = "ID do pedido", example = "1")
        Long id,

        @Schema(description = "Nome do cliente", example = "João Silva")
        String cliente,

        @Schema(description = "Itens do pedido")
        List<ItemPedidoResponseDTO> itens,

        @Schema(description = "Valor total calculado no servidor", example = "70.90")
        BigDecimal valorTotal,

        @Schema(description = "Status atual do pedido", example = "RECEBIDO")
        StatusPedido status
) {
}
