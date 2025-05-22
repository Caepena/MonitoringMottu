package br.com.fiap.MonitoringMottu.exception;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ValidationHandler {

    record ValidationErrorMessage(String field, String message) {
        public ValidationErrorMessage(FieldError fieldError) {
            this(fieldError.getField(), fieldError.getDefaultMessage());
        }
    }

    @ExceptionHandler(exception = MethodArgumentNotValidException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public List<ValidationErrorMessage> handler(MethodArgumentNotValidException exception) {
        return exception.getFieldErrors()
                .stream()
                .map(ValidationErrorMessage::new)
                .toList();
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleFormat(HttpMessageNotReadableException ex) {
        if (ex.getMessage().contains("LocalDate")) {
            return "Formato de data inválido. Use o formato: yyyy/MM/dd";
        }

        return "Erro ao processar a requisição. Verifique o corpo da requisição.";
    }
}
