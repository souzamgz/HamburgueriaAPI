package com.hamburgueria.service;

import com.hamburgueria.dto.CategoriaResponseDTO;
import com.hamburgueria.dto.ProdutoRequestDTO;
import com.hamburgueria.dto.ProdutoResponseDTO;
import com.hamburgueria.entity.Categoria;
import com.hamburgueria.entity.Produto;
import com.hamburgueria.exception.ProdutoNaoEncontradoException;
import com.hamburgueria.repository.ProdutoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final CategoriaService categoriaService;

    @Transactional
    public ProdutoResponseDTO cadastrarProduto(ProdutoRequestDTO dados) {
        Produto produto = new Produto();

        produto.setNome(dados.nome());
        produto.setCategoria(categoriaService.buscarOuCriarCategoria(dados.categoria()));
        produto.setPrecoUnitario(dados.precoUnitario());

        Produto produtoSalvo = produtoRepository.save(produto);

        return converterParaDTO(produtoSalvo);
    }

    public List<ProdutoResponseDTO> listarProdutos() {
        return produtoRepository.findByDisponivelTrue()
                .stream()
                .map(this::converterParaDTO)
                .toList();
    }

    public ProdutoResponseDTO buscarProdutoPorId(Long id) {
        Produto produto = buscarProdutoId(id);

        return converterParaDTO(produto);
    }

    @Transactional
    public ProdutoResponseDTO atualizarProdutoPorId(Long id, ProdutoRequestDTO produto) {
        Produto produtoEntity = buscarProdutoId(id);

        if (produto.nome() != null) {
            produtoEntity.setNome(produto.nome());
        }

        if (produto.categoria() != null) {
            produtoEntity.setCategoria(categoriaService.buscarOuCriarCategoria(produto.categoria()));
        }

        if (produto.precoUnitario() != null) {
            produtoEntity.setPrecoUnitario(produto.precoUnitario());
        }

        Produto produtoAtualizado = produtoRepository.saveAndFlush(produtoEntity);

        return converterParaDTO(produtoAtualizado);
    }

    @Transactional
    public ProdutoResponseDTO atualizarDisponibilidade(Long id, Boolean disponivel) {
        Produto produto = buscarProdutoId(id);

        produto.setDisponivel(disponivel);

        Produto produtoAtualizado = produtoRepository.saveAndFlush(produto);

        return converterParaDTO(produtoAtualizado);
    }

    @Transactional
    public void excluirProduto(Long id) {
        Produto produto = buscarProdutoId(id);

        produtoRepository.delete(produto);
    }

    private Produto buscarProdutoId(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new ProdutoNaoEncontradoException(id));
    }

    private ProdutoResponseDTO converterParaDTO(Produto produto) {
        return new ProdutoResponseDTO(
                produto.getId(),
                produto.getNome(),
                converterCategoriaParaDTO(produto.getCategoria()),
                produto.getPrecoUnitario(),
                produto.isDisponivel());
    }

    private CategoriaResponseDTO converterCategoriaParaDTO(Categoria categoria) {
        return new CategoriaResponseDTO(
                categoria.getId(),
                categoria.getNome(),
                categoria.getDescricao());
    }
}
