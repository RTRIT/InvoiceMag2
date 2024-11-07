package com.example.invoiceProject.DTO.response;

import com.example.invoiceProject.Model.Address;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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
