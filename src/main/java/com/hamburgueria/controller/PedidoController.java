package com.hamburgueria.controller;

import com.hamburgueria.dto.PedidoRequestDTO;
import com.hamburgueria.dto.PedidoResponseDTO;
import com.hamburgueria.dto.StatusPedidoRequestDTO;
import com.hamburgueria.service.PedidoService;
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

@Tag(name = "Pedidos", description = "Criação, consulta e atualização do status dos pedidos")
@AllArgsConstructor
@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    @Operation(summary = "Criar pedido", description = "Cria um pedido e calcula os subtotais e o valor total no servidor.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Pedido criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados do pedido inválidos ou produto indisponível"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    @PostMapping
    public ResponseEntity<PedidoResponseDTO> cadastrar(
            @Valid @RequestBody PedidoRequestDTO dados) {

        PedidoResponseDTO pedidoSalvo =
                pedidoService.cadastrarPedido(dados);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(pedidoSalvo);
    }

    @Operation(summary = "Listar pedidos")
    @ApiResponse(responseCode = "200", description = "Lista de pedidos")
    @GetMapping
    public ResponseEntity<List<PedidoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(
                pedidoService.listarPedidos()
        );
    }

    @Operation(summary = "Buscar pedido por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedido encontrado"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> buscarPorId(
            @Parameter(description = "ID do pedido", example = "1")
            @PathVariable Long id) {

        return ResponseEntity.ok(
                pedidoService.buscarPedidoPorId(id)
        );
    }

    @Operation(
            summary = "Atualizar status do pedido",
            description = "Avança o status seguindo a sequência RECEBIDO -> EM_PREPARO -> PRONTO -> ENTREGUE. Transições fora da sequência retornam 400."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Status atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Transição de status inválida"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    })
    @PatchMapping("/{id}/status")
    public ResponseEntity<PedidoResponseDTO> atualizarStatus(
            @Parameter(description = "ID do pedido", example = "1")
            @PathVariable Long id,
            @Valid @RequestBody StatusPedidoRequestDTO dados) {

        return ResponseEntity.ok(
                pedidoService.atualizarStatusPedido(id, dados.status())
        );
    }
}
