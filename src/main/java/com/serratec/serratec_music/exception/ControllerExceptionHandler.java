package com.serratec.serratec_music.exception;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {

    // Erros de validação (ex: campos obrigatórios, e-mail inválido, etc.)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroResposta> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<String> erros = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(field -> field.getField() + ": " + field.getDefaultMessage())
                .collect(Collectors.toList());

        ErroResposta erro = new ErroResposta(
                HttpStatus.BAD_REQUEST.value(),
                "Erro de validação nos campos enviados.",
                LocalDateTime.now(),
                erros
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    // Qualquer outro erro genérico (evita que a aplicação quebre)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroResposta> handleGenericException(Exception ex) {
        ErroResposta erro = new ErroResposta(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Erro interno no servidor.",
                LocalDateTime.now(),
                List.of(ex.getMessage())
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erro);
    }
}
