package com.paymentreceiptservice.entity;

import com.paymentreceiptservice.model.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "payment_receipts")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentReceipt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String reservationCode;
    private LocalDateTime reservationDateTime;
    private int laps;
    private int numberOfPeople;
    private String reservedBy; // nombre del cliente principal

    @Transient // JPA ignorará esta propiedad al persistir
    private List<PaymentDetail> paymentDetails = new ArrayList<>();

}
