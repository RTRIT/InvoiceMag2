package com.example.invoiceProject.DTO.requests;

import com.example.invoiceProject.Model.Privilege;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleRequest {

    long id;
    String roleName;

    //    String description;
    Set<Long> privileges;
}