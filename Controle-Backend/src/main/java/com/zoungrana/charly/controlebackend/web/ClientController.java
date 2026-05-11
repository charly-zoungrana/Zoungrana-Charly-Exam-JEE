package com.zoungrana.charly.controlebackend.web;

import com.zoungrana.charly.controlebackend.dto.ClientDTO;
import com.zoungrana.charly.controlebackend.services.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
@CrossOrigin("*")
@Slf4j
@Tag(name = "Client Management", description = "API pour la gestion des clients")
public class ClientController {
    
    @Autowired
    private ClientService clientService;
    
    @Operation(summary = "Créer un nouveau client", description = "Ajoute un nouveau client au système")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Client créé avec succès"),
        @ApiResponse(responseCode = "400", description = "Données invalides"),
        @ApiResponse(responseCode = "409", description = "Email déjà utilisé")
    })
    @PostMapping
    public ResponseEntity<ClientDTO> createClient(
            @RequestBody ClientDTO clientDTO) {
        log.info("Creating new client: {}", clientDTO.getEmail());
        ClientDTO createdClient = clientService.saveClient(clientDTO);
        return new ResponseEntity<>(createdClient, HttpStatus.CREATED);
    }
    
    @Operation(summary = "Récupérer un client par ID", description = "Retourne les détails d'un client spécifique")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Client trouvé"),
        @ApiResponse(responseCode = "404", description = "Client non trouvé")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> getClient(
            @Parameter(description = "ID du client") @PathVariable Long id) {
        log.info("Getting client with ID: {}", id);
        ClientDTO client = clientService.getClient(id);
        return ResponseEntity.ok(client);
    }
    
    @Operation(summary = "Lister tous les clients", description = "Retourne la liste de tous les clients")
    @ApiResponse(responseCode = "200", description = "Liste des clients récupérée avec succès")
    @GetMapping
    public ResponseEntity<List<ClientDTO>> getAllClients() {
        log.info("Getting all clients");
        List<ClientDTO> clients = clientService.listClients();
        return ResponseEntity.ok(clients);
    }
    
    @Operation(summary = "Mettre à jour un client", description = "Modifie les informations d'un client existant")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Client mis à jour"),
        @ApiResponse(responseCode = "404", description = "Client non trouvé"),
        @ApiResponse(responseCode = "400", description = "Données invalides")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ClientDTO> updateClient(
            @Parameter(description = "ID du client") @PathVariable Long id,
             @RequestBody ClientDTO clientDTO) {
        log.info("Updating client with ID: {}", id);
        ClientDTO updatedClient = clientService.updateClient(id, clientDTO);
        return ResponseEntity.ok(updatedClient);
    }
    
    @Operation(summary = "Supprimer un client", description = "Supprime un client du système")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Client supprimé"),
        @ApiResponse(responseCode = "404", description = "Client non trouvé")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(
            @Parameter(description = "ID du client") @PathVariable Long id) {
        log.info("Deleting client with ID: {}", id);
        clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }
    
    @Operation(summary = "Rechercher des clients par nom", description = "Recherche des clients contenant le mot-clé dans leur nom")
    @GetMapping("/search")
    public ResponseEntity<List<ClientDTO>> searchClients(
            @Parameter(description = "Mot-clé de recherche") @RequestParam String keyword) {
        log.info("Searching clients with keyword: {}", keyword);
        List<ClientDTO> clients = clientService.searchClientsByName(keyword);
        return ResponseEntity.ok(clients);
    }
    
    @Operation(summary = "Trouver un client par email", description = "Recherche un client par son adresse email")
    @GetMapping("/by-email")
    public ResponseEntity<ClientDTO> getClientByEmail(
            @Parameter(description = "Email du client") @RequestParam String email) {
        log.info("Finding client by email: {}", email);
        ClientDTO client = clientService.findByEmail(email);
        if (client != null) {
            return ResponseEntity.ok(client);
        }
        return ResponseEntity.notFound().build();
    }
    
    @Operation(summary = "Vérifier si un email existe", description = "Vérifie si un email est déjà utilisé")
    @GetMapping("/check-email")
    public ResponseEntity<Boolean> checkEmailExists(
            @Parameter(description = "Email à vérifier") @RequestParam String email) {
        boolean exists = clientService.existsByEmail(email);
        return ResponseEntity.ok(exists);
    }
    
    @Operation(summary = "Lister les clients avec leurs contrats", description = "Retourne les clients avec la liste de leurs contrats")
    @GetMapping("/with-contracts")
    public ResponseEntity<List<ClientDTO>> getClientsWithContracts() {
        log.info("Getting clients with their contracts");
        List<ClientDTO> clients = clientService.getClientsWithContrats();
        return ResponseEntity.ok(clients);
    }
}
