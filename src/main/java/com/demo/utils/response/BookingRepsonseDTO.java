package com.demo.utils.response;

import lombok.Data;

import java.util.Date;

@Data
public class BookingRepsonseDTO {
    private Long Id_Booking;

    private Date startDate;

    private Date endDate;

    private String startTime;

    private String EndTime;

    private Customer_Slot_Response_DTO Id_C_Slot;

    private CustomerResponseDTO Id_Customer;
}
