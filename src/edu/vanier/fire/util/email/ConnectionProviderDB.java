package edu.vanier.fire.util.email;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * A class that provides a method to connect to an SQLite database.
 * @author rabahs
 */
public class ConnectionProviderDB {

    /**
     * Opens a connection to an SQLite database.
     * 
     * @param databaseName the name of the SQLite database file (it can also be 
     * a relative path.  
     * @return a connection to an SQLite database.
     */
    public static Connection getConnection(String databaseName) {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection dbConnection = DriverManager.getConnection("jdbc:sqlite:data/" + databaseName);
            return dbConnection;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}
