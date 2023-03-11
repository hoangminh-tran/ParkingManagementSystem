package com.demo.utils.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseResidentInfoSlot {
    private String id_Resident;

    private Date DateOfPayment;

    private String id_Payment;

    private String id_Invoice;

    private String typeOfPayment;

    private String typeOfVehicle;
}
