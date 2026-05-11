package com.zoungrana.charly.controlebackend.dto;

import com.zoungrana.charly.controlebackend.enums.StatutContrat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContratAssuranceDTO {
    private Long id;
    private LocalDate dateSouscription;
    private StatutContrat statut;
    private LocalDate dateValidation;
    private Double montantCotisation;
    private Integer duree;
    private Double tauxCouverture;
    private Long clientId;
    private String clientNom;
    private LocalDateTime dateCreation;
    private LocalDateTime dateModification;
    private List<PaiementDTO> paiements;
    private String typeContrat; // AUTO, HABITATION, SANTE
}
