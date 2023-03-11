package com.demo.utils.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingCustomerResponseDTO {
    private Long id_Booking;

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

    private Integer total_Of_Money;


}
