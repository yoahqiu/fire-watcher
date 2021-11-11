package edu.vanier.fire;

import com.lynden.gmapsfx.javascript.object.LatLong;
import edu.vanier.fire.ctrl.ContactPopupController;
import java.io.IOException;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ContactPopup extends Stage {

    public ContactPopup(LatLong center, Stage owner) throws IOException {
        this.initOwner(owner);
        this.initModality(Modality.APPLICATION_MODAL);
        
        Loader loader = new Loader();
        loader.setAsContacts();
        Parent root = loader.getLoader().load();
        ((ContactPopupController) loader.getLoader().getController()).setCenter(center);
        
        Scene scene = new Scene(root);
        
        this.setTitle("Contacts");
        Image icon = new Image(getClass().getResourceAsStream("fxml/data/aboutIcon.png"));
        this.getIcons().add(icon);
        this.setResizable(false);
        this.setScene(scene);
    }
}
