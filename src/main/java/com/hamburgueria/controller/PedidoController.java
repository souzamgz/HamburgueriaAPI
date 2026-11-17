package com.hamburgueria.controller;

import com.hamburgueria.dto.PedidoRequestDTO;
import com.hamburgueria.dto.PedidoResponseDTO;
import com.hamburgueria.dto.StatusPedidoRequestDTO;
import com.hamburgueria.service.PedidoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<PedidoResponseDTO> cadastrar(
            @Valid @RequestBody PedidoRequestDTO dados) {

        PedidoResponseDTO pedidoSalvo =
                pedidoService.cadastrarPedido(dados);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(pedidoSalvo);
    }

    @GetMapping
    public ResponseEntity<List<PedidoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(
                pedidoService.listarPedidos()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> buscarPorId(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                pedidoService.buscarPedidoPorId(id)
        );
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<PedidoResponseDTO> atualizarStatus(
            @PathVariable Long id,
            @Valid @RequestBody StatusPedidoRequestDTO dados) {

        return ResponseEntity.ok(
                pedidoService.atualizarStatusPedido(id, dados.status())
        );
    }
}
