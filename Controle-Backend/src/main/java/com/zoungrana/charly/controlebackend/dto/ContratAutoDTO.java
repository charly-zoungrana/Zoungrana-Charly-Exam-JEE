package com.zoungrana.charly.controlebackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class ContratAutoDTO extends ContratAssuranceDTO {
    private String immatriculation;
    private String marque;
    private String modele;
    private String typeContrat;
    
    public ContratAutoDTO() {
        super();
        this.typeContrat = "AUTO";
    }
}
