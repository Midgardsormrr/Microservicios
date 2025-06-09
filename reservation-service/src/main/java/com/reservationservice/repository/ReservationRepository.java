package com.reservationservice.repository;

import com.reservationservice.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByStartDateTimeLessThanAndEndDateTimeGreaterThan(LocalDateTime endDateTime, LocalDateTime startDateTime);

    List<Reservation> findByStartDateTimeBetweenAndStatus(LocalDateTime localDateTime, LocalDateTime localDateTime1, String confirmed);

}

