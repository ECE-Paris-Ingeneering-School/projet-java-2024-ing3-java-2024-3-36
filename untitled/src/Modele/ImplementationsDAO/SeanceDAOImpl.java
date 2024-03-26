package Modele.ImplementationsDAO;

import Modele.InterfaceDAO.SeanceDAO;
import Modele.Objets.Seance;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SeanceDAOImpl implements SeanceDAO {
    private String jdbcURL = "jdbc:mysql://localhost:3306/projetcinema";
    private String jdbcUsername = "root";
    private String jdbcPassword = "";

    private static final String INSERT_SEANCES_SQL = "INSERT INTO seances" + " (filmId, heure, salle) VALUES " + " (?, ?, ?);";
    private static final String SELECT_SEANCE_BY_ID = "select id, filmId, heure, salle from seances where id =?";
    private static final String SELECT_ALL_SEANCES = "select * from seances";
    private static final String DELETE_SEANCES_SQL = "delete from seances where id = ?;";
    private static final String UPDATE_SEANCES_SQL = "update seances set filmId = ?, heure = ?, salle =? where id = ?;";

    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    @Override
    public void ajouterSeance(Seance seance) throws Exception {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SEANCES_SQL)) {
            preparedStatement.setInt(1, seance.getFilmId());
            preparedStatement.setTimestamp(2, Timestamp.valueOf(seance.getHeure()));
            preparedStatement.setString(3, seance.getSalle());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    @Override
    public Seance trouverSeanceParId(int id) throws Exception {
        Seance seance = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_SEANCE_BY_ID);) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int filmId = rs.getInt("filmId");
                Timestamp heure = rs.getTimestamp("heure");
                String salle = rs.getString("salle");
                seance = new Seance(id, filmId, heure.toLocalDateTime(), salle);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return seance;
    }

    @Override
    public List<Seance> listerToutesLesSeances() throws Exception {
        List<Seance> seances = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_SEANCES);) {
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                int filmId = rs.getInt("filmId");
                Timestamp heure = rs.getTimestamp("heure");
                String salle = rs.getString("salle");
                seances.add(new Seance(id, filmId, heure.toLocalDateTime(), salle));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return seances;
    }

    @Override
    public void mettreAJourSeance(Seance seance) throws Exception {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_SEANCES_SQL);) {
            statement.setInt(1, seance.getFilmId());
            statement.setTimestamp(2, Timestamp.valueOf(seance.getHeure()));
            statement.setString(3, seance.getSalle());
            statement.setInt(4, seance.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    @Override
    public void supprimerSeance(int id) throws Exception {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_SEANCES_SQL);) {
            statement.setInt(1, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
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