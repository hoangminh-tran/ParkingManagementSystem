package com.demo.utils.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CancelBookingDTO {
    private Long id_booking;

    private String id_Customer;

    private String id_C_slot;

    private String id_Building;
}
