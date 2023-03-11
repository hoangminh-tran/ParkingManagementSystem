package com.demo.utils.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentCustomerReponseDTO {
    private Long Id_Booking;

    private String id_C_Slot;

    private Date startDate;

    private Date endDate;

    private String startTime;

    private String endTime;

    private String Id_Payment;

    private String Type_Of_Payment; // Type of Payment

    private double total_of_money;

    private String Id_C_Invoice;

    private boolean Status_Invoice;
}
