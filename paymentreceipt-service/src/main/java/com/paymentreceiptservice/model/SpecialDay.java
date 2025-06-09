package com.paymentreceiptservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpecialDay {
    private Long id;
    private LocalDate date;
    private String type;                // HOLIDAY, WEEKEND
    private double priceMultiplier;
}
