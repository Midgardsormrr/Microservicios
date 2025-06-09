package com.reservationservice.service;

import com.reservationservice.entity.Reservation;
import com.reservationservice.model.*;
import com.reservationservice.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    RestTemplate restTemplate;

    public List<Reservation> getReservations() {
        return reservationRepository.findAll();
    }

    public Reservation getReservationById(Long id) {
        return reservationRepository.findById(id).orElse(null);
    }

    public List<Reservation> getReservationsByDate(LocalDate date) {
        return reservationRepository.findAll().stream()
                .filter(r -> r.getStartDateTime().toLocalDate().equals(date))
                .collect(Collectors.toList());
    }

    public Reservation updateReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    public boolean deleteReservation(Long id) throws Exception {
        try {
            reservationRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public Reservation saveReservation(Reservation reservation) {
        if (reservation.getNumberOfPeople() < 1 || reservation.getNumberOfPeople() > 15) {
            throw new RuntimeException("Número de personas inválido (1 a 15 permitido)");
        }

        Integer totalDuration = restTemplate.getForObject(
                "http://pricing-service/pricing/laps/"+ reservation.getLaps() +"/duration", Integer.class);
        if (totalDuration == null) {
            throw new RuntimeException("No hay configuración de precio para esa cantidad de vueltas");
        }

        LocalDateTime start = reservation.getStartDateTime();
        LocalDateTime end = start.plusMinutes(totalDuration);

        DayOfWeek day = start.getDayOfWeek();
        LocalTime startTime = start.toLocalTime();
        LocalTime openingTime = (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY)
                ? LocalTime.of(10, 0)
                : LocalTime.of(14, 0);
        LocalTime closingTime = LocalTime.of(22, 0);

        if (startTime.isBefore(openingTime) || end.toLocalTime().isAfter(closingTime)) {
            throw new RuntimeException("Reserva fuera del horario permitido");
        }

        List<Reservation> conflicting = reservationRepository
                .findByStartDateTimeLessThanAndEndDateTimeGreaterThan(end, start);
        if (!conflicting.isEmpty()) {
            throw new RuntimeException("Horario no disponible. Hay otra reserva que se superpone.");
        }

        List<String> clientRuts = reservation.getClientRuts();
        if (clientRuts == null || clientRuts.isEmpty()) {
            throw new RuntimeException("No ingresaste ningun rut");
        }

        List<Client> clients = Arrays.asList(restTemplate.postForObject(
                "http://client-service/clients/batch", clientRuts, Client[].class));

        if (clients.size() != clientRuts.size()) {
            throw new RuntimeException("Uno o más RUTs no están registrados en el sistema.");
        }

        List<String> kartCodes = reservation.getKartCodes();
        if (kartCodes == null || kartCodes.isEmpty()) {
            throw new RuntimeException("No ingresaste ningún kart");
        }

        List<Kart> karts = Arrays.asList(restTemplate.postForObject(
                "http://kart-service/karts/available", kartCodes, Kart[].class));
        if (karts.size() != kartCodes.size()) {
            throw new RuntimeException("Uno o más karts no están disponibles");
        }


        Reservation newReservation = new Reservation();
        newReservation.setStartDateTime(start);
        newReservation.setEndDateTime(end);
        newReservation.setLaps(reservation.getLaps());
        newReservation.setNumberOfPeople(reservation.getNumberOfPeople());
        newReservation.setStatus("CONFIRMED");
        newReservation.setClientRuts(reservation.getClientRuts());
        newReservation.setKartCodes(reservation.getKartCodes());
        newReservation.setReservationCode("RES-" + LocalDateTime.now().toLocalDate() + "-K" + (int) (Math.random() * 1000));
        reservationRepository.save(newReservation);

        HttpEntity<Reservation> request = new HttpEntity<>(newReservation);
        PaymentReceipt receipt = restTemplate.postForObject(
                "http://paymentreceipt-service/receipts", request, PaymentReceipt.class);

        return newReservation;
    }

    public Map<String, Map<YearMonth, Long>> generateRevenueReport(LocalDate startDate, LocalDate endDate) {
        List<Reservation> reservations = reservationRepository
                .findByStartDateTimeBetweenAndStatus(
                        startDate.atStartOfDay(),
                        endDate.atTime(23, 59, 59),
                        "CONFIRMED"
                );

        Map<String, Map<YearMonth, Long>> report = new LinkedHashMap<>();

        List<String> categories = List.of(
                "10 vueltas o máx 10 min",
                "15 vueltas o máx 15 min",
                "20 vueltas o máx 20 min"
        );
        categories.forEach(cat -> report.put(cat, new TreeMap<>()));

        for (Reservation reservation : reservations) {
            PaymentReceipt receipt = restTemplate.getForObject(
                    "http://payment-service/receipts/" + reservation.getReservationCode(),
                    PaymentReceipt.class
            );

            if (receipt == null) continue;

            YearMonth month = YearMonth.from(reservation.getStartDateTime());
            String category = getCategoryByLaps(reservation.getLaps());

            double total = receipt.getPaymentDetails().stream()
                    .mapToDouble(PaymentDetail::getAmount)
                    .sum();

            report.get(category).merge(month, (long) total, Long::sum);
        }

        Map<YearMonth, Long> monthlyTotals = new TreeMap<>();
        report.forEach((category, months) ->
                months.forEach((month, amount) ->
                        monthlyTotals.merge(month, amount, Long::sum)
                )
        );
        report.put("TOTAL", monthlyTotals);
        return report;
    }

    public Map<String, Map<YearMonth, Long>> generatePeopleReport(LocalDate startDate, LocalDate endDate) {
        List<Reservation> reservations = reservationRepository
                .findByStartDateTimeBetweenAndStatus(
                        startDate.atStartOfDay(),
                        endDate.atTime(23, 59, 59),
                        "CONFIRMED"
                );

        Map<String, Map<YearMonth, Long>> report = new LinkedHashMap<>();

        List<String> categories = List.of(
                "1-2 personas",
                "3-5 personas",
                "6-10 personas",
                "11-15 personas"
        );
        categories.forEach(cat -> report.put(cat, new TreeMap<>()));

        for (Reservation reservation : reservations) {
            PaymentReceipt receipt = restTemplate.getForObject(
                    "http://paymentreceipt-service/receipts/" + reservation.getReservationCode(),
                    PaymentReceipt.class
            );

            if (receipt == null) continue;

            YearMonth month = YearMonth.from(reservation.getStartDateTime());
            String category = getCategoryByPeople(reservation.getNumberOfPeople());

            double total = receipt.getPaymentDetails().stream()
                    .mapToDouble(PaymentDetail::getAmount)
                    .sum();

            report.get(category).merge(month, (long) total, Long::sum);
        }

        Map<YearMonth, Long> monthlyTotals = new TreeMap<>();
        report.forEach((category, months) ->
                months.forEach((month, amount) ->
                        monthlyTotals.merge(month, amount, Long::sum)
                )
        );
        report.put("TOTAL", monthlyTotals);
        return report;
    }

    public List<Map<String, Object>> getWeeklySchedule(LocalDate startDate, LocalDate endDate) {
        LocalDateTime start = startDate.atStartOfDay();
        LocalDateTime end = endDate.atTime(23, 59, 59);

        return reservationRepository
                .findByStartDateTimeBetweenAndStatus(start, end, "CONFIRMED")
                .stream()
                .map(res -> {
                    Map<String, Object> response = new HashMap<>();
                    response.put("reservationCode", res.getReservationCode());
                    response.put("start", res.getStartDateTime());
                    response.put("end", res.getEndDateTime());
                    return response;
                }).collect(Collectors.toList());
    }

    private String getCategoryByLaps(int laps) {
        return switch (laps) {
            case 10 -> "10 vueltas o máx 10 min";
            case 15 -> "15 vueltas o máx 15 min";
            case 20 -> "20 vueltas o máx 20 min";
            default -> throw new IllegalArgumentException("Categoría no válida");
        };
    }

    private String getCategoryByPeople(int numberOfPeople) {
        return switch (numberOfPeople) {
            case 1, 2 -> "1-2 personas";
            case 3, 4, 5 -> "3-5 personas";
            case 6, 7, 8, 9, 10 -> "6-10 personas";
            case 11, 12, 13, 14, 15 -> "11-15 personas";
            default -> throw new IllegalArgumentException("Número de personas no válido: " + numberOfPeople);
        };
    }
}
