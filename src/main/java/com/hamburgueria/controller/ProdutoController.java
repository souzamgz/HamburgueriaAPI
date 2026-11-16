
package com.hamburgueria.controller;

import com.hamburgueria.entity.Produto;
import com.hamburgueria.service.ProdutoService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @PostMapping
    public ResponseEntity<Produto> cadastrar(
            @RequestBody Produto produto) {
        Produto produtoSalvo = produtoService.cadastrarProduto(produto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(produtoSalvo);
    }

    @GetMapping
    public ResponseEntity<List<Produto>> listarTodos() {
        return ResponseEntity.ok(
                produtoService.listarProdutos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> buscarPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                produtoService.buscarProdutoPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(
            @PathVariable Long id) {

        produtoService.excluirProduto(id);

        return ResponseEntity.noContent().build();
    }
}
