package Modele.InterfaceDAO;

import Modele.Objets.Seance;

import java.util.List;

public interface SeanceDAO {
    void ajouterSeance(Seance seance) throws Exception;
    Seance trouverSeanceParId(int id) throws Exception;
    List<Seance> listerToutesLesSeances() throws Exception;
    void mettreAJourSeance(Seance seance) throws Exception;
    void supprimerSeance(int id) throws Exception;
}
