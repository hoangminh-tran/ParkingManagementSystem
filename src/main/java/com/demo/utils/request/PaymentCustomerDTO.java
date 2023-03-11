package com.demo.utils.request;

import lombok.Data;

@Data
public class PaymentCustomerDTO {
    private Long Id_Booking;
    private String id_Building;
    private String Type_Of_Payment;
}
