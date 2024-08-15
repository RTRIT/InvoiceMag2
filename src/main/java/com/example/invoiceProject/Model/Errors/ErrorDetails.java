package com.example.invoiceProject.Model.Errors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


//Define custom exception object
@Getter
@Setter
@AllArgsConstructor
public class ErrorDetails extends RuntimeException {
    String message;
    String description;
}
