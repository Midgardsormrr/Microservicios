// ======= PAYMENT RECEIPT CONTROLLER =======
package com.paymentreceiptservice.controller;

import com.paymentreceiptservice.entity.PaymentReceipt;
import com.paymentreceiptservice.model.Reservation;
import com.paymentreceiptservice.service.PaymentReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/receipts")
@CrossOrigin("*")
public class PaymentReceiptController {

    @Autowired
    private PaymentReceiptService paymentReceiptService;

    /**
     * GET RECEIPT BY RESERVATION CODE
     * Returns 404 Not Found if receipt does not exist
     */
    @GetMapping("/{reservationCode}")
    public ResponseEntity<PaymentReceipt> getReceiptByReservationCode(
            @PathVariable String reservationCode) {
        PaymentReceipt receipt = paymentReceiptService.getReceiptByReservationCode(reservationCode);
        if (receipt == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(receipt);
    }

    /**
     * GET ALL RECEIPTS
     * Returns 204 No Content if no receipts are found
     */
    @GetMapping
    public ResponseEntity<List<PaymentReceipt>> getAllReceipts() {
        List<PaymentReceipt> receipts = paymentReceiptService.getAllReceipts();
        if (receipts.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(receipts);
    }

    /**
     * CREATE NEW RECEIPT
     * Returns 201 Created with the generated receipt
     */
    @PostMapping
    public ResponseEntity<PaymentReceipt> generateReceipt(
            @RequestBody Reservation reservation) {
        PaymentReceipt receipt = paymentReceiptService.generateReceipt(reservation);
        return ResponseEntity
                .status(201)
                .body(receipt);
    }
}
