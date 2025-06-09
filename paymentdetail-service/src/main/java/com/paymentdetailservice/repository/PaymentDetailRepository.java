package com.paymentdetailservice.repository;

import com.paymentdetailservice.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentDetailRepository extends JpaRepository<PaymentDetail, Long> {

    List<PaymentDetail> findByReceiptId(Long receiptId);
}

