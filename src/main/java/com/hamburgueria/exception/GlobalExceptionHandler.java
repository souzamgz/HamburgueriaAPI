package com.hamburgueria.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProdutoNaoEncontradoException.class)
    public ResponseEntity<String> tratarProdutoNaoEncontrado(
            ProdutoNaoEncontradoException exception) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }

    @ExceptionHandler(CategoriaNaoEncontrada.class)
    public ResponseEntity<String> tratarCategoriaNaoEncontrada(
            CategoriaNaoEncontrada exception) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }

    @ExceptionHandler(PedidoNaoEncontradoException.class)
    public ResponseEntity<String> tratarPedidoNaoEncontrado(
            PedidoNaoEncontradoException exception) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }

    @ExceptionHandler(ParametroObrigatorioException.class)
    public ResponseEntity<String> tratarParametroObrigatorio(
            ParametroObrigatorioException exception) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }

    @ExceptionHandler(TransicaoStatusInvalidaException.class)
    public ResponseEntity<String> tratarTransicaoStatusInvalida(
            TransicaoStatusInvalidaException exception) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }

    @ExceptionHandler(ProdutoIndisponivelException.class)
    public ResponseEntity<String> tratarProdutoIndisponivel(
            ProdutoIndisponivelException exception) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }

    @ExceptionHandler(CategoriaEmUsoException.class)
    public ResponseEntity<String> tratarCategoriaEmUso(
            CategoriaEmUsoException exception) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> tratarValidacao(
            MethodArgumentNotValidException exception) {

        String mensagem = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(" "));

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(mensagem);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> tratarCorpoInvalido(
            HttpMessageNotReadableException exception) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Corpo da requisição inválido.");
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> tratarIntegridade(
            DataIntegrityViolationException exception) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body("Operação não permitida: registro vinculado a outro.");
    }
}
