package com.paymentreceiptservice.service;

import com.paymentreceiptservice.entity.*;
import com.paymentreceiptservice.model.*;
import com.paymentreceiptservice.repository.PaymentReceiptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PaymentReceiptService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private PaymentReceiptRepository paymentReceiptRepository;

    public PaymentReceipt generateReceipt(Reservation reservation) {
        int laps = reservation.getLaps();
        int people = reservation.getNumberOfPeople();
        LocalDate reservationDate = reservation.getStartDateTime().toLocalDate();

        Float totalPrice = restTemplate.getForObject(
                "http://pricing-service/pricing/laps/" + laps + "/price",
                Float.class
        );


        if (totalPrice == null) {
            throw new RuntimeException("No hay precio configurado para esta cantidad de vueltas");
        }

        float basePricePerPerson = totalPrice / people;
        System.out.println("Precio base por persona: " + basePricePerPerson);

        // 2) Obtener multiplicador por día especial desde SpecialDay Service
        DayOfWeek day = reservationDate.getDayOfWeek();
        float priceMultiplier = 1.0f;

        if (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY) {
            SpecialDay weekend = restTemplate.getForObject(
                    "http://specialday-service/special-days/type/WEEKEND",
                    SpecialDay.class
            );

            if (weekend != null) {
                priceMultiplier = (float) weekend.getPriceMultiplier();
                System.out.println("Aplicado multiplicador de fin de semana: " + priceMultiplier);
            }
        } else {
            SpecialDay holiday = restTemplate.getForObject(
                    "http://specialday-service/special-days/date/" + reservationDate,
                    SpecialDay.class
            );

            if (holiday != null && "HOLIDAY".equalsIgnoreCase(holiday.getType())) {
                priceMultiplier = (float) holiday.getPriceMultiplier();
                System.out.println("Aplicado multiplicador de feriado: " + priceMultiplier);
            }
        }

        float priceAfterDayMultiplier = basePricePerPerson * priceMultiplier;
        System.out.println("Precio después de multiplicador de día especial: " + priceAfterDayMultiplier);

        // 3) % descuento grupal (lógica local)
        float groupDiscount = calculateGroupDiscount(people);
        System.out.println("Descuento grupal: " + groupDiscount + "%");

        // 4) Obtener todos los cleientes
        List<Client> clients = restTemplate.postForObject(
                "http://client-service/clients/batch",
                reservation.getClientRuts(),
                List.class
        );

        if (clients == null || clients.isEmpty()) {
            throw new RuntimeException("No se encontraron clientes para esta reserva");
        }

        // 5) Lógica cumpleañeros
        SpecialDay birthdaySpecial = restTemplate.getForObject(
                "http://specialday-service/special-days/type/BIRTHDAY", // <- ruta corregida
                SpecialDay.class
        );

        if (birthdaySpecial == null) {
            System.out.println("No se encontró un especial de cumpleaños en el servicio.");
        } else {
            System.out.println("Multiplicador de cumpleaños: " + birthdaySpecial.getPriceMultiplier());
        }



        int maxBirthday = calculateMaxBirthday(people);
        Set<String> birthdayNames = getBirthdayClients(clients, reservationDate, maxBirthday);

        // 6) Construir detalles de pago
        List<PaymentDetail> details = buildPaymentDetails(
                clients,
                priceAfterDayMultiplier,
                groupDiscount,
                birthdayNames,
                birthdaySpecial
        );

        // 7) Armar y guardar recibo
        PaymentReceipt receipt = new PaymentReceipt();
        receipt.setReservationCode(reservation.getReservationCode());
        receipt.setReservationDateTime(reservation.getStartDateTime());
        receipt.setLaps(laps);
        receipt.setNumberOfPeople(people);
        receipt.setReservedBy(clients.get(0).getName());
        receipt.setPaymentDetails(details);

        return paymentReceiptRepository.save(receipt);
    }

    // Métodos auxiliares
    private float calculateGroupDiscount(int people) {
        if (people >= 3 && people <= 5) return 10f;
        if (people >= 6 && people <= 10) return 20f;
        if (people >= 11 && people <= 15) return 30f;
        return 0f;
    }

    private int calculateMaxBirthday(int people) {
        if (people >= 3 && people <= 5) return 1;
        if (people >= 6 && people <= 10) return 2;
        return 0;
    }

    private Set<String> getBirthdayClients(List<Client> clients, LocalDate reservationDate, int maxBirthday) {
        return clients.stream()
                .filter(c -> c.getBirthDate() != null
                        && c.getBirthDate().getMonth() == reservationDate.getMonth()
                        && c.getBirthDate().getDayOfMonth() == reservationDate.getDayOfMonth())
                .limit(maxBirthday)
                .map(Client::getName)
                .collect(Collectors.toSet());
    }

    private List<PaymentDetail> buildPaymentDetails(
            List<Client> clients,
            float priceAfterDayMultiplier,
            float groupDiscount,
            Set<String> birthdayNames,
            SpecialDay birthdaySpecial
    ) {
        return clients.stream().map(client -> {
            PaymentDetail d = new PaymentDetail();
            d.setClientName(client.getName());

            // Aplicar descuento grupal
            float p1 = priceAfterDayMultiplier * (1 - groupDiscount / 100f);

            // Obtener y aplicar descuento por frecuencia desde Client Service
            Integer visits = restTemplate.getForObject(
                    "http://client-service/clients/" + client.getId() + "/visits",
                    Integer.class
            );

            float freqDisc = calculateFrequencyDiscount(visits != null ? visits : 0);
            float p2 = p1 * (1 - freqDisc / 100f);

            // Aplicar multiplicador cumpleaños si corresponde
            float p3 = p2;
            if (birthdayNames.contains(client.getName()) && birthdaySpecial != null) {
                p3 = p2 * (float) birthdaySpecial.getPriceMultiplier();
            }

            d.setAmount(p3);
            return d;
        }).collect(Collectors.toList());
    }

    private float calculateFrequencyDiscount(int visits) {
        if (visits >= 7) return 30f;
        if (visits >= 5) return 20f;
        if (visits >= 2) return 10f;
        return 0f;
    }

    public PaymentReceipt getReceiptByReservationCode(String reservationCode) {
        return paymentReceiptRepository.findByReservationCode(reservationCode);
    }

    public List<PaymentReceipt> getAllReceipts() {
        return paymentReceiptRepository.findAll();
    }
}