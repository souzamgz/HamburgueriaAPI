package com.hamburgueria.exception;

public class CategoriaNaoEncontrada extends RuntimeException {

    public CategoriaNaoEncontrada(Long id) {
        super("Categoria não encontrada com o ID: " + id);
    }
}
