package com.hamburgueria.controller;

import com.hamburgueria.dto.CategoriaRequestDTO;
import com.hamburgueria.dto.CategoriaResponseDTO;
import com.hamburgueria.service.CategoriaService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    @PostMapping
    public ResponseEntity<CategoriaResponseDTO> cadastrar(
            @RequestBody CategoriaRequestDTO dados) {
        CategoriaResponseDTO newCategoria = categoriaService.cadastrarCategoria(dados);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(newCategoria);
    }

    @GetMapping
    public ResponseEntity<List<CategoriaResponseDTO>> listarTodos() {
        return ResponseEntity.ok(
                categoriaService.listarCategorias()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> buscarId(
            @PathVariable Long id) {
        return ResponseEntity.ok(
                categoriaService.buscarCategoriaPorId(id)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluir(
            @PathVariable Long id) {
        return ResponseEntity.ok(
                categoriaService.excluirCategoria(id)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> atualizarCategoria(
            @PathVariable Long id,
            @RequestBody CategoriaRequestDTO categoria) {
        return ResponseEntity.ok(
                categoriaService.atualizarCategoriaPorId(id, categoria)
        );
    }
}
