package com.demo.utils.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResidentBookingHistory {
    private String id_R_invoice;
    private Date Time;
    private double Money;

    private String Building;

    private String id_R_slot;

    private String typeOfVehicle;

    private boolean status_invoice;
}
