package Modele.Objets;

public class Offres {
    private double regulier;
    private double senior;
    private double enfant;

    // Constructeur
    public Offres(double regulier, double senior, double enfant) {
        this.regulier = regulier;
        this.senior = senior;
        this.enfant = enfant;
    }

    public double getRegulier() {
        return regulier;
    }

    public void setRegulier(double regulier) {
        this.regulier = regulier;
    }

    public double getSenior() {
        return senior;
    }

    public void setSenior(double senior) {
        this.senior = senior;
    }

    public double getEnfant() {
        return enfant;
    }

    public void setEnfant(double enfant) {
        this.enfant = enfant;
    }

    // Méthode toString pour l'affichage
    @Override
    public String toString() {
        return "Offre{" +
                "regulier=" + regulier +
                ", senior=" + senior +
                ", enfant=" + enfant +
                '}';
    }
}
