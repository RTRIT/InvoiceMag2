package com.example.invoiceProject.Handler;

import com.example.invoiceProject.Exception.ApplicationException;
import com.example.invoiceProject.Exception.ResourceNotFoundException;
import com.example.invoiceProject.Model.Errors.ErrorDetails;
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
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<?> handleGlobalException(Exception ex, WebRequest request) {
//        ErrorDetails errorDetails = new ErrorDetails(ex.getMessage(), request.getDescription(false));
//        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
//    }

//    @ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "User not found!!!")
//    public UserNotFoundException(){
//
//    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorDetails errorDetails(Exception ex, WebRequest request){
        return new ErrorDetails("User Not Found", "Testing time");
    }
//    public ResponseEntity<?> handleCustomException(ApplicationException ex, WebRequest request){
//        ErrorDetails errorDetails = new ErrorDetails(ex.getMessage(), request.getDescription(false));
//        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
//    }

//    @ExceptionHandler(IndexOutOfBoundsException.class)
//    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
//    public ErrorDetails errorDetails(Exception ex, WebRequest request){
//        return new ErrorDetails("The object doesn't exist", "Testing time");
//    }
//    @ExceptionHandler(SQLException.class)
//    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
//    public ErrorDetails errorSqlDetails(Exception ex, WebRequest request){
//        return new ErrorDetails("The SQL problems", "Testing time");
//    }


}
