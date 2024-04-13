package com.crud.atenciones.advice;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
//Capturar errores
@RestControllerAdvice
public class AtencionMedicaExceptionHandler {
    //Metodo que retorna errores
    @ExceptionHandler(MethodArgumentNotValidException.class)//Cuando se produzca esta excepcion se dispara el método
    public Map<String, String> handleInvalidArguments(MethodArgumentNotValidException exception){
        Map<String, String> errors = new HashMap<>();

        exception.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });
        return errors;
    }
}
