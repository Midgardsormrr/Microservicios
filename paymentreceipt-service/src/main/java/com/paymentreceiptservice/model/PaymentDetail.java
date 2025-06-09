package com.paymentreceiptservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDetail {
    private Long id;
    private String clientName;
    private float amount;
    private Long receiptId;
}
