package com.clientservice.service;

import com.clientservice.entity.Client;
import com.clientservice.repository.ClientRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    public List<Client> getClients() {return clientRepository.findAll(); }

    public Client saveClient(Client client) {
        return clientRepository.save(client);
    }

    public Client getClientByRut(String rut) {
        return (Client) clientRepository.findByRut(rut).orElse(null);
    }

    public Client updateClient(Client client) {
        return clientRepository.save(client);
    }
    @Transactional
    public void deleteClientByRut(String rut) throws Exception {
        clientRepository.deleteByRut(rut);
    }
}