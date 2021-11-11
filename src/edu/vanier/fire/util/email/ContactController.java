/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.vanier.fire.util.email;

import edu.vanier.fire.model.Contact;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

/**
 * Controller class that connects the Contact table with application
 * @author Dam, Haowyeeh Brandon
 */
public class ContactController {

    private Connection connection;
    private PreparedStatement prepStatement;
    private Statement statement;
    private ResultSet resultSet;
    private final String tableName;

    public ContactController() {
        connection = null;
        statement = null;
        resultSet = null;
        tableName = "Contact";
    }

    /**
     * method to add a new contact into the database
     * @param contact 
     */
    public void insert(Contact contact) {
        String sql = "INSERT INTO contact(name,email) VALUES(?,?)";

        try {
            connection = ConnectionProviderDB.getConnection("firewatcher.db");
            prepStatement = connection.prepareStatement(sql);
            prepStatement.setString(1, contact.getName());
            prepStatement.setString(2, contact.getEmail());
            prepStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }

                if (statement != null) {
                    statement.close();
                }

                if (resultSet != null) {
                    resultSet.close();
                }

            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }

    /**
     * method to convert db file to HashMap
     *
     * @return the converted data
     */
    public HashMap<Integer, Contact> getAllContacts() {
        HashMap<Integer, Contact> contactMap = new HashMap<>();

        try {
            connection = ConnectionProviderDB.getConnection("firewatcher.db");
            statement = connection.createStatement();
            statement.setQueryTimeout(30);
            String query = String.format("select * from %s", tableName);
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                contactMap.put(resultSet.getInt("ID"),
                        new Contact(resultSet.getString("Name"),
                                resultSet.getString("Email")));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }

                if (statement != null) {
                    statement.close();
                }

                if (resultSet != null) {
                    resultSet.close();
                }

            } catch (SQLException e) {
                System.out.println(e);
            }
        }

        return contactMap;
    }

    /**
     * method to remove a contact from the database
     * @param contact 
     */
    public void delete(Contact contact) {
        String sql = "DELETE FROM contact WHERE name = ?";

        try {
            connection = ConnectionProviderDB.getConnection("firewatcher.db");
            prepStatement = connection.prepareStatement(sql);
            prepStatement.setString(1, contact.getName());
            prepStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }

                if (statement != null) {
                    statement.close();
                }

                if (resultSet != null) {
                    resultSet.close();
                }

            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }
}
