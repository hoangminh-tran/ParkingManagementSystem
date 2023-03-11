package com.demo.utils.response;

import com.demo.entity.Resident;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceResidentResponse {
    private String Id_R_Invoice;

    private String Id_Payment;

    private String TypeOfPayment; // Type of Payment

    private boolean Status;

    private double Total_Of_Money;

    private Date Time;

    private String Id_Resident;
}
