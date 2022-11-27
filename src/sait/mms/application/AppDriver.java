package sait.mms.application;

import sait.mms.managers.MovieManagementSystem;

import java.sql.SQLException;

public class AppDriver {
    public static void main(String[] args) {
        MovieManagementSystem mms = new MovieManagementSystem();

        try {
            mms.displayMenu();
        } catch (SQLException ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
    }
}
