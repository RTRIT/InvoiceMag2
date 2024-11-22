package com.example.invoiceProject.DTO.response;

import com.example.invoiceProject.Model.Address;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DepartmentResponse {
    String nameDepartment;
    String taxId;
    Address address;
    String phoneNumber;
    String email;
    String bank;
    String bankAccount;
}
