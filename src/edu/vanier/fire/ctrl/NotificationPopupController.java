/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.vanier.fire.ctrl;

import edu.vanier.fire.Loader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author brandon
 */
public class NotificationPopupController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    public void handleAddContact(ActionEvent event) throws IOException {
        Loader loader = new Loader();
        loader.setAsEditContact();
        Parent root = loader.getLoader().load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }
    
    @FXML
    public void handleEditInfo(ActionEvent event) throws IOException {
        Loader loader = new Loader();
        loader.setAsPersonalInfo();
        Parent root = loader.getLoader().load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }    
}
