package com.demo.utils.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResidentResponseDTO {
    private String idUser;

    private String Id_R_Invoice;

    private String Id_Payment;

    private boolean Status;

    private Date dateOfPayment;

    private double Total_Of_Money;

    private String TypeOfPayment; // Type of Payment
}
