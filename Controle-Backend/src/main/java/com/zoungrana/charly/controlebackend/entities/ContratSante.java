package com.zoungrana.charly.controlebackend.entities;

import com.zoungrana.charly.controlebackend.enums.NiveauCouverture;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("SANTE")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ContratSante extends ContratAssurance {

    @Enumerated(EnumType.STRING)
    @Column(name = "niveau_couverture")
    private NiveauCouverture niveauCouverture;

    @Column(name = "nb_personnes")
    private Integer nbPersonnes;
}
