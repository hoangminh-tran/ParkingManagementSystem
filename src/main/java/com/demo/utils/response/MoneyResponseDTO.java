package com.demo.utils.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MoneyResponseDTO {
    double CAR_MONEY_BY_HOUR;
    double BIKE_MONEY_BY_HOUR;
    double MOTO_MONEY_BY_HOUR;
    double CAR_MONEY_BY_DAY;
    double BIKE_MONEY_BY_DAY;
    double MOTO_MONEY_BY_DAY;
    double CAR_MONEY_BY_MONTH;
    double BIKE_MONEY_BY_MONTH;
    double MOTO_MONEY_BY_MONTH;
}
