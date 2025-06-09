package com.kartservice.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Kart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;
    private String code;                 // K001, K002, …
    private String status;               // AVAILABLE, UNDER_MAINTENANCE

    public void setStatus(String status) {
        this.status = status;
    }
}

