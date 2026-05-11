package com.zoungrana.charly.controlebackend;

import com.zoungrana.charly.controlebackend.entities.*;
import com.zoungrana.charly.controlebackend.enums.*;
import com.zoungrana.charly.controlebackend.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

@SpringBootApplication
public class ControleBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(ControleBackendApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(
            ClientRepository clientRepository,
            ContratAutoRepository contratAutoRepository,
            ContratHabitationRepository contratHabitationRepository,
            ContratSanteRepository contratSanteRepository,
            PaiementRepository paiementRepository
    ) {
        return args -> {
            // Création des clients de test
            Stream.of("Charly", "Hugues", "Hassan", "Arouna", "Youssef")
                    .forEach(name -> {
                        Client client = new Client();
                        client.setNom(name);
                        client.setEmail(name.toLowerCase() + "@gmail.com");
                        clientRepository.save(client);
                    });

            // Récupérer tous les clients créés
            List<Client> clients = clientRepository.findAll();
            System.out.println("=== Clients créés ===");
            clients.forEach(client -> System.out.println("Client: " + client.getNom() + " - Email: " + client.getEmail()));

            Random random = new Random();

            // Créer des contrats d'assurance automobile pour chaque client
            clients.forEach(client -> {
                // Contrat Auto
                ContratAuto contratAuto = new ContratAuto();
                contratAuto.setDateSouscription(LocalDate.now().minusMonths(random.nextInt(12)));
                contratAuto.setStatut(random.nextBoolean() ? StatutContrat.VALIDE : StatutContrat.EN_COURS);
                contratAuto.setDateValidation(contratAuto.getStatut() == StatutContrat.VALIDE ? 
                    LocalDate.now().minusMonths(random.nextInt(6)) : null);
                contratAuto.setMontantCotisation(500 + random.nextDouble() * 1500);
                contratAuto.setDuree(12);
                contratAuto.setTauxCouverture(0.7 + random.nextDouble() * 0.3);
                contratAuto.setClient(client);
                contratAuto.setImmatriculation("AB-" + (1000 + random.nextInt(9000)) + "-CD");
                contratAuto.setMarque(getRandomMarque());
                contratAuto.setModele(getRandomModele());
                contratAutoRepository.save(contratAuto);

                // Contrat Habitation
                ContratHabitation contratHabitation = new ContratHabitation();
                contratHabitation.setDateSouscription(LocalDate.now().minusMonths(random.nextInt(12)));
                contratHabitation.setStatut(random.nextBoolean() ? StatutContrat.VALIDE : StatutContrat.EN_COURS);
                contratHabitation.setDateValidation(contratHabitation.getStatut() == StatutContrat.VALIDE ? 
                    LocalDate.now().minusMonths(random.nextInt(6)) : null);
                contratHabitation.setMontantCotisation(300 + random.nextDouble() * 800);
                contratHabitation.setDuree(12);
                contratHabitation.setTauxCouverture(0.6 + random.nextDouble() * 0.4);
                contratHabitation.setClient(client);
                contratHabitation.setTypeLogement(getRandomTypeLogement());
                contratHabitation.setAdresse(random.nextInt(100) + " Rue de la République, " + getRandomVille());
                contratHabitation.setSuperficie(50 + random.nextDouble() * 150);
                contratHabitationRepository.save(contratHabitation);

                // Contrat Santé
                ContratSante contratSante = new ContratSante();
                contratSante.setDateSouscription(LocalDate.now().minusMonths(random.nextInt(12)));
                contratSante.setStatut(random.nextBoolean() ? StatutContrat.VALIDE : StatutContrat.EN_COURS);
                contratSante.setDateValidation(contratSante.getStatut() == StatutContrat.VALIDE ? 
                    LocalDate.now().minusMonths(random.nextInt(6)) : null);
                contratSante.setMontantCotisation(200 + random.nextDouble() * 600);
                contratSante.setDuree(12);
                contratSante.setTauxCouverture(0.5 + random.nextDouble() * 0.5);
                contratSante.setClient(client);
                contratSante.setNiveauCouverture(getRandomNiveauCouverture());
                contratSante.setNbPersonnes(1 + random.nextInt(4));
                contratSanteRepository.save(contratSante);
            });

            // Créer des paiements pour chaque contrat
            List<ContratAuto> contratsAuto = contratAutoRepository.findAll();
            List<ContratHabitation> contratsHabitation = contratHabitationRepository.findAll();
            List<ContratSante> contratsSante = contratSanteRepository.findAll();

            // Paiements pour contrats auto
            contratsAuto.forEach(contrat -> {
                for (int i = 0; i < 6; i++) {
                    Paiement paiement = new Paiement();
                    paiement.setDatePaiement(LocalDateTime.now().minusMonths(6 - i));
                    paiement.setMontant(contrat.getMontantCotisation() / 12);
                    paiement.setTypePaiement(TypePaiement.MENSUALITE);
                    paiement.setContrat(contrat);
                    paiementRepository.save(paiement);
                }
            });

            // Paiements pour contrats habitation
            contratsHabitation.forEach(contrat -> {
                for (int i = 0; i < 4; i++) {
                    Paiement paiement = new Paiement();
                    paiement.setDatePaiement(LocalDateTime.now().minusMonths(12 - i * 3));
                    paiement.setMontant(contrat.getMontantCotisation() / 4);
                    paiement.setTypePaiement(TypePaiement.ANNUEL);
                    paiement.setContrat(contrat);
                    paiementRepository.save(paiement);
                }
            });

            // Paiements pour contrats santé
            contratsSante.forEach(contrat -> {
                for (int i = 0; i < 12; i++) {
                    Paiement paiement = new Paiement();
                    paiement.setDatePaiement(LocalDateTime.now().minusMonths(12 - i));
                    paiement.setMontant(contrat.getMontantCotisation() / 12);
                    paiement.setTypePaiement(TypePaiement.MENSUALITE);
                    paiement.setContrat(contrat);
                    paiementRepository.save(paiement);
                }
            });

            // Afficher les statistiques
            System.out.println("\n=== Statistiques des données créées ===");
            System.out.println("Nombre total de clients: " + clientRepository.count());
            System.out.println("Nombre de contrats auto: " + contratAutoRepository.count());
            System.out.println("Nombre de contrats habitation: " + contratHabitationRepository.count());
            System.out.println("Nombre de contrats santé: " + contratSanteRepository.count());
            System.out.println("Nombre total de paiements: " + paiementRepository.count());

            // Afficher quelques exemples de données
            System.out.println("\n=== Exemples de contrats créés ===");
            contratAutoRepository.findAll().stream().limit(2).forEach(contrat -> {
                System.out.println("Contrat Auto - ID: " + contrat.getId() + 
                    ", Client: " + contrat.getClient().getNom() +
                    ", Immatriculation: " + contrat.getImmatriculation() +
                    ", Marque: " + contrat.getMarque());
            });

            contratHabitationRepository.findAll().stream().limit(2).forEach(contrat -> {
                System.out.println("Contrat Habitation - ID: " + contrat.getId() +
                    ", Client: " + contrat.getClient().getNom() +
                    ", Type: " + contrat.getTypeLogement() +
                    ", Superficie: " + contrat.getSuperficie() + "m²");
            });

            contratSanteRepository.findAll().stream().limit(2).forEach(contrat -> {
                System.out.println("Contrat Santé - ID: " + contrat.getId() +
                    ", Client: " + contrat.getClient().getNom() +
                    ", Niveau: " + contrat.getNiveauCouverture() +
                    ", Personnes: " + contrat.getNbPersonnes());
            });
        };
    }

    // Méthodes utilitaires pour générer des données aléatoires
    private String getRandomMarque() {
        String[] marques = {"Renault", "Peugeot", "Citroën", "Volkswagen", "BMW", "Mercedes", "Audi", "Toyota"};
        return marques[new Random().nextInt(marques.length)];
    }

    private String getRandomModele() {
        String[] modeles = {"Clio", "208", "C3", "Golf", "Série 3", "Classe A", "A3", "Yaris"};
        return modeles[new Random().nextInt(modeles.length)];
    }

    private TypeLogement getRandomTypeLogement() {
        TypeLogement[] types = {TypeLogement.APPARTEMENT, TypeLogement.MAISON, TypeLogement.LOCAL_COMMERCIAL};
        return types[new Random().nextInt(types.length)];
    }

    private NiveauCouverture getRandomNiveauCouverture() {
        NiveauCouverture[] niveaux = {NiveauCouverture.BASIQUE, NiveauCouverture.INTERMEDIAIRE, NiveauCouverture.PREMIUM};
        return niveaux[new Random().nextInt(niveaux.length)];
    }

    private String getRandomVille() {
        String[] villes = {"Paris", "Lyon", "Marseille", "Toulouse", "Nice", "Nantes", "Strasbourg", "Bordeaux"};
        return villes[new Random().nextInt(villes.length)];
    }
}
