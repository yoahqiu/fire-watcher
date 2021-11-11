/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.vanier.fire.ctrl;

import edu.vanier.fire.Loader;
import edu.vanier.fire.util.email.ContactController;
import edu.vanier.fire.model.Contact;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Brandon
 */
public class EditContactController implements Initializable {

    @FXML
    private TableView<Contact> tvContacts;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtEmail;

    @FXML
    private Label lblInvalid;

    @FXML
    private Label lblEmpty;

    @FXML
    private TableColumn<Contact, String> name;

    @FXML
    private TableColumn<Contact, String> email;

    private HashMap<Integer, Contact> contactMap;
    public ObservableList<Contact> list;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        list = FXCollections.observableArrayList();

        contactMap = new ContactController().getAllContacts();

        for (Integer key : contactMap.keySet()) {
            Contact contactTemp = contactMap.get(key);
            list.add(contactTemp);
        }
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        tvContacts.setItems(list);
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
    public void handleAdd(ActionEvent event) {
        String nameTemp = txtName.getText();
        String emailTemp = txtEmail.getText();

        Contact contactNew = new Contact(nameTemp, emailTemp);

        if (nameTemp.isEmpty() || emailTemp.isEmpty()) {
            lblInvalid.setVisible(false);
            lblEmpty.setVisible(true);
        } else if (isEmailValid(emailTemp)) {
            new ContactController().insert(contactNew);
            setTableView();
            txtName.clear();
            txtEmail.clear();
            lblInvalid.setVisible(false);
            lblEmpty.setVisible(false);
        } else {
            lblEmpty.setVisible(false);
            lblInvalid.setVisible(true);
        }
    }

    @FXML
    public void handleRemove(ActionEvent event) {
        Contact contactSelct = tvContacts.getSelectionModel().getSelectedItem();

        if (contactSelct != null) {
            new ContactController().delete(contactSelct);
            setTableView();
        }
    }

    public void setTableView() {
        list.clear();
        contactMap = new ContactController().getAllContacts();

        for (Integer key : contactMap.keySet()) {
            Contact contactTemp = contactMap.get(key);
            list.add(contactTemp);
        }
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        tvContacts.setItems(list);
    }

    /**
     * method to check if email is valid format
     *
     * @param email the email to check
     * @return if its well formated or not
     */
    public boolean isEmailValid(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }
}
