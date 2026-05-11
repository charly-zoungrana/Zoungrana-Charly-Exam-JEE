package com.zoungrana.charly.controlebackend.services;

import com.zoungrana.charly.controlebackend.dto.PaiementDTO;
import com.zoungrana.charly.controlebackend.enums.TypePaiement;

import java.time.LocalDateTime;
import java.util.List;

public interface PaiementService {
    
    PaiementDTO savePaiement(PaiementDTO paiementDTO);
    PaiementDTO updatePaiement(Long paiementId, PaiementDTO paiementDTO);
    PaiementDTO getPaiement(Long paiementId);
    List<PaiementDTO> listPaiements();
    List<PaiementDTO> getPaiementsByContratId(Long contratId);
    List<PaiementDTO> getPaiementsByClientId(Long clientId);
    List<PaiementDTO> getPaiementsByType(TypePaiement typePaiement);
    List<PaiementDTO> getPaiementsByDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    List<PaiementDTO> getPaiementsByMontantBetween(Double minMontant, Double maxMontant);
    void deletePaiement(Long paiementId);
    
    // Fonctionnalités spécifiques
    List<PaiementDTO> getPaiementsMensuelsByContrat(Long contratId);
    List<PaiementDTO> getPaiementsAnnuelsByContrat(Long contratId);
    List<PaiementDTO> getPaiementsExceptionnelsByContrat(Long contratId);
    
    // Statistiques et agrégations
    Double getTotalMontantByContratId(Long contratId);
    Double getTotalMontantByClientId(Long clientId);
    Double getTotalMontantByType(TypePaiement typePaiement);
    Double getAverageMontantByType(TypePaiement typePaiement);
    long countPaiementsByContratId(Long contratId);
    long countPaiementsByType(TypePaiement typePaiement);
    
    // Rapports
    List<Object[]> getRapportPaiementsParType();
    List<Object[]> getRapportPaiementsParMois();
    List<Object[]> getRapportPaiementsParClient();
}
