package com.zoungrana.charly.controlebackend.services;

import com.zoungrana.charly.controlebackend.dtos.ClientDTO;

import java.util.List;

public interface ClientService {
    ClientDTO saveClient(ClientDTO clientDTO);
    ClientDTO getClient(Long clientId);
    List<ClientDTO> listClients();
    ClientDTO updateClient(Long clientId, ClientDTO clientDTO);
    void deleteClient(Long clientId);
    ClientDTO findByEmail(String email);
    boolean existsByEmail(String email);
    List<ClientDTO> searchClientsByName(String keyword);
    List<ClientDTO> getClientsWithContrats();
}
