package Modele.ImplementationsDAO;

import Modele.InterfaceDAO.OffresDAO;
import Modele.Objets.Offres;

import java.sql.*;
import Utils.ConnectionDatabase;


public class OffresDAOImpl implements OffresDAO {
    // Constantes pour les requêtes SQL
    private static final String SELECT_OFFRE_REGULIER_SQL = "select * FROM offres;";
    private static final String SELECT_OFFRE_SENIOR_SQL = "SELECT * FROM offres;";
    private static final String SELECT_OFFRE_ENFANT_SQL = "SELECT * FROM offres;";
    private static final String UPDATE_OFFRE_REGULIER_SQL = "UPDATE offres SET regulier = ?;";
    private static final String UPDATE_OFFRE_SENIOR_SQL = "UPDATE offres SET senior = ?;";
    private static final String UPDATE_OFFRE_ENFANT_SQL = "UPDATE offres SET enfant = ?;";

    @Override
    public double getOffreRegulier() throws Exception {
        double regulier = 0;
        try (Connection connection = ConnectionDatabase.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_OFFRE_REGULIER_SQL)) {
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                regulier = rs.getDouble("regulier");
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return regulier;
    }

    @Override
    public double getOffreSenior() throws Exception {
        double senior = 0;
        try (Connection connection = ConnectionDatabase.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_OFFRE_SENIOR_SQL)) {
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                senior = rs.getDouble("senior");
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return senior;
    }

    @Override
    public double getOffreEnfant() throws Exception {
        double enfant = 0;
        try (Connection connection = ConnectionDatabase.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_OFFRE_ENFANT_SQL)) {
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                enfant = rs.getDouble("enfant");
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return enfant;
    }

    private Offres trouverOffre(String sqlQuery) throws Exception {
        Offres offre = null;
        try (Connection connection = ConnectionDatabase.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                double regulier = rs.getDouble("regulier");
                double senior = rs.getDouble("senior");
                double enfant = rs.getDouble("enfant");
                offre = new Offres(regulier, senior, enfant);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return offre;
    }
    @Override
    public void modifierOffreRegulier(double nouvelleValeur) throws Exception {
        modifierOffre(UPDATE_OFFRE_REGULIER_SQL, nouvelleValeur);
    }
    @Override
    public void modifierOffreSenior(double nouvelleValeur) throws Exception {
        modifierOffre(UPDATE_OFFRE_SENIOR_SQL, nouvelleValeur);
    }
    @Override
    public void modifierOffreEnfant(double nouvelleValeur) throws Exception {
        modifierOffre(UPDATE_OFFRE_ENFANT_SQL, nouvelleValeur);
    }

    private void modifierOffre(String sqlQuery, double nouvelleValeur) throws Exception {
        try (Connection connection = ConnectionDatabase.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setDouble(1, nouvelleValeur);
            preparedStatement.executeUpdate();
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
