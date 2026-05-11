package com.zoungrana.charly.controlebackend.web;

import com.zoungrana.charly.controlebackend.dto.*;
import com.zoungrana.charly.controlebackend.enums.StatutContrat;
import com.zoungrana.charly.controlebackend.services.ContratAssuranceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/contrats")
@CrossOrigin("*")
@Slf4j
@Tag(name = "Contract Management", description = "API pour la gestion des contrats d'assurance")
public class ContratAssuranceController {
    
    @Autowired
    private ContratAssuranceService contratService;
    
    // === CONTRATS AUTOMOBILE ===
    
    @Operation(summary = "Créer un contrat automobile", description = "Ajoute un nouveau contrat d'assurance automobile")
    @PostMapping("/auto")
    public ResponseEntity<ContratAutoDTO> createContratAuto(
             @RequestBody ContratAutoDTO contratAutoDTO) {
        log.info("Creating new auto contract for client: {}", contratAutoDTO.getClientId());
        ContratAutoDTO created = contratService.saveContratAuto(contratAutoDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }
    
    @Operation(summary = "Récupérer un contrat automobile", description = "Retourne les détails d'un contrat automobile spécifique")
    @GetMapping("/auto/{id}")
    public ResponseEntity<ContratAutoDTO> getContratAuto(@PathVariable Long id) {
        ContratAutoDTO contrat = contratService.getContratAuto(id);
        return ResponseEntity.ok(contrat);
    }
    
    @Operation(summary = "Lister tous les contrats automobiles", description = "Retourne la liste de tous les contrats automobiles")
    @GetMapping("/auto")
    public ResponseEntity<List<ContratAutoDTO>> getAllContratsAuto() {
        List<ContratAutoDTO> contrats = contratService.listContratsAuto();
        return ResponseEntity.ok(contrats);
    }
    
    @Operation(summary = "Mettre à jour un contrat automobile", description = "Modifie un contrat automobile existant")
    @PutMapping("/auto/{id}")
    public ResponseEntity<ContratAutoDTO> updateContratAuto(
            @PathVariable Long id,  @RequestBody ContratAutoDTO dto) {
        ContratAutoDTO updated = contratService.updateContratAuto(id, dto);
        return ResponseEntity.ok(updated);
    }
    
    @Operation(summary = "Supprimer un contrat automobile", description = "Supprime un contrat automobile")
    @DeleteMapping("/auto/{id}")
    public ResponseEntity<Void> deleteContratAuto(@PathVariable Long id) {
        contratService.deleteContratAuto(id);
        return ResponseEntity.noContent().build();
    }
    
    @Operation(summary = "Valider un contrat automobile", description = "Change le statut du contrat à VALIDÉ")
    @PostMapping("/auto/{id}/valider")
    public ResponseEntity<ContratAutoDTO> validerContratAuto(@PathVariable Long id) {
        ContratAutoDTO validated = contratService.validerContratAuto(id);
        return ResponseEntity.ok(validated);
    }
    
    @Operation(summary = "Résilier un contrat automobile", description = "Change le statut du contrat à RÉSILIÉ")
    @PostMapping("/auto/{id}/resilier")
    public ResponseEntity<ContratAutoDTO> resilierContratAuto(@PathVariable Long id) {
        ContratAutoDTO resiliated = contratService.resilierContratAuto(id);
        return ResponseEntity.ok(resiliated);
    }
    
    @Operation(summary = "Rechercher des contrats auto par marque", description = "Recherche des contrats par marque de véhicule")
    @GetMapping("/auto/search/marque")
    public ResponseEntity<List<ContratAutoDTO>> searchContratsAutoByMarque(
            @Parameter(description = "Marque du véhicule") @RequestParam String marque) {
        List<ContratAutoDTO> contrats = contratService.searchContratsAutoByMarque(marque);
        return ResponseEntity.ok(contrats);
    }
    
    @Operation(summary = "Rechercher des contrats auto par immatriculation", description = "Recherche des contrats par numéro d'immatriculation")
    @GetMapping("/auto/search/immatriculation")
    public ResponseEntity<List<ContratAutoDTO>> searchContratsAutoByImmatriculation(
            @Parameter(description = "Numéro d'immatriculation") @RequestParam String immatriculation) {
        List<ContratAutoDTO> contrats = contratService.searchContratsAutoByImmatriculation(immatriculation);
        return ResponseEntity.ok(contrats);
    }
    
    // === CONTRATS HABITATION ===
    
    @Operation(summary = "Créer un contrat habitation", description = "Ajoute un nouveau contrat d'assurance habitation")
    @PostMapping("/habitation")
    public ResponseEntity<ContratHabitationDTO> createContratHabitation(
             @RequestBody ContratHabitationDTO dto) {
        ContratHabitationDTO created = contratService.saveContratHabitation(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }
    
    @Operation(summary = "Récupérer un contrat habitation", description = "Retourne les détails d'un contrat habitation")
    @GetMapping("/habitation/{id}")
    public ResponseEntity<ContratHabitationDTO> getContratHabitation(@PathVariable Long id) {
        ContratHabitationDTO contrat = contratService.getContratHabitation(id);
        return ResponseEntity.ok(contrat);
    }
    
    @Operation(summary = "Lister tous les contrats habitation", description = "Retourne la liste de tous les contrats habitation")
    @GetMapping("/habitation")
    public ResponseEntity<List<ContratHabitationDTO>> getAllContratsHabitation() {
        List<ContratHabitationDTO> contrats = contratService.listContratsHabitation();
        return ResponseEntity.ok(contrats);
    }
    
    @Operation(summary = "Mettre à jour un contrat habitation", description = "Modifie un contrat habitation existant")
    @PutMapping("/habitation/{id}")
    public ResponseEntity<ContratHabitationDTO> updateContratHabitation(
            @PathVariable Long id,  @RequestBody ContratHabitationDTO dto) {
        ContratHabitationDTO updated = contratService.updateContratHabitation(id, dto);
        return ResponseEntity.ok(updated);
    }
    
    @Operation(summary = "Supprimer un contrat habitation", description = "Supprime un contrat habitation")
    @DeleteMapping("/habitation/{id}")
    public ResponseEntity<Void> deleteContratHabitation(@PathVariable Long id) {
        contratService.deleteContratHabitation(id);
        return ResponseEntity.noContent().build();
    }
    
    @Operation(summary = "Valider un contrat habitation", description = "Change le statut du contrat à VALIDÉ")
    @PostMapping("/habitation/{id}/valider")
    public ResponseEntity<ContratHabitationDTO> validerContratHabitation(@PathVariable Long id) {
        ContratHabitationDTO validated = contratService.validerContratHabitation(id);
        return ResponseEntity.ok(validated);
    }
    
    @Operation(summary = "Résilier un contrat habitation", description = "Change le statut du contrat à RÉSILIÉ")
    @PostMapping("/habitation/{id}/resilier")
    public ResponseEntity<ContratHabitationDTO> resilierContratHabitation(@PathVariable Long id) {
        ContratHabitationDTO resiliated = contratService.resilierContratHabitation(id);
        return ResponseEntity.ok(resiliated);
    }
    
    @Operation(summary = "Rechercher des contrats habitation par type de logement", description = "Recherche des contrats par type de logement")
    @GetMapping("/habitation/search/type-logement")
    public ResponseEntity<List<ContratHabitationDTO>> searchContratsHabitationByTypeLogement(
            @Parameter(description = "Type de logement") @RequestParam String typeLogement) {
        List<ContratHabitationDTO> contrats = contratService.searchContratsHabitationByTypeLogement(typeLogement);
        return ResponseEntity.ok(contrats);
    }
    
    @Operation(summary = "Rechercher des contrats habitation par adresse", description = "Recherche des contrats par adresse")
    @GetMapping("/habitation/search/adresse")
    public ResponseEntity<List<ContratHabitationDTO>> searchContratsHabitationByAdresse(
            @Parameter(description = "Adresse à rechercher") @RequestParam String adresse) {
        List<ContratHabitationDTO> contrats = contratService.searchContratsHabitationByAdresse(adresse);
        return ResponseEntity.ok(contrats);
    }
    
    // === CONTRATS SANTÉ ===
    
    @Operation(summary = "Créer un contrat santé", description = "Ajoute un nouveau contrat d'assurance santé")
    @PostMapping("/sante")
    public ResponseEntity<ContratSanteDTO> createContratSante(
             @RequestBody ContratSanteDTO dto) {
        ContratSanteDTO created = contratService.saveContratSante(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }
    
    @Operation(summary = "Récupérer un contrat santé", description = "Retourne les détails d'un contrat santé")
    @GetMapping("/sante/{id}")
    public ResponseEntity<ContratSanteDTO> getContratSante(@PathVariable Long id) {
        ContratSanteDTO contrat = contratService.getContratSante(id);
        return ResponseEntity.ok(contrat);
    }
    
    @Operation(summary = "Lister tous les contrats santé", description = "Retourne la liste de tous les contrats santé")
    @GetMapping("/sante")
    public ResponseEntity<List<ContratSanteDTO>> getAllContratsSante() {
        List<ContratSanteDTO> contrats = contratService.listContratsSante();
        return ResponseEntity.ok(contrats);
    }
    
    @Operation(summary = "Mettre à jour un contrat santé", description = "Modifie un contrat santé existant")
    @PutMapping("/sante/{id}")
    public ResponseEntity<ContratSanteDTO> updateContratSante(
            @PathVariable Long id,  @RequestBody ContratSanteDTO dto) {
        ContratSanteDTO updated = contratService.updateContratSante(id, dto);
        return ResponseEntity.ok(updated);
    }
    
    @Operation(summary = "Supprimer un contrat santé", description = "Supprime un contrat santé")
    @DeleteMapping("/sante/{id}")
    public ResponseEntity<Void> deleteContratSante(@PathVariable Long id) {
        contratService.deleteContratSante(id);
        return ResponseEntity.noContent().build();
    }
    
    @Operation(summary = "Valider un contrat santé", description = "Change le statut du contrat à VALIDÉ")
    @PostMapping("/sante/{id}/valider")
    public ResponseEntity<ContratSanteDTO> validerContratSante(@PathVariable Long id) {
        ContratSanteDTO validated = contratService.validerContratSante(id);
        return ResponseEntity.ok(validated);
    }
    
    @Operation(summary = "Résilier un contrat santé", description = "Change le statut du contrat à RÉSILIÉ")
    @PostMapping("/sante/{id}/resilier")
    public ResponseEntity<ContratSanteDTO> resilierContratSante(@PathVariable Long id) {
        ContratSanteDTO resiliated = contratService.resilierContratSante(id);
        return ResponseEntity.ok(resiliated);
    }
    
    @Operation(summary = "Rechercher des contrats santé par niveau de couverture", description = "Recherche des contrats par niveau de couverture")
    @GetMapping("/sante/search/niveau-couverture")
    public ResponseEntity<List<ContratSanteDTO>> searchContratsSanteByNiveauCouverture(
            @Parameter(description = "Niveau de couverture") @RequestParam String niveauCouverture) {
        List<ContratSanteDTO> contrats = contratService.searchContratsSanteByNiveauCouverture(niveauCouverture);
        return ResponseEntity.ok(contrats);
    }
    
    @Operation(summary = "Rechercher des contrats santé par nombre de personnes", description = "Recherche des contrats par nombre de personnes couvertes")
    @GetMapping("/sante/search/nb-personnes")
    public ResponseEntity<List<ContratSanteDTO>> searchContratsSanteByNbPersonnes(
            @Parameter(description = "Nombre de personnes") @RequestParam Integer nbPersonnes) {
        List<ContratSanteDTO> contrats = contratService.searchContratsSanteByNbPersonnes(nbPersonnes);
        return ResponseEntity.ok(contrats);
    }
    
    // === FONCTIONNALITÉS GÉNÉRALES ===
    
    @Operation(summary = "Lister tous les contrats", description = "Retourne tous les types de contrats")
    @GetMapping
    public ResponseEntity<List<ContratAssuranceDTO>> getAllContrats() {
        List<ContratAssuranceDTO> contrats = contratService.listAllContrats();
        return ResponseEntity.ok(contrats);
    }
    
    @Operation(summary = "Lister les contrats d'un client", description = "Retourne tous les contrats d'un client spécifique")
    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<ContratAssuranceDTO>> getContratsByClient(@PathVariable Long clientId) {
        List<ContratAssuranceDTO> contrats = contratService.getContratsByClientId(clientId);
        return ResponseEntity.ok(contrats);
    }
    
    @Operation(summary = "Lister les contrats par statut", description = "Filtre les contrats par statut")
    @GetMapping("/statut/{statut}")
    public ResponseEntity<List<ContratAssuranceDTO>> getContratsByStatut(@PathVariable StatutContrat statut) {
        List<ContratAssuranceDTO> contrats = contratService.getContratsByStatut(statut);
        return ResponseEntity.ok(contrats);
    }
    
    @Operation(summary = "Lister les contrats par période", description = "Filtre les contrats par date de souscription")
    @GetMapping("/periode")
    public ResponseEntity<List<ContratAssuranceDTO>> getContratsByDateRange(
            @Parameter(description = "Date de début") @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @Parameter(description = "Date de fin") @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        List<ContratAssuranceDTO> contrats = contratService.getContratsByDateSouscriptionBetween(startDate, endDate);
        return ResponseEntity.ok(contrats);
    }
    
    @Operation(summary = "Lister les contrats non validés", description = "Retourne les contrats en attente de validation")
    @GetMapping("/non-valides")
    public ResponseEntity<List<ContratAssuranceDTO>> getContratsNonValides() {
        List<ContratAssuranceDTO> contrats = contratService.getContratsNonValides();
        return ResponseEntity.ok(contrats);
    }
    
    @Operation(summary = "Lister les contrats validés", description = "Retourne les contrats actuellement valides")
    @GetMapping("/valides")
    public ResponseEntity<List<ContratAssuranceDTO>> getContratsValides() {
        List<ContratAssuranceDTO> contrats = contratService.getContratsValides();
        return ResponseEntity.ok(contrats);
    }
    
    @Operation(summary = "Lister les contrats résiliés", description = "Retourne les contrats résiliés")
    @GetMapping("/resilies")
    public ResponseEntity<List<ContratAssuranceDTO>> getContratsResilies() {
        List<ContratAssuranceDTO> contrats = contratService.getContratsResilies();
        return ResponseEntity.ok(contrats);
    }
    
    // === STATISTIQUES ===
    
    @Operation(summary = "Compter le nombre total de contrats", description = "Retourne le nombre total de contrats")
    @GetMapping("/stats/total")
    public ResponseEntity<Long> getTotalContrats() {
        long total = contratService.countTotalContrats();
        return ResponseEntity.ok(total);
    }
    
    @Operation(summary = "Compter les contrats par statut", description = "Retourne le nombre de contrats par statut")
    @GetMapping("/stats/statut/{statut}")
    public ResponseEntity<Long> getContratsCountByStatut(@PathVariable StatutContrat statut) {
        long count = contratService.countContratsByStatut(statut);
        return ResponseEntity.ok(count);
    }
    
    @Operation(summary = "Compter les contrats par type", description = "Retourne le nombre de contrats par type")
    @GetMapping("/stats/type/{type}")
    public ResponseEntity<Long> getContratsCountByType(@PathVariable String type) {
        long count = contratService.countContratsByType(type);
        return ResponseEntity.ok(count);
    }
    
    @Operation(summary = "Statistiques par type de contrat", description = "Retourne les statistiques détaillées par type")
    @GetMapping("/stats/par-type")
    public ResponseEntity<List<Object[]>> getStatistiquesParType() {
        List<Object[]> stats = contratService.getStatistiquesParType();
        return ResponseEntity.ok(stats);
    }
}
