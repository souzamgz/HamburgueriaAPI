package com.hamburgueria.exception;

public class CategoriaEmUsoException extends RuntimeException {

    public CategoriaEmUsoException(Long id) {
        super("Categoria não pode ser excluída pois está vinculada a produtos. ID: " + id);
    }
}
