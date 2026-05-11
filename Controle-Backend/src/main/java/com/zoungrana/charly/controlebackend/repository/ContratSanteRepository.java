package com.zoungrana.charly.controlebackend.repository;

import com.zoungrana.charly.controlebackend.entities.ContratSante;
import com.zoungrana.charly.controlebackend.enums.NiveauCouverture;
import com.zoungrana.charly.controlebackend.enums.StatutContrat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContratSanteRepository extends JpaRepository<ContratSante, Long> {
    
    List<ContratSante> findByClientId(Long clientId);
    
    List<ContratSante> findByStatut(StatutContrat statut);
    
    List<ContratSante> findByNiveauCouverture(NiveauCouverture niveauCouverture);
    
    List<ContratSante> findByNbPersonnes(Integer nbPersonnes);
    
    List<ContratSante> findByNbPersonnesBetween(Integer minPersonnes, Integer maxPersonnes);
    
    List<ContratSante> findByClientIdAndStatut(Long clientId, StatutContrat statut);
    
    List<ContratSante> findByClientIdAndNiveauCouverture(Long clientId, NiveauCouverture niveauCouverture);
    
    @Query("SELECT c FROM ContratSante c WHERE c.nbPersonnes >= :minPersonnes ORDER BY c.nbPersonnes ASC")
    List<ContratSante> findByNbPersonnesMin(@Param("minPersonnes") Integer minPersonnes);
    
    @Query("SELECT c FROM ContratSante c WHERE c.client.id = :clientId AND c.nbPersonnes >= :minPersonnes")
    List<ContratSante> findByClientIdAndNbPersonnesMin(@Param("clientId") Long clientId, @Param("minPersonnes") Integer minPersonnes);
    
    @Query("SELECT AVG(c.nbPersonnes) FROM ContratSante c WHERE c.niveauCouverture = :niveauCouverture")
    Double getAverageNbPersonnesByNiveauCouverture(@Param("niveauCouverture") NiveauCouverture niveauCouverture);
    
    @Query("SELECT COUNT(c) FROM ContratSante c WHERE c.client.id = :clientId")
    long countByClientId(@Param("clientId") Long clientId);
    
    @Query("SELECT COUNT(c) FROM ContratSante c WHERE c.niveauCouverture = :niveauCouverture")
    long countByNiveauCouverture(@Param("niveauCouverture") NiveauCouverture niveauCouverture);
    
    @Query("SELECT COUNT(c) FROM ContratSante c WHERE c.client.id = :clientId AND c.nbPersonnes >= :minPersonnes")
    long countByClientIdAndNbPersonnesMin(@Param("clientId") Long clientId, @Param("minPersonnes") Integer minPersonnes);
    
    @Query("SELECT c FROM ContratSante c JOIN FETCH c.client LEFT JOIN FETCH c.paiements WHERE c.id = :id")
    ContratSante findByIdWithClientAndPaiements(@Param("id") Long id);
    
    @Query("SELECT c FROM ContratSante c JOIN FETCH c.client LEFT JOIN FETCH c.paiements WHERE c.client.id = :clientId")
    List<ContratSante> findByClientIdWithClientAndPaiements(@Param("clientId") Long clientId);
    
    @Query("SELECT c FROM ContratSante c WHERE c.niveauCouverture = :niveauCouverture AND c.nbPersonnes <= :maxPersonnes")
    List<ContratSante> findByNiveauCouvertureAndMaxPersonnes(@Param("niveauCouverture") NiveauCouverture niveauCouverture, @Param("maxPersonnes") Integer maxPersonnes);
}
