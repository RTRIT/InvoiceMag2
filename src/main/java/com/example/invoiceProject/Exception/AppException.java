package com.example.invoiceProject.Exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

//
@Getter
@AllArgsConstructor
public class AppException extends RuntimeException{
    public AppException(String message){
        super(message);
    }
    public AppException(String message, Throwable cause) {
        super(message, cause);
    }
}
