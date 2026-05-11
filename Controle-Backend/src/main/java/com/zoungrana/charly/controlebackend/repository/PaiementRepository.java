package com.zoungrana.charly.controlebackend.repository;

import com.zoungrana.charly.controlebackend.entities.Paiement;
import com.zoungrana.charly.controlebackend.enums.TypePaiement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface PaiementRepository extends JpaRepository<Paiement, Long> {
    
    List<Paiement> findByContratId(Long contratId);
    
    List<Paiement> findByTypePaiement(TypePaiement typePaiement);
    
    List<Paiement> findByDatePaiementBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    List<Paiement> findByMontantBetween(Double minMontant, Double maxMontant);
    
    List<Paiement> findByContratIdAndTypePaiement(Long contratId, TypePaiement typePaiement);
    
    @Query("SELECT p FROM Paiement p WHERE p.contrat.client.id = :clientId")
    List<Paiement> findByClientId(@Param("clientId") Long clientId);
    
    @Query("SELECT SUM(p.montant) FROM Paiement p WHERE p.contrat.id = :contratId")
    Double getTotalMontantByContratId(@Param("contratId") Long contratId);
    
    @Query("SELECT SUM(p.montant) FROM Paiement p WHERE p.contrat.id = :contratId AND p.typePaiement = :typePaiement")
    Double getTotalMontantByContratIdAndTypePaiement(@Param("contratId") Long contratId, @Param("typePaiement") TypePaiement typePaiement);
    
    long countByContratId(Long contratId);
    
    @Query("SELECT p FROM Paiement p JOIN FETCH p.contrat c JOIN FETCH c.client WHERE p.id = :id")
    Paiement findByIdWithContratAndClient(@Param("id") Long id);
    
    long countByTypePaiement(TypePaiement typePaiement);
    
    @Query("SELECT AVG(p.montant) FROM Paiement p WHERE p.typePaiement = :typePaiement")
    Double getAverageMontantByTypePaiement(@Param("typePaiement") TypePaiement typePaiement);
}
