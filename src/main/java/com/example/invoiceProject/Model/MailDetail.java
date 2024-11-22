package com.example.invoiceProject.Model;

import jakarta.persistence.Entity;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class MailDetail {

    private String recipient;
    private List<String> cc;
    private String msgBody;
    private String subject;
    private String attachment;

}
