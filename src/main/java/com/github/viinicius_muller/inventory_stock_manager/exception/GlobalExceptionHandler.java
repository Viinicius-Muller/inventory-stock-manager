package com.github.viinicius_muller.inventory_stock_manager.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleTypeMismatch(MethodArgumentTypeMismatchException err) {
        String msg = "O parâmetro '"+err.getName()+"' recebeu um valor inválido: "+err.getValue()+".";

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", "Tipo de Parâmetro Inválido");
        body.put("message: ", msg);

        return new ResponseEntity<>(body,HttpStatus.BAD_REQUEST);
    }

    //Objetos não encotrados pelo ‘id’
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleNoEntity(EntityNotFoundException err) {
        String msg = "Objeto não encontrado na base de dados.";

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", "Id Inválido");
        body.put("message: ", msg);

        return  new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    //Objeto com mesmo valor único em certa coluna
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleSameValues(DataIntegrityViolationException err) {
        Throwable rootCause = err.getMostSpecificCause();
        String causeMsg = rootCause.getMessage();

        String[] partes = causeMsg.split("'");

        String value = partes[1];
        String field = partes[3];

        String msg = "O valor '"+value+"' já existe no campo '"+field+"'.";

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", "Conflito de Dados");
        body.put("message: ", msg);

        return  new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ActiveObjectException.class)
    public ResponseEntity<Object> handleActiveObject(ActiveObjectException err) {
        String msg = "Erro ao ativar objeto: "+err.getMessage()+".";

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", "Conflito de Dados");
        body.put("message: ", msg);

        return  new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}
