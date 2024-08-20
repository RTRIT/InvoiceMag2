package com.example.invoiceProject.Handler;

import com.example.invoiceProject.Exception.ApplicationException;
import com.example.invoiceProject.Exception.CustomException;
import com.example.invoiceProject.Exception.ResourceNotFoundException;
import com.example.invoiceProject.Model.Errors.ErrorDetails;
import jakarta.persistence.NonUniqueResultException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.sql.SQLException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Handle global exceptions

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(Exception e){
        return ResponseEntity.status(400).body(e.getMessage());
    }

    @ExceptionHandler(NonUniqueResultException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorDetails nonUniqueResultException(Exception ex, WebRequest request){
        return new ErrorDetails("There are more than one this role in DB", "Testing time");
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<String> handleCustomException(Exception e){
        return ResponseEntity.status(409).body(e.getMessage());
    }




}
