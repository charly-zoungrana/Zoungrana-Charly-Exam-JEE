package com.zoungrana.charly.controlebackend.entities;

import com.zoungrana.charly.controlebackend.enums.TypeLogement;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
//@DiscriminatorValue("HABITATION")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ContratHabitation extends ContratAssurance {
    

    @Enumerated(EnumType.STRING)
    @Column(name = "type_logement")
    private TypeLogement typeLogement;

    @Column(name = "adresse")
    private String adresse;

    @Column(name = "superficie")
    private Double superficie;
}
