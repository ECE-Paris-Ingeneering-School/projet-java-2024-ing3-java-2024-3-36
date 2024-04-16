package Modele.InterfaceDAO;

import java.util.List;
import Modele.Objets.Film;

public interface FilmDAO {
    List<byte[]> recupererAfficheBytes();
    String recupererTitreParIndex(int index);
    Film recupFilm(int id);
    List<Film> recupAllFilms();
    boolean ajouterFilm(Film film);
    boolean updateFilm(Film film);
    boolean supprimerFilm(int id);
    int recupererIdFilmParIndex(int index);
}
