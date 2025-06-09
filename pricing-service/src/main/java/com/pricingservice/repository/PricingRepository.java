package com.pricingservice.repository;


import com.pricingservice.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PricingRepository extends JpaRepository<Pricing, Long> {
    Optional<Object> findByLaps(int laps);

    @Query("SELECT p.basePrice FROM Pricing p WHERE p.laps = :laps")
    Float getTotalPriceByLaps(@Param("laps") int laps);

    @Query("SELECT p.totalDuration FROM Pricing p WHERE p.laps = :laps")
    Integer getTotalDurationByLaps(@Param("laps") int laps);

}
