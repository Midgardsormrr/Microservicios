package com.reservationservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class PaymentReceipt {
    private Long id;
    private String reservationCode;
    private LocalDateTime reservationDateTime;
    private int laps;
    private int numberOfPeople;
    private String reservedBy; // nombre del cliente principal
    private List<PaymentDetail> paymentDetails;
}
