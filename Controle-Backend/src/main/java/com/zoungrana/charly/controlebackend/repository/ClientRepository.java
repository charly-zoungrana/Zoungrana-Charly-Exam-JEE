package com.zoungrana.charly.controlebackend.repository;

import com.zoungrana.charly.controlebackend.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long> {
    
    Client findByEmail(String email);
    
    boolean existsByEmail(String email);
    
    List<Client> findByNomContainingIgnoreCase(String nom);
    
    @Query("SELECT c FROM Client c LEFT JOIN FETCH c.contrats WHERE c.id = ?1")
    Client findByIdWithContrats(Long id);
    
    @Query("SELECT c FROM Client c LEFT JOIN FETCH c.contrats")
    List<Client> findAllWithContrats();
    
    @Query("SELECT COUNT(c) FROM Client c")
    long countTotalClients();
    
    @Query("SELECT COUNT(c) FROM Client c WHERE SIZE(c.contrats) > 0")
    long countClientsWithContrats();
}
