package com.hamburgueria.dto;

import com.hamburgueria.entity.StatusPedido;

import java.math.BigDecimal;
import java.util.List;

public record PedidoResponseDTO(
        Long id,
        String cliente,
        List<ItemPedidoResponseDTO> itens,
        BigDecimal valorTotal,
        StatusPedido status
) {
}
