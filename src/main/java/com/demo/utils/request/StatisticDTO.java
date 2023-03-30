package com.demo.utils.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatisticDTO {
    private String startDate;
    private String endDate;
    private String totalOfMoney;
    private String numberOfCustomerInvoice;

    private String numberOfResidentInvoice;

    private String numberOfCustomerInvoiceExpire;

    private String numberOfResidentInvoiceExpire;

}
