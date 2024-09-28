package com.example.invoiceProject.Exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    USER_EXISTED(1004,"User existed", HttpStatus.BAD_REQUEST),
    USER_IS_NOT_EXISTED(1005, "User is not existed", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(1006, "Unauthenticated", HttpStatus.BAD_REQUEST),
    ROLE_EXISTED(1011, "This role name is taken", HttpStatus.BAD_REQUEST),
    EMPTY_PRIVILEGE(1012, "Privilege is empty", HttpStatus.BAD_REQUEST);

    ErrorCode(int code, String message, HttpStatusCode httpStatusCode){
        this.code = code;
        this.message = message;
        this.statusCode = httpStatusCode;
    }
    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;


}

