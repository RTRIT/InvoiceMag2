//package com.example.invoiceProject.Util;
//
//import com.example.invoiceProject.Model.Invoice;
//import com.example.invoiceProject.enums.InvoiceKind;
//import org.springframework.data.jpa.domain.Specification;
//
//import java.time.LocalDate;
//
//public class InvoiceSpecification {
//    public static Specification<Invoice> betweenDates(LocalDate startDate, LocalDate endDate){
//        return (root, query, builder) -> builder.between(root.get("invoiceDate"), startDate, endDate);
//    }
//
//    public static Specification<Invoice> sequenceNo(Long sequenceNo){
//        return (root, query, builder) -> builder.equal(root.get("sequenceNo"), sequenceNo);
//    }
//    public static Specification<Invoice> hasStatus(String status){
//        return (root, query, builder) -> builder.equal(root.get("status"), status);
//    }
//    public static Specification<Invoice> hasPaymentType(String paymentType){
//        return (root, query, builder) -> builder.equal(root.get("paymentType"), paymentType);
//    }
//    public static Specification<Invoice> hasKind(InvoiceKind kind){
//        return (root, query, builder) -> builder.equal(root.get("kind"), kind);
//
//    }
//
//}
