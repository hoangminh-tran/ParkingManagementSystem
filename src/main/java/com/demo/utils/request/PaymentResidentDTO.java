package com.demo.utils.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResidentDTO {
    private String TypeOfPayment; // Type of Payment

    private String idUser;

    private String id_Building;

    private Date dateOfPayment;
}
