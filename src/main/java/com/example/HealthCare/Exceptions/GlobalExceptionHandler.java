package com.example.HealthCare.Exceptions;




import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;

import java.util.*;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleValidation(MethodArgumentNotValidException ex){

        Map<String,String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {

            String fieldName = error.getField();

            String errorMessage = error.getDefaultMessage();

            errors.put(fieldName,errorMessage);

        });

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errors);
    }
}