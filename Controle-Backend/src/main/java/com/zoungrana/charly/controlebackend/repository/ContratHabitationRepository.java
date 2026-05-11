package com.zoungrana.charly.controlebackend.repository;

import com.zoungrana.charly.controlebackend.entities.ContratHabitation;
import com.zoungrana.charly.controlebackend.enums.StatutContrat;
import com.zoungrana.charly.controlebackend.enums.TypeLogement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContratHabitationRepository extends JpaRepository<ContratHabitation, Long> {
    
    List<ContratHabitation> findByClientId(Long clientId);
    
    List<ContratHabitation> findByStatut(StatutContrat statut);
    
    List<ContratHabitation> findByTypeLogement(TypeLogement typeLogement);
    
    List<ContratHabitation> findByAdresseContainingIgnoreCase(String adresse);
    
    List<ContratHabitation> findBySuperficieBetween(Double minSuperficie, Double maxSuperficie);
    
    List<ContratHabitation> findByClientIdAndStatut(Long clientId, StatutContrat statut);
    
    List<ContratHabitation> findByClientIdAndTypeLogement(Long clientId, TypeLogement typeLogement);
    
    @Query("SELECT c FROM ContratHabitation c WHERE c.superficie >= :minSuperficie ORDER BY c.superficie ASC")
    List<ContratHabitation> findBySuperficieMin(@Param("minSuperficie") Double minSuperficie);
    
    @Query("SELECT c FROM ContratHabitation c WHERE c.client.id = :clientId AND c.superficie >= :minSuperficie")
    List<ContratHabitation> findByClientIdAndSuperficieMin(@Param("clientId") Long clientId, @Param("minSuperficie") Double minSuperficie);
    
    @Query("SELECT AVG(c.superficie) FROM ContratHabitation c WHERE c.typeLogement = :typeLogement")
    Double getAverageSuperficieByTypeLogement(@Param("typeLogement") TypeLogement typeLogement);
    
    @Query("SELECT COUNT(c) FROM ContratHabitation c WHERE c.client.id = :clientId")
    long countByClientId(@Param("clientId") Long clientId);
    
    @Query("SELECT COUNT(c) FROM ContratHabitation c WHERE c.typeLogement = :typeLogement")
    long countByTypeLogement(@Param("typeLogement") TypeLogement typeLogement);
    
    @Query("SELECT c FROM ContratHabitation c JOIN FETCH c.client LEFT JOIN FETCH c.paiements WHERE c.id = :id")
    ContratHabitation findByIdWithClientAndPaiements(@Param("id") Long id);
    
    @Query("SELECT c FROM ContratHabitation c JOIN FETCH c.client LEFT JOIN FETCH c.paiements WHERE c.client.id = :clientId")
    List<ContratHabitation> findByClientIdWithClientAndPaiements(@Param("clientId") Long clientId);
    
    @Query("SELECT c FROM ContratHabitation c WHERE c.adresse = :adresse")
    List<ContratHabitation> findByAdresseExact(@Param("adresse") String adresse);
}
