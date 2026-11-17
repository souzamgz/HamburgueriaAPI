package com.hamburgueria.exception;

public class ParametroObrigatorioException extends RuntimeException {

    public ParametroObrigatorioException(String nomeParametro) {
        super("Parâmetro obrigatório não informado: " + nomeParametro);
    }
}
