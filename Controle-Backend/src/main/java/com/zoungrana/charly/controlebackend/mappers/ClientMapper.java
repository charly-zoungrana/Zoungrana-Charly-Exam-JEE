package com.zoungrana.charly.controlebackend.mappers;

import com.zoungrana.charly.controlebackend.dto.ClientDTO;
import com.zoungrana.charly.controlebackend.dto.ContratAssuranceDTO;
import com.zoungrana.charly.controlebackend.entities.Client;
import com.zoungrana.charly.controlebackend.entities.ContratAssurance;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ClientMapper {

    @Autowired
    private ContratAssuranceMapper contratAssuranceMapper;
    
    public ClientDTO fromClient(Client client) {
        ClientDTO clientDTO = new ClientDTO();
        BeanUtils.copyProperties(client, clientDTO);
        List<ContratAssurance> contratAssurances=client.getContrats();
        List<ContratAssuranceDTO> contratsDTO = contratAssurances.stream()
                .map(ContratAssuranceMapper::fromContratAssurance)
                .toList();
        clientDTO.setContrats(contratsDTO);

        return clientDTO;
    }
    
    public Client fromClientDTO(ClientDTO clientDTO) {
        Client client = new Client();
        BeanUtils.copyProperties(clientDTO, client);
        List<ContratAssuranceDTO> contratAssurancesDTO=clientDTO.getContrats();

        return client;
    }
}
