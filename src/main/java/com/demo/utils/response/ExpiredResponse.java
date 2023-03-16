package com.demo.utils.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpiredResponse {
    String id_user;
    String id_invoice;
    Date current_date;
    String current_time;
    Date end_date;
    String end_time;
    int expired;
    double fine;
    boolean warning;
}