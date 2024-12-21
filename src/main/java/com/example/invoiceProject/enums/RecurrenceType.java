package com.example.invoiceProject.enums;

import lombok.Getter;

@Getter
public enum RecurrenceType {

    DAILY("daily", 1),
    WEEKLY("weekly", 2),
    MONTHLY("monthly", 3),
    ANNUALLY("annually", 4);



    RecurrenceType(String type, Integer value){
        this.type=type;
        this.value=value;
    }

    private final String type;
    private final Integer value;

    //Override to the return value of getter
//    @Override
//    public String toString() {
//        return type; // Return the string value instead of the enum constant name
//    }



}
