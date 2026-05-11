package com.zoungrana.charly.controlebackend.mappers;

import com.zoungrana.charly.controlebackend.dto.*;
import com.zoungrana.charly.controlebackend.entities.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;


@Service
public class ContratAssuranceMapper {
    
    public static ContratAssuranceDTO fromContratAssurance(ContratAssurance contrat) {
        ContratAssuranceDTO contratDTO = new ContratAssuranceDTO();
        BeanUtils.copyProperties(contrat, contratDTO);
        
        if (contrat.getClient() != null) {
            contratDTO.setClientId(contrat.getClient().getId());
            contratDTO.setClientNom(contrat.getClient().getNom());
        }
        
        if (contrat instanceof ContratAuto) {
            return fromContratAuto((ContratAuto) contrat);
        } else if (contrat instanceof ContratHabitation) {
            return fromContratHabitation((ContratHabitation) contrat);
        } else if (contrat instanceof ContratSante) {
            return fromContratSante((ContratSante) contrat);
        }
        
        contratDTO.setTypeContrat(contrat.getClass().getSimpleName());
        return contratDTO;
    }
    
    public static ContratAutoDTO fromContratAuto(ContratAuto contrat) {
        ContratAutoDTO dto = new ContratAutoDTO();
        BeanUtils.copyProperties(contrat, dto);
        
        if (contrat.getClient() != null) {
            dto.setClientId(contrat.getClient().getId());
            dto.setClientNom(contrat.getClient().getNom());
        }
        
        dto.setTypeContrat("AUTO");
        return dto;
    }
    
    public static ContratHabitationDTO fromContratHabitation(ContratHabitation contrat) {
        ContratHabitationDTO dto = new ContratHabitationDTO();
        BeanUtils.copyProperties(contrat, dto);
        
        if (contrat.getClient() != null) {
            dto.setClientId(contrat.getClient().getId());
            dto.setClientNom(contrat.getClient().getNom());
        }
        
        dto.setTypeContrat("HABITATION");
        return dto;
    }
    
    public static ContratSanteDTO fromContratSante(ContratSante contrat) {
        ContratSanteDTO dto = new ContratSanteDTO();
        BeanUtils.copyProperties(contrat, dto);
        
        if (contrat.getClient() != null) {
            dto.setClientId(contrat.getClient().getId());
            dto.setClientNom(contrat.getClient().getNom());
        }
        
        dto.setTypeContrat("SANTE");
        return dto;
    }
    
    public static ContratAuto fromContratAutoDTO(ContratAutoDTO dto) {
        ContratAuto contrat = new ContratAuto();
        BeanUtils.copyProperties(dto, contrat);
        return contrat;
    }
    
    public static ContratHabitation fromContratHabitationDTO(ContratHabitationDTO dto) {
        ContratHabitation contrat = new ContratHabitation();
        BeanUtils.copyProperties(dto, contrat);
        return contrat;
    }
    
    public static ContratSante fromContratSanteDTO(ContratSanteDTO dto) {
        ContratSante contrat = new ContratSante();
        BeanUtils.copyProperties(dto, contrat);
        return contrat;
    }
}
