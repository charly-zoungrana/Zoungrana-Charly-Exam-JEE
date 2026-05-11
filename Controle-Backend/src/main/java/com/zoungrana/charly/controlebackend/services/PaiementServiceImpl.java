package com.zoungrana.charly.controlebackend.services;

import com.zoungrana.charly.controlebackend.dto.PaiementDTO;
import com.zoungrana.charly.controlebackend.entities.Paiement;
import com.zoungrana.charly.controlebackend.entities.ContratAssurance;
import com.zoungrana.charly.controlebackend.enums.TypePaiement;
import com.zoungrana.charly.controlebackend.mappers.PaiementMapper;
import com.zoungrana.charly.controlebackend.repository.PaiementRepository;
import com.zoungrana.charly.controlebackend.repository.ContratAssuranceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class PaiementServiceImpl implements PaiementService {
    
    @Autowired
    private PaiementRepository paiementRepository;
    
    @Autowired
    private ContratAssuranceRepository contratRepository;
    
    @Override
    public PaiementDTO savePaiement(PaiementDTO paiementDTO) {
        log.info("Saving new payment for contract: {}", paiementDTO.getContratId());
        Paiement paiement = PaiementMapper.fromPaiementDTO(paiementDTO);
        
        ContratAssurance contrat = contratRepository.findById(paiementDTO.getContratId())
                .orElseThrow(() -> new RuntimeException("Contract not found"));
        paiement.setContrat(contrat);
        
        Paiement savedPaiement = paiementRepository.save(paiement);
        return PaiementMapper.fromPaiement(savedPaiement);
    }
    
    @Override
    public PaiementDTO updatePaiement(Long paiementId, PaiementDTO paiementDTO) {
        log.info("Updating payment with ID: {}", paiementId);
        Paiement paiement = paiementRepository.findById(paiementId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        
        paiement.setDatePaiement(paiementDTO.getDatePaiement());
        paiement.setMontant(paiementDTO.getMontant());
        paiement.setTypePaiement(paiementDTO.getTypePaiement());
        
        if (paiementDTO.getContratId() != null && !paiementDTO.getContratId().equals(paiement.getContrat().getId())) {
            ContratAssurance contrat = contratRepository.findById(paiementDTO.getContratId())
                    .orElseThrow(() -> new RuntimeException("Contract not found"));
            paiement.setContrat(contrat);
        }
        
        Paiement updatedPaiement = paiementRepository.save(paiement);
        return PaiementMapper.fromPaiement(updatedPaiement);
    }
    
    @Override
    public PaiementDTO getPaiement(Long paiementId) {
        log.info("Getting payment with ID: {}", paiementId);
        Paiement paiement = paiementRepository.findById(paiementId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        return PaiementMapper.fromPaiement(paiement);
    }
    
    @Override
    public List<PaiementDTO> listPaiements() {
        log.info("Getting all payments");
        List<Paiement> paiements = paiementRepository.findAll();
        return paiements.stream()
                .map(PaiementMapper::fromPaiement)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<PaiementDTO> getPaiementsByContratId(Long contratId) {
        log.info("Getting payments for contract: {}", contratId);
        List<Paiement> paiements = paiementRepository.findByContratId(contratId);
        return paiements.stream()
                .map(PaiementMapper::fromPaiement)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<PaiementDTO> getPaiementsByClientId(Long clientId) {
        log.info("Getting payments for client: {}", clientId);
        List<Paiement> paiements = paiementRepository.findByClientId(clientId);
        return paiements.stream()
                .map(PaiementMapper::fromPaiement)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<PaiementDTO> getPaiementsByType(TypePaiement typePaiement) {
        log.info("Getting payments by type: {}", typePaiement);
        List<Paiement> paiements = paiementRepository.findByTypePaiement(typePaiement);
        return paiements.stream()
                .map(PaiementMapper::fromPaiement)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<PaiementDTO> getPaiementsByDateBetween(LocalDateTime startDate, LocalDateTime endDate) {
        log.info("Getting payments between {} and {}", startDate, endDate);
        List<Paiement> paiements = paiementRepository.findByDatePaiementBetween(startDate, endDate);
        return paiements.stream()
                .map(PaiementMapper::fromPaiement)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<PaiementDTO> getPaiementsByMontantBetween(Double minMontant, Double maxMontant) {
        log.info("Getting payments between {} and {}", minMontant, maxMontant);
        List<Paiement> paiements = paiementRepository.findByMontantBetween(minMontant, maxMontant);
        return paiements.stream()
                .map(PaiementMapper::fromPaiement)
                .collect(Collectors.toList());
    }
    
    @Override
    public void deletePaiement(Long paiementId) {
        log.info("Deleting payment with ID: {}", paiementId);
        Paiement paiement = paiementRepository.findById(paiementId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        paiementRepository.delete(paiement);
    }
    
    @Override
    public List<PaiementDTO> getPaiementsMensuelsByContrat(Long contratId) {
        return getPaiementsByContratIdAndType(contratId, TypePaiement.MENSUALITE);
    }
    
    @Override
    public List<PaiementDTO> getPaiementsAnnuelsByContrat(Long contratId) {
        return getPaiementsByContratIdAndType(contratId, TypePaiement.ANNUEL);
    }
    
    @Override
    public List<PaiementDTO> getPaiementsExceptionnelsByContrat(Long contratId) {
        return getPaiementsByContratIdAndType(contratId, TypePaiement.EXCEPTIONNEL);
    }
    
    private List<PaiementDTO> getPaiementsByContratIdAndType(Long contratId, TypePaiement typePaiement) {
        List<Paiement> paiements = paiementRepository.findByContratIdAndTypePaiement(contratId, typePaiement);
        return paiements.stream()
                .map(PaiementMapper::fromPaiement)
                .collect(Collectors.toList());
    }
    
    @Override
    public Double getTotalMontantByContratId(Long contratId) {
        return paiementRepository.getTotalMontantByContratId(contratId);
    }
    
    @Override
    public Double getTotalMontantByClientId(Long clientId) {
        List<Paiement> paiements = paiementRepository.findByClientId(clientId);
        return paiements.stream()
                .mapToDouble(Paiement::getMontant)
                .sum();
    }
    
    @Override
    public Double getTotalMontantByType(TypePaiement typePaiement) {
        List<Paiement> paiements = paiementRepository.findByTypePaiement(typePaiement);
        return paiements.stream()
                .mapToDouble(Paiement::getMontant)
                .sum();
    }
    
    @Override
    public Double getAverageMontantByType(TypePaiement typePaiement) {
        return paiementRepository.getAverageMontantByTypePaiement(typePaiement);
    }
    
    @Override
    public long countPaiementsByContratId(Long contratId) {
        return paiementRepository.countByContratId(contratId);
    }
    
    @Override
    public long countPaiementsByType(TypePaiement typePaiement) {
        return paiementRepository.countByTypePaiement(typePaiement);
    }
    
    @Override
    public List<Object[]> getRapportPaiementsParType() {
        List<Object[]> rapport = new java.util.ArrayList<>();
        
        for (TypePaiement type : TypePaiement.values()) {
            long count = countPaiementsByType(type);
            Double total = getTotalMontantByType(type);
            rapport.add(new Object[]{type.name(), count, total});
        }
        
        return rapport;
    }
    
    @Override
    public List<Object[]> getRapportPaiementsParMois() {
        List<Object[]> rapport = new java.util.ArrayList<>();
        
        // Pour les 12 derniers mois
        for (int i = 11; i >= 0; i--) {
            LocalDateTime startOfMonth = LocalDateTime.now().minusMonths(i).withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
            LocalDateTime endOfMonth = startOfMonth.plusMonths(1).minusSeconds(1);
            
            List<Paiement> paiements = paiementRepository.findByDatePaiementBetween(startOfMonth, endOfMonth);
            long count = paiements.size();
            Double total = paiements.stream().mapToDouble(Paiement::getMontant).sum();
            
            rapport.add(new Object[]{startOfMonth.getMonth().name() + " " + startOfMonth.getYear(), count, total});
        }
        
        return rapport;
    }
    
    @Override
    public List<Object[]> getRapportPaiementsParClient() {
        List<Object[]> rapport = new java.util.ArrayList<>();
        
        // Obtenir tous les paiements et les grouper par client
        paiementRepository.findAll().stream()
                .collect(Collectors.groupingBy(p -> p.getContrat().getClient()))
                .forEach((client, paiements) -> {
                    long count = paiements.size();
                    Double total = paiements.stream().mapToDouble(Paiement::getMontant).sum();
                    rapport.add(new Object[]{client.getNom(), count, total});
                });
        
        return rapport;
    }
}
