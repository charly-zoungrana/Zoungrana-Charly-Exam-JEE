package com.zoungrana.charly.controlebackend.web;

import com.zoungrana.charly.controlebackend.dto.PaiementDTO;
import com.zoungrana.charly.controlebackend.enums.TypePaiement;
import com.zoungrana.charly.controlebackend.services.PaiementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/paiements")
@CrossOrigin("*")
@Slf4j
@Tag(name = "Payment Management", description = "API pour la gestion des paiements")
public class PaiementController {
    
    @Autowired
    private PaiementService paiementService;
    
    @Operation(summary = "Créer un paiement", description = "Ajoute un nouveau paiement pour un contrat")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Paiement créé avec succès"),
        @ApiResponse(responseCode = "400", description = "Données invalides"),
        @ApiResponse(responseCode = "404", description = "Contrat non trouvé")
    })
    @PostMapping
    public ResponseEntity<PaiementDTO> createPaiement(
             @RequestBody PaiementDTO paiementDTO) {
        log.info("Creating new payment for contract: {}", paiementDTO.getContratId());
        PaiementDTO created = paiementService.savePaiement(paiementDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }
    
    @Operation(summary = "Récupérer un paiement", description = "Retourne les détails d'un paiement spécifique")
    @GetMapping("/{id}")
    public ResponseEntity<PaiementDTO> getPaiement(@PathVariable Long id) {
        PaiementDTO paiement = paiementService.getPaiement(id);
        return ResponseEntity.ok(paiement);
    }
    
    @Operation(summary = "Lister tous les paiements", description = "Retourne la liste de tous les paiements")
    @GetMapping
    public ResponseEntity<List<PaiementDTO>> getAllPaiements() {
        List<PaiementDTO> paiements = paiementService.listPaiements();
        return ResponseEntity.ok(paiements);
    }
    
    @Operation(summary = "Mettre à jour un paiement", description = "Modifie les informations d'un paiement existant")
    @PutMapping("/{id}")
    public ResponseEntity<PaiementDTO> updatePaiement(
            @PathVariable Long id,  @RequestBody PaiementDTO paiementDTO) {
        PaiementDTO updated = paiementService.updatePaiement(id, paiementDTO);
        return ResponseEntity.ok(updated);
    }
    
    @Operation(summary = "Supprimer un paiement", description = "Supprime un paiement du système")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaiement(@PathVariable Long id) {
        paiementService.deletePaiement(id);
        return ResponseEntity.noContent().build();
    }
    
    @Operation(summary = "Lister les paiements d'un contrat", description = "Retourne tous les paiements pour un contrat spécifique")
    @GetMapping("/contrat/{contratId}")
    public ResponseEntity<List<PaiementDTO>> getPaiementsByContrat(@PathVariable Long contratId) {
        List<PaiementDTO> paiements = paiementService.getPaiementsByContratId(contratId);
        return ResponseEntity.ok(paiements);
    }
    
    @Operation(summary = "Lister les paiements d'un client", description = "Retourne tous les paiements pour un client spécifique")
    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<PaiementDTO>> getPaiementsByClient(@PathVariable Long clientId) {
        List<PaiementDTO> paiements = paiementService.getPaiementsByClientId(clientId);
        return ResponseEntity.ok(paiements);
    }
    
    @Operation(summary = "Lister les paiements par type", description = "Filtre les paiements par type (MENSUALITE, ANNUEL, EXCEPTIONNEL)")
    @GetMapping("/type/{type}")
    public ResponseEntity<List<PaiementDTO>> getPaiementsByType(@PathVariable TypePaiement type) {
        List<PaiementDTO> paiements = paiementService.getPaiementsByType(type);
        return ResponseEntity.ok(paiements);
    }
    
    @Operation(summary = "Lister les paiements par période", description = "Filtre les paiements par plage de dates")
    @GetMapping("/periode")
    public ResponseEntity<List<PaiementDTO>> getPaiementsByDateRange(
            @Parameter(description = "Date de début") @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime startDate,
            @Parameter(description = "Date de fin") @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime endDate) {
        List<PaiementDTO> paiements = paiementService.getPaiementsByDateBetween(startDate, endDate);
        return ResponseEntity.ok(paiements);
    }
    
    @Operation(summary = "Lister les paiements par montant", description = "Filtre les paiements par plage de montants")
    @GetMapping("/montant-range")
    public ResponseEntity<List<PaiementDTO>> getPaiementsByMontantRange(
            @Parameter(description = "Montant minimum") @RequestParam Double minMontant,
            @Parameter(description = "Montant maximum") @RequestParam Double maxMontant) {
        List<PaiementDTO> paiements = paiementService.getPaiementsByMontantBetween(minMontant, maxMontant);
        return ResponseEntity.ok(paiements);
    }
    
    @Operation(summary = "Lister les mensualités d'un contrat", description = "Retourne uniquement les paiements mensuels d'un contrat")
    @GetMapping("/contrat/{contratId}/mensuels")
    public ResponseEntity<List<PaiementDTO>> getPaiementsMensuelsByContrat(@PathVariable Long contratId) {
        List<PaiementDTO> paiements = paiementService.getPaiementsMensuelsByContrat(contratId);
        return ResponseEntity.ok(paiements);
    }
    
    @Operation(summary = "Lister les paiements annuels d'un contrat", description = "Retourne uniquement les paiements annuels d'un contrat")
    @GetMapping("/contrat/{contratId}/annuels")
    public ResponseEntity<List<PaiementDTO>> getPaiementsAnnuelsByContrat(@PathVariable Long contratId) {
        List<PaiementDTO> paiements = paiementService.getPaiementsAnnuelsByContrat(contratId);
        return ResponseEntity.ok(paiements);
    }
    
    @Operation(summary = "Lister les paiements exceptionnels d'un contrat", description = "Retourne uniquement les paiements exceptionnels d'un contrat")
    @GetMapping("/contrat/{contratId}/exceptionnels")
    public ResponseEntity<List<PaiementDTO>> getPaiementsExceptionnelsByContrat(@PathVariable Long contratId) {
        List<PaiementDTO> paiements = paiementService.getPaiementsExceptionnelsByContrat(contratId);
        return ResponseEntity.ok(paiements);
    }
    
    // === STATISTIQUES ET AGRÉGATIONS ===
    
    @Operation(summary = "Total des paiements par contrat", description = "Calcule le montant total des paiements pour un contrat")
    @GetMapping("/contrat/{contratId}/total")
    public ResponseEntity<Double> getTotalPaiementsByContrat(@PathVariable Long contratId) {
        Double total = paiementService.getTotalMontantByContratId(contratId);
        return ResponseEntity.ok(total != null ? total : 0.0);
    }
    
    @Operation(summary = "Total des paiements par client", description = "Calcule le montant total des paiements pour un client")
    @GetMapping("/client/{clientId}/total")
    public ResponseEntity<Double> getTotalPaiementsByClient(@PathVariable Long clientId) {
        Double total = paiementService.getTotalMontantByClientId(clientId);
        return ResponseEntity.ok(total != null ? total : 0.0);
    }
    
    @Operation(summary = "Total des paiements par type", description = "Calcule le montant total des paiements par type")
    @GetMapping("/type/{type}/total")
    public ResponseEntity<Double> getTotalPaiementsByType(@PathVariable TypePaiement type) {
        Double total = paiementService.getTotalMontantByType(type);
        return ResponseEntity.ok(total != null ? total : 0.0);
    }
    
    @Operation(summary = "Moyenne des paiements par type", description = "Calcule le montant moyen des paiements par type")
    @GetMapping("/type/{type}/moyenne")
    public ResponseEntity<Double> getAveragePaiementsByType(@PathVariable TypePaiement type) {
        Double average = paiementService.getAverageMontantByType(type);
        return ResponseEntity.ok(average != null ? average : 0.0);
    }
    
    @Operation(summary = "Nombre de paiements par contrat", description = "Compte le nombre de paiements pour un contrat")
    @GetMapping("/contrat/{contratId}/count")
    public ResponseEntity<Long> getCountPaiementsByContrat(@PathVariable Long contratId) {
        Long count = paiementService.countPaiementsByContratId(contratId);
        return ResponseEntity.ok(count != null ? count : 0L);
    }
    
    @Operation(summary = "Nombre de paiements par type", description = "Compte le nombre de paiements par type")
    @GetMapping("/type/{type}/count")
    public ResponseEntity<Long> getCountPaiementsByType(@PathVariable TypePaiement type) {
        Long count = paiementService.countPaiementsByType(type);
        return ResponseEntity.ok(count != null ? count : 0L);
    }
    
    // === RAPPORTS ===
    
    @Operation(summary = "Rapport des paiements par type", description = "Génère un rapport détaillé des paiements par type")
    @GetMapping("/rapports/par-type")
    public ResponseEntity<List<Object[]>> getRapportPaiementsParType() {
        List<Object[]> rapport = paiementService.getRapportPaiementsParType();
        return ResponseEntity.ok(rapport);
    }
    
    @Operation(summary = "Rapport des paiements par mois", description = "Génère un rapport mensuel des paiements sur 12 mois")
    @GetMapping("/rapports/par-mois")
    public ResponseEntity<List<Object[]>> getRapportPaiementsParMois() {
        List<Object[]> rapport = paiementService.getRapportPaiementsParMois();
        return ResponseEntity.ok(rapport);
    }
    
    @Operation(summary = "Rapport des paiements par client", description = "Génère un rapport des paiements groupés par client")
    @GetMapping("/rapports/par-client")
    public ResponseEntity<List<Object[]>> getRapportPaiementsParClient() {
        List<Object[]> rapport = paiementService.getRapportPaiementsParClient();
        return ResponseEntity.ok(rapport);
    }
}
