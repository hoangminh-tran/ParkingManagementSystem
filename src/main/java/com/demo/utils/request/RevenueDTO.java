package com.demo.utils.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RevenueDTO {
    private String id_Building;

    private double income;

    private String id_manager;

    private int countCustomer;

    private int countResident;
}
