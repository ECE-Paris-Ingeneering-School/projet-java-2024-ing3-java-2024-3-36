package Modele.InterfaceDAO;

import Modele.Objets.Billet;

import java.util.List;

public interface BilletDAO {
    void ajouterBillet(Billet billet) throws Exception;
    Billet trouverBilletParId(int id) throws Exception;
    List<Billet> listerTousLesBillets() throws Exception;
    void mettreAJourBillet(Billet billet) throws Exception;
    void supprimerBillet(int id) throws Exception;
    public List<Billet> listerBilletsParClientId(int clientId)throws Exception;
}
