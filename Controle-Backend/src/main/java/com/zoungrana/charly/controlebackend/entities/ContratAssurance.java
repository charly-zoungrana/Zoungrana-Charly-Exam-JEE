package com.zoungrana.charly.controlebackend.entities;

import com.zoungrana.charly.controlebackend.enums.StatutContrat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
//@DiscriminatorColumn(name = "type_contrat", discriminatorType = DiscriminatorType.STRING)
@Table(name = "contrats_assurance")
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class ContratAssurance {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_souscription")
    private LocalDate dateSouscription;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut", nullable = false)
    private StatutContrat statut;
    
    @Column(name = "date_validation")
    private LocalDate dateValidation;
    

    @Column(name = "montant_cotisation")
    private Double montantCotisation;

    @Column(name = "duree")
    private Integer duree;

    @Column(name = "taux_couverture")
    private Double tauxCouverture;
    
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
    
    @CreationTimestamp
    @Column(name = "date_creation", updatable = false)
    private LocalDateTime dateCreation;
    
    @UpdateTimestamp
    @Column(name = "date_modification")
    private LocalDateTime dateModification;
    
    @OneToMany(mappedBy = "contrat", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Paiement> paiements;
}
