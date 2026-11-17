package com.hamburgueria;

import com.hamburgueria.dto.CategoriaRequestDTO;
import com.hamburgueria.dto.ItemPedidoRequestDTO;
import com.hamburgueria.dto.PedidoRequestDTO;
import com.hamburgueria.dto.PedidoResponseDTO;
import com.hamburgueria.dto.ProdutoRequestDTO;
import com.hamburgueria.dto.ProdutoResponseDTO;
import com.hamburgueria.entity.StatusPedido;
import com.hamburgueria.exception.ProdutoIndisponivelException;
import com.hamburgueria.exception.TransicaoStatusInvalidaException;
import com.hamburgueria.service.PedidoService;
import com.hamburgueria.service.ProdutoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class PedidoServiceTests {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private ProdutoService produtoService;

    @Test
    void deveCalcularValorTotalDoPedido() {
        ProdutoResponseDTO produto = cadastrarProduto("X-Burger", "25.90");

        PedidoResponseDTO pedido = pedidoService.cadastrarPedido(
                new PedidoRequestDTO("João", List.of(
                        new ItemPedidoRequestDTO(produto.id(), 2)
                ))
        );

        assertEquals(new BigDecimal("51.80"), pedido.valorTotal());
        assertEquals(new BigDecimal("51.80"), pedido.itens().get(0).precoSubtotal());
        assertEquals(StatusPedido.RECEBIDO, pedido.status());
    }

    @Test
    void deveBloquearTransicaoInvalida() {
        PedidoResponseDTO pedido = cadastrarPedido();

        assertThrows(TransicaoStatusInvalidaException.class,
                () -> pedidoService.atualizarStatusPedido(pedido.id(), StatusPedido.ENTREGUE));
    }

    @Test
    void devePermitirSequenciaValidaDeStatus() {
        PedidoResponseDTO pedido = cadastrarPedido();

        assertEquals(StatusPedido.EM_PREPARO,
                pedidoService.atualizarStatusPedido(pedido.id(), StatusPedido.EM_PREPARO).status());

        assertEquals(StatusPedido.PRONTO,
                pedidoService.atualizarStatusPedido(pedido.id(), StatusPedido.PRONTO).status());

        assertEquals(StatusPedido.ENTREGUE,
                pedidoService.atualizarStatusPedido(pedido.id(), StatusPedido.ENTREGUE).status());
    }

    @Test
    void deveBloquearPedidoComProdutoIndisponivel() {
        ProdutoResponseDTO produto = cadastrarProduto("X-Burger", "25.90");
        produtoService.atualizarDisponibilidade(produto.id(), false);

        assertThrows(ProdutoIndisponivelException.class,
                () -> pedidoService.cadastrarPedido(
                        new PedidoRequestDTO("João", List.of(
                                new ItemPedidoRequestDTO(produto.id(), 1)
                        ))
                ));
    }

    @Test
    void deveReutilizarCategoriaComMesmoNome() {
        ProdutoResponseDTO primeiro = cadastrarProduto("X-Burger", "25.90");
        ProdutoResponseDTO segundo = cadastrarProduto("X-Salada", "22.90");

        assertEquals(primeiro.categoria().id(), segundo.categoria().id());
    }

    private PedidoResponseDTO cadastrarPedido() {
        ProdutoResponseDTO produto = cadastrarProduto("X-Burger", "25.90");

        return pedidoService.cadastrarPedido(
                new PedidoRequestDTO("João", List.of(
                        new ItemPedidoRequestDTO(produto.id(), 1)
                ))
        );
    }

    private ProdutoResponseDTO cadastrarProduto(String nome, String preco) {
        return produtoService.cadastrarProduto(
                new ProdutoRequestDTO(
                        nome,
                        new CategoriaRequestDTO(null, "Hamburguer", null),
                        new BigDecimal(preco)
                )
        );
    }
}
