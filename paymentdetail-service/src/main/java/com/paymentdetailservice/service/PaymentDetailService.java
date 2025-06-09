package com.paymentdetailservice.service;

import com.paymentdetailservice.entity.PaymentDetail;
import com.paymentdetailservice.repository.PaymentDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentDetailService {
    @Autowired
    private PaymentDetailRepository detailRepository;

    public ArrayList<PaymentDetail> getPaymentDetails() {
        return (ArrayList<PaymentDetail>) detailRepository.findAll();
    }

    public PaymentDetail savePaymentDetail(PaymentDetail detail) {
        return detailRepository.save(detail);
    }

    public List<PaymentDetail> getPaymentDetailById(Long receiptId) {
        return detailRepository.findByReceiptId(receiptId);  // Esto debería devolver una lista de PaymentDetail
    }

    public PaymentDetail updatePaymentDetail(PaymentDetail detail) {
        return detailRepository.save(detail);
    }

    public boolean deletePaymentDetail(Long id) throws Exception {
        try {
            detailRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
