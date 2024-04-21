package Controller;

import Modele.ImplementationsDAO.*;
import Modele.InterfaceDAO.*;

import java.util.Scanner;


public class Main {

    private static BilletDAO billetDAO = new BilletDAOImpl();
    private static ClientDAO clientDAO = new ClientDAOImpl();
    private static EmployeDAO employeDAO = new EmployeDAOImpl();
    private static FilmDAO filmDAO = new FilmDAOImpl();
    private static SeanceDAO seanceDAO = new SeanceDAOImpl();
    private static OffresDAO offresDAO = new OffresDAOImpl();
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {

        GererClientsPage.ConnexionPage connexionPage = new GererClientsPage.ConnexionPage(clientDAO,billetDAO,employeDAO,filmDAO,seanceDAO,offresDAO,scanner);
        connexionPage.setVisible(true);
    }
}