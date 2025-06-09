package com.specialdayservice.repository;


import com.specialdayservice.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface SpecialDayRepository extends JpaRepository<SpecialDay, Long> {

    boolean existsByDate(LocalDate date);

    SpecialDay findByDate(LocalDate localDate);

    SpecialDay findByType(String weekend);

}