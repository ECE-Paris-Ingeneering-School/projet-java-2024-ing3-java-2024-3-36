package Modele.InterfaceDAO;

import Modele.Objets.Employe;
import java.util.List;

public interface EmployeDAO {
    void ajouterEmploye(Employe employe) throws Exception;
    Employe trouverEmployeParId(int id) throws Exception;
    void mettreAJourEmploye(Employe employe) throws Exception;
    void supprimerEmploye(int id) throws Exception;
    List<Employe> listerTousLesEmployes() throws Exception;
}
