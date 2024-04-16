package Modele.InterfaceDAO;

public interface OffresDAO {
    double getOffreRegulier() throws Exception;
    double getOffreSenior() throws Exception;
    double getOffreEnfant() throws Exception;
    void modifierOffreRegulier(double nouvelleValeur) throws Exception;
    void modifierOffreSenior(double nouvelleValeur) throws Exception;
    void modifierOffreEnfant(double nouvelleValeur) throws Exception;
}
