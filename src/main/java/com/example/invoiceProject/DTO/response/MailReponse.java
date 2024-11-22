package com.example.invoiceProject.DTO.response;


import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MailReponse {
    boolean success;
    String messagee;
}
