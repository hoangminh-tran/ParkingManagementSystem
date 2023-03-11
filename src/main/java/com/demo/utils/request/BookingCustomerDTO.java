package com.demo.utils.request;

import lombok.Data;

import java.util.Date;

@Data
public class BookingCustomerDTO {
    private String idUser; // i

    private String fullname;

    private String email;

    private String phone;

    private String id_Building;

    private String type_Of_Vehicle;

    private String id_C_Slot;

    private Date startDate;

    private Date endDate;

    private String startTime;

    private String endTime;
}
