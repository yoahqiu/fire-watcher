/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.vanier.fire;

import java.io.IOException;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class NotificationPopup extends Stage {
    public NotificationPopup(Stage owner) throws IOException {
        this.initOwner(owner);
        this.initModality(Modality.APPLICATION_MODAL);
        
        Loader loader = new Loader();
        loader.setAsNotif();
        Parent root = loader.getLoader().load();
        
        Scene scene = new Scene(root);
        
        this.setTitle("Notifications");
        Image icon = new Image(getClass().getResourceAsStream("fxml/data/aboutIcon.png"));
        this.getIcons().add(icon);
        this.setResizable(false);
        this.setScene(scene);
    }
}
