package com.zoungrana.charly.controlebackend.services;

import com.zoungrana.charly.controlebackend.dto.ClientDTO;
import com.zoungrana.charly.controlebackend.entities.Client;
import com.zoungrana.charly.controlebackend.mappers.ClientMapper;
import com.zoungrana.charly.controlebackend.repository.ClientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class ClientServiceImpl implements ClientService {
    
    @Autowired
    private ClientRepository clientRepository;
    
    @Autowired
    private ClientMapper clientMapper;
    
    @Override
    public ClientDTO saveClient(ClientDTO clientDTO) {
        log.info("Saving new client: {}", clientDTO.getEmail());
        Client client = clientMapper.fromClientDTO(clientDTO);
        Client savedClient = clientRepository.save(client);
        return clientMapper.fromClient(savedClient);
    }
    
    @Override
    public ClientDTO getClient(Long clientId) {
        log.info("Getting client with ID: {}", clientId);
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found with ID: " + clientId));
        return clientMapper.fromClient(client);

    }
    
    @Override
    public List<ClientDTO> listClients() {
        log.info("Getting all clients");
        List<Client> clients = clientRepository.findAll();
        return clients.stream()
                .map(clientMapper::fromClient)
                .collect(Collectors.toList());
    }
    
    @Override
    public ClientDTO updateClient(Long clientId, ClientDTO clientDTO) {
        log.info("Updating client with ID: {}", clientId);
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found with ID: " + clientId));
        
        client.setNom(clientDTO.getNom());
        client.setEmail(clientDTO.getEmail());
        
        Client updatedClient = clientRepository.save(client);
        return clientMapper.fromClient(updatedClient);
    }
    
    @Override
    public void deleteClient(Long clientId) {
        log.info("Deleting client with ID: {}", clientId);
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found with ID: " + clientId));
        clientRepository.delete(client);
    }
    
    @Override
    public ClientDTO findByEmail(String email) {
        log.info("Finding client by email: {}", email);
        Client client = clientRepository.findByEmail(email);
        return client != null ? clientMapper.fromClient(client) : null;
    }
    
    @Override
    public boolean existsByEmail(String email) {
        return clientRepository.existsByEmail(email);
    }
    
    @Override
    public List<ClientDTO> searchClientsByName(String keyword) {
        log.info("Searching clients by name: {}", keyword);
        List<Client> clients = clientRepository.findByNomContainingIgnoreCase(keyword);
        return clients.stream()
                .map(clientMapper::fromClient)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<ClientDTO> getClientsWithContrats() {
        log.info("Getting clients with their contracts");
        List<Client> clients = clientRepository.findAllWithContrats();
        return clients.stream()
                .map(clientMapper::fromClient)
                .collect(Collectors.toList());
    }
}
