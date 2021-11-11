package edu.vanier.fire;

import java.io.IOException;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AboutPopup extends Stage {
    public AboutPopup(Stage owner) throws IOException {
        this.initOwner(owner);
        this.initModality(Modality.APPLICATION_MODAL);
        
        Loader loader = new Loader();
        loader.setAsAbout();
        Parent root = loader.getLoader().load();
        Scene scene = new Scene(root);
        
        this.setTitle("About Fire Watcher");
        Image icon = new Image(getClass().getResourceAsStream("fxml/data/aboutIcon.png"));
        this.getIcons().add(icon);
        this.setResizable(false);
        this.setScene(scene);
    }
}
