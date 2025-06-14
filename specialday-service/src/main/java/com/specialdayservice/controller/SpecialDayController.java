package com.specialdayservice.controller;

import com.specialdayservice.entity.SpecialDay;
import com.specialdayservice.service.SpecialDayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/special-days")
public class SpecialDayController {

    @Autowired
    private SpecialDayService specialDayService;

    @GetMapping
    public ResponseEntity<List<SpecialDay>> getAll() {
        List<SpecialDay> specialDays = specialDayService.getSpecialDays();
        if(specialDays.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(specialDays);
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<SpecialDay> getByDate(@PathVariable("date") String date) {
        SpecialDay specialDay = specialDayService.getSpecialDayByDate(LocalDate.parse(date));
        if(specialDay == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(specialDay);
    }

    @GetMapping("/is-special/{date}")
    public ResponseEntity<SpecialDay> isSpecial(@PathVariable("date") String date) {
        SpecialDay isSpecial = specialDayService.isSpecialDay(LocalDate.parse(date));
        return ResponseEntity.ok(isSpecial);
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<SpecialDay> getByType(@PathVariable("type") String type) {
        SpecialDay specialDay = specialDayService.getSpecialDayByType(type);
        if (specialDay == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(specialDay);
    }

}