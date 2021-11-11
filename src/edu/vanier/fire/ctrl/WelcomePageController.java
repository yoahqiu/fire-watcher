package edu.vanier.fire.ctrl;

import edu.vanier.fire.Loader;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author doraFC
 */
public class WelcomePageController implements Initializable {
    
    @FXML
    private Button btnWelcome;
    @FXML
    private Button btnExit;
    
    public Stage stage;
    
    @FXML
    private void handleWelcome(ActionEvent event) throws IOException {
        System.out.println("Loading main scene");
        
        Loader loader = new Loader();
        loader.setAsMainPage();
        Parent root = loader.getLoader().load();
        Scene scene = new Scene(root);
        ((MainPageController) loader.getLoader().getController()).setStage(stage);
        stage.setScene(scene);
    }
    
    @FXML
    private void handleExit(ActionEvent event) {
        System.out.println("Exiting program");
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Fire Watcher");
        alert.setHeaderText("Are you sure you want to exit?");
        alert.setContentText(null);
        
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK)
            Platform.exit();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
