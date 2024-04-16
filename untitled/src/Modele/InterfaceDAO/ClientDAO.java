package Modele.InterfaceDAO;

import Modele.Objets.Client;

import java.util.List;

public interface ClientDAO {
    void ajouterClient(Client client) throws Exception;
    Client trouverClientParId(int id) throws Exception;
    List<Client> listerTousLesClients() throws Exception;
    void mettreAJourClient(Client client) throws Exception;
    void supprimerClient(int id) throws Exception;
    String trouverNomClientParEmailEtMotDePasse(String email, String motDePasse);
    int trouverIDParEmailEtMotDePasse(String email, String motDePasse) throws Exception;
    String trouverEmailParId(int id) throws Exception;
}
