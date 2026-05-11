package com.zoungrana.charly.controlebackend.repository;

import com.zoungrana.charly.controlebackend.entities.ContratAssurance;
import com.zoungrana.charly.controlebackend.enums.StatutContrat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ContratAssuranceRepository extends JpaRepository<ContratAssurance, Long> {
    
    List<ContratAssurance> findByClientId(Long clientId);
    
    List<ContratAssurance> findByStatut(StatutContrat statut);
    
    List<ContratAssurance> findByDateSouscriptionBetween(LocalDate startDate, LocalDate endDate);
    
    List<ContratAssurance> findByClientIdAndStatut(Long clientId, StatutContrat statut);
    
    @Query("SELECT c FROM ContratAssurance c JOIN FETCH c.client LEFT JOIN FETCH c.paiements WHERE c.id = :id")
    ContratAssurance findByIdWithClientAndPaiements(@Param("id") Long id);
    
    @Query("SELECT c FROM ContratAssurance c JOIN FETCH c.client LEFT JOIN FETCH c.paiements WHERE c.client.id = :clientId")
    List<ContratAssurance> findByClientIdWithClientAndPaiements(@Param("clientId") Long clientId);
    
    @Query("SELECT COUNT(c) FROM ContratAssurance c WHERE c.client.id = :clientId AND c.statut = :statut")
    long countByClientIdAndStatut(@Param("clientId") Long clientId, @Param("statut") StatutContrat statut);
    
    @Query("SELECT COUNT(c) FROM ContratAssurance c")
    long countTotalContrats();
    
    @Query("SELECT COUNT(c) FROM ContratAssurance c WHERE c.statut = :statut")
    long countByStatut(@Param("statut") StatutContrat statut);
    
    @Query("SELECT c FROM ContratAssurance c WHERE c.dateValidation IS NULL")
    List<ContratAssurance> findContratsNonValides();
    
    @Query("SELECT c FROM ContratAssurance c WHERE c.dateValidation <= :date")
    List<ContratAssurance> findContratsValidesAvant(@Param("date") LocalDate date);
}
