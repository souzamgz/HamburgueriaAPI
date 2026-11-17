package com.hamburgueria.controller;

import com.hamburgueria.dto.CategoriaRequestDTO;
import com.hamburgueria.dto.CategoriaResponseDTO;
import com.hamburgueria.service.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Categorias", description = "Cadastro, consulta, atualização e exclusão de categorias")
@AllArgsConstructor
@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    @Operation(summary = "Cadastrar categoria", description = "Cadastra uma nova categoria ou retorna a existente com o mesmo nome.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Categoria cadastrada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Nome da categoria não informado")
    })
    @PostMapping
    public ResponseEntity<CategoriaResponseDTO> cadastrar(
            @RequestBody CategoriaRequestDTO dados) {
        CategoriaResponseDTO newCategoria = categoriaService.cadastrarCategoria(dados);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(newCategoria);
    }

    @Operation(summary = "Listar categorias")
    @ApiResponse(responseCode = "200", description = "Lista de categorias")
    @GetMapping
    public ResponseEntity<List<CategoriaResponseDTO>> listarTodos() {
        return ResponseEntity.ok(
                categoriaService.listarCategorias()
        );
    }

    @Operation(summary = "Buscar categoria por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Categoria encontrada"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> buscarId(
            @Parameter(description = "ID da categoria", example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(
                categoriaService.buscarCategoriaPorId(id)
        );
    }

    @Operation(summary = "Excluir categoria")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Categoria excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada"),
            @ApiResponse(responseCode = "409", description = "Categoria vinculada a produtos")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> excluir(
            @Parameter(description = "ID da categoria", example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(
                categoriaService.excluirCategoria(id)
        );
    }

    @Operation(summary = "Atualizar categoria", description = "Atualiza os dados informados da categoria.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Categoria atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponseDTO> atualizarCategoria(
            @Parameter(description = "ID da categoria", example = "1")
            @PathVariable Long id,
            @RequestBody CategoriaRequestDTO categoria) {
        return ResponseEntity.ok(
                categoriaService.atualizarCategoriaPorId(id, categoria)
        );
    }
}
