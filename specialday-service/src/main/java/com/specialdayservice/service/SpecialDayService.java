package com.specialdayservice.service;

import com.specialdayservice.entity.SpecialDay;
import com.specialdayservice.repository.SpecialDayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;

@Service
public class SpecialDayService {
    @Autowired
    private SpecialDayRepository specialDayRepository;

    public ArrayList<SpecialDay> getSpecialDays() {
        return (ArrayList<SpecialDay>) specialDayRepository.findAll();
    }

    public SpecialDay getSpecialDayByDate(LocalDate date) {
        return (SpecialDay) specialDayRepository.findByDate(date);
    }

    public SpecialDay isSpecialDay(LocalDate date) {
        return specialDayRepository.existsByDate(date);
    }

    public SpecialDay getSpecialDayByType(String type) {
        return specialDayRepository.findByType(type);
    }

}
