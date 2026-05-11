package com.zoungrana.charly.controlebackend.dto;

import com.zoungrana.charly.controlebackend.enums.TypePaiement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaiementDTO {
    private Long id;
    private LocalDateTime datePaiement;
    private Double montant;
    private TypePaiement typePaiement;
    private Long contratId;
    private String contratType;
    private String clientNom;
    private LocalDateTime dateCreation;
    private LocalDateTime dateModification;
}
