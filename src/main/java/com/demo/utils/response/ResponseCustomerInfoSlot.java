package com.demo.utils.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseCustomerInfoSlot {
    private String id_Customer;

    private Date startDate;

    private Date endDate;

    private String startTime;

    private String endTime;

    private String id_Payment;

    private String id_Invoice;

    private String typeOfPayment;

    private String typeOfVehicle;

}
