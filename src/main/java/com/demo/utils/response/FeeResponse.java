package com.demo.utils.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeeResponse {
    private String id_invoice;
    private String id_user;
    private boolean status_fee;
    private Date current_date;
    private String current_time;
    private Date end_date;
    private String end_time;
    private int expired;
    private Double based_fee;
    private Double fined_fee;
    boolean warning;
    private Double sum;
    private Double has_paid;
    private Double total_fee;
}
