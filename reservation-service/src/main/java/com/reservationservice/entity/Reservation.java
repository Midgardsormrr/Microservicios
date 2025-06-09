package com.reservationservice.entity;

import javax.persistence.*;
import com.reservationservice.model.PaymentReceipt;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    private String reservationCode;      // Ej: "RES-20231025-K001"
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private int laps;
    private int numberOfPeople;
    private String status;               // CONFIRMED, CANCELLED

    // Lista de objetos Cliente
    @ElementCollection
    @CollectionTable(name = "reservation_clients", joinColumns = @JoinColumn(name = "reservation_id"))
    @Column(name = "client_rut")
    private List<String> clientRuts;

    // Objeto Kart reservado
    @ElementCollection
    @CollectionTable(name = "reservation_karts", joinColumns = @JoinColumn(name = "reservation_id"))
    @Column(name = "kart_code")
    private List<String> kartCodes;

    // Objeto PaymentReceipt asociado
    @Transient
    private PaymentReceipt paymentReceipt;

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

}
