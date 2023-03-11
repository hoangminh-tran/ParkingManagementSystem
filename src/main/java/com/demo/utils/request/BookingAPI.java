package com.demo.utils.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingAPI {
    private Long id_booking;

    private Date startDate;

    private Date endDate;

    private String startTime;

    private String endTime;

    private String id_customer;

    private String id_C_slot;
}
