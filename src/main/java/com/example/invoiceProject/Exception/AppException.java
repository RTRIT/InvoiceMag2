package com.example.invoiceProject.Exception;

import lombok.AllArgsConstructor;
import lombok.Getter;


public class AppException extends RuntimeException{
    private ErrorCode errorCode;
    public AppException(ErrorCode errorCode){
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode(){
        return errorCode;
    }
    public void setErrorCode(){
        this.errorCode = errorCode;
    }

}
