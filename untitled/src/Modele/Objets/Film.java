package Modele.Objets;

public class Film {
    private int id;
    private static String titre;
    private String genre;
    private int duree;
    private String description;
    private String realisateur;
    private byte[] affiche; // Nouvel attribut pour l'affiche du film

    public Film(int id, String titre, String genre, int duree, String description, String realisateur, byte[] affiche) {
        this.id = id;
        this.titre = titre;
        this.genre = genre;
        this.duree = duree;
        this.description = description;
        this.realisateur = realisateur;
        this.affiche = affiche;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public  String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRealisateur() {
        return realisateur;
    }

    public void setRealisateur(String realisateur) {
        this.realisateur = realisateur;
    }

    public byte[] getAffiche() {
        return affiche;
    }

    public void setAffiche(byte[] affiche) {
        this.affiche = affiche;
    }

    @Override
    public String toString() {
        return "Film{" +
                "id=" + id +
                ", titre='" + titre + '\'' +
                ", genre='" + genre + '\'' +
                ", duree=" + duree +
                ", description='" + description + '\'' +
                ", realisateur='" + realisateur + '\'' +
                '}';
    }
}
