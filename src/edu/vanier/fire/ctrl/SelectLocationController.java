/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.vanier.fire.ctrl;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.event.GMapMouseEvent;
import com.lynden.gmapsfx.javascript.event.UIEventType;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.Marker;
import com.lynden.gmapsfx.javascript.object.MarkerOptions;
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
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author brandon
 */
public class SelectLocationController implements Initializable, MapComponentInitializedListener {

    final private String mapKey = "AIzaSyBjCe0RtBkpcjIUKgx36r1-0YVqq17aTm0";

    private GoogleMap map;
    private Marker marker;
    public LatLong locationLatLong;

    @FXML
    private GoogleMapView mapView;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mapView.setKey(mapKey);
        mapView.addMapInializedListener(this);
//        setHandle();
    }

    @Override
    public void mapInitialized() {

        //Set the initial properties of the map.
        MapOptions mapOptions = new MapOptions();

        InfoController infoController = new InfoController();
        PersonalInfo personalInfo = infoController.getInfo();
        double lat = personalInfo.getLat();
        double lon = personalInfo.getLon();
        LatLong latLong;
        
        if (lat != Double.NaN) {
            latLong = new LatLong(lat, lon);
            locationLatLong = latLong;
            marker = new Marker(new MarkerOptions().position(latLong));
        }
        else
            latLong = new LatLong(45.501167, -73.650230);
        
        mapOptions.center(latLong);
        mapOptions.overviewMapControl(false);
        mapOptions.panControl(false);
        mapOptions.rotateControl(false);
        mapOptions.scaleControl(false);
        mapOptions.streetViewControl(false);
        mapOptions.zoomControl(true);
        mapOptions.zoom(10);

        map = mapView.createMap(mapOptions);
        map.addMarker(marker);
        setHandle();
    }

    @FXML
    private void handleSelect(ActionEvent event) throws IOException {
        InfoController infoController = new InfoController();
        
        PersonalInfo personalInfo = infoController.getInfo();
        personalInfo.setLat(locationLatLong.getLatitude());
        personalInfo.setLon(locationLatLong.getLongitude());
        infoController.delete();
        infoController.insert(personalInfo);
        
        Loader loader = new Loader();
        loader.setAsPersonalInfo();
        Parent root = loader.getLoader().load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

    @FXML
    private void handleCancel(ActionEvent event) throws IOException {
        Loader loader = new Loader();
        loader.setAsPersonalInfo();
        Parent root = loader.getLoader().load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }
    
    /**
     * method to set up every click on the map
     */
    public void setHandle() {
        MarkerOptions markerOptions = new MarkerOptions();
       
        map.addMouseEventHandler(UIEventType.click, (GMapMouseEvent mouseEvent) -> {
            map.removeMarker(marker);
            locationLatLong = mouseEvent.getLatLong();
            marker.setPosition(locationLatLong);
            map.addMarker(marker);
        });
    }
}
