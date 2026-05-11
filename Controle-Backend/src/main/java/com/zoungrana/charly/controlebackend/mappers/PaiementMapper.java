package com.zoungrana.charly.controlebackend.mappers;

import com.zoungrana.charly.controlebackend.dto.PaiementDTO;
import com.zoungrana.charly.controlebackend.entities.Paiement;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class PaiementMapper {
    
    public PaiementDTO fromPaiement(Paiement paiement) {
        PaiementDTO dto = new PaiementDTO();
        BeanUtils.copyProperties(paiement, dto);
        
        if (paiement.getContrat() != null) {
            dto.setContratId(paiement.getContrat().getId());
            dto.setContratType(paiement.getContrat().getClass().getSimpleName());
            dto.setClientNom(paiement.getContrat().getClient() != null ? 
                paiement.getContrat().getClient().getNom() : null);
        }
        
        return dto;
    }
    
    public Paiement fromPaiementDTO(PaiementDTO dto) {
        Paiement paiement = new Paiement();
        BeanUtils.copyProperties(dto, paiement);
        return paiement;
    }
}
