package Modele.Objets;

public class Billet {
    private int id;
    private int seanceId;
    private int clientId;
    private double prix;
    private String categorie;

    public Billet(int id, int seanceId, int clientId, double prix) {
        this.id = id;
        this.seanceId = seanceId;
        this.clientId = clientId;
        this.prix = prix;
    }




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSeanceId() {
        return seanceId;
    }

    public void setSeanceId(int seanceId) {
        this.seanceId = seanceId;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    @Override
    public String toString() {
        return "Billet{" +
                "id=" + id +
                ", seanceId=" + seanceId +
                ", clientId=" + clientId +
                ", prix=" + prix +
                ", categorie='" + categorie + '\'' +
                '}';
    }
}

