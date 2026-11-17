package com.hamburgueria.controller;

import com.hamburgueria.dto.ProdutoDisponibilidadeRequestDTO;
import com.hamburgueria.dto.ProdutoRequestDTO;
import com.hamburgueria.dto.ProdutoResponseDTO;
import com.hamburgueria.service.ProdutoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    @PostMapping
    public ResponseEntity<ProdutoResponseDTO> cadastrar(
            @Valid @RequestBody ProdutoRequestDTO dados) {

        ProdutoResponseDTO produtoSalvo =
                produtoService.cadastrarProduto(dados);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(produtoSalvo);
    }

    @GetMapping
    public ResponseEntity<List<ProdutoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(
                produtoService.listarProdutos()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> buscarPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                produtoService.buscarProdutoPorId(id)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(
            @PathVariable Long id) {

        produtoService.excluirProduto(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> atualizarProduto(
            @PathVariable Long id,
            @RequestBody ProdutoRequestDTO produto) {

        return ResponseEntity.ok(
                produtoService.atualizarProdutoPorId(id, produto)
        );
    }

    @PatchMapping("/{id}/disponibilidade")
    public ResponseEntity<ProdutoResponseDTO> atualizarDisponibilidade(
            @PathVariable Long id,
            @Valid @RequestBody ProdutoDisponibilidadeRequestDTO dados) {

        return ResponseEntity.ok(
                produtoService.atualizarDisponibilidade(id, dados.disponivel())
        );
    }
}
