package com.example.invoiceProject.Exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

//
@Getter
@AllArgsConstructor
public class ApplicationException extends RuntimeException{
    public ApplicationException(String message){
        super(message);
    }
    public ApplicationException(String message, Throwable cause) {
        super(message, cause);
    }
}
