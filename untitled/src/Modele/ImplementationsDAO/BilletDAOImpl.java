package Modele.ImplementationsDAO;

import Modele.InterfaceDAO.BilletDAO;
import Modele.Objets.Billet;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Utils.ConnectionDatabase;

public class BilletDAOImpl implements BilletDAO {

    private static final String INSERT_BILLETS_SQL = "INSERT INTO billets" + " (seanceId, clientId, prix, categorie) VALUES " +
            " (?, ?, ?, ?);";

    private static final String SELECT_BILLET_BY_ID = "SELECT id, seanceId, clientId, prix, categorie FROM billets WHERE id = ?";
    private static final String SELECT_ALL_BILLETS = "SELECT * FROM billets";
    private static final String DELETE_BILLETS_SQL = "DELETE FROM billets WHERE id = ?;";
    private static final String UPDATE_BILLETS_SQL = "UPDATE billets SET seanceId = ?, clientId = ?, prix = ?, categorie = ? WHERE id = ?;";


    @Override
    public void ajouterBillet(Billet billet) throws Exception {
        try (Connection connection = ConnectionDatabase.getConnection();
             // Notez l'utilisation de Statement.RETURN_GENERATED_KEYS pour pouvoir récupérer les clés générées
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_BILLETS_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, billet.getSeanceId());
            preparedStatement.setInt(2, billet.getClientId());
            preparedStatement.setDouble(3, billet.getPrix());
            preparedStatement.setString(4, billet.getCategorie());

            int affectedRows = preparedStatement.executeUpdate();

            // Vérifiez que l'insertion a bien fonctionné
            if (affectedRows == 0) {
                throw new SQLException("La création du billet a échoué, aucune ligne affectée.");
            }

            // Récupérez l'ID généré pour le billet
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    // Assignez l'ID généré à l'objet billet
                    billet.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("La création du billet a échoué, aucun ID obtenu.");
                }
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    @Override
    public Billet trouverBilletParId(int id) throws Exception {
        Billet billet = null;
        try (Connection connection = ConnectionDatabase.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BILLET_BY_ID)) {
            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int seanceId = rs.getInt("seanceId");
                int clientId = rs.getInt("clientId");
                double prix = rs.getDouble("prix");
                String categorie = rs.getString("categorie");
                billet = new Billet(id, seanceId, clientId, prix, categorie);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return billet;
    }

    @Override
    public List<Billet> listerTousLesBillets() throws Exception {
        List<Billet> billets = new ArrayList<>();
        try (Connection connection = ConnectionDatabase.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_BILLETS)) {
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                int seanceId = rs.getInt("seanceId");
                int clientId = rs.getInt("clientId");
                double prix = rs.getDouble("prix");
                String categorie = rs.getString("categorie");
                billets.add(new Billet(id,seanceId, clientId, prix, categorie));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return billets;
    }

    @Override
    public void mettreAJourBillet(Billet billet) throws Exception {
        try (Connection connection = ConnectionDatabase.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_BILLETS_SQL)) {
            statement.setInt(1, billet.getSeanceId());
            statement.setInt(2, billet.getClientId());
            statement.setDouble(3, billet.getPrix());
            statement.setString(4, billet.getCategorie());
            statement.setInt(5, billet.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    @Override
    public void supprimerBillet(int id) throws Exception {
        try (Connection connection = ConnectionDatabase.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_BILLETS_SQL)) {
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