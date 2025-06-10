package com.reservationservice.controller;

import com.reservationservice.entity.Reservation;
import com.reservationservice.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @GetMapping
    public ResponseEntity<List<Reservation>> getAllReservations() {
        List<Reservation> reservations = reservationService.getReservations();
        if(reservations.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(reservations);
    }

    @PostMapping()
    public ResponseEntity<Reservation> save(@RequestBody Reservation reservation) {
        Reservation reservationNew = reservationService.saveReservation(reservation);
        return ResponseEntity.ok(reservationNew);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getById(@PathVariable("id") Long id) {
        Reservation reservation = reservationService.getReservationById(id);
        if(reservation == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(reservation);
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<List<Reservation>> getByDate(@PathVariable String date) {
        List<Reservation> reservations = reservationService.getReservationsByDate(LocalDate.parse(date));
        if(reservations.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(reservations);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reservation> updateReservation(@PathVariable("id") Long id, @RequestBody Reservation reservation) {
        reservation.setId(id);
        Reservation updatedReservation = reservationService.updateReservation(reservation);
        if(updatedReservation == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(updatedReservation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteReservation(@PathVariable("id") Long id) throws Exception {
        boolean deleted = reservationService.deleteReservation(id);
        if(!deleted)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(true);
    }

    @GetMapping("/report/by-laps")
    public ResponseEntity<Map<String, Map<YearMonth, Long>>> generateReport(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {

        Map<String, Map<YearMonth, Long>> report = reservationService.generateRevenueReport(start, end);
        if(report.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(report);
    }

    @GetMapping("/report/by-people")
    public ResponseEntity<Map<String, Map<YearMonth, Long>>> generatePeopleReport(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {

        Map<String, Map<YearMonth, Long>> report = reservationService.generatePeopleReport(start, end);
        if(report.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(report);
    }

    @GetMapping("/weekly-schedule")
    public ResponseEntity<List<Map<String, Object>>> getWeeklySchedule(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {

        List<Map<String, Object>> schedule = reservationService.getWeeklySchedule(start, end);
        if(schedule.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(schedule);
    }
}