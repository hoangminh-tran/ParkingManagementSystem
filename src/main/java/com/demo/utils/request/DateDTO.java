package com.demo.utils.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor@NoArgsConstructor
public class DateDTO {
    private String time;
    private Date startDate;
    private Date endDate;
    private String startTime;
    private String endTime;
}
