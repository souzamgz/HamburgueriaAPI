package com.hamburgueria.service;

import com.hamburgueria.dto.CategoriaRequestDTO;
import com.hamburgueria.dto.CategoriaResponseDTO;
import com.hamburgueria.entity.Categoria;
import com.hamburgueria.exception.CategoriaEmUsoException;
import com.hamburgueria.exception.CategoriaNaoEncontrada;
import com.hamburgueria.exception.ParametroObrigatorioException;
import com.hamburgueria.repository.CategoriaRepository;
import com.hamburgueria.repository.ProdutoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final ProdutoRepository produtoRepository;

    @Transactional
    public CategoriaResponseDTO cadastrarCategoria(CategoriaRequestDTO dados) {
        Categoria categoria = buscarOuCriarCategoria(dados);

        return converterParaDTO(categoria);
    }

    @Transactional
    public Categoria buscarOuCriarCategoria(CategoriaRequestDTO dados) {
        if (dados.id() != null && dados.id() > 0) {
            return buscarCategoriaId(dados.id());
        }

        if (dados.nome() == null || dados.nome().isBlank()) {
            throw new ParametroObrigatorioException("nome");
        }

        return categoriaRepository.findByNomeIgnoreCase(dados.nome())
                .orElseGet(() -> categoriaRepository.saveAndFlush(
                        new Categoria(dados.nome(), descricaoOuPadrao(dados.descricao()))
                ));
    }

    public List<CategoriaResponseDTO> listarCategorias() {
        return categoriaRepository.findAll()
                .stream()
                .map(this::converterParaDTO)
                .toList();
    }

    public CategoriaResponseDTO buscarCategoriaPorId(Long id) {
        Categoria categoria = buscarCategoriaId(id);

        return converterParaDTO(categoria);
    }

    @Transactional
    public String excluirCategoria(Long id) {
        Categoria categoria = buscarCategoriaId(id);

        if (produtoRepository.existsByCategoriaId(id)) {
            throw new CategoriaEmUsoException(id);
        }

        categoriaRepository.delete(categoria);

        return "Categoria id (" + id + ") excluída com sucesso";
    }

    @Transactional
    public CategoriaResponseDTO atualizarCategoriaPorId(Long id, CategoriaRequestDTO categoria) {
        Categoria categoriaEntity = buscarCategoriaId(id);

        if (categoria.nome() != null) {
            categoriaEntity.setNome(categoria.nome());
        }

        if (categoria.descricao() != null) {
            categoriaEntity.setDescricao(descricaoOuPadrao(categoria.descricao()));
        }

        Categoria categoriaAtualizada = categoriaRepository.saveAndFlush(categoriaEntity);

        return converterParaDTO(categoriaAtualizada);
    }

    private Categoria buscarCategoriaId(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new CategoriaNaoEncontrada(id));
    }

    private CategoriaResponseDTO converterParaDTO(Categoria categoria) {
        return new CategoriaResponseDTO(
                categoria.getId(),
                categoria.getNome(),
                categoria.getDescricao());
    }

    private static String descricaoOuPadrao(String descricao) {
        return descricao == null || descricao.isBlank() ? "Nenhuma Descrição" : descricao;
    }
}
