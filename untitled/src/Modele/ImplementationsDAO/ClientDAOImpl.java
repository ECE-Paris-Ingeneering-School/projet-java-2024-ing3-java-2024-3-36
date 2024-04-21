package Modele.ImplementationsDAO;

import Modele.InterfaceDAO.ClientDAO;
import Modele.Objets.Client;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Utils.ConnectionDatabase;
public class ClientDAOImpl implements ClientDAO {
    private static final String INSERT_CLIENTS_SQL = "INSERT INTO clients" +
            " (nom, email, type,motDePasse) VALUES (?, ?, ?,?);";

    private static final String SELECT_CLIENT_BY_ID = "select id, nom, email, type,motDePasse from clients where id =?";
    private static final String SELECT_ALL_CLIENTS = "select * from clients";
    private static final String DELETE_CLIENTS_SQL = "delete from clients where id = ?;";
    private static final String UPDATE_CLIENTS_SQL = "update clients set nom = ?, email= ?, type =?,motDePasse=? where id = ?;";


    @Override
    public void ajouterClient(Client client) throws Exception {
        try (Connection connection = ConnectionDatabase.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CLIENTS_SQL)) {
            preparedStatement.setString(1, client.getNom());
            preparedStatement.setString(2, client.getEmail());
            preparedStatement.setString(3, client.getType());
            preparedStatement.setString(4,client.getMotDePasse());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }
    @Override
    public String trouverNomClientParEmailEtMotDePasse(String email, String motDePasse) {
        String nomClient = null;
        try (Connection connection = ConnectionDatabase.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT nom FROM clients WHERE email = ? AND motDePasse = ?")) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, motDePasse);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                nomClient = rs.getString("nom");
            } else {
                // Si aucun client n'est trouvé avec l'email et le mot de passe fournis, préciser qu'aucun client correspondant n'a été trouvé
                throw new Exception("Aucun client trouvé avec cet email et ce mot de passe.");
            }
        } catch (Exception e) {
            printSQLException((SQLException) e);
        }
        return nomClient;
    }
    @Override
    public int trouverIDParEmailEtMotDePasse(String email, String motDePasse) throws Exception {
        int userID = -1; // Valeur par défaut si l'utilisateur n'est pas trouvé

        try (Connection connection = ConnectionDatabase.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT id FROM clients WHERE email = ? AND motDePasse = ?")) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, motDePasse);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                userID = rs.getInt("id");
            }
        } catch (SQLException e) {
            printSQLException(e);
        }

        return userID;
    }
    @Override
    public Client trouverClientParId(int id) throws Exception {
        Client client = null;
        try (Connection connection = ConnectionDatabase.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CLIENT_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String nom = rs.getString("nom");
                String email = rs.getString("email");
                String type = rs.getString("type");
                String motDePasse = rs.getString("motDePasse");
                client = new Client(id, nom, email, type,motDePasse);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return client;
    }

    @Override
    public List<Client> listerTousLesClients() throws Exception {
        List<Client> clients = new ArrayList<>();
        try (Connection connection = ConnectionDatabase.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_CLIENTS)) {
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("nom");
                String email = rs.getString("email");
                String type = rs.getString("type");
                String motDePasse = rs.getString("motDePasse");
                clients.add(new Client(id, nom, email, type,motDePasse));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return clients;
    }

    @Override
    public void mettreAJourClient(Client client) throws Exception {
        try (Connection connection = ConnectionDatabase.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_CLIENTS_SQL)) {
            statement.setString(1, client.getNom());
            statement.setString(2, client.getEmail());
            statement.setString(3, client.getType());
            statement.setString(5, client.getMotDePasse());
            statement.setInt(4, client.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    @Override
    public void supprimerClient(int id) throws Exception {
        try (Connection connection = ConnectionDatabase.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_CLIENTS_SQL)) {
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
