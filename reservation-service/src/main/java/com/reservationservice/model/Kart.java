package com.reservationservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Kart {
    private Long id;
    private String code;                 // K001, K002, …
    private String status;               // AVAILABLE, UNDER_MAINTENANCE
    public void setStatus(String status) {
        this.status = status;
    }
}
