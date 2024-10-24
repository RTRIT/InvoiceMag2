package com.example.invoiceProject.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class InvalidToken {
    @Id
    String id;

    Date expiryTime;
}
