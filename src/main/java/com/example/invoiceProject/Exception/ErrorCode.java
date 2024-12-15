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
    ROLE_IS_NOT_EXISTED(1012, "This role name is taken", HttpStatus.BAD_REQUEST),
    EMPTY_PRIVILEGE(1021, "Privilege is empty", HttpStatus.BAD_REQUEST),
    PRIVILEGE_IS_NOT_EXISTED(1022, "Privilege name is not existed", HttpStatus.BAD_REQUEST),
    PRIVILEGE_IS_EXISTED_ALREADY(1023, "Privilege is existed already", HttpStatus.BAD_REQUEST),

    VENDOR_EXISTED(1013, "Vendor existed", HttpStatus.BAD_REQUEST),
    VENDOR_NOT_FOUND(1014, "Vendor not found", HttpStatus.BAD_REQUEST),
    INVALID_SEARCH_CRITERIA(1015, "Invalid search criteria", HttpStatus.BAD_REQUEST),
    EMAIL_EXISTED(1016, "Email existed", HttpStatus.BAD_REQUEST),
    PHONENUMBER_EXISTED(1017, "Phonenumber existed", HttpStatus.BAD_REQUEST),


    TOKEN_EXPIRED(1020, "Token has expired", HttpStatus.BAD_REQUEST),
    TOKEN_UNSUPPORTED(1021, "Unsupported JWT token", HttpStatus.BAD_REQUEST),
    TOKEN_MALFORMED(1022, "Malformed JWT token", HttpStatus.BAD_REQUEST),
    TOKEN_INVALID_SIGNATURE(1023, "Invalid JWT signature", HttpStatus.BAD_REQUEST),
    TOKEN_ILLEGAL_ARGUMENT(1024, "JWT token is empty or null", HttpStatus.BAD_REQUEST),

    DEPARTMENT_IS_NOT_EXISTED(1030,"Department is not existed", HttpStatus.BAD_REQUEST),
    DEPARTMENT_NAME_IS_EXISTED(1031,"Department is existed already", HttpStatus.BAD_REQUEST),
    DEPARTMENT_MAIL_IS_EXISTED(1032,"Department Mail is existed already", HttpStatus.BAD_REQUEST);

    ErrorCode(int code, String message, HttpStatusCode httpStatusCode){
        this.code = code;
        this.message = message;
        this.statusCode = httpStatusCode;
    }
    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;
}

