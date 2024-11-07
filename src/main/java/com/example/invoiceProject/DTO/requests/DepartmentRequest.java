package com.example.invoiceProject.DTO.requests;

import com.example.invoiceProject.Model.Address;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DepartmentRequest {
    String nameDepartment;
    String taxId;
    Address address;
    String phoneNumber;
    String email;
    String bank;
    String bankAccount;
}
