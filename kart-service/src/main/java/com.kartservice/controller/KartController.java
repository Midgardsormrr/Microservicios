// ======= KART CONTROLLER =======
package com.kartservice.controller;

import com.kartservice.entity.Kart;
import com.kartservice.service.KartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/karts")
@CrossOrigin("*")
public class KartController {

    @Autowired
    private KartService kartService;

    /**
     * GET ALL KARTS
     * Returns 204 No Content if no karts are found
     */
    @GetMapping
    public ResponseEntity<List<Kart>> getAllKarts() {
        List<Kart> karts = kartService.getKarts();
        if (karts.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(karts);
    }

    /**
     * GET KART BY ID
     * Returns 404 Not Found if kart does not exist
     */
    @GetMapping("/{id}")
    public ResponseEntity<Kart> getKartById(@PathVariable Long id) {
        Kart kart = kartService.getKartById(id);
        if (kart == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(kart);
    }

    /**
     * CREATE NEW KART
     * Returns 201 Created with the created entity
     */
    @PostMapping
    public ResponseEntity<Kart> createKart(@RequestBody Kart kart) {
        Kart savedKart = kartService.saveKart(kart);
        return ResponseEntity
                .status(201)
                .body(savedKart);
    }

    /**
     * UPDATE KART STATUS
     * Returns 404 Not Found if kart does not exist
     */
    @PutMapping("/{id}/status")
    public ResponseEntity<Kart> updateStatus(@PathVariable Long id, @RequestParam String status) {
        Kart existing = kartService.getKartById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        Kart updatedKart = kartService.updateKartStatus(id, status);
        return ResponseEntity.ok(updatedKart);
    }

    /**
     * DELETE KART BY ID
     * Returns 204 No Content if deletion is successful, 404 if kart not found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteKart(@PathVariable Long id) throws Exception {
        Kart existing = kartService.getKartById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        kartService.deleteKart(id);
        return ResponseEntity.noContent().build();
    }
}
