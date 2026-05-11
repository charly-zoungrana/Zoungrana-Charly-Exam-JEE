package com.zoungrana.charly.controlebackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientDTO {
    private Long id;
    private String nom;
    private String email;
    private LocalDateTime dateCreation;
    private LocalDateTime dateModification;
    private List<ContratAssuranceDTO> contrats;
}
