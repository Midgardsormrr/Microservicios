// ======= KART SERVICE =======
package com.kartservice.service;

import com.kartservice.entity.Kart;
import com.kartservice.repository.KartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class KartService {
    @Autowired
    private KartRepository kartRepository;

    public ArrayList<Kart> getKarts() {
        return (ArrayList<Kart>) kartRepository.findAll();
    }

    public Kart saveKart(Kart kart) {
        return kartRepository.save(kart);
    }

    public Kart getKartById(Long Id) {
        return kartRepository.findById(Id).orElse(null);
    }

    public Kart updateKartStatus(Long Id, String status) {
        Kart kart = getKartById(Id);
        if (kart != null) {
            kart.setStatus(status);
            return kartRepository.save(kart);
        }
        return null;
    }

    public void deleteKart(Long id) throws Exception {
        try {
            kartRepository.deleteById(id);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public List<Kart> getAvailableKartsByCodes(List<String> kartCodes) {
        return kartRepository.findByCodeInAndStatus(kartCodes, "AVAILABLE");
    }

}