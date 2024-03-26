package Modele.ImplementationsDAO;

import Modele.InterfaceDAO.EmployeDAO;
import Modele.Objets.Employe;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Utils.ConnectionDatabase;

public class EmployeDAOImpl implements EmployeDAO {

    private static final String INSERT_EMPLOYES_SQL = "INSERT INTO employes" + " (nom, position, email, motDePasse) VALUES " +
            " (?, ?, ?, ?);";

    private static final String SELECT_EMPLOYE_BY_ID = "select id, nom, position, email, motDePasse from employes where id =?";
    private static final String SELECT_ALL_EMPLOYES = "select * from employes";
    private static final String DELETE_EMPLOYES_SQL = "delete from employes where id = ?;";
    private static final String UPDATE_EMPLOYES_SQL = "update employes set nom = ?, position = ?, email = ?, motDePasse = ? where id = ?;";

    @Override
    public void ajouterEmploye(Employe employe) throws Exception {
        try (Connection connection = ConnectionDatabase.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_EMPLOYES_SQL)) {
            preparedStatement.setString(1, employe.getNom());
            preparedStatement.setString(2, employe.getPosition());
            preparedStatement.setString(3, employe.getEmail());
            preparedStatement.setString(4, employe.getMotDePasse());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
            throw new Exception("Error occurred while adding employee.", e);
        }
    }

    @Override
    public Employe trouverEmployeParId(int id) throws Exception {
        Employe employe = null;
        try (Connection connection = ConnectionDatabase.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_EMPLOYE_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String nom = rs.getString("nom");
                String position = rs.getString("position");
                String email = rs.getString("email");
                String motDePasse = rs.getString("motDePasse");
                employe = new Employe(id, nom, position, email, motDePasse);
            }
        } catch (SQLException e) {
            printSQLException(e);
            throw new Exception("Error occurred while finding employee by id.", e);
        }
        return employe;
    }

    @Override
    public List<Employe> listerTousLesEmployes() throws Exception {
        List<Employe> employes = new ArrayList<>();
        try (Connection connection = ConnectionDatabase.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_EMPLOYES)) {
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("nom");
                String position = rs.getString("position");
                String email = rs.getString("email");
                String motDePasse = rs.getString("motDePasse");
                employes.add(new Employe(id, nom, position, email, motDePasse));
            }
        } catch (SQLException e) {
            printSQLException(e);
            throw new Exception("Error occurred while listing all employees.", e);
        }
        return employes;
    }

    @Override
    public void mettreAJourEmploye(Employe employe) throws Exception {
        try (Connection connection = ConnectionDatabase.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_EMPLOYES_SQL)) {
            statement.setString(1, employe.getNom());
            statement.setString(2, employe.getPosition());
            statement.setString(3, employe.getEmail());
            statement.setString(4, employe.getMotDePasse());
            statement.setInt(5, employe.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
            throw new Exception("Error occurred while updating employee.", e);
        }
    }

    @Override
    public void supprimerEmploye(int id) throws Exception {
        try (Connection connection = ConnectionDatabase.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_EMPLOYES_SQL)) {
            statement.setInt(1, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
            throw new Exception("Error occurred while deleting employee.", e);
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
