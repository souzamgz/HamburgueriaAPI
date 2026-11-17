package com.hamburgueria.controller;

import com.hamburgueria.dto.ProdutoDisponibilidadeRequestDTO;
import com.hamburgueria.dto.ProdutoRequestDTO;
import com.hamburgueria.dto.ProdutoResponseDTO;
import com.hamburgueria.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Produtos", description = "Cadastro, consulta, atualização e exclusão de produtos do cardápio")
@AllArgsConstructor
@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    @Operation(summary = "Cadastrar produto", description = "Cadastra um novo produto no cardápio.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Produto cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados do produto inválidos")
    })
    @PostMapping
    public ResponseEntity<ProdutoResponseDTO> cadastrar(
            @Valid @RequestBody ProdutoRequestDTO dados) {

        ProdutoResponseDTO produtoSalvo =
                produtoService.cadastrarProduto(dados);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(produtoSalvo);
    }

    @Operation(summary = "Listar produtos disponíveis", description = "Retorna somente os produtos com disponibilidade ativa.")
    @ApiResponse(responseCode = "200", description = "Lista de produtos disponíveis")
    @GetMapping
    public ResponseEntity<List<ProdutoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(
                produtoService.listarProdutos()
        );
    }

    @Operation(summary = "Buscar produto por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Produto encontrado"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> buscarPorId(
            @Parameter(description = "ID do produto", example = "1")
            @PathVariable Long id) {

        return ResponseEntity.ok(
                produtoService.buscarProdutoPorId(id)
        );
    }

    @Operation(summary = "Excluir produto")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Produto excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado"),
            @ApiResponse(responseCode = "409", description = "Produto vinculado a um pedido")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(
            @Parameter(description = "ID do produto", example = "1")
            @PathVariable Long id) {

        produtoService.excluirProduto(id);

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Atualizar produto", description = "Atualiza os dados informados do produto.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados do produto inválidos"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> atualizarProduto(
            @Parameter(description = "ID do produto", example = "1")
            @PathVariable Long id,
            @RequestBody ProdutoRequestDTO produto) {

        return ResponseEntity.ok(
                produtoService.atualizarProdutoPorId(id, produto)
        );
    }

    @Operation(summary = "Atualizar disponibilidade", description = "Ativa ou desativa um produto no cardápio.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Disponibilidade atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Disponibilidade não informada"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    @PatchMapping("/{id}/disponibilidade")
    public ResponseEntity<ProdutoResponseDTO> atualizarDisponibilidade(
            @Parameter(description = "ID do produto", example = "1")
            @PathVariable Long id,
            @Valid @RequestBody ProdutoDisponibilidadeRequestDTO dados) {

        return ResponseEntity.ok(
                produtoService.atualizarDisponibilidade(id, dados.disponivel())
        );
    }
}
