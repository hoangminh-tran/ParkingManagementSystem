package com.demo.utils.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor

public class CustomerBookingHistory {
    private String id_C_invoice;

    private Date startDate;

    private Date endDate;

    private String startTime;

    private String endTime;
    private double Money;

    private String Building;

    private String id_C_slot;

    private String typeOfVehicle;

    private boolean status_invoice;
}
