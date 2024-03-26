package Modele.ImplementationsDAO;

import Modele.InterfaceDAO.ClientDAO;
import Modele.Objets.Client;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientDAOImpl implements ClientDAO {
    private String jdbcURL = "jdbc:mysql://localhost:3306/projetcinema";
    private String jdbcUsername = "root";
    private String jdbcPassword = "";

    private static final String INSERT_CLIENTS_SQL = "INSERT INTO clients" +
            " (nom, email, type) VALUES (?, ?, ?);";

    private static final String SELECT_CLIENT_BY_ID = "select id, nom, email, type from clients where id =?";
    private static final String SELECT_ALL_CLIENTS = "select * from clients";
    private static final String DELETE_CLIENTS_SQL = "delete from clients where id = ?;";
    private static final String UPDATE_CLIENTS_SQL = "update clients set nom = ?, email= ?, type =? where id = ?;";

    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException e) {
            printSQLException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    @Override
    public void ajouterClient(Client client) throws Exception {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CLIENTS_SQL)) {
            preparedStatement.setString(1, client.getNom());
            preparedStatement.setString(2, client.getEmail());
            preparedStatement.setString(3, client.getType());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    @Override
    public Client trouverClientParId(int id) throws Exception {
        Client client = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CLIENT_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String nom = rs.getString("nom");
                String email = rs.getString("email");
                String type = rs.getString("type");
                client = new Client(id, nom, email, type);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return client;
    }

    @Override
    public List<Client> listerTousLesClients() throws Exception {
        List<Client> clients = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_CLIENTS)) {
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("nom");
                String email = rs.getString("email");
                String type = rs.getString("type");
                clients.add(new Client(id, nom, email, type));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return clients;
    }

    @Override
    public void mettreAJourClient(Client client) throws Exception {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_CLIENTS_SQL)) {
            statement.setString(1, client.getNom());
            statement.setString(2, client.getEmail());
            statement.setString(3, client.getType());
            statement.setInt(4, client.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    @Override
    public void supprimerClient(int id) throws Exception {
        try (Connection connection = getConnection();
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
