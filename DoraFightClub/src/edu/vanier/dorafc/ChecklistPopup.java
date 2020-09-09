package edu.vanier.dorafc;

import com.lynden.gmapsfx.javascript.object.LatLongBounds;
import edu.vanier.dorafc.ctrl.ContactPopupController;
import java.io.IOException;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author aweso
 */
public class ChecklistPopup extends Stage {
    
    public ChecklistPopup(Stage owner) throws IOException {
        this.initOwner(owner);
        this.initModality(Modality.APPLICATION_MODAL);
        
        Loader loader = new Loader();
        loader.setAsChecklist();
        Parent root = loader.getLoader().load();
        
        Scene scene = new Scene(root);
        
        this.setTitle("Checklist");
        Image icon = new Image(getClass().getResourceAsStream("fxml/data/aboutIcon.png"));
        this.getIcons().add(icon);
        this.setResizable(false);
        this.setScene(scene);
    }
    
}
