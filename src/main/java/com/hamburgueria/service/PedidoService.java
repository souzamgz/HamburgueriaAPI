package com.hamburgueria.service;

import com.hamburgueria.dto.ItemPedidoRequestDTO;
import com.hamburgueria.dto.ItemPedidoResponseDTO;
import com.hamburgueria.dto.PedidoRequestDTO;
import com.hamburgueria.dto.PedidoResponseDTO;
import com.hamburgueria.entity.ItemPedido;
import com.hamburgueria.entity.Pedido;
import com.hamburgueria.entity.Produto;
import com.hamburgueria.entity.StatusPedido;
import com.hamburgueria.exception.PedidoNaoEncontradoException;
import com.hamburgueria.exception.ProdutoIndisponivelException;
import com.hamburgueria.exception.ProdutoNaoEncontradoException;
import com.hamburgueria.exception.TransicaoStatusInvalidaException;
import com.hamburgueria.repository.PedidoRepository;
import com.hamburgueria.repository.ProdutoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ProdutoRepository produtoRepository;

    @Transactional
    public PedidoResponseDTO cadastrarPedido(PedidoRequestDTO dados) {
        Pedido pedido = new Pedido();
        pedido.setCliente(dados.cliente());
        pedido.setStatus(StatusPedido.RECEBIDO);

        BigDecimal valorTotal = BigDecimal.ZERO;

        for (ItemPedidoRequestDTO itemDTO : dados.itens()) {
            Produto produto = produtoRepository.findById(itemDTO.produtoId())
                    .orElseThrow(() -> new ProdutoNaoEncontradoException(itemDTO.produtoId()));

            if (!produto.isDisponivel()) {
                throw new ProdutoIndisponivelException(produto.getId());
            }

            BigDecimal precoSubtotal = produto.getPrecoUnitario()
                    .multiply(BigDecimal.valueOf(itemDTO.quantidade()));

            ItemPedido item = new ItemPedido();
            item.setProduto(produto);
            item.setQuantidade(itemDTO.quantidade());
            item.setPrecoSubtotal(precoSubtotal);

            pedido.adicionarItem(item);
            valorTotal = valorTotal.add(precoSubtotal);
        }

        pedido.setValorTotal(valorTotal);

        Pedido pedidoSalvo = pedidoRepository.save(pedido);

        return converterParaDTO(pedidoSalvo);
    }

    @Transactional(readOnly = true)
    public List<PedidoResponseDTO> listarPedidos() {
        return pedidoRepository.findAll()
                .stream()
                .map(this::converterParaDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public PedidoResponseDTO buscarPedidoPorId(Long id) {
        Pedido pedido = buscarPedidoId(id);

        return converterParaDTO(pedido);
    }

    @Transactional
    public PedidoResponseDTO atualizarStatusPedido(Long id, StatusPedido novoStatus) {
        Pedido pedido = buscarPedidoId(id);

        if (!pedido.getStatus().podeTransicionarPara(novoStatus)) {
            throw new TransicaoStatusInvalidaException(pedido.getStatus(), novoStatus);
        }

        pedido.setStatus(novoStatus);

        Pedido pedidoAtualizado = pedidoRepository.saveAndFlush(pedido);

        return converterParaDTO(pedidoAtualizado);
    }

    private Pedido buscarPedidoId(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new PedidoNaoEncontradoException(id));
    }

    private PedidoResponseDTO converterParaDTO(Pedido pedido) {
        List<ItemPedidoResponseDTO> itens = pedido.getItens()
                .stream()
                .map(this::converterItemParaDTO)
                .toList();

        return new PedidoResponseDTO(
                pedido.getId(),
                pedido.getCliente(),
                itens,
                pedido.getValorTotal(),
                pedido.getStatus());
    }

    private ItemPedidoResponseDTO converterItemParaDTO(ItemPedido item) {
        return new ItemPedidoResponseDTO(
                item.getId(),
                item.getProduto().getId(),
                item.getProduto().getNome(),
                item.getQuantidade(),
                item.getPrecoSubtotal());
    }
}
