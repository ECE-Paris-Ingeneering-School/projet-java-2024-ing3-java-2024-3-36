package Modele.Objets;

public class Client {
    private int id;
    private String nom;
    private String email;
    private String type;
    private String etat;

    public Client(int id, String nom, String email, String type, String etat) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.type = type;
        this.etat = etat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String type) {
        this.etat = etat;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", email='" + email + '\'' +
                ", type='" + type + '\'' +
                ", etat='" + etat + '\'' +
                '}';
    }

}

