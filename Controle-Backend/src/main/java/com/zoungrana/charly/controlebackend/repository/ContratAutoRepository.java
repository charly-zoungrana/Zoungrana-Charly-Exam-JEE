package com.zoungrana.charly.controlebackend.repository;

import com.zoungrana.charly.controlebackend.entities.ContratAuto;
import com.zoungrana.charly.controlebackend.enums.StatutContrat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContratAutoRepository extends JpaRepository<ContratAuto, Long> {
    
    List<ContratAuto> findByClientId(Long clientId);
    
    List<ContratAuto> findByStatut(StatutContrat statut);
    
    List<ContratAuto> findByImmatriculationContainingIgnoreCase(String immatriculation);
    
    List<ContratAuto> findByMarqueContainingIgnoreCase(String marque);
    
    List<ContratAuto> findByModeleContainingIgnoreCase(String modele);
    
    List<ContratAuto> findByClientIdAndStatut(Long clientId, StatutContrat statut);
    
    @Query("SELECT c FROM ContratAuto c WHERE c.immatriculation = :immatriculation")
    ContratAuto findByImmatriculation(@Param("immatriculation") String immatriculation);
    
    @Query("SELECT c FROM ContratAuto c WHERE c.client.id = :clientId AND c.marque = :marque")
    List<ContratAuto> findByClientIdAndMarque(@Param("clientId") Long clientId, @Param("marque") String marque);
    
    @Query("SELECT c FROM ContratAuto c WHERE c.client.id = :clientId AND c.modele = :modele")
    List<ContratAuto> findByClientIdAndModele(@Param("clientId") Long clientId, @Param("modele") String modele);
    
    boolean existsByImmatriculation(String immatriculation);
    
    @Query("SELECT COUNT(c) FROM ContratAuto c WHERE c.client.id = :clientId")
    long countByClientId(@Param("clientId") Long clientId);
    
    @Query("SELECT COUNT(c) FROM ContratAuto c WHERE c.marque = :marque")
    long countByMarque(@Param("marque") String marque);
    
    @Query("SELECT c FROM ContratAuto c JOIN FETCH c.client LEFT JOIN FETCH c.paiements WHERE c.id = :id")
    ContratAuto findByIdWithClientAndPaiements(@Param("id") Long id);
    
    @Query("SELECT c FROM ContratAuto c JOIN FETCH c.client LEFT JOIN FETCH c.paiements WHERE c.client.id = :clientId")
    List<ContratAuto> findByClientIdWithClientAndPaiements(@Param("clientId") Long clientId);
}
