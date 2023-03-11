package com.demo.utils.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CancelPaymentDTO {
    private Long Id_Booking;

    private String Id_Payment;

    private String Id_C_Invoice;
}
