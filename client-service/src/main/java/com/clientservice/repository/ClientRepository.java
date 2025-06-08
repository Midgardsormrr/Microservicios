package com.clientservice.repository;

import com.clientservice.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Object> findByRut(String rut);

    List<Client> findByRutIn(List<String> clientRuts);

    void deleteByRut(String rut);

}
