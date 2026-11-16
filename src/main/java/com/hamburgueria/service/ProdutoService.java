
package com.hamburgueria.service;

import com.hamburgueria.dto.ProdutoRequestDTO;
import com.hamburgueria.dto.ProdutoResponseDTO;
import com.hamburgueria.entity.Produto;
import com.hamburgueria.exception.ProdutoNaoEncontradoException;
import com.hamburgueria.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public ProdutoResponseDTO cadastrarProduto(ProdutoRequestDTO dados) {
        Produto produto = new Produto();

        produto.setNome(dados.nome());
        produto.setCategoria(dados.categoria());
        produto.setPrecoUnitario(dados.precoUnitario());

        Produto produtoSalvo = produtoRepository.save(produto);

        return converterParaDTO(produtoSalvo);
    }

    public List<ProdutoResponseDTO> listarProdutos() {
        return produtoRepository.findAll()
                .stream()
                .map(this::converterParaDTO)
                .toList();
    }

    public ProdutoResponseDTO buscarProdutoPorId(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ProdutoNaoEncontradoException(id));

        return converterParaDTO(produto);
    }

    public void excluirProduto(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ProdutoNaoEncontradoException(id));

        produtoRepository.delete(produto);
    }

    private ProdutoResponseDTO converterParaDTO(Produto produto) {
        return new ProdutoResponseDTO(
                produto.getId(),
                produto.getNome(),
                produto.getCategoria(),
                produto.getPrecoUnitario(),
                produto.isDisponivel());
    }
}
