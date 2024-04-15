package Modele.Objets;

import java.time.LocalDateTime;

public class Seance {
    private int id;
    private int filmId;
    private LocalDateTime heure;
    private String salle;

    public Seance(int id, int filmId, LocalDateTime heure, String salle) {
        this.id = id;
        this.filmId = filmId;
        this.heure = heure;
        this.salle = salle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFilmId() {
        return filmId;
    }

    public void setFilmId(int filmId) {
        this.filmId = filmId;
    }

    public LocalDateTime getHeure() {
        return heure;
    }

    public void setHeure(LocalDateTime heure) {
        this.heure = heure;
    }

    public String getSalle() {
        return salle;
    }

    public void setSalle(String salle) {
        this.salle = salle;
    }

    @Override
    public String toString() {
        return "Seance{" +
                "id=" + id +
                ", filmId=" + filmId +
                ", heure=" + heure.toString() + // ou utilisez un formatter si vous voulez un format spécifique
                ", salle='" + salle + '\'' +
                '}';
    }


    public String getFilmTitre() {
        return Film.getTitre();
    }
}

