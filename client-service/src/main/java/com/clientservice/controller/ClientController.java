package com.clientservice.controller;

import com.clientservice.entity.Client;
import com.clientservice.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
@CrossOrigin("*")
public class ClientController {

    @Autowired
    private ClientService clientService;

    /**
     * GET ALL CLIENTS
     * Returns 204 No Content if no clients are found
     */
    @GetMapping
    public ResponseEntity<List<Client>> getAllClients() {
        List<Client> clients = clientService.getClients();
        if (clients.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(clients);
    }

    /**
     * GET CLIENT BY RUT
     * Returns 404 Not Found if client does not exist
     */
    @GetMapping("/{rut}")
    public ResponseEntity<Client> getClientByRut(@PathVariable String rut) {
        Client client = clientService.getClientByRut(rut);
        if (client == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(client);
    }

    /**
     * CREATE NEW CLIENT
     * Returns 201 Created with the created entity
     */
    @PostMapping
    public ResponseEntity<Client> createClient(@RequestBody Client client) {
        Client savedClient = clientService.saveClient(client);
        return ResponseEntity
                .status(201)
                .body(savedClient);
    }

    /**
     * UPDATE EXISTING CLIENT
     * Returns 404 Not Found if client does not exist
     */
    @PutMapping
    public ResponseEntity<Client> updateClient(@RequestBody Client client) {
        Client existing = clientService.getClientByRut(client.getRut());
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        Client updatedClient = clientService.updateClient(client);
        return ResponseEntity.ok(updatedClient);
    }

    /**
     * DELETE CLIENT BY RUT
     * Returns 204 No Content if deletion is successful, 404 if client not found
     */
    @DeleteMapping("/{rut}")
    public ResponseEntity<Void> deleteClientByRut(@PathVariable String rut) throws Exception {
        Client existing = clientService.getClientByRut(rut);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        clientService.deleteClientByRut(rut);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/batch")
    public ResponseEntity<List<Client>> getClientsByRuts(@RequestBody List<String> ruts) {
        List<Client> clients = clientService.getClientsByRuts(ruts);
        return ResponseEntity.ok(clients);
    }

    @GetMapping("/{id}/visits")
    public ResponseEntity<Integer> getVisitCount(@PathVariable Long id) {
        Client client = clientService.getClientById(id);
        if (client == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(client.getMonthlyVisitCount()); // asumiendo que tienes ese campo
    }


}
