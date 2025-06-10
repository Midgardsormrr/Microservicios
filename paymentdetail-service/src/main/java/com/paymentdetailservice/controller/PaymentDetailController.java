// ======= PAYMENT DETAIL CONTROLLER =======
package com.paymentdetailservice.controller;

import com.paymentdetailservice.entity.PaymentDetail;
import com.paymentdetailservice.service.PaymentDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payment-details")
public class PaymentDetailController {

    @Autowired
    private PaymentDetailService detailService;

    /**
     * GET ALL PAYMENT DETAILS
     * Returns 204 No Content if no details are found
     */
    @GetMapping
    public ResponseEntity<List<PaymentDetail>> getAllDetails() {
        List<PaymentDetail> details = detailService.getPaymentDetails();
        if (details.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(details);
    }

    /**
     * CREATE NEW PAYMENT DETAIL
     * Returns 201 Created with the created entity
     */
    @PostMapping
    public ResponseEntity<PaymentDetail> createDetail(@RequestBody PaymentDetail detail) {
        PaymentDetail saved = detailService.savePaymentDetail(detail);
        return ResponseEntity
                .status(201)
                .body(saved);
    }

    /**
     * UPDATE EXISTING PAYMENT DETAIL
     * Returns 404 Not Found if detail does not exist
     */
    @PutMapping
    public ResponseEntity<PaymentDetail> updateDetail(@RequestBody PaymentDetail detail) {
        try {
            PaymentDetail updated = detailService.updatePaymentDetail(detail);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * DELETE PAYMENT DETAIL BY ID
     * Returns 204 No Content if deleted, 404 if detail not found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDetail(@PathVariable Long id) {
        try {
            detailService.deletePaymentDetail(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
