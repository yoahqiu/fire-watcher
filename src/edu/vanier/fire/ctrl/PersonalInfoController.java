/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.vanier.fire.ctrl;

import edu.vanier.fire.Loader;
import edu.vanier.fire.model.PersonalInfo;
import edu.vanier.fire.util.email.InfoController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author brandon
 */
public class PersonalInfoController implements Initializable {

    @FXML
    private TextField txtName;
    @FXML
    private TextField txtEmail;
    @FXML
    private Label lblEmailNotValid;

    private InfoController infoController = infoController = new InfoController();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        PersonalInfo personalInfo = infoController.getInfo();
        if (personalInfo.getName() != null) {
            txtName.setText(personalInfo.getName());
        }
        if (personalInfo.getEmail() != null) {
            txtEmail.setText(personalInfo.getEmail());
        }
    }

    @FXML
    public void handleBack(ActionEvent event) throws IOException {
        Loader loader = new Loader();
        loader.setAsNotif();
        Parent root = loader.getLoader().load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

    @FXML
    public void handleSave(ActionEvent event) {
        String name = txtName.getText();
        String email = txtEmail.getText();
        PersonalInfo personalInfo;

        if (email.isEmpty() || !isEmailValid(email)) {
            System.out.println(email);
            System.out.println(name);
            lblEmailNotValid.setVisible(true);
        } else {
            lblEmailNotValid.setVisible(false);
            personalInfo = infoController.getInfo();
            personalInfo.setName(name);
            personalInfo.setEmail(email);
            infoController.delete();
            infoController.insert(personalInfo);
            System.out.println("Added Personal Info");
        }

    }

    @FXML
    public void handleSelectLocation(ActionEvent event) throws IOException {
        Loader loader = new Loader();
        loader.setAsSelectLocation();
        Parent root = loader.getLoader().load();
        Scene scene = new Scene(root);
        
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }
    
    /**
     * method to check the format of an email
     * @param email the email to check
     * @return if the email is formatted or not
     */
    public boolean isEmailValid(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }

}
