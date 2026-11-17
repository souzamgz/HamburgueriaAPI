package com.hamburgueria.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Status", description = "Verificação de disponibilidade da API")
@RestController
@RequestMapping("/api")
public class HomeController {

    @Operation(summary = "Verificar status da API", description = "Retorna uma mensagem confirmando que a API está em execução.")
    @ApiResponse(responseCode = "200", description = "API em execução")
    @GetMapping("/status")
    public String VerificaStatus() {
        return "Hamburgueria API is running!";
    }
}
