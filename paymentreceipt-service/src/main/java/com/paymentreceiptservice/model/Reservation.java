package com.paymentreceiptservice.model;
import com.paymentreceiptservice.entity.PaymentReceipt;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {
    private Long id;
    private String reservationCode;      // Ej: "RES-20231025-K001"
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private int laps;
    private int numberOfPeople;
    private String status;               // CONFIRMED, CANCELLED
    private List<String> clientRuts;
    private List<String> kartCodes;
    private PaymentReceipt paymentReceipt;
    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }
}
