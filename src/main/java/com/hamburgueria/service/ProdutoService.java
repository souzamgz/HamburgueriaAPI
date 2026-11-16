package com.hamburgueria.service;

import com.hamburgueria.entity.Produto;
import com.hamburgueria.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {
    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    public Produto cadastrarProduto(Produto produto) {
        return produtoRepository.save(produto);
    }

    public List<Produto> listarProdutos() {
        return produtoRepository.findAll();
    }

    public Produto buscarProdutoPorId(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> {
                    throw new RuntimeException("Produto não encontrado com o ID: " + id);
                });
    }

    public void excluirProduto(Long id) {
        Produto produto = buscarProdutoPorId(id);
        produtoRepository.delete(produto);
    }
}
