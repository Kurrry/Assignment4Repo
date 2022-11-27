package sait.mms.drivers;

import sait.mms.contacts.DatabaseDriver;

import java.sql.*;

public class MariaDBServer implements DatabaseDriver {

    private static final String SERVER = "localhost";
    private static final int PORT = 3306;
    private static final String DATABASE = "cprg251";
    private static final String USERNAME = "cprg251";
    private static final String PASSWORD = "password";
    private static Connection conn = null;
    private ResultSet results = null;

    /**
     * Connects to the database.
     *
     * @throws SQLException
     */
    @Override
    public void connect() throws SQLException {
        String url = String.format("jdbc:mariadb://%s:%d/%s?user=%s&password=%s",
                SERVER, PORT, DATABASE, USERNAME, PASSWORD);
        conn = DriverManager.getConnection(url);
    }

    /**
     * Performs a retrieval from the database (ie: SELECT)
     *
     * @param query Query to send to database.
     * @return Returns the results as a ResultSet
     * @throws SQLException Thrown if problem performing query.
     */
    @Override
    public ResultSet get(PreparedStatement query) throws SQLException {
        return results = query.executeQuery();
    }

    /**
     * Performs an update query (UPDATE, DELETE, DROP, etc.) on the database.
     *
     * @param query Query to send to database.
     * @throws SQLException
     */
    @Override
    public void update(PreparedStatement query) throws SQLException {
        query.execute();
    }

    /**
     * Disconnects from the database.
     *
     * @throws SQLException
     */
    @Override
    public void disconnect() throws SQLException {
        conn.close();
    }

    public static Connection getConn() {
        return conn;
    }
}
