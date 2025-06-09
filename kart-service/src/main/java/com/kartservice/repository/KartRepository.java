package com.kartservice.repository;

import com.kartservice.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KartRepository extends JpaRepository<Kart, Long> {

    List<Kart> findByCodeInAndStatus(List<String> kartCodes, String available);

}