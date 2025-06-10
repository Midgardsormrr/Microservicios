// ======= PRICING CONTROLLER =======
package com.pricingservice.controller;

import com.pricingservice.entity.Pricing;
import com.pricingservice.service.PricingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pricing")
public class PricingController {

    @Autowired
    private PricingService pricingService;

    /**
     * GET ALL PRICING ENTRIES
     * Returns 204 No Content if no pricing entries are found
     */
    @GetMapping
    public ResponseEntity<List<Pricing>> getAllPricing() {
        List<Pricing> pricingList = pricingService.getPricing();
        if (pricingList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(pricingList);
    }

    /**
     * GET PRICING BY LAPS
     * Returns 404 Not Found if no pricing exists for the given laps
     */
    @GetMapping("/laps/{laps}")
    public ResponseEntity<Pricing> getByLaps(@PathVariable int laps) {
        Pricing pricing = pricingService.getPricingByLaps(laps);
        if (pricing == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pricing);
    }

    /**
     * GET TOTAL DURATION BY LAPS
     * Returns 404 Not Found if no pricing exists for the given laps
     */
    @GetMapping("/laps/{laps}/duration")
    public ResponseEntity<Integer> getTotalDurationByLaps(@PathVariable int laps) {
        Pricing pricing = pricingService.getPricingByLaps(laps);
        if (pricing == null) {
            return ResponseEntity.notFound().build();
        }
        Integer duration = pricingService.getTotalDurationByLaps(laps);
        return ResponseEntity.ok(duration);
    }

    @GetMapping("/laps/{laps}/price")
    public ResponseEntity<Float> getPriceByLaps(@PathVariable int laps) {
        Pricing pricing = pricingService.getPricingByLaps(laps);
        if (pricing == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok((float) pricing.getBasePrice());
    }

}
