package com.pricingservice.service;

import com.pricingservice.entity.Pricing;
import com.pricingservice.repository.PricingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class PricingService {
    @Autowired
    private PricingRepository pricingRepository;

    public ArrayList<Pricing> getPricing() {
        return (ArrayList<Pricing>) pricingRepository.findAll();
    }

    public Pricing getPricingByLaps(int laps) {
        return (Pricing) pricingRepository.findByLaps(laps).orElse(null);
    }

    public Integer getTotalDurationByLaps(int laps) {
        return pricingRepository.getTotalDurationByLaps(laps);
    }

}

