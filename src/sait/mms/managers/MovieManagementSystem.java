package sait.mms.managers;

import sait.mms.drivers.MariaDBServer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class MovieManagementSystem {
    private final MariaDBServer server;
    private ResultSet results;
    private final Scanner input = new Scanner(System.in);

    public MovieManagementSystem() {
        server = new MariaDBServer();
    }

    public void displayMenu() throws SQLException {

            System.out.println("\nBig Lon's Movie Manager");
            System.out.println("1\tAdd New Movie");
            System.out.println("2\tPrint movies released in year");
            System.out.println("3\tPrint random list of movies");
            System.out.println("4\tDelete a movie");
            System.out.println("5\tExit");

            System.out.print("\nEnter an option: ");
            int choice = input.nextInt();
            switch (choice) {
                case 1:
                    addMovie();
                    displayMenu();
                    break;
                case 2:
                    printMoviesInYear();
                    displayMenu();
                    break;

                case 3:
                    printRandomMovies();
                    displayMenu();
                    break;

                case 4:
                    deleteMovie();
                    displayMenu();
                    break;

                case 5:
                    System.out.println("\nGoodbye!");
                    System.exit(0);
            }
        }


    private void addMovie() throws SQLException {
        Scanner input = new Scanner(System.in);
        server.connect();

        String update = "insert into movies values (?, ?, ?, ?);";

        try (PreparedStatement query = MariaDBServer.getConn().prepareStatement(update)){
            System.out.print("\nEnter unique ID: ");
            query.setInt(1, input.nextInt());
            System.out.print("Enter movie title: ");
            query.setString(3, input.next());
            System.out.print("Enter duration: ");
            query.setInt(2, input.nextInt());
            System.out.print("Enter year: ");
            query.setInt(4, input.nextInt());
            server.update(query);
        } catch (SQLException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }

        System.out.println("Added movie to database.");
        server.disconnect();
    }

    private void printMoviesInYear() throws SQLException {
        server.connect();
        String moviesInYear = "select duration, year, title from movies where year = ?;";

        try (PreparedStatement query = MariaDBServer.getConn().prepareStatement(moviesInYear)) {

            System.out.print("\nEnter in year: ");
            query.setInt(1, input.nextInt());
            results = server.get(query);
            displayResults(results);

        } catch (SQLException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }

        server.disconnect();
    }

    private void printRandomMovies() throws SQLException {

        server.connect();
        String movies = "select duration, year, title from movies order by rand() limit ?;";

        try (PreparedStatement query = MariaDBServer.getConn().prepareStatement(movies)) {
            System.out.print("\nEnter number of movies: ");
            query.setInt(1, input.nextInt());

            results = server.get(query);

            displayResults(results);
        } catch (SQLException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }

        server.disconnect();
    }

    private void deleteMovie() throws SQLException {

        server.connect();
        String delete = "delete from movies where id = ?";

        try (PreparedStatement query = MariaDBServer.getConn().prepareStatement(delete)) {
            System.out.print("\nEnter the movie ID that you want to delete: ");
            int id = input.nextInt();
            query.setInt(1, id);

            server.update(query);

            System.out.println("Movie " + id + " is deleted.");
        } catch (SQLException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }

        server.disconnect();
    }

    private void displayResults(ResultSet results) throws SQLException {
        int totalDuration = 0;
        System.out.println("\nMovie List");
        System.out.println("Duration\tYear\tTitle");

        while (results.next()) {
            totalDuration += results.getInt(1);
            System.out.printf("%d\t\t    %d\t%s\n",
                    results.getInt(1),
                    results.getInt(2),
                    results.getString(3));
        }

        System.out.println("\nTotal duration: " + totalDuration + " minutes");
    }
}
