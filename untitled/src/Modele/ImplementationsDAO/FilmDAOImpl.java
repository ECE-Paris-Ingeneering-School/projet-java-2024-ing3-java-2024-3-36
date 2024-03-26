package Modele.ImplementationsDAO;

import Modele.InterfaceDAO.FilmDAO;
import Modele.Objets.Film;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Utils.ConnectionDatabase;

public class FilmDAOImpl implements FilmDAO {

    private static final String INSERT_FILMS_SQL = "INSERT INTO films" + " (titre, genre, duree, description, realisateur) VALUES " +
            " (?, ?, ?, ?, ?);";

    private static final String SELECT_FILM_BY_ID = "select id,titre,genre,duree,description,realisateur from films where id =?";
    private static final String SELECT_ALL_FILMS = "select * from films";
    private static final String DELETE_FILMS_SQL = "delete from films where id = ?;";
    private static final String UPDATE_FILMS_SQL = "update films set titre = ?, genre= ?, duree =?, description =?, realisateur=? where id = ?;";

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
                film = new Film(id, titre, genre, duree, description, realisateur);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return film;
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
                films.add(new Film(id, titre, genre, duree, description, realisateur));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return films;
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
            statement.setInt(6, film.getId());

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
