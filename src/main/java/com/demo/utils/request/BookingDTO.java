package com.demo.utils.request;

import lombok.Data;

import java.util.Date;

@Data
public class BookingDTO {
    private Date startDate;

    private Date endDate;

    private String startTime;

    private String EndTime;

    private String Id_C_Slot;

    private String Id_Customer;
}
