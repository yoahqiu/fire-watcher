/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.vanier.fire.util.email;

import edu.vanier.fire.model.PersonalInfo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Controller class that connects the info table with the application
 * @author Dam, Haowyeeh Brandon
 */
public class InfoController {

    private Connection connection;
    private PreparedStatement prepStatement;
    private Statement statement;
    private ResultSet resultSet;
    private final String tableName;

    public InfoController() {
        connection = null;
        statement = null;
        resultSet = null;
        tableName = "Info";
    }

    /**
     * method to update the new personal info
     * @param personalInfo 
     */
    public void insert(PersonalInfo personalInfo) {
        String sql = "INSERT INTO info(name,email,lat,lon) VALUES(?,?,?,?)";

        try {
            connection = ConnectionProviderDB.getConnection("firewatcher.db");
            prepStatement = connection.prepareStatement(sql);
            prepStatement.setString(1, personalInfo.getName());
            prepStatement.setString(2, personalInfo.getEmail());
            prepStatement.setDouble(3, personalInfo.getLat());
            prepStatement.setDouble(4, personalInfo.getLon());
            prepStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }

                if (prepStatement != null) {
                    prepStatement.close();
                }

            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }

    /**
     * method to convert the data into a model
     *
     * @return the converted data
     */
    public PersonalInfo getInfo() {
        PersonalInfo personalInfo = new PersonalInfo(null, null);
        try {
            connection = ConnectionProviderDB.getConnection("firewatcher.db");
            statement = connection.createStatement();
            statement.setQueryTimeout(30);
            String query = String.format("select * from %s", tableName);
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                personalInfo.setName(resultSet.getString("Name"));
                personalInfo.setEmail(resultSet.getString("Email"));
                personalInfo.setLat(resultSet.getDouble("Lat"));
                personalInfo.setLon(resultSet.getDouble("Lon"));
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

        return personalInfo;
    }

    /**
     * method to remove the previous personal info
     */
    public void delete() {
        String sql = "DELETE FROM " + tableName;

        try {
            connection = ConnectionProviderDB.getConnection("firewatcher.db");
            prepStatement = connection.prepareStatement(sql);
            prepStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }

                if (prepStatement != null) {
                    prepStatement.close();
                }

            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }
}
