/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.vanier.fire.ctrl;

import com.lynden.gmapsfx.javascript.object.LatLong;
import edu.vanier.fire.model.CountryContactModel;
import edu.vanier.fire.util.network.RESTAPIController;
import edu.vanier.fire.util.parser.CountryContactParser;
import edu.vanier.fire.util.parser.GeoCodingParser;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.collections.FXCollections;

/**
 * FXML Controller class
 *
 * @author DoraFC
 */
public class ContactPopupController implements Initializable {

    private LatLong center;
    private ArrayList<CountryContactModel> numbers;
    private HashMap<String, CountryContactModel> contacts;
    
    @FXML
    private TableView tblContact;
    @FXML
    private TableColumn<String, CountryContactModel> colCountry;
    @FXML
    private TableColumn<String, CountryContactModel> colEmergency;
    @FXML
    private TableColumn<String, CountryContactModel> colPolice;
    @FXML
    private TableColumn<String, CountryContactModel> colAmbulance;
    
    private final ObservableList<CountryContactModel> list = FXCollections.observableArrayList(new ArrayList<CountryContactModel>());
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colCountry.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmergency.setCellValueFactory(new PropertyValueFactory<>("emergency"));
        colPolice.setCellValueFactory(new PropertyValueFactory<>("police"));
        colAmbulance.setCellValueFactory(new PropertyValueFactory<>("ambulance"));
        
        tblContact.setItems(list);
        CountryContactParser parser = new CountryContactParser();
        
        contacts = parser.parse();
    }    

    public void setCenter(LatLong center) {
        this.center = center;

        boolean isEmpty = true;
        String country = findCountry().toLowerCase();
        for (String key : contacts.keySet()) {
            if (contacts.get(key).getName().toLowerCase().contains(country)) {
                list.add(contacts.get(key));
                isEmpty = false;
            }
        }
        
        if (isEmpty) {
            list.add(new CountryContactModel("No country found"));
        }
        
        tblContact.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }
    
    public void handleBack(ActionEvent event) {
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }
    
    /**
     * method to find the country
     * @return the country name
     */
    public String findCountry() {
        final String KEY = "db8cce1f9b1244d1a97404d29c6e0ff8";
        String url = String.format(GeoCodingParser.HEAD_LINK, center.getLatitude(), center.getLongitude(), KEY);
        
        RESTAPIController controller = new RESTAPIController();
        String response = controller.getJSon(url);
        
        return GeoCodingParser.getCountry(response);
        
    }
}
