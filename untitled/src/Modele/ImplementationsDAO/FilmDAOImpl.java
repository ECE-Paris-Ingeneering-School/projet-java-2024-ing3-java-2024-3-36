package Modele.ImplementationsDAO;

import Modele.InterfaceDAO.FilmDAO;
import Modele.Objets.Film;

import java.io.ByteArrayInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Utils.ConnectionDatabase;

public class FilmDAOImpl implements FilmDAO {

    // Requête SQL pour insérer un film avec l'attribut Affiche
    private static final String INSERT_FILMS_SQL = "INSERT INTO films (titre, genre, duree, description, realisateur, Affiche, url_ba) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_AFFICHE_BYTES = "SELECT Affiche FROM films";

    private static final String SELECT_FILM_BY_ID = "SELECT id, titre, genre, duree, description, realisateur, Affiche, url_ba FROM films WHERE id = ?";
    private static final String SELECT_ALL_FILMS = "SELECT id, titre, genre, duree, description, realisateur, Affiche, url_ba FROM films";
    private static final String DELETE_FILMS_SQL = "DELETE FROM films WHERE id = ?";
    private static final String UPDATE_FILMS_SQL = "UPDATE films SET titre = ?, genre = ?, duree = ?, description = ?, realisateur = ?, Affiche = ?, url_ba = ? WHERE id = ?";

    @Override
    public Film recupFilm(int id) {
        Film film = null;
        try (Connection connection = ConnectionDatabase.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_FILM_BY_ID);) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String titre = rs.getString("titre");
                String genre = rs.getString("genre");
                int duree = rs.getInt("duree");
                String description = rs.getString("description");
                String realisateur = rs.getString("realisateur");
                byte[] afficheBytes = rs.getBytes("Affiche");
                String url_ba = rs.getString("url_ba");
                film = new Film(id, titre, genre, duree, description, realisateur, afficheBytes, url_ba);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return film;
    }
    @Override
    public int recupererIdFilmParIndex(int index) {
        int filmId = -1; // Valeur par défaut si aucun film n'est trouvé
        try (Connection connection = ConnectionDatabase.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT id FROM films LIMIT ?, 1");) {
            preparedStatement.setInt(1, index);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                filmId = rs.getInt("id");
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return filmId;
    }

    @Override
    public String recupererTitreParIndex(int index) {
        String titre = null;
        try (Connection connection = ConnectionDatabase.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT titre FROM films LIMIT ?, 1");) {
            preparedStatement.setInt(1, index);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                titre = rs.getString("titre");
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return titre;
    }

    @Override
    public String trouverTitreParId(int id) throws Exception {
        String titre = null;
        String sql = "SELECT titre FROM films WHERE id = ?";

        try (Connection connection = ConnectionDatabase.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                titre = resultSet.getString("titre");
            }
        }
        return titre;
    }

    @Override
    public String trouverURLParTitre(String titre) throws Exception {
        String url_ba = null;
        String sql = "SELECT url_ba FROM films WHERE titre = ?";

        try (Connection connection = ConnectionDatabase.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, titre);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                url_ba = resultSet.getString("url_ba");
            }
        }
        return url_ba;
    }

    @Override
    public int recupererIdFilmParTitre(String titre) throws Exception {
        int id = -1;
        String sql = "SELECT id FROM films WHERE titre = ?";

        try (Connection connection = ConnectionDatabase.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, titre);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                id = resultSet.getInt("id");
            }
        }
        return id;
    }

    @Override
    public List<Film> recupAllFilms() {
        List<Film> films = new ArrayList<>();
        try (Connection connection = ConnectionDatabase.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_FILMS);) {
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String titre = rs.getString("titre");
                String genre = rs.getString("genre");
                int duree = rs.getInt("duree");
                String description = rs.getString("description");
                String realisateur = rs.getString("realisateur");
                byte[] afficheBytes = rs.getBytes("Affiche");
                String url_ba = rs.getString("url_ba");
                films.add(new Film(id, titre, genre, duree, description, realisateur, afficheBytes, url_ba));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return films;
    }
    @Override
    public List<byte[]> recupererAfficheBytes() {
        List<byte[]> affichesBytes = new ArrayList<>();
        try (Connection connection = ConnectionDatabase.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_AFFICHE_BYTES)) {

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                affichesBytes.add(rs.getBytes("Affiche"));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return affichesBytes;
    }

    @Override
    public boolean ajouterFilm(Film film) {
        boolean rowInserted = false;
        try (Connection connection = ConnectionDatabase.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_FILMS_SQL)) {
            preparedStatement.setString(1, film.getTitre());
            preparedStatement.setString(2, film.getGenre());
            preparedStatement.setInt(3, film.getDuree());
            preparedStatement.setString(4, film.getDescription());
            preparedStatement.setString(5, film.getRealisateur());
            preparedStatement.setBlob(6, new ByteArrayInputStream(film.getAffiche()));
            preparedStatement.setString(7, film.getUrl_ba());

            rowInserted = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            printSQLException(e);
        }
        return rowInserted;
    }

    @Override
    public boolean updateFilm(Film film) {
        boolean rowUpdated = false;
        try (Connection connection = ConnectionDatabase.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_FILMS_SQL);) {
            statement.setString(1, film.getTitre());
            statement.setString(2, film.getGenre());
            statement.setInt(3, film.getDuree());
            statement.setString(4, film.getDescription());
            statement.setString(5, film.getRealisateur());
            statement.setBlob(6, new ByteArrayInputStream(film.getAffiche()));
            statement.setString(7, film.getUrl_ba());
            statement.setInt(8, film.getId());

            rowUpdated = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            printSQLException(e);
        }
        return rowUpdated;
    }

    @Override
    public boolean supprimerFilm(int id) {
        boolean rowDeleted = false;
        try (Connection connection = ConnectionDatabase.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_FILMS_SQL);) {
            statement.setInt(1, id);
            rowDeleted = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            printSQLException(e);
        }
        return rowDeleted;
    }

    private void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}
