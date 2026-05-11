package com.zoungrana.charly.controlebackend.dto;

import com.zoungrana.charly.controlebackend.enums.TypeLogement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class ContratHabitationDTO extends ContratAssuranceDTO {
    private TypeLogement typeLogement;
    private String adresse;
    private Double superficie;
    private String typeContrat;
    
    public ContratHabitationDTO() {
        super();
        this.typeContrat = "HABITATION";
    }
}
