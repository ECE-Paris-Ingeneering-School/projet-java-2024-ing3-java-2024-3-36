package Modele.InterfaceDAO;

import java.util.List;
import Modele.Objets.Film;

public interface FilmDAO {
    List<byte[]> recupererAfficheBytes();
    Film recupFilm(int id);
    List<Film> recupAllFilms();
    boolean ajouterFilm(Film film);
    boolean updateFilm(Film film);
    boolean supprimerFilm(int id);
}
