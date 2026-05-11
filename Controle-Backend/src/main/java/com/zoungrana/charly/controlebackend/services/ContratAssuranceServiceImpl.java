package com.zoungrana.charly.controlebackend.services;

import com.zoungrana.charly.controlebackend.dto.*;
import com.zoungrana.charly.controlebackend.entities.*;
import com.zoungrana.charly.controlebackend.enums.StatutContrat;
import com.zoungrana.charly.controlebackend.mappers.ContratAssuranceMapper;
import com.zoungrana.charly.controlebackend.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class ContratAssuranceServiceImpl implements ContratAssuranceService {
    
    @Autowired
    private ContratAutoRepository contratAutoRepository;
    
    @Autowired
    private ContratHabitationRepository contratHabitationRepository;
    
    @Autowired
    private ContratSanteRepository contratSanteRepository;
    
    @Autowired
    private ContratAssuranceRepository contratAssuranceRepository;
    
    @Autowired
    private ClientRepository clientRepository;
    
    // Contrats Automobile
    @Override
    public ContratAutoDTO saveContratAuto(ContratAutoDTO contratAutoDTO) {
        log.info("Saving new auto contract for client: {}", contratAutoDTO.getClientId());
        ContratAuto contrat = ContratAssuranceMapper.fromContratAutoDTO(contratAutoDTO);
        
        Client client = clientRepository.findById(contratAutoDTO.getClientId())
                .orElseThrow(() -> new RuntimeException("Client not found"));
        contrat.setClient(client);
        
        ContratAuto savedContrat = contratAutoRepository.save(contrat);
        return ContratAssuranceMapper.fromContratAuto(savedContrat);
    }
    
    @Override
    public ContratAutoDTO updateContratAuto(Long contratId, ContratAutoDTO contratAutoDTO) {
        log.info("Updating auto contract with ID: {}", contratId);
        ContratAuto contrat = contratAutoRepository.findById(contratId)
                .orElseThrow(() -> new RuntimeException("Auto contract not found"));
        
        contrat.setDateSouscription(contratAutoDTO.getDateSouscription());
        contrat.setStatut(contratAutoDTO.getStatut());
        contrat.setDateValidation(contratAutoDTO.getDateValidation());
        contrat.setMontantCotisation(contratAutoDTO.getMontantCotisation());
        contrat.setDuree(contratAutoDTO.getDuree());
        contrat.setTauxCouverture(contratAutoDTO.getTauxCouverture());
        contrat.setImmatriculation(contratAutoDTO.getImmatriculation());
        contrat.setMarque(contratAutoDTO.getMarque());
        contrat.setModele(contratAutoDTO.getModele());
        
        ContratAuto updatedContrat = contratAutoRepository.save(contrat);
        return ContratAssuranceMapper.fromContratAuto(updatedContrat);
    }
    
    @Override
    public ContratAutoDTO getContratAuto(Long contratId) {
        log.info("Getting auto contract with ID: {}", contratId);
        ContratAuto contrat = contratAutoRepository.findById(contratId)
                .orElseThrow(() -> new RuntimeException("Auto contract not found"));
        return ContratAssuranceMapper.fromContratAuto(contrat);
    }
    
    @Override
    public List<ContratAutoDTO> listContratsAuto() {
        log.info("Getting all auto contracts");
        List<ContratAuto> contrats = contratAutoRepository.findAll();
        return contrats.stream()
                .map(ContratAssuranceMapper::fromContratAuto)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<ContratAutoDTO> getContratsAutoByClientId(Long clientId) {
        log.info("Getting auto contracts for client: {}", clientId);
        List<ContratAuto> contrats = contratAutoRepository.findByClientId(clientId);
        return contrats.stream()
                .map(ContratAssuranceMapper::fromContratAuto)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<ContratAutoDTO> searchContratsAutoByMarque(String marque) {
        log.info("Searching auto contracts by marque: {}", marque);
        List<ContratAuto> contrats = contratAutoRepository.findByMarqueContainingIgnoreCase(marque);
        return contrats.stream()
                .map(ContratAssuranceMapper::fromContratAuto)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<ContratAutoDTO> searchContratsAutoByImmatriculation(String immatriculation) {
        log.info("Searching auto contracts by immatriculation: {}", immatriculation);
        List<ContratAuto> contrats = contratAutoRepository.findByImmatriculationContainingIgnoreCase(immatriculation);
        return contrats.stream()
                .map(ContratAssuranceMapper::fromContratAuto)
                .collect(Collectors.toList());
    }
    
    @Override
    public void deleteContratAuto(Long contratId) {
        log.info("Deleting auto contract with ID: {}", contratId);
        ContratAuto contrat = contratAutoRepository.findById(contratId)
                .orElseThrow(() -> new RuntimeException("Auto contract not found"));
        contratAutoRepository.delete(contrat);
    }
    
    @Override
    public ContratAutoDTO validerContratAuto(Long contratId) {
        log.info("Validating auto contract with ID: {}", contratId);
        ContratAuto contrat = contratAutoRepository.findById(contratId)
                .orElseThrow(() -> new RuntimeException("Auto contract not found"));
        
        contrat.setStatut(StatutContrat.VALIDE);
        contrat.setDateValidation(LocalDate.now());
        
        ContratAuto updatedContrat = contratAutoRepository.save(contrat);
        return ContratAssuranceMapper.fromContratAuto(updatedContrat);
    }
    
    @Override
    public ContratAutoDTO resilierContratAuto(Long contratId) {
        log.info("Resiliating auto contract with ID: {}", contratId);
        ContratAuto contrat = contratAutoRepository.findById(contratId)
                .orElseThrow(() -> new RuntimeException("Auto contract not found"));
        
        contrat.setStatut(StatutContrat.RESILIE);
        
        ContratAuto updatedContrat = contratAutoRepository.save(contrat);
        return ContratAssuranceMapper.fromContratAuto(updatedContrat);
    }
    
    // Contrats Habitation
    @Override
    public ContratHabitationDTO saveContratHabitation(ContratHabitationDTO dto) {
        log.info("Saving new habitation contract");
        ContratHabitation contrat = ContratAssuranceMapper.fromContratHabitationDTO(dto);
        
        Client client = clientRepository.findById(dto.getClientId())
                .orElseThrow(() -> new RuntimeException("Client not found"));
        contrat.setClient(client);
        
        ContratHabitation saved = contratHabitationRepository.save(contrat);
        return ContratAssuranceMapper.fromContratHabitation(saved);
    }
    
    @Override
    public ContratHabitationDTO updateContratHabitation(Long contratId, ContratHabitationDTO dto) {
        ContratHabitation contrat = contratHabitationRepository.findById(contratId)
                .orElseThrow(() -> new RuntimeException("Habitation contract not found"));
        
        contrat.setDateSouscription(dto.getDateSouscription());
        contrat.setStatut(dto.getStatut());
        contrat.setDateValidation(dto.getDateValidation());
        contrat.setMontantCotisation(dto.getMontantCotisation());
        contrat.setDuree(dto.getDuree());
        contrat.setTauxCouverture(dto.getTauxCouverture());
        contrat.setTypeLogement(dto.getTypeLogement());
        contrat.setAdresse(dto.getAdresse());
        contrat.setSuperficie(dto.getSuperficie());
        
        ContratHabitation updated = contratHabitationRepository.save(contrat);
        return ContratAssuranceMapper.fromContratHabitation(updated);
    }
    
    @Override
    public ContratHabitationDTO getContratHabitation(Long contratId) {
        ContratHabitation contrat = contratHabitationRepository.findById(contratId)
                .orElseThrow(() -> new RuntimeException("Habitation contract not found"));
        return ContratAssuranceMapper.fromContratHabitation(contrat);
    }
    
    @Override
    public List<ContratHabitationDTO> listContratsHabitation() {
        List<ContratHabitation> contrats = contratHabitationRepository.findAll();
        return contrats.stream()
                .map(ContratAssuranceMapper::fromContratHabitation)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<ContratHabitationDTO> getContratsHabitationByClientId(Long clientId) {
        List<ContratHabitation> contrats = contratHabitationRepository.findByClientId(clientId);
        return contrats.stream()
                .map(ContratAssuranceMapper::fromContratHabitation)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<ContratHabitationDTO> searchContratsHabitationByTypeLogement(String typeLogement) {
        List<ContratHabitation> contrats = contratHabitationRepository.findByTypeLogement(
                com.zoungrana.charly.controlebackend.enums.TypeLogement.valueOf(typeLogement.toUpperCase()));
        return contrats.stream()
                .map(ContratAssuranceMapper::fromContratHabitation)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<ContratHabitationDTO> searchContratsHabitationByAdresse(String adresse) {
        List<ContratHabitation> contrats = contratHabitationRepository.findByAdresseContainingIgnoreCase(adresse);
        return contrats.stream()
                .map(ContratAssuranceMapper::fromContratHabitation)
                .collect(Collectors.toList());
    }
    
    @Override
    public void deleteContratHabitation(Long contratId) {
        ContratHabitation contrat = contratHabitationRepository.findById(contratId)
                .orElseThrow(() -> new RuntimeException("Habitation contract not found"));
        contratHabitationRepository.delete(contrat);
    }
    
    @Override
    public ContratHabitationDTO validerContratHabitation(Long contratId) {
        ContratHabitation contrat = contratHabitationRepository.findById(contratId)
                .orElseThrow(() -> new RuntimeException("Habitation contract not found"));
        
        contrat.setStatut(StatutContrat.VALIDE);
        contrat.setDateValidation(LocalDate.now());
        
        ContratHabitation updated = contratHabitationRepository.save(contrat);
        return ContratAssuranceMapper.fromContratHabitation(updated);
    }
    
    @Override
    public ContratHabitationDTO resilierContratHabitation(Long contratId) {
        ContratHabitation contrat = contratHabitationRepository.findById(contratId)
                .orElseThrow(() -> new RuntimeException("Habitation contract not found"));
        
        contrat.setStatut(StatutContrat.RESILIE);
        
        ContratHabitation updated = contratHabitationRepository.save(contrat);
        return ContratAssuranceMapper.fromContratHabitation(updated);
    }
    
    // Contrats Santé
    @Override
    public ContratSanteDTO saveContratSante(ContratSanteDTO dto) {
        log.info("Saving new santé contract");
        ContratSante contrat = ContratAssuranceMapper.fromContratSanteDTO(dto);
        
        Client client = clientRepository.findById(dto.getClientId())
                .orElseThrow(() -> new RuntimeException("Client not found"));
        contrat.setClient(client);
        
        ContratSante saved = contratSanteRepository.save(contrat);
        return ContratAssuranceMapper.fromContratSante(saved);
    }
    
    @Override
    public ContratSanteDTO updateContratSante(Long contratId, ContratSanteDTO dto) {
        ContratSante contrat = contratSanteRepository.findById(contratId)
                .orElseThrow(() -> new RuntimeException("Santé contract not found"));
        
        contrat.setDateSouscription(dto.getDateSouscription());
        contrat.setStatut(dto.getStatut());
        contrat.setDateValidation(dto.getDateValidation());
        contrat.setMontantCotisation(dto.getMontantCotisation());
        contrat.setDuree(dto.getDuree());
        contrat.setTauxCouverture(dto.getTauxCouverture());
        contrat.setNiveauCouverture(dto.getNiveauCouverture());
        contrat.setNbPersonnes(dto.getNbPersonnes());
        
        ContratSante updated = contratSanteRepository.save(contrat);
        return ContratAssuranceMapper.fromContratSante(updated);
    }
    
    @Override
    public ContratSanteDTO getContratSante(Long contratId) {
        ContratSante contrat = contratSanteRepository.findById(contratId)
                .orElseThrow(() -> new RuntimeException("Santé contract not found"));
        return ContratAssuranceMapper.fromContratSante(contrat);
    }
    
    @Override
    public List<ContratSanteDTO> listContratsSante() {
        List<ContratSante> contrats = contratSanteRepository.findAll();
        return contrats.stream()
                .map(ContratAssuranceMapper::fromContratSante)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<ContratSanteDTO> getContratsSanteByClientId(Long clientId) {
        List<ContratSante> contrats = contratSanteRepository.findByClientId(clientId);
        return contrats.stream()
                .map(ContratAssuranceMapper::fromContratSante)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<ContratSanteDTO> searchContratsSanteByNiveauCouverture(String niveauCouverture) {
        List<ContratSante> contrats = contratSanteRepository.findByNiveauCouverture(
                com.zoungrana.charly.controlebackend.enums.NiveauCouverture.valueOf(niveauCouverture.toUpperCase()));
        return contrats.stream()
                .map(ContratAssuranceMapper::fromContratSante)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<ContratSanteDTO> searchContratsSanteByNbPersonnes(Integer nbPersonnes) {
        List<ContratSante> contrats = contratSanteRepository.findByNbPersonnes(nbPersonnes);
        return contrats.stream()
                .map(ContratAssuranceMapper::fromContratSante)
                .collect(Collectors.toList());
    }
    
    @Override
    public void deleteContratSante(Long contratId) {
        ContratSante contrat = contratSanteRepository.findById(contratId)
                .orElseThrow(() -> new RuntimeException("Santé contract not found"));
        contratSanteRepository.delete(contrat);
    }
    
    @Override
    public ContratSanteDTO validerContratSante(Long contratId) {
        ContratSante contrat = contratSanteRepository.findById(contratId)
                .orElseThrow(() -> new RuntimeException("Santé contract not found"));
        
        contrat.setStatut(StatutContrat.VALIDE);
        contrat.setDateValidation(LocalDate.now());
        
        ContratSante updated = contratSanteRepository.save(contrat);
        return ContratAssuranceMapper.fromContratSante(updated);
    }
    
    @Override
    public ContratSanteDTO resilierContratSante(Long contratId) {
        ContratSante contrat = contratSanteRepository.findById(contratId)
                .orElseThrow(() -> new RuntimeException("Santé contract not found"));
        
        contrat.setStatut(StatutContrat.RESILIE);
        
        ContratSante updated = contratSanteRepository.save(contrat);
        return ContratAssuranceMapper.fromContratSante(updated);
    }
    
    // Fonctionnalités générales
    @Override
    public List<ContratAssuranceDTO> listAllContrats() {
        List<ContratAssurance> contrats = contratAssuranceRepository.findAll();
        return contrats.stream()
                .map(ContratAssuranceMapper::fromContratAssurance)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<ContratAssuranceDTO> getContratsByClientId(Long clientId) {
        List<ContratAssurance> contrats = contratAssuranceRepository.findByClientId(clientId);
        return contrats.stream()
                .map(ContratAssuranceMapper::fromContratAssurance)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<ContratAssuranceDTO> getContratsByStatut(StatutContrat statut) {
        List<ContratAssurance> contrats = contratAssuranceRepository.findByStatut(statut);
        return contrats.stream()
                .map(ContratAssuranceMapper::fromContratAssurance)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<ContratAssuranceDTO> getContratsByDateSouscriptionBetween(LocalDate startDate, LocalDate endDate) {
        List<ContratAssurance> contrats = contratAssuranceRepository.findByDateSouscriptionBetween(startDate, endDate);
        return contrats.stream()
                .map(ContratAssuranceMapper::fromContratAssurance)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<ContratAssuranceDTO> getContratsNonValides() {
        List<ContratAssurance> contrats = contratAssuranceRepository.findContratsNonValides();
        return contrats.stream()
                .map(ContratAssuranceMapper::fromContratAssurance)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<ContratAssuranceDTO> getContratsValides() {
        return getContratsByStatut(StatutContrat.VALIDE);
    }
    
    @Override
    public List<ContratAssuranceDTO> getContratsResilies() {
        return getContratsByStatut(StatutContrat.RESILIE);
    }
    
    // Statistiques
    @Override
    public long countTotalContrats() {
        return contratAssuranceRepository.countTotalContrats();
    }
    
    @Override
    public long countContratsByStatut(StatutContrat statut) {
        return contratAssuranceRepository.countByStatut(statut);
    }
    
    @Override
    public long countContratsByType(String type) {
        switch (type.toUpperCase()) {
            case "AUTO": return contratAutoRepository.count();
            case "HABITATION": return contratHabitationRepository.count();
            case "SANTE": return contratSanteRepository.count();
            default: return 0;
        }
    }
    
    @Override
    public List<Object[]> getStatistiquesParType() {
        List<Object[]> stats = new java.util.ArrayList<>();
        stats.add(new Object[]{"AUTO", contratAutoRepository.count()});
        stats.add(new Object[]{"HABITATION", contratHabitationRepository.count()});
        stats.add(new Object[]{"SANTE", contratSanteRepository.count()});
        return stats;
    }
}
