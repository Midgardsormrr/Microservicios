package com.reservationservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Client {
    private Long id;
    private String rut;
    private String name;
    private String email;
    private LocalDate birthDate;
    private int monthlyVisitCount;
}
