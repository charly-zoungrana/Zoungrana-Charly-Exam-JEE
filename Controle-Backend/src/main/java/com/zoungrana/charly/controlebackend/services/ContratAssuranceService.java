package com.zoungrana.charly.controlebackend.services;

import com.zoungrana.charly.controlebackend.dtos.ContratAssuranceDTO;
import com.zoungrana.charly.controlebackend.dtos.ContratAutoDTO;
import com.zoungrana.charly.controlebackend.dtos.ContratHabitationDTO;
import com.zoungrana.charly.controlebackend.dtos.ContratSanteDTO;
import com.zoungrana.charly.controlebackend.enums.StatutContrat;

import java.time.LocalDate;
import java.util.List;

public interface ContratAssuranceService {
    
    // Contrats Automobile
    ContratAutoDTO saveContratAuto(ContratAutoDTO contratAutoDTO);
    ContratAutoDTO updateContratAuto(Long contratId, ContratAutoDTO contratAutoDTO);
    ContratAutoDTO getContratAuto(Long contratId);
    List<ContratAutoDTO> listContratsAuto();
    List<ContratAutoDTO> getContratsAutoByClientId(Long clientId);
    List<ContratAutoDTO> searchContratsAutoByMarque(String marque);
    List<ContratAutoDTO> searchContratsAutoByImmatriculation(String immatriculation);
    void deleteContratAuto(Long contratId);
    ContratAutoDTO validerContratAuto(Long contratId);
    ContratAutoDTO resilierContratAuto(Long contratId);
    
    // Contrats Habitation
    ContratHabitationDTO saveContratHabitation(ContratHabitationDTO contratHabitationDTO);
    ContratHabitationDTO updateContratHabitation(Long contratId, ContratHabitationDTO contratHabitationDTO);
    ContratHabitationDTO getContratHabitation(Long contratId);
    List<ContratHabitationDTO> listContratsHabitation();
    List<ContratHabitationDTO> getContratsHabitationByClientId(Long clientId);
    List<ContratHabitationDTO> searchContratsHabitationByTypeLogement(String typeLogement);
    List<ContratHabitationDTO> searchContratsHabitationByAdresse(String adresse);
    void deleteContratHabitation(Long contratId);
    ContratHabitationDTO validerContratHabitation(Long contratId);
    ContratHabitationDTO resilierContratHabitation(Long contratId);
    
    // Contrats Santé
    ContratSanteDTO saveContratSante(ContratSanteDTO contratSanteDTO);
    ContratSanteDTO updateContratSante(Long contratId, ContratSanteDTO contratSanteDTO);
    ContratSanteDTO getContratSante(Long contratId);
    List<ContratSanteDTO> listContratsSante();
    List<ContratSanteDTO> getContratsSanteByClientId(Long clientId);
    List<ContratSanteDTO> searchContratsSanteByNiveauCouverture(String niveauCouverture);
    List<ContratSanteDTO> searchContratsSanteByNbPersonnes(Integer nbPersonnes);
    void deleteContratSante(Long contratId);
    ContratSanteDTO validerContratSante(Long contratId);
    ContratSanteDTO resilierContratSante(Long contratId);
    
    // Fonctionnalités générales
    List<ContratAssuranceDTO> listAllContrats();
    List<ContratAssuranceDTO> getContratsByClientId(Long clientId);
    List<ContratAssuranceDTO> getContratsByStatut(StatutContrat statut);
    List<ContratAssuranceDTO> getContratsByDateSouscriptionBetween(LocalDate startDate, LocalDate endDate);
    List<ContratAssuranceDTO> getContratsNonValides();
    List<ContratAssuranceDTO> getContratsValides();
    List<ContratAssuranceDTO> getContratsResilies();
    
    // Statistiques
    long countTotalContrats();
    long countContratsByStatut(StatutContrat statut);
    long countContratsByType(String type);
    List<Object[]> getStatistiquesParType();
}
