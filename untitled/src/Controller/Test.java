package Controller;

import Modele.ImplementationsDAO.*;
import Modele.InterfaceDAO.*;
import Modele.Objets.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class Test {
    private static BilletDAO billetDAO = new BilletDAOImpl();
    private static ClientDAO clientDAO = new ClientDAOImpl();
    private static EmployeDAO employeDAO = new EmployeDAOImpl();
    private static FilmDAO filmDAO = new FilmDAOImpl();
    private static SeanceDAO seanceDAO = new SeanceDAOImpl();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("Choisissez une option:");
            System.out.println("1. Gérer les billets");
            System.out.println("2. Gérer les clients");
            System.out.println("3. Gérer les employés");
            System.out.println("4. Gérer les films");
            System.out.println("5. Gérer les séances");
            System.out.println("6. Quitter");
            int choice = scanner.nextInt(); //test
            switch (choice) {
                case 1:
                    gererBillets(billetDAO,scanner);
                    break;
                case 2:
                    gererClients(clientDAO,scanner);
                    break;
                case 3:
                    gererEmployes(employeDAO, scanner);
                    break;
                case 4:
                    gererFilms(filmDAO, scanner);
                    break;
                case 6:
                    System.out.println("Au revoir !");
                    System.exit(0);
                default:
                    System.out.println("Choix non valide. Veuillez réessayer.");
            }
        }
    }

    private static void gererBillets(BilletDAO billetDAO, Scanner scanner) {
        boolean continuer = true;
        while (continuer) {
            System.out.println("\nGestion des billets");
            System.out.println("1. Ajouter un billet");
            System.out.println("2. Trouver un billet par ID");
            System.out.println("3. Lister tous les billets");
            System.out.println("4. Mettre à jour un billet");
            System.out.println("5. Supprimer un billet");
            System.out.println("6. Retour au menu principal");
            System.out.print("Votre choix : ");
            int choix = scanner.nextInt();
            scanner.nextLine(); // Consomme la ligne restante

            try {
                switch (choix) {
                    case 1: {
                        // Ajouter un billet
                        System.out.println("Ajout d'un billet.");

                        System.out.print("Entrez l'ID de la séance : ");
                        int seanceId = scanner.nextInt();
                        scanner.nextLine(); // Consomme la ligne restante
                        System.out.print("Entrez l'ID du client : ");
                        int clientId = scanner.nextInt();
                        scanner.nextLine(); // Consomme la ligne restante
                        System.out.print("Entrez le prix du billet : ");
                        double prix = scanner.nextDouble();
                        scanner.nextLine(); // Consomme la ligne restante
                        System.out.print("Entrez la catégorie du billet : ");
                        String categorie = scanner.nextLine();

                        Billet billet = new Billet(100,seanceId, clientId, prix, categorie);
                        billetDAO.ajouterBillet(billet);
                        System.out.println("Billet ajouté avec succès.");
                        break;
                    }
                    case 2: {
                        // Trouver un billet par ID
                        System.out.print("Entrez l'ID du billet : ");
                        int id = scanner.nextInt();
                        Billet billet = billetDAO.trouverBilletParId(id);
                        System.out.println(billet != null ? billet.toString() : "Billet non trouvé.");
                        break;
                    }
                    case 3: {
                        // Lister tous les billets
                        List<Billet> billets = billetDAO.listerTousLesBillets();
                        if (billets.isEmpty()) {
                            System.out.println("Aucun billet disponible.");
                        } else {
                            billets.forEach(b -> System.out.println(b.toString()));
                        }
                        break;
                    }
                    case 4: {
                        // Mettre à jour un billet
                        System.out.println("Mise à jour d'un billet.");
                        System.out.print("Entrez l'ID du billet à mettre à jour : ");
                        int id = scanner.nextInt();
                        // Trouver le billet existant
                        Billet billet = billetDAO.trouverBilletParId(id);
                        if (billet == null) {
                            System.out.println("Billet non trouvé.");
                            break;
                        }
                        billet.setId(id);
                        // Supposons que l'on puisse seulement mettre à jour le prix du billet
                        System.out.print("Entrez le nouveau prix du billet : ");
                        double nouveauPrix = scanner.nextDouble();
                        billet.setPrix(nouveauPrix);
                        billetDAO.mettreAJourBillet(billet);
                        System.out.println("Billet mis à jour avec succès.");
                        break;
                    }
                    case 5: {
                        // Supprimer un billet
                        System.out.print("Entrez l'ID du billet à supprimer : ");
                        int idSuppression = scanner.nextInt();
                        billetDAO.supprimerBillet(idSuppression);
                        System.out.println("Billet supprimé avec succès.");
                        break;
                    }
                    case 6:
                        continuer = false;
                        break;
                    default:
                        System.out.println("Choix invalide. Veuillez réessayer.");
                }
            } catch (Exception e) {
                System.out.println("Une erreur est survenue : " + e.getMessage());
                scanner.nextLine(); // Nettoie le buffer du scanner en cas d'entrées incorrectes
            }
        }
    }

    private static void gererClients(ClientDAO clientDAO, Scanner scanner) {
        boolean continuer = true;
        while (continuer) {
            System.out.println("\nGestion des clients");
            System.out.println("1. Ajouter un client");
            System.out.println("2. Trouver un client par ID");
            System.out.println("3. Lister tous les clients");
            System.out.println("4. Mettre à jour un client");
            System.out.println("5. Supprimer un client");
            System.out.println("6. Retour au menu principal");
            System.out.print("Votre choix : ");
            int choix = scanner.nextInt();
            scanner.nextLine(); // Consomme la ligne restante

            try {
                switch (choix) {
                    case 1: {
                        // Ajouter un client
                        System.out.println("Ajout d'un client.");

                        System.out.print("Entrez le nom du client : ");
                        String nom = scanner.nextLine();
                        System.out.print("Entrez l'email du client : ");
                        String email = scanner.nextLine();
                        System.out.print("Entrez le type du client : ");
                        String type = scanner.nextLine();

                        Client client = new Client(10,nom, email, type);
                        clientDAO.ajouterClient(client);
                        System.out.println("Client ajouté avec succès.");
                        break;
                    }
                    case 2: {
                        // Trouver un client par ID
                        System.out.print("Entrez l'ID du client : ");
                        int id = scanner.nextInt();
                        Client client = clientDAO.trouverClientParId(id);
                        System.out.println(client != null ? client.toString() : "Client non trouvé.");
                        break;
                    }
                    case 3: {
                        // Lister tous les clients
                        List<Client> clients = clientDAO.listerTousLesClients();
                        if (clients.isEmpty()) {
                            System.out.println("Aucun client disponible.");
                        } else {
                            clients.forEach(c -> System.out.println(c.toString()));
                        }
                        break;
                    }
                    case 4: {
                        // Mettre à jour un client
                        System.out.println("Mise à jour d'un client.");
                        System.out.print("Entrez l'ID du client à mettre à jour : ");
                        int id = scanner.nextInt();
                        // Trouver le client existant
                        Client client = clientDAO.trouverClientParId(id);
                        if (client == null) {
                            System.out.println("Client non trouvé.");
                            break;
                        }
                        scanner.nextLine(); // Consomme la ligne restante
                        System.out.print("Entrez le nouveau nom du client : ");
                        String nouveauNom = scanner.nextLine();
                        System.out.print("Entrez le nouvel email du client : ");
                        String nouvelEmail = scanner.nextLine();
                        System.out.print("Entrez le nouveau type du client : ");
                        String nouveauType = scanner.nextLine();
                        client.setNom(nouveauNom);
                        client.setEmail(nouvelEmail);
                        client.setType(nouveauType);
                        clientDAO.mettreAJourClient(client);
                        System.out.println("Client mis à jour avec succès.");
                        break;
                    }
                    case 5: {
                        // Supprimer un client
                        System.out.print("Entrez l'ID du client à supprimer : ");
                        int idSuppression = scanner.nextInt();
                        clientDAO.supprimerClient(idSuppression);
                        System.out.println("Client supprimé avec succès.");
                        break;
                    }
                    case 6:
                        continuer = false;
                        break;
                    default:
                        System.out.println("Choix invalide. Veuillez réessayer.");
                }
            } catch (Exception e) {
                System.out.println("Une erreur est survenue : " + e.getMessage());
                scanner.nextLine(); // Nettoie le buffer du scanner en cas d'entrées incorrectes
            }
        }
    }

    private static void gererEmployes(EmployeDAO employeDAO, Scanner scanner) {
        boolean continuer = true;
        while (continuer) {
            System.out.println("\nGestion des employés");
            System.out.println("1. Ajouter un employé");
            System.out.println("2. Trouver un employé par ID");
            System.out.println("3. Lister tous les employés");
            System.out.println("4. Mettre à jour un employé");
            System.out.println("5. Supprimer un employé");
            System.out.println("6. Retour au menu principal");
            System.out.print("Votre choix : ");
            int choix = scanner.nextInt();
            scanner.nextLine(); // Consomme la ligne restante

            try {
                switch (choix) {
                    case 1: {
                        // Ajouter un employé
                        System.out.println("Ajout d'un employé.");

                        System.out.print("Entrez le nom de l'employé : ");
                        String nom = scanner.nextLine();
                        System.out.print("Entrez la position de l'employé : ");
                        String position = scanner.nextLine();
                        System.out.print("Entrez l'email de l'employé : ");
                        String email = scanner.nextLine();
                        System.out.print("Entrez le mot de passe de l'employé : ");
                        String motDePasse = scanner.nextLine();

                        Employe employe = new Employe(0, nom, position, email, motDePasse);
                        employeDAO.ajouterEmploye(employe);
                        System.out.println("Employé ajouté avec succès.");
                        break;
                    }
                    case 2: {
                        // Trouver un employé par ID
                        System.out.print("Entrez l'ID de l'employé : ");
                        int id = scanner.nextInt();
                        Employe employe = employeDAO.trouverEmployeParId(id);
                        System.out.println(employe != null ? employe.toString() : "Employé non trouvé.");
                        break;
                    }
                    case 3: {
                        // Lister tous les employés
                        List<Employe> employes = employeDAO.listerTousLesEmployes();
                        if (employes.isEmpty()) {
                            System.out.println("Aucun employé disponible.");
                        } else {
                            employes.forEach(e -> System.out.println(e.toString()));
                        }
                        break;
                    }
                    case 4: {
                        // Mettre à jour un employé
                        System.out.println("Mise à jour d'un employé.");
                        System.out.print("Entrez l'ID de l'employé à mettre à jour : ");
                        int id = scanner.nextInt();
                        // Trouver l'employé existant
                        Employe employe = employeDAO.trouverEmployeParId(id);
                        if (employe == null) {
                            System.out.println("Employé non trouvé.");
                            break;
                        }
                        scanner.nextLine(); // Consomme la ligne restante
                        System.out.print("Entrez le nouveau nom de l'employé : ");
                        String nouveauNom = scanner.nextLine();
                        System.out.print("Entrez la nouvelle position de l'employé : ");
                        String nouvellePosition = scanner.nextLine();
                        System.out.print("Entrez le nouvel email de l'employé : ");
                        String nouvelEmail = scanner.nextLine();
                        System.out.print("Entrez le nouveau mot de passe de l'employé : ");
                        String nouveauMotDePasse = scanner.nextLine();

                        employe.setNom(nouveauNom);
                        employe.setPosition(nouvellePosition);
                        employe.setEmail(nouvelEmail);
                        employe.setMotDePasse(nouveauMotDePasse);
                        employeDAO.mettreAJourEmploye(employe);
                        System.out.println("Employé mis à jour avec succès.");
                        break;
                    }
                    case 5: {
                        // Supprimer un employé
                        System.out.print("Entrez l'ID de l'employé à supprimer : ");
                        int idSuppression = scanner.nextInt();
                        employeDAO.supprimerEmploye(idSuppression);
                        System.out.println("Employé supprimé avec succès.");
                        break;
                    }
                    case 6:
                        continuer = false;
                        break;
                    default:
                        System.out.println("Choix invalide. Veuillez réessayer.");
                }
            } catch (Exception e) {
                System.out.println("Une erreur est survenue : " + e.getMessage());
                scanner.nextLine(); // Nettoie le buffer du scanner en cas d'entrées incorrectes
            }
        }
    }

    private static void gererFilms(FilmDAO filmDAO, Scanner scanner) {
        boolean continuer = true;
        while (continuer) {
            System.out.println("\nGestion des films");
            System.out.println("1. Ajouter un film");
            System.out.println("2. Trouver un film par ID");
            System.out.println("3. Lister tous les films");
            System.out.println("4. Mettre à jour un film");
            System.out.println("5. Supprimer un film");
            System.out.println("6. Retour au menu principal");
            System.out.print("Votre choix : ");
            int choix = scanner.nextInt();
            scanner.nextLine(); // Consomme la ligne restante

            try {
                switch (choix) {
                    case 1: {
                        // Ajouter un film
                        System.out.println("Ajout d'un film.");

                        System.out.print("Entrez le titre du film : ");
                        String titre = scanner.nextLine();
                        System.out.print("Entrez le genre du film : ");
                        String genre = scanner.nextLine();
                        System.out.print("Entrez la durée du film : ");
                        int duree = scanner.nextInt();
                        scanner.nextLine(); // Consomme la ligne restante
                        System.out.print("Entrez la description du film : ");
                        String description = scanner.nextLine();
                        System.out.print("Entrez le réalisateur du film : ");
                        String realisateur = scanner.nextLine();

                        Film film = new Film(0, titre, genre, duree, description, realisateur);
                        filmDAO.ajouterFilm(film);
                        System.out.println("Film ajouté avec succès.");
                        break;
                    }
                    case 2: {
                        // Trouver un film par ID
                        System.out.print("Entrez l'ID du film : ");
                        int id = scanner.nextInt();
                        Film film = filmDAO.recupFilm(id);
                        System.out.println(film != null ? film.toString() : "Film non trouvé.");
                        break;
                    }
                    case 3: {
                        // Lister tous les films
                        List<Film> films = filmDAO.recupAllFilms();
                        if (films.isEmpty()) {
                            System.out.println("Aucun film disponible.");
                        } else {
                            films.forEach(f -> System.out.println(f.toString()));
                        }
                        break;
                    }
                    case 4: {
                        // Mettre à jour un film
                        System.out.println("Mise à jour d'un film.");
                        System.out.print("Entrez l'ID du film à mettre à jour : ");
                        int id = scanner.nextInt();
                        scanner.nextLine(); // Consomme la ligne restante
                        Film film = filmDAO.recupFilm(id);
                        if (film == null) {
                            System.out.println("Film non trouvé.");
                            break;
                        }
                        System.out.print("Entrez le nouveau titre du film : ");
                        String nouveauTitre = scanner.nextLine();
                        System.out.print("Entrez le nouveau genre du film : ");
                        String nouveauGenre = scanner.nextLine();
                        System.out.print("Entrez la nouvelle durée du film : ");
                        int nouvelleDuree = scanner.nextInt();
                        scanner.nextLine(); // Consomme la ligne restante
                        System.out.print("Entrez la nouvelle description du film : ");
                        String nouvelleDescription = scanner.nextLine();
                        System.out.print("Entrez le nouveau réalisateur du film : ");
                        String nouveauRealisateur = scanner.nextLine();

                        film.setTitre(nouveauTitre);
                        film.setGenre(nouveauGenre);
                        film.setDuree(nouvelleDuree);
                        film.setDescription(nouvelleDescription);
                        film.setRealisateur(nouveauRealisateur);

                        filmDAO.updateFilm(film);
                        System.out.println("Film mis à jour avec succès.");
                        break;
                    }
                    case 5: {
                        // Supprimer un film
                        System.out.print("Entrez l'ID du film à supprimer : ");
                        int idSuppression = scanner.nextInt();
                        filmDAO.supprimerFilm(idSuppression);
                        System.out.println("Film supprimé avec succès.");
                        break;
                    }
                    case 6:
                        continuer = false;
                        break;
                    default:
                        System.out.println("Choix invalide. Veuillez réessayer.");
                }
            } catch (Exception e) {
                System.out.println("Une erreur est survenue : " + e.getMessage());
                scanner.nextLine(); // Nettoie le buffer du scanner en cas d'entrées incorrectes
            }
        }
    }

    private static void gererSeances(SeanceDAO seanceDAO, Scanner scanner) {
        boolean continuer = true;
        while (continuer) {
            System.out.println("\nGestion des séances");
            System.out.println("1. Ajouter une séance");
            System.out.println("2. Trouver une séance par ID");
            System.out.println("3. Lister toutes les séances");
            System.out.println("4. Mettre à jour une séance");
            System.out.println("5. Supprimer une séance");
            System.out.println("6. Retour au menu principal");
            System.out.print("Votre choix : ");
            int choix = scanner.nextInt();
            scanner.nextLine(); // Consomme la ligne restante

            try {
                switch (choix) {
                    case 1: {
                        // Ajouter une séance
                        System.out.println("Ajout d'une séance.");

                        System.out.print("Entrez l'ID du film : ");
                        int filmId = scanner.nextInt();
                        scanner.nextLine(); // Consomme la ligne restante
                        System.out.print("Entrez la date et l'heure de la séance (AAAA-MM-JJ HH:MM:SS) : ");
                        String dateTimeString = scanner.nextLine();
                        LocalDateTime heure = LocalDateTime.parse(dateTimeString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                        System.out.print("Entrez le numéro de la salle : ");
                        String salle = scanner.nextLine();

                        Seance seance = new Seance(0, filmId, heure, salle);
                        seanceDAO.ajouterSeance(seance);
                        System.out.println("Séance ajoutée avec succès.");
                        break;
                    }
                    case 2: {
                        // Trouver une séance par ID
                        System.out.print("Entrez l'ID de la séance : ");
                        int id = scanner.nextInt();
                        Seance seance = seanceDAO.trouverSeanceParId(id);
                        System.out.println(seance != null ? seance.toString() : "Séance non trouvée.");
                        break;
                    }
                    case 3: {
                        // Lister toutes les séances
                        List<Seance> seances = seanceDAO.listerToutesLesSeances();
                        if (seances.isEmpty()) {
                            System.out.println("Aucune séance disponible.");
                        } else {
                            seances.forEach(s -> System.out.println(s.toString()));
                        }
                        break;
                    }
                    case 4: {
                        // Mettre à jour une séance
                        System.out.println("Mise à jour d'une séance.");
                        System.out.print("Entrez l'ID de la séance à mettre à jour : ");
                        int id = scanner.nextInt();
                        // Trouver la séance existante
                        Seance seance = seanceDAO.trouverSeanceParId(id);
                        if (seance == null) {
                            System.out.println("Séance non trouvée.");
                            break;
                        }
                        seance.setId(id);
                        System.out.print("Entrez le nouvel ID du film : ");
                        int filmId = scanner.nextInt();
                        seance.setFilmId(filmId);
                        scanner.nextLine(); // Consomme la ligne restante
                        System.out.print("Entrez la nouvelle date et heure de la séance (AAAA-MM-JJ HH:MM:SS) : ");
                        String dateTimeString = scanner.nextLine();
                        LocalDateTime heure = LocalDateTime.parse(dateTimeString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                        seance.setHeure(heure);
                        System.out.print("Entrez le nouveau numéro de salle : ");
                        String salle = scanner.nextLine();
                        seance.setSalle(salle);

                        seanceDAO.mettreAJourSeance(seance);
                        System.out.println("Séance mise à jour avec succès.");
                        break;
                    }
                    case 5: {
                        // Supprimer une séance
                        System.out.print("Entrez l'ID de la séance à supprimer : ");
                        int idSuppression = scanner.nextInt();
                        seanceDAO.supprimerSeance(idSuppression);
                        System.out.println("Séance supprimée avec succès.");
                        break;
                    }
                    case 6:
                        continuer = false;
                        break;
                    default:
                        System.out.println("Choix invalide. Veuillez réessayer.");
                }
            } catch (Exception e) {
                System.out.println("Une erreur est survenue : " + e.getMessage());
                scanner.nextLine(); // Nettoie le buffer du scanner en cas d'entrées incorrectes
            }
        }
    }



}
