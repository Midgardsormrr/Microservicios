package com.paymentreceiptservice.repository;

import com.paymentreceiptservice.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentReceiptRepository extends JpaRepository<PaymentReceipt, Long> {

    PaymentReceipt findByReservationCode(String reservationCode);

}