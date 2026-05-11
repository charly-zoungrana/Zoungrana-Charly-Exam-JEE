package com.zoungrana.charly.controlebackend.dto;

import com.zoungrana.charly.controlebackend.enums.NiveauCouverture;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class ContratSanteDTO extends ContratAssuranceDTO {
    private NiveauCouverture niveauCouverture;
    private Integer nbPersonnes;
    private String typeContrat;
    
    public ContratSanteDTO() {
        super();
        this.typeContrat = "SANTE";
    }
}
